package hr.algebra.model;

public enum Genre {

    ACTION("Akcija"),
    ACTION2("Akcijski"),
    COMEDY("Komedija"),
    DRAMA("Drama"),
    CRIME_DRAMA("Krimi drama"),
    HORROR("Horor"),
    ROMANCE("Romance"),
    SCI_FI("SF"),
    DOCUMENTARY("Documentary"),
    ANIMATION("Animacija"),
    FANTASY("Fantasy"),
    THRILLER("Triler"),
    EPIC_WESTERN("Epski vestern");

    private final String displayName;

    private Genre(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Genre fromString(String text) {
        for (Genre genre : Genre.values()) {
            if (genre.displayName.equalsIgnoreCase(text)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("No constant with displayName " + text + " found");
    }
}
