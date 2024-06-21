package hr.algebra.model;

public class Person {
    // Tought: I dont really care fore first or last name just full name

    private int id;
    //private String firstName;
    //private String lastName;
    private String name;

    //TODO: #refactor Call smaller ctor
    public Person(int id, String name) {
        this.id = id;
        this.name = name;
        //this.firstName = FirstName;
        //this.lastName = LastName;
    }

    public Person(String name) {
        //this.firstName = firstName;
        //this.lastName = lastName;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /*
    public String getFirstName() {
    return firstName;
    }
    public void setFirstName(String FirstName) {
    this.firstName = FirstName;
    }
    public String getLastName() {
    return lastName;
    }
    public void setLastName(String LastName) {
    this.lastName = LastName;
    }
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
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
        final Person other = (Person) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Name= " + name;
    }

}
