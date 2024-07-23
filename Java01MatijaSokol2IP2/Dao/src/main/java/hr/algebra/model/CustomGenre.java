package hr.algebra.model;

import java.util.HashSet;
import java.util.Set;

public class CustomGenre {

    private static final Set<String> customGenres = new HashSet<>();

    public static void addCustomGenre(String genreName) {
        customGenres.add(genreName);
    }

    public static boolean isCustomGenre(String genreName) {
        return customGenres.contains(genreName);
    }

    public static Set<String> getCustomGenres() {
        return new HashSet<>(customGenres);
    }
}
