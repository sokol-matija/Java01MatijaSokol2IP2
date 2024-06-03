package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Movie;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class SqlRepository implements Repository {

    private static final String ID_MOVIE = "IDMovie";
    private static final String TITLE = "Title";
    //private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String DESCRIPTION = "Description";
    private static final String ORIGINAL_TITLE = "OriginalTitle";
    //private static final String DIRECTOR = "Director";
    //private static final String ACTORS = "Actors";
    private static final String DURATION = "Duration";
    private static final String YEAR = "Year";
    //private static final String GENRES = "Genres";
    private static final String IMAGE_LINK = "ImageLink";
    private static final String RATING = "Rating";
    private static final String TYPE = "Type";
    //private static final String LINK = "Link";
    //private static final String DATE_PLAYING = "DatePlaying";

    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?,?,?,?)}";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?)}";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";

    @Override
    public int createMovie(Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            stmt.setString(TITLE, movie.getType());
            stmt.setString(DESCRIPTION, movie.getDescription());
            stmt.setString(ORIGINAL_TITLE, movie.getOriginalTitle());
            stmt.setInt(DURATION, movie.getDuration());
            stmt.setInt(YEAR, movie.getYear());
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
                stmt.setString(DESCRIPTION, movie.getDescription());
                stmt.setString(ORIGINAL_TITLE, movie.getOriginalTitle());
                stmt.setInt(DURATION, movie.getDuration());
                stmt.setInt(YEAR, movie.getYear());
                stmt.setString(IMAGE_LINK, movie.getImageLink());
                stmt.setInt(RATING, movie.getRating());
                stmt.setString(TYPE, movie.getType());
                stmt.registerOutParameter(ID_MOVIE, Types.INTEGER);
                stmt.executeUpdate();
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
                    return Optional.of(new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            rs.getString(DESCRIPTION),
                            rs.getString(ORIGINAL_TITLE),
                            rs.getInt(DURATION),
                            rs.getInt(YEAR),
                            rs.getString(IMAGE_LINK),
                            rs.getInt(RATING),
                            rs.getString(TYPE)));
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
                movies.add(new Movie(
                        rs.getInt(ID_MOVIE),
                        rs.getString(TITLE),
                        rs.getString(DESCRIPTION),
                        rs.getString(ORIGINAL_TITLE),
                        rs.getInt(DURATION),
                        rs.getInt(YEAR),
                        rs.getString(IMAGE_LINK),
                        rs.getInt(RATING),
                        rs.getString(TYPE)));
            }
        }
        return movies;
    }
}
