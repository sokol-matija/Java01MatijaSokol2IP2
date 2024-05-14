package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Actor;
import hr.algebra.model.Movie;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class SqlRepository implements Repository {

    private static final String ID_MOVIE = "IDMovie";
    private static final String TITLE = "Title";
    private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String DESCRIPTION = "Description";
    private static final String LINK = "Link";
    private static final String YEAR = "Year";
    private static final String PICTURE_PATH = "PicturePath";
    private static final String RATING = "Rating";
    private static final String TYPE = "Type";
    private static final String DIRECTOR_ID = "DirectorID";
    private static final String ACTOR_IDS = "ActorIDs";

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
            stmt.setString(PICTURE_PATH, movie.getPicturePath());
            stmt.setString(PUBLISHED_DATE, movie.getPublishedDate().format(Movie.DATE_FORMATTER));
            stmt.setInt(YEAR, movie.getYear());
            stmt.setDouble(RATING, movie.getRating());
            stmt.setString(TYPE, movie.getType());

            stmt.setInt(DIRECTOR_ID, movie.getDirector().getId());

            List<Integer> actorIds = new ArrayList<>();
            for (Actor actor : movie.getActors()) {
                actorIds.add(actor.getId());
            }
            stmt.setArray(ACTOR_IDS, con.createArrayOf("INTEGER", actorIds.toArray()));

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
            stmt.setString(PICTURE_PATH, movie.getPicturePath());
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
            stmt.setArray(ACTOR_IDS, con.createArrayOf("INTEGER", actorIds.toArray()));

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

    @Override
    public List<Movie> selectMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIES)) {
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

}
