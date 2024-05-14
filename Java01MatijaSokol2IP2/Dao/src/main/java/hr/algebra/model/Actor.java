package hr.algebra.model;

public class Actor {

    private int id;
    private String FirstName;
    private String LastName;

    public Actor(int id, String FirstName, String LastName) {
        setId(id);
        setFirstName(FirstName);
        setLastName(LastName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    @Override
    public String toString() {
        return "Actor{" + "ID=" + id + " FirstName=" + FirstName + ", LastName=" + LastName + '}';
    }

}
