package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Genre;
import hr.algebra.model.Movie;
import hr.algebra.model.Person;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.sql.DataSource;

public class SqlRepository implements Repository {

    private static final String ID_MOVIE = "IDMovie";
    private static final String TITLE = "Title";
    //TODO: add publish date
    private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String DESCRIPTION = "Description";
    private static final String ORIGINAL_TITLE = "OriginalTitle";
    private static final String DIRECTOR = "Director";
    private static final String ACTORS = "Actors";
    private static final String DURATION = "Duration";
    private static final String YEAR = "Year";
    private static final String GENRES = "Genres";
    private static final String IMAGE_LINK = "ImageLink";
    private static final String RATING = "Rating";
    private static final String TYPE = "Type";
    //private static final String LINK = "Link";
    //private static final String DATE_PLAYING = "DatePlaying";
    //TODO: Add genre to database createmovie
    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?)}";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";

    @Override
    public int createMovie(Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            stmt.setString(TITLE, movie.getTitle());
            ZonedDateTime publishedDate = ZonedDateTime.parse(movie.getPublishedDate() + ":00Z");
            String formattedPublishedDate = publishedDate.format(DateTimeFormatter.RFC_1123_DATE_TIME);
            stmt.setString(PUBLISHED_DATE, formattedPublishedDate);
            stmt.setString(DESCRIPTION, movie.getDescription());
            stmt.setString(ORIGINAL_TITLE, movie.getOriginalTitle());
            List<Person> actors = movie.getActors();
            String actorsString = actors.stream()
                    .map(Person::toString)
                    .collect(Collectors.joining(","));
            stmt.setString(ACTORS, actorsString);
            stmt.setString(DIRECTOR, movie.getDirector().toString());
            stmt.setInt(DURATION, movie.getDuration());
            stmt.setInt(YEAR, movie.getYear());
            String genresString = movie.getGenres().stream()
                    .map(Genre::name)
                    .collect(Collectors.joining(","));
            stmt.setString(GENRES, genresString);
            stmt.setString(IMAGE_LINK, movie.getImageLink());
            stmt.setInt(RATING, movie.getRating());
            stmt.setString(TYPE, movie.getType());
            stmt.registerOutParameter(ID_MOVIE, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(ID_MOVIE);
        }
    }

    @Override
    public void createMovies(List<Movie> movies) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {

            for (Movie movie : movies) {
                stmt.setString(TITLE, movie.getTitle());

                ZonedDateTime publishedDate = ZonedDateTime.parse(movie.getPublishedDate() + ":00Z");
                String formattedPublishedDate = publishedDate.format(DateTimeFormatter.RFC_1123_DATE_TIME);
                stmt.setString(PUBLISHED_DATE, formattedPublishedDate);

                stmt.setString(DESCRIPTION, movie.getDescription());
                stmt.setString(ORIGINAL_TITLE, movie.getOriginalTitle());
                List<Person> actors = movie.getActors();
                String actorsString = actors.stream()
                        .map(Person::toString)
                        .collect(Collectors.joining(","));
                stmt.setString(ACTORS, actorsString);
                stmt.setString(DIRECTOR, movie.getDirector().toString());
                stmt.setInt(DURATION, movie.getDuration());
                stmt.setInt(YEAR, movie.getYear());
                String genresString = movie.getGenres().stream()
                        .map(Genre::name)
                        .collect(Collectors.joining(","));
                stmt.setString(GENRES, genresString);
                stmt.setString(IMAGE_LINK, movie.getImageLink());
                stmt.setInt(RATING, movie.getRating());
                stmt.setString(TYPE, movie.getType());
                stmt.registerOutParameter(ID_MOVIE, Types.INTEGER);
                stmt.executeUpdate();
                //TODO: Is this redundant? - Clear the param for next iter
                stmt.clearParameters();
            }
        }
    }

    @Override
    public void updateMovie(int id, Movie data) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteMovie(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Movie> selectMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIE)) {
            stmt.setInt(ID_MOVIE, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    String directorName = rs.getString(DIRECTOR);
                    String actorsString = rs.getString(ACTORS);

                    Person director = new Person(directorName);

                    ZonedDateTime publishedDate = ZonedDateTime.parse(rs.getString(PUBLISHED_DATE), DateTimeFormatter.RFC_1123_DATE_TIME);

                    List<Person> actors = Arrays.stream(actorsString.split(","))
                            .map(String::trim)
                            .map(Person::new)
                            .collect(Collectors.toList());

                    List<Genre> genres = Arrays.stream(rs.getString(GENRES).split(","))
                            .map(String::trim)
                            .map(Genre::valueOf)
                            .collect(Collectors.toList());

                    return Optional.of(new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            publishedDate.toLocalDateTime(),
                            rs.getString(DESCRIPTION),
                            rs.getString(ORIGINAL_TITLE),
                            director,
                            actors,
                            rs.getInt(DURATION),
                            rs.getInt(YEAR),
                            genres,
                            rs.getString(IMAGE_LINK),
                            rs.getInt(RATING),
                            rs.getString(TYPE)
                    ));
                }
            }

        }
        return Optional.empty();
    }

    @Override
    public List<Movie> selectMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSourc = DataSourceSingleton.getInstance();
        try (Connection con = dataSourc.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIES); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                String directorName = rs.getString(DIRECTOR);
                String actorsString = rs.getString(ACTORS);

                Person director = new Person(directorName);

                ZonedDateTime publishedDate = ZonedDateTime.parse(rs.getString(PUBLISHED_DATE), DateTimeFormatter.RFC_1123_DATE_TIME);

                List<Person> actors = Arrays.stream(actorsString.split(","))
                        .map(String::trim)
                        .map(Person::new)
                        .collect(Collectors.toList());

                List<Genre> genres = Arrays.stream(rs.getString(GENRES).split(","))
                        .map(String::trim)
                        .map(Genre::valueOf)
                        .collect(Collectors.toList());

                movies.add(new Movie(
                        rs.getInt(ID_MOVIE),
                        rs.getString(TITLE),
                        publishedDate.toLocalDateTime(),
                        rs.getString(DESCRIPTION),
                        rs.getString(ORIGINAL_TITLE),
                        director,
                        actors,
                        rs.getInt(DURATION),
                        rs.getInt(YEAR),
                        genres,
                        rs.getString(IMAGE_LINK),
                        rs.getInt(RATING),
                        rs.getString(TYPE)
                ));
            }
        }
        return movies;
    }
}
