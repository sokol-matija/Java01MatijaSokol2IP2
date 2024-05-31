package hr.algebra.model;

import java.util.Arrays;
import java.util.List;

public class Genre {

    public int id;
    public List<String> type;

    public Genre(String... type) {
        this.type = Arrays.asList(type);
    }

    public Genre(int id, String... type) {
        this(type);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Genre{" + "id=" + id + ", type=" + type + '}';
    }
}
