package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.CustomGenre;
import hr.algebra.model.Genre;
import hr.algebra.model.Movie;
import hr.algebra.model.Person;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
    private static final String PICTURE_PATH = "PicturePath";
    //private static final String LINK = "Link";
    //private static final String DATE_PLAYING = "DatePlaying";
    //TODO: Add genre to database createmovie
    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie (?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String DELETE_MOVIE = "{CALL deleteMovie (?)}";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?)}";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";
    //TODO: Delete movie
    //TODO: Update movie

    private static final String CREATE_USER = "{ CALL createUser (?,?,?) }";

    public void createUser(String username, String password) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_USER)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setInt(3, 1); // Assuming 2 is the ID for the USER role

            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("Username already exists")) {
                throw new Exception("Username already exists. Please choose a different username.");
            } else {
                throw e; // Re-throw other SQL exceptions
            }
        }
    }

    @Override
    public int createMovie(Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            stmt.setString(TITLE, movie.getTitle());
            ZonedDateTime publishedDate = movie.getPublishedDate();
            String formattedPublishedDate = publishedDate.format(Movie.DATE_FORMATTER);
            stmt.setString(PUBLISHED_DATE, formattedPublishedDate);
            /*
            if (movie.getPublishedDate().toString().endsWith("Z")) {
                publishedDate = ZonedDateTime.parse(movie.getPublishedDate().toString());
            } else {
                publishedDate = ZonedDateTime.parse(movie.getPublishedDate().toString() + ":00Z");
            }
             */
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

            List<Genre> genres = movie.getGenres();
            String genresString = genres == null ? "" : genres.stream()
                    .filter(Objects::nonNull)
                    .map(genre -> genre == Genre.OTHER ? CustomGenre.getCustomGenres().stream().findFirst().orElse("Other") : genre.name())
                    .collect(Collectors.joining(","));
            stmt.setString(GENRES, genresString);

            stmt.setString(IMAGE_LINK, movie.getImageLink());
            stmt.setInt(RATING, movie.getRating());
            stmt.setString(TYPE, movie.getType());
            stmt.setString(PICTURE_PATH, movie.getPicturePath());
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

                ZonedDateTime publishedDate = movie.getPublishedDate();
                String formattedPublishedDate = publishedDate.format(Movie.DATE_FORMATTER);
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
                        .map(genre -> genre == Genre.OTHER
                        ? CustomGenre.getCustomGenres().stream().findFirst().orElse("Other")
                        : genre.name())
                        .collect(Collectors.joining(","));
                stmt.setString(GENRES, genresString);

                stmt.setString(IMAGE_LINK, movie.getImageLink());
                stmt.setInt(RATING, movie.getRating());
                stmt.setString(TYPE, movie.getType());
                stmt.setString(PICTURE_PATH, movie.getPicturePath());
                stmt.registerOutParameter(ID_MOVIE, Types.INTEGER);
                stmt.executeUpdate();
                //TODO: Is this redundant? - Clear the param for next iter
                stmt.clearParameters();
            }
        }
    }

    @Override
    public void updateMovie(int id, Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {
            stmt.setInt(ID_MOVIE, id);
            stmt.setString(TITLE, movie.getTitle());

            String dateString = movie.getPublishedDate().toString().trim();
            System.out.println("Original date string = " + dateString);
            ZonedDateTime publishedDate;
            if (dateString.endsWith("Z")) {
                publishedDate = ZonedDateTime.parse(dateString);
            } else {
                String completeDate = dateString + ":00:00Z";
                publishedDate = ZonedDateTime.parse(completeDate);
            }
            System.out.println("Parsed ZonedDateTime = " + publishedDate);
            String formattedPublishedDate = publishedDate.format(DateTimeFormatter.RFC_1123_DATE_TIME);
            System.out.println("Formatted PublishedDate = " + formattedPublishedDate);

            stmt.setString(PUBLISHED_DATE, formattedPublishedDate);
            stmt.setString(DESCRIPTION, movie.getDescription());
            stmt.setString(ORIGINAL_TITLE, movie.getOriginalTitle());
            stmt.setString(DIRECTOR, movie.getDirector().toString());

            String actorsString = movie.getActors().stream()
                    .map(Person::toString)
                    .collect(Collectors.joining(","));
            stmt.setString(ACTORS, actorsString);

            stmt.setInt(DURATION, movie.getDuration());
            stmt.setInt(YEAR, movie.getYear());

            String genresString = movie.getGenres().stream()
                    .map(Genre::name)
                    .collect(Collectors.joining(","));
            stmt.setString(GENRES, genresString);

            stmt.setString(IMAGE_LINK, movie.getImageLink());
            stmt.setInt(RATING, movie.getRating());
            stmt.setString(TYPE, movie.getType());
            stmt.setString(PICTURE_PATH, movie.getPicturePath());
            System.out.println("Picture_path = " + movie.getPicturePath());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
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
                            publishedDate,
                            rs.getString(DESCRIPTION),
                            rs.getString(ORIGINAL_TITLE),
                            director,
                            actors,
                            rs.getInt(DURATION),
                            rs.getInt(YEAR),
                            genres,
                            rs.getString(IMAGE_LINK),
                            rs.getInt(RATING),
                            rs.getString(TYPE),
                            rs.getString(PICTURE_PATH)
                    ));
                }
            }

        }
        return Optional.empty();
    }

    @Override
    public List<Movie> selectMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIES); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String directorName = rs.getString(DIRECTOR);
                String actorsString = rs.getString(ACTORS);
                String genresString = rs.getString(GENRES);

                Person director = new Person(directorName);

                ZonedDateTime publishedDate = ZonedDateTime.parse(rs.getString(PUBLISHED_DATE), DateTimeFormatter.RFC_1123_DATE_TIME);

                List<Person> actors = Arrays.stream(actorsString.split(","))
                        .map(String::trim)
                        .map(Person::new)
                        .collect(Collectors.toList());

                List<Genre> genres = Arrays.stream(genresString.split(","))
                        .map(String::trim)
                        .map(Genre::safeValueOf)
                        .collect(Collectors.toList());

                movies.add(new Movie(
                        rs.getInt(ID_MOVIE),
                        rs.getString(TITLE),
                        publishedDate,
                        rs.getString(DESCRIPTION),
                        rs.getString(ORIGINAL_TITLE),
                        director,
                        actors,
                        rs.getInt(DURATION),
                        rs.getInt(YEAR),
                        genres,
                        rs.getString(IMAGE_LINK),
                        rs.getInt(RATING),
                        rs.getString(TYPE),
                        rs.getString(PICTURE_PATH)
                ));
            }
        }
        return movies;
    }
}
