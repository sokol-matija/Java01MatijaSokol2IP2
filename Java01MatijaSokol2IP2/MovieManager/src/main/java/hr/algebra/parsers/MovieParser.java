package hr.algebra.parsers;

import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MovieParser {

    private static final String RSS_URL = "https://www.blitz-cinestar-bh.ba/rss.aspx?id=2682";
    private static final String ATTRIBUTE_URL = "url";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";

    private static String cleanData(String data) {
        return data.replace("<![CDATA[", "").replace("]]>", "").trim();
    }

    private static String cleanDescriptionData(String data) {
        String cData = cleanData(data);
        cData = removeHtmlElements(cData);
        return cData;
    }

    private static String getImgURL(String cData) {
        Document document = Jsoup.parse(cData);
        Element img = document.select("img").first();
        if (img != null) {
            return img.attr("src");
        }
        return null;
    }

    private static String removeHtmlElements(String cData) {
        return Jsoup.parse(cData).text();
    }

    private MovieParser() {
    }

    private enum TagType {
        ITEM("item"),
        TITLE("title"),
        PUB_DATE("pubDate"),
        DESCRIPTION("description"),
        ORIGNAZIV("orignaziv"),
        REDATELJ("redatelj"),
        GLUMCI("glumci"),
        TRAJANJE("trajanje"),
        GODINA("godina"),
        ZANR("zanr"),
        PLAKAT("plakat"),
        RATING("rating"),
        VRSTA("vrsta"),
        LINK("link"),
        DATUM_PRIKAZIVANJA("datumprikazivanja");

        private final String name;

        private TagType(String name) {
            this.name = name;
        }

        private static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }

    public static List<Movie> parse() throws IOException, XMLStreamException {

        List<Movie> movies = new ArrayList<>();

        HttpURLConnection con = UrlConnectionFactory.getHttpURLConnection(RSS_URL);
        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);

            StartElement startElement = null;
            Movie movie = null;
            Optional<TagType> tagType = Optional.empty();

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);
                        if (tagType.isPresent() && tagType.get().equals(TagType.ITEM)) {
                            movie = new Movie();
                            movies.add(movie);
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        String data = event.asCharacters().getData();
                        if (tagType.isPresent() && movie != null) {
                            switch (tagType.get()) {
                                case TITLE:
                                    if (!data.isBlank()) {

                                        movie.setTitle(cleanData(data));
                                    }
                                    break;
                                case PUB_DATE:
                                    if (!data.isBlank()) {
                                        movie.setPublishedDate(
                                                LocalDateTime.parse(data, DateTimeFormatter.RFC_1123_DATE_TIME)
                                        );
                                    }
                                    break;
                                //TODO: Add image to enum and Movie Class
                                case DESCRIPTION:
                                    if (!data.isBlank()) {
                                        String cData = cleanDescriptionData(data);
                                        movie.setDescription(cData);

                                        String imgURL = getImgURL(cData);
                                        movie.setImageLink(imgURL);
                                    }
                                    break;
                                case ORIGNAZIV:
                                    if (!data.isBlank()) {
                                        movie.setOriginalTitle(cleanData(data));
                                    }
                                    break;
                                //TODO: Make Readatelj parser
                                case REDATELJ:
                                    if (!data.isBlank()) {
                                        String cData = cleanData(data);
                                        String[] details = cData.split(" ");
                                        String firstName = details[0];
                                        StringBuilder sb = new StringBuilder();
                                        for (int i = 1; i < details.length; i++) {
                                            sb.append(details[i]);
                                        }
                                        String lastName = sb.toString();
                                        movie.setDirector(new Director(firstName, lastName));
                                    }
                                    break;
                                //TODO: Make Glumci parser
                                case GLUMCI:
                                    if (!data.isBlank()) {
                                        List<Actor> actorsList = new ArrayList<>();
                                        String cData = cleanData(data);
                                        String[] actorNames = cData.split(", ");
                                        for (String actor : actorNames) {
                                            String[] details = actor.split(" ");
                                            String firstName = details[0];
                                            if (details.length == 1) {
                                                actorsList.add(new Actor(firstName));
                                            }
                                            StringBuilder sb = new StringBuilder();
                                            for (int i = 1; i < details.length; i++) {
                                                sb.append(details[i]);
                                            }
                                            String lastName = sb.toString();
                                            actorsList.add(new Actor(firstName, lastName));
                                            movie.setActors(actorsList);
                                        }

                                    }
                                    break;
                                case TRAJANJE:
                                    if (!data.isBlank()) {
                                        movie.setDuration(Integer.parseInt(cleanData(data)));
                                    }
                                    break;
                                case GODINA:
                                    if (!data.isBlank()) {
                                        movie.setYear(Integer.parseInt(data));
                                    }
                                    break;
                                //TODO: Make Zanr parser

                                case ZANR:
                                    if (!data.isBlank()) {
                                        String cData = cleanData(data);
                                        List<String> genres = Arrays.asList(cData.split("\\s*,\\s*"));
                                        movie.setGenres(genres);
                                    }
                                    break;
                                case PLAKAT:
                                    if (!data.isBlank()) {
                                        String cDaga = cleanData(data);
                                        movie.setImageLink(cDaga);

                                    }
                                    break;
                                case RATING:
                                    if (!data.isBlank()) {
                                        movie.setRating(Integer.parseInt(data));
                                    }
                                    break;
                                case VRSTA:
                                    if (!data.isBlank()) {
                                        // blank
                                        // premijera
                                        // matineja
                                    }
                                    break;
                                //TODO: Link and GUID the same remove one
                                case LINK:
                                    if (!data.isBlank()) {
                                        movie.setLink(data);
                                    }
                                    break;
                                //TODO: Set date in model
                                case DATUM_PRIKAZIVANJA:
                                    if (!data.isBlank()) {
                                        movie.setDatePlaying(data);
                                    }
                                    break;
                                //TODO: Link buy tickest choose sitting and hours when show starts could be multiple
                                //<![CDATA[ <a href='https://karte.cinestarcinemas.ba/webticketnet/performance.aspx?oid=6F171000023PBWJEQC&coid=40000000014AKFBLFD'>16.55
                                //</a>, <a href='https://karte.cinestarcinemas.ba/webticketnet/performance.aspx?oid=1C171000023PBWJEQC&coid=40000000014AKFBLFD'>19.00</a>,
                                //<a href='https://karte.cinestarcinemas.ba/webticketnet/performance.aspx?oid=7C171000023PBWJEQC&coid=40000000014AKFBLFD'>21.55</a> ]]>
                                default:
                                    throw new AssertionError(tagType.get().name());

                            }
                        }
                }
            }
        }

        return movies;
    }
}
