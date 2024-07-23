package hr.algebra.dal;

import hr.algebra.model.Movie;
import java.util.List;
import java.util.Optional;

public interface Repository {
    //TODO: Separate in dif inferface for each table:

    void createUser(String username, String password) throws Exception;

    int createMovie(Movie movie) throws Exception;

    void createMovies(List<Movie> movies) throws Exception;

    void updateMovie(int id, Movie data) throws Exception;

    void deleteMovie(int id) throws Exception;

    Optional<Movie> selectMovie(int id) throws Exception;

    List<Movie> selectMovies() throws Exception;

    void addFavoriteMovie(String username, int movieId) throws Exception;

    void removeFavoriteMovie(String username, int movieId) throws Exception;

    List<Movie> selectFavoriteMovies(String username) throws Exception;

    boolean isMovieInFavorites(int movieId) throws Exception;

    void deleteAllMovies() throws Exception;
}
