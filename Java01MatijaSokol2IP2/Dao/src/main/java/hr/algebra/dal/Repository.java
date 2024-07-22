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

//    //Actors
//    int createActor(Actor actor) throws Exception;
//
//    void createActors(List<Actor> actors) throws Exception;
//
//    void updateActor(int id, Actor actor) throws Exception;
//
//    void deleteActor(int id) throws Exception;
//
//    Optional<Actor> selectActor(int id) throws Exception;
//
//    List<Actor> selectActors() throws Exception;
//
//    //Genre
//    int createGenre(Genre genre) throws Exception;
//
//    void createGenres(List<Genre> genres) throws Exception;
//
//    void updateGenre(int id, Genre genre) throws Exception;
//
//    void deleteGenre(int id) throws Exception;
//
//    Optional<Genre> selectGenre(int id) throws Exception;
//
//    List<Genre> selectGenres() throws Exception;
//
//    //Director
//    int createDirector(Director director) throws Exception;
//
//    void createDirectors(List<Director> directors) throws Exception;
//
//    void updateDirector(int id, Director director) throws Exception;
//
//    void deleteDirector(int id) throws Exception;
//
//    Optional<Director> selectDirector(int id) throws Exception;
//
//    List<Director> selectDirectors() throws Exception;
}
