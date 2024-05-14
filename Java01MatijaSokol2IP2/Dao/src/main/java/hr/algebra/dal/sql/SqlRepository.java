package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Movie;
import java.util.List;
import java.util.Optional;

public class SqlRepository implements Repository {

    //TODO: Figure out user defined data type how to use
    private static final String ID_MOVIE = "IDMovie";
    private static final String TITLE = "Title";
    private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String DESCRIPTION = "Description";
    private static final String ORIGINAL_DAT = "OriginalDate";
    //private static final Director DIRECTOR = "Director";
    //private static final Actor ACTOR = "Actor";
    //private static final int DURATION = "Duration";
    private static final String LINK = "Link";
    private static final String YEAR = "Year";
    //private static final List<Genre> GENRES = "Genres";
    private static final String PICTURE_PATH = "PicturePath";
    private static final String RATIN = "Rating";
    private static final String TYPE = "Type";
    private static final String DIRECTOR_ID = "DirectorID";
    private static final String ACTOR_IDS = "ActorIDs";
    @Override
    public int createMovie(Movie movie) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
