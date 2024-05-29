package hr.algebra.parsers;

import java.util.Optional;

public class MovieParser {

    private MovieParser() {
    }

    private enum TagType {
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
        GUID("guid"),
        REZERVACIJA("rezervacija"),
        DATUM_PRIKAZIVANJA("datumprikazivanja"),
        PREDSTAVE("predstave"),
        SORT("sort"),
        TRAILER("trailer");

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
}
