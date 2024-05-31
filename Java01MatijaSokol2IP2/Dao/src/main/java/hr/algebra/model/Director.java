package hr.algebra.model;

public class Director {

    private int id;
    private String FirstName;
    private String LastName;

    public Director(String FirstName, String LastName) {
        this.FirstName = FirstName;
        this.LastName = LastName;
    }

    public Director(int id, String FirstName, String LastName) {
        this(FirstName, LastName);
        setId(id);
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
        return "Director{" + "FirstName=" + FirstName + ", LastName=" + LastName + '}';
    }

}
