package hr.algebra.model;

public class User {

    private String usrename;
    private String password;
    private Role role;

    public User(String usrename, String password, Role role) {
        this.usrename = usrename;
        this.password = password;
        this.role = role;
    }

    public String getUsrename() {
        return usrename;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

}
