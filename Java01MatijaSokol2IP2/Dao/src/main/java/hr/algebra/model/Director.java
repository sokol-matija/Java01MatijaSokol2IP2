package hr.algebra.model;

public class Director {

    private String FirstName;
    private String LastName;

    public Director(String FirstName, String LastName) {
        getFirstName();
        getLastName();
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
