package hr.algebra.service;

import hr.algebra.dal.sql.DataSourceSingleton;
import hr.algebra.model.Role;
import hr.algebra.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

public class AuthService {

    private Map<String, User> user = new HashMap<>();

    public AuthService() {
        user.put("admin", new User("admin", "admin", Role.ADMIN));
    }

    public User authenticate(String username, String password) {
        String sql
                = "select u.Username, u.Password, roles.Name"
                + "from users as u"
                + "join roles on u.Role_id = roles.IDRoles"
                + "where u.Username = ? and u.Password = ?";

        DataSource dataSource = DataSourceSingleton.getInstance();
        //TODO: Make a prepared statement into a callabe statement
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String userRole = rs.getString("role");
                    Role role = Role.valueOf(userRole.toUpperCase());
                    return new User(username, password, role);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Authentication failed
    }
}
