package hr.algebra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Movie {

    //Mon, 13 May 2024 22:00:00 GMT
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME;

    private int id;
    private String title;
    private LocalDateTime publishedDate;
    private String description;
    private String originalTitle;
    private Director director;
    private Actor actor;
    private int duration;
    private String link;
    private int year;
    private List<Genre> genres;
    private String picturePath;
    private int rating;
    private String type;

    public Movie(String title, LocalDateTime publishedDate, String description, String originalTitle, Director director, Actor actor, int duration, String link, int year, List<Genre> genres, String picturePath, int rating, String type) {
        setTitle(title);
        setPublishedDate(publishedDate);
        setDescription(description);
        setOriginalTitle(originalTitle);
        setDirector(director);
        setActor(actor);
        setDuration(duration);
        setLink(link);
        setYear(year);
        setGenres(genres);
        setPicturePath(picturePath);
        setRating(rating);
        setType(type);

    }

    public Movie(int id, String title, LocalDateTime publishedDate, String description, String originalTitle, Director director, Actor actor, int duration, String link, int year, List<Genre> genres, String picturePath, int rating, String type) {
        this(title, publishedDate, description, originalTitle, director, actor, duration, link, year, genres, picturePath, rating, type);
        this.id = id;
    }

    public Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
