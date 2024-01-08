import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {

    private Connection connection = null;

    public ConnectDB() {
        Dotenv dotenv = Dotenv.load();

        String username = dotenv.get("DB_USERNAME");
        String password = dotenv.get("DB_PASSWORD");

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/battle_of_the_gods", username, password);

            if (connection == null) {
                throw new RuntimeException("DB Connection failed");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
