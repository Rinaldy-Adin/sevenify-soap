package db;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class DB {
    private static final Connection connection = startConnection();

    private static Connection startConnection() {
        Dotenv dotenv = Dotenv.load();

        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://" + dotenv.get("DB_URL") + "/" + dotenv.get("MYSQL_DATABASE");
        String user = dotenv.get("MYSQL_USER");
        String password = dotenv.get("MYSQL_PASSWORD");

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public static Connection getConnection() {
        return DB.connection;
    }
}
