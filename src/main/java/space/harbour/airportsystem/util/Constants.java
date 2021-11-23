package space.harbour.airportsystem.util;

import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Data
public class Constants {
    static private final String PASSWORD = "dimash";
    static private final String USERNAME = "postgres";
    static private final String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static String[] COLUMN_HEADERS = {"departurecity", "departuredate", "arrivalcity", "arrivaldate"};

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}
