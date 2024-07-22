package hr.algebra.service;

import hr.algebra.dal.Repository;
import hr.algebra.dal.RepositoryFactory;
import hr.algebra.dal.sql.DataSourceSingleton;
import hr.algebra.dal.sql.SqlRepository;
import hr.algebra.model.Role;
import hr.algebra.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class AuthService {

    public User authenticate(String username, String password) throws SQLException {
        String sql = "{call authenticateUser(?, ?, ?)}";

        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            // Set parameters for the callable statement
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.registerOutParameter(3, java.sql.Types.NVARCHAR);

            stmt.execute();

            String userRole = stmt.getString(3);
            if (userRole != null) {
                Role role;
                try {
                    role = Role.valueOf(userRole.toUpperCase());
                } catch (IllegalArgumentException e) {
                    // Handle the case where the role value is not valid
                    System.err.println("Invalid role value: " + userRole);
                    throw new SQLException("Invalid role value: " + userRole, e);
                }
                System.out.println("This is ok");
                return new User(username, password, role);
            }

            return null; // Return null or handle the case where no user is found

        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
            throw e; // Optionally rethrow the exception or handle it appropriately
        }
    }

    public boolean registerUser(String username, String password) throws SQLException {
        Repository repository = RepositoryFactory.getRepository();
        try {
            ((SqlRepository) repository).createUser(username, password);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
