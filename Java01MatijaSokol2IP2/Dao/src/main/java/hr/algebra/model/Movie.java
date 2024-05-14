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
    private List<Genre> genres
    private String picturePath;

}
