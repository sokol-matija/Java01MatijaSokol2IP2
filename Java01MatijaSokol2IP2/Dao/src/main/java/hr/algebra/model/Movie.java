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
    private int Type;

    public Movie(String title, LocalDateTime publishedDate, String description, String originalTitle, Director director, Actor actor, int duration, String link, int year, List<Genre> genres, String picturePath, int rating, int Type) {
        this.title = title;
        this.publishedDate = publishedDate;
        this.description = description;
        this.originalTitle = originalTitle;
        this.director = director;
        this.actor = actor;
        this.duration = duration;
        this.link = link;
        this.year = year;
        this.genres = genres;
        this.picturePath = picturePath;
        this.rating = rating;
        this.Type = Type;
    }

}
