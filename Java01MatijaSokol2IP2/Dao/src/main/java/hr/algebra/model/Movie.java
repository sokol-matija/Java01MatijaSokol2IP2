package hr.algebra.model;

import java.time.format.DateTimeFormatter;

public class Movie {

    //Mon, 13 May 2024 22:00:00 GMT
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME;

    private int id;
    private String title;
    //private LocalDateTime publishedDate;
    private String description;
    private String originalTitle;
    //private Director director;
    //private List<Actor> actors;
    private int duration;
    private int year;
    //private List<String> genres;
    private String imageLink;
    private int rating;
    private String type;
    //private String picturePath;

    public Movie() {
    }

    public Movie(String title, String description, String originalTitle, int duration, int year, String imageLink, int rating, String type) {
        this.title = title;
        this.description = description;
        this.originalTitle = originalTitle;
        this.duration = duration;
        this.year = year;
        this.imageLink = imageLink;
        this.rating = rating;
        this.type = type;
    }

    public Movie(int id, String title, String description, String originalTitle, int duration, int year, String imageLink, int rating, String type) {
        this(title, description, originalTitle, duration, year, imageLink, rating, type);
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
        return "id=" + id + ", " + title + ", " + shortDescription + ", " + originalTitle + ", " + duration + ", " + year + ", " + imageLink + ", " + rating + ", " + type;
    }

}
