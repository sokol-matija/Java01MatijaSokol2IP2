package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Genre;
import hr.algebra.model.Movie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public abstract class SqlRepository implements Repository {

    private static final String ID_MOVIE = "IDMovie";
    private static final String TITLE = "Title";
    private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String DESCRIPTION = "Description";
    private static final String ORIGINAL_TITLE = "OriginalTitle";
    private static final String YEAR = "Year";
    private static final String PICTURE_PATH = "PicturePath";
    private static final String RATING = "Rating";
    private static final String TYPE = "Type";
    private static final String DIRECTOR_ID = "DirectorID";
    private static final String ACTOR_IDS = "ActorIDs";
    private static final String LINK = "Link";

    private static final String CREATE_MOVIE = "{ CALL CreateMovie (?,?,?,?,?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_MOVIE = "{ CALL UpdateMovie (?,?,?,?,?,?,?,?,?,?,?,?) }";
    private static final String DELETE_MOVIE = "{ CALL DeleteMovie (?) }";
    private static final String SELECT_MOVIE = "{ CALL SelectMovie (?) }";
    private static final String SELECT_ALL_MOVIES = "{ CALL SelectAllMovies }";

    private static final String CREATE_ACTOR = "{ CALL CreateActor (?,?) }";
    private static final String UPDATE_ACTOR = "{ CALL UpdateActor (?,?,?) }";
    private static final String DELETE_ACTOR = "{ CALL DeleteActor (?) }";
    private static final String SELECT_ACTOR = "{ CALL SelectActor (?) }";
    private static final String SELECT_ALL_ACTORS = "{ CALL SelectAllActors }";

    private static final String CREATE_GENRE = "{ CALL CreateGenre (?) }";
    private static final String UPDATE_GENRE = "{ CALL UpdateGenre (?,?) }";
    private static final String DELETE_GENRE = "{ CALL DeleteGenre (?) }";
    private static final String SELECT_GENRE = "{ CALL SelectGenre (?) }";
    private static final String SELECT_ALL_GENRES = "{ CALL SelectAllGenres }";

    private static final String CREATE_DIRECTOR = "{ CALL CreateDirector (?,?) }";
    private static final String UPDATE_DIRECTOR = "{ CALL UpdateDirector (?,?,?) }";
    private static final String DELETE_DIRECTOR = "{ CALL DeleteDirector (?) }";
    private static final String SELECT_DIRECTOR = "{ CALL SelectDirector (?) }";
    private static final String SELECT_ALL_DIRECTORS = "{ CALL SelectAllDirectors }";

    @Override
    public int createMovie(Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            stmt.setString(TITLE, movie.getTitle());
            stmt.setString(DESCRIPTION, movie.getDescription());
            stmt.setString(LINK, movie.getLink());
            stmt.setString(PUBLISHED_DATE, movie.getPublishedDate().format(Movie.DATE_FORMATTER));
            stmt.setInt(YEAR, movie.getYear());
            stmt.setDouble(RATING, movie.getRating());
            stmt.setString(TYPE, movie.getType());

            stmt.setInt(DIRECTOR_ID, movie.getDirector().getId());

            List<Integer> actorIds = new ArrayList<>();
            for (Actor actor : movie.getActors()) {
                actorIds.add(actor.getId());
            }
            //TODO: Fix error
            //stmt.setArray(ACTOR_IDS, con.createArrayOf("INTEGER", actorIds.toArray()));

            stmt.registerOutParameter(ID_MOVIE, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(ID_MOVIE);
        }
    }

    @Override
    public void createMovies(List<Movie> movies) throws Exception {
        for (Movie movie : movies) {
            createMovie(movie);
        }
    }

    @Override
    public void updateMovie(int id, Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {
            stmt.setInt(ID_MOVIE, id);
            stmt.setString(TITLE, movie.getTitle());
            stmt.setString(DESCRIPTION, movie.getDescription());
            stmt.setString(LINK, movie.getLink());
            stmt.setString(PUBLISHED_DATE, movie.getPublishedDate().format(Movie.DATE_FORMATTER));
            stmt.setInt(YEAR, movie.getYear());
            stmt.setDouble(RATING, movie.getRating());
            stmt.setString(TYPE, movie.getType());

            // Set director
            stmt.setInt(DIRECTOR_ID, movie.getDirector().getId());

            // Set actors (assuming there's a separate table for actors)
            List<Integer> actorIds = new ArrayList<>();
            for (Actor actor : movie.getActors()) {
                actorIds.add(actor.getId());
            }
            //TODO: Fix error
//            stmt.setArray(ACTOR_IDS, con.createArrayOf("INTEGER", actorIds.toArray()));

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {
            stmt.setInt(ID_MOVIE, id);
            stmt.executeUpdate();
        }
    }

    /*
//TODO Fix Movie Constructor
    @Override
    public List<Movie> selectMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ALL_MOVIES)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            LocalDateTime.parse(PUBLISHED_DATE, Movie.DATE_FORMATTER),
                            rs.getString(DESCRIPTION),
                            null, //TODO: Placeholder for Director object, will be set later
                            new ArrayList<>(), // TODO: Placeholder for actors list, will be set later
                            rs.getInt(YEAR),
                            new ArrayList<>(), // TODO: Placeholder for genres list, will be set later
                            rs.getString(PICTURE_PATH),
                            rs.getInt(RATING),
                            rs.getString(TYPE)
                    );

                    // TODO: Load actors and genres
                    // movie.setActors(fetchActorsForMovie(movie.getId()));
                    // movie.setGenres(fetchGenresForMovie(movie.getId()));
                    movies.add(movie);
                }
            }
        }
        return movies;
    }
     */
    @Override
    public int createActor(Actor actor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_ACTOR)) {
            stmt.setString(1, actor.getFirstName());
            stmt.setString(2, actor.getLastName());
            stmt.executeUpdate();
            // Assuming the ID of the newly created actor is generated by the database, you can retrieve it here if needed
            return 0; // Return the newly created actor's ID
        }
    }

    @Override
    public void createActors(List<Actor> actors) throws Exception {
        for (Actor actor : actors) {
            createActor(actor);
        }
    }

    @Override
    public void updateActor(int id, Actor actor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_ACTOR)) {
            stmt.setInt(1, id);
            stmt.setString(2, actor.getFirstName());
            stmt.setString(3, actor.getLastName());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteActor(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_ACTOR)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Actor> selectActor(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ACTOR)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Actor actor = new Actor(
                            rs.getInt("ID"), // Assuming column name for ID in the Actor table
                            rs.getString("FirstName"), // Assuming column name for FirstName in the Actor table
                            rs.getString("LastName") // Assuming column name for LastName in the Actor table
                    );
                    return Optional.of(actor);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public List<Actor> selectActors() throws Exception {
        List<Actor> actors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ALL_ACTORS)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Actor actor = new Actor(
                            rs.getInt("ID"), // Assuming column name for ID in the Actor table
                            rs.getString("FirstName"), // Assuming column name for FirstName in the Actor table
                            rs.getString("LastName") // Assuming column name for LastName in the Actor table
                    );
                    actors.add(actor);
                }
            }
        }
        return actors;
    }

    /*
    //TODO: Fix createGenre
    @Override
    public int createGenre(Genre genre) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_GENRE)) {
            stmt.setString(1, genre.getType());
            stmt.executeUpdate();
            // Assuming the ID of the newly created genre is generated by the database, you can retrieve it here if needed
            return 0; // Return the newly created genre's ID
        }
    }
     */

 /*
    @Override
    public void createGenres(List<Genre> genres) throws Exception {
        for (Genre genre : genres) {
            createGenre(genre);
        }
    }
     */
 /*
    @Override
    public void updateGenre(int id, Genre genre) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_GENRE)) {
            stmt.setInt(1, id);
            stmt.setString(2, genre.getType());
            stmt.executeUpdate();
        }
    }
     */
    @Override
    public void deleteGenre(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_GENRE)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Genre> selectGenre(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_GENRE)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Genre genre = new Genre(
                            rs.getInt("ID"), // Assuming column name for ID in the Genre table
                            rs.getString("Type") // Assuming column name for Type in the Genre table
                    );
                    return Optional.of(genre);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public List<Genre> selectGenres() throws Exception {
        List<Genre> genres = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ALL_GENRES)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Genre genre = new Genre(
                            rs.getInt("ID"), // Assuming column name for ID in the Genre table
                            rs.getString("Type") // Assuming column name for Type in the Genre table
                    );
                    genres.add(genre);
                }
            }
        }
        return genres;
    }

    @Override
    public int createDirector(Director director) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_DIRECTOR)) {
            stmt.setString(1, director.getFirstName());
            stmt.setString(2, director.getLastName());
            stmt.executeUpdate();
            // Assuming the ID of the newly created director is generated by the database, you can retrieve it here if needed
            return 0; // Return the newly created director's ID
        }
    }

    @Override
    public void createDirectors(List<Director> directors) throws Exception {
        for (Director director : directors) {
            createDirector(director);
        }
    }

    @Override
    public void updateDirector(int id, Director director) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_DIRECTOR)) {
            stmt.setInt(1, id);
            stmt.setString(2, director.getFirstName());
            stmt.setString(3, director.getLastName());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteDirector(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_DIRECTOR)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Director> selectDirector(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_DIRECTOR)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Director director = new Director(
                            rs.getInt("ID"), // Assuming column name for ID in the Directors table
                            rs.getString("FirstName"), // Assuming column name for FirstName in the Directors table
                            rs.getString("LastName") // Assuming column name for LastName in the Directors table
                    );
                    return Optional.of(director);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public List<Director> selectDirectors() throws Exception {
        List<Director> directors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ALL_DIRECTORS)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Director director = new Director(
                            rs.getInt("ID"), // Assuming column name for ID in the Directors table
                            rs.getString("FirstName"), // Assuming column name for FirstName in the Directors table
                            rs.getString("LastName") // Assuming column name for LastName in the Directors table
                    );
                    directors.add(director);
                }
            }
        }
        return directors;
    }

    @Override
    public Optional<Movie> selectMovie(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
