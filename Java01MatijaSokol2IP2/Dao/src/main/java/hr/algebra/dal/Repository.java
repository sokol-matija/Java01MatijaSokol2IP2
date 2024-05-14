package hr.algebra.dal;

import hr.algebra.model.Movie;
import java.util.List;
import java.util.Optional;

public interface Repository {

    int createMovie(Movie movie) throws Exception;

    void createMovies(List<Movie> movies) throws Exception;

    void updateMovie(int id, Movie movies) throws Exception;

    void deleteMovie(int id) throws Exception;

    Optional<Movie> selectMovie(int id) throws Exception;

    List<Movie> selectMovies() throws Exception;
}
