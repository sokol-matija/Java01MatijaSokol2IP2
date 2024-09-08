package hr.algebra.dal.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

public class DatabaseInitilizer {

    private static final String PATH = "/config/MoviesTest.sql";

    public static void runScript() {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection(); BufferedReader reader = new BufferedReader(new InputStreamReader(
                DatabaseInitilizer.class.getResourceAsStream(PATH)))) {

            if (reader == null) {
                throw new IOException("Resource not found: " + PATH);
            }

            StringBuilder sb = new StringBuilder();
            String line;
            Statement stmt = conn.createStatement();
            int statementCount = 0;
            while ((line = reader.readLine()) != null) {
                // Skip comments and empty lines
                if (line.trim().isEmpty() || line.trim().startsWith("--")) {
                    continue;
                }
                sb.append(line).append("\n");
                // Execute statement when encountering semicolon
                if (line.trim().endsWith("GO")) {
                    String sqlStatement = sb.toString().substring(0, sb.toString().length() - 3);
                    statementCount++;
                    executeStatement(stmt, sqlStatement);
                    sb = new StringBuilder();
                    // Recreate statement to prevent closed statement issue
                    stmt = conn.createStatement();
                }
            }
            // Execute the remaining statement if any
            if (sb.length() > 0) {
                String sqlStatement = sb.toString();
                statementCount++;
                executeStatement(stmt, sqlStatement);
            }

        } catch (SQLException | IOException e) {
        }
    }

    private static void executeStatement(Statement stmt, String sql) {
        try {
            stmt.execute(sql);
        } catch (SQLException e) {
        }
    }
}
