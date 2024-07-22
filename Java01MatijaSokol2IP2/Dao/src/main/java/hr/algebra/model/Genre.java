package hr.algebra.model;

import java.util.HashMap;
import java.util.Map;

public enum Genre {
    ACTION("Akcija"),
    ACTION2("Akcijski"),
    ACTION_COMEDY("Akcijska komedija"),
    ACTION_TRILER("akcijski triler"),
    ACTION_ADVENTURE("Akcijska avantura"),
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
    EPIC_WESTERN("Epski vestern"),
    CONCERT("Koncert"),
    OTHER("Other");

    public final String displayName;
    private static final Map<String, Genre> displayNameMap = new HashMap<>();

    static {
        for (Genre genre : values()) {
            displayNameMap.put(genre.displayName.toLowerCase(), genre);
        }
    }

    private Genre(String displayName) {
        this.displayName = displayName;
    }

    public static Genre fromString(String text) {
        return displayNameMap.getOrDefault(text.toLowerCase(), OTHER);
    }

    public static Genre safeValueOf(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            System.out.println("Custom genre encountered: " + name);
            CustomGenre.addCustomGenre(name);
            return OTHER;
        }
    }
}
