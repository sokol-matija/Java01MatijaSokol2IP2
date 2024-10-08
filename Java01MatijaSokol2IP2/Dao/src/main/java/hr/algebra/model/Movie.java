package hr.algebra.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Movie {

    //Mon, 13 May 2024 22:00:00 GMT
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME;

    private int id;
    private String title;
    private ZonedDateTime publishedDate;
    private String description;
    private String originalTitle;
    private Person director;
    private List<Person> actors;
    private int duration;
    private int year;
    private List<Genre> genres;
    private String imageLink;
    private int rating;
    private String type;
    private String picturePath;

    public Movie() {
    }

    public Movie(String title, ZonedDateTime publishedDate, String description, String originalTitle, Person director, List<Person> actors, int duration, int year, List<Genre> genres, String imageLink, int rating, String type, String picturePath) {
        this.title = title;
        this.publishedDate = publishedDate;
        this.description = description;
        this.originalTitle = originalTitle;
        this.director = director;
        this.actors = actors;
        this.duration = duration;
        this.year = year;
        this.genres = genres;
        this.imageLink = imageLink;
        this.rating = rating;
        this.type = type;
        this.picturePath = picturePath;
    }

    public Movie(int id, String title, ZonedDateTime publishedDate, String description, String originalTitle, Person director, List<Person> actors, int duration, int year, List<Genre> genres, String imageLink, int rating, String type, String picturePath) {
        this(title, publishedDate, description, originalTitle, director, actors, duration, year, genres, imageLink, rating, type, picturePath);
        this.id = id;
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

    public ZonedDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(ZonedDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

    public List<Person> getActors() {
        return actors;
    }

    public void setActors(List<Person> actors) {
        this.actors = actors;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movie other = (Movie) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        String shortDescription = description.length() > 20 ? description.substring(0, 20) : description;
        return "id=" + id + ", " + title + ", " + shortDescription + ", " + originalTitle + ", " + director + ", " + actors + ", " + duration + ", " + year + genres + ", " + imageLink + ", " + rating + ", " + type;
    }

    public String getGenresAsString() {
        return genresToString(this.genres);
    }

    private String genresToString(List<Genre> genres) {
        return genres.stream()
                .map(genre -> genre == Genre.OTHER
                ? CustomGenre.getCustomGenres().stream().findFirst().orElse("Other")
                : genre.displayName)
                .collect(Collectors.joining(", "));
    }

}
