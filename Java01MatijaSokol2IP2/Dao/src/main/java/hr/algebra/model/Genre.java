package hr.algebra.model;

public class Genre {

    public int id;
    public String type;

    public Genre(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Genre{" + "id=" + id + ", type=" + type + '}';
    }

}
