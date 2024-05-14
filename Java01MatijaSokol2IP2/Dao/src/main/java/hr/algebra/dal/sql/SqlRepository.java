package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie (?,?,?,?,?,?,?,?,?,?) }";
    private static final String DELETE_MOVIE = "{ CALL deleteMovie (?) }";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?) }";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";

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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updateMovies(int id, Movie movies) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteMovies(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Movie> selectMovie(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Movie> selectMovies() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
