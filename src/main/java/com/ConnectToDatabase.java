package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDatabase {

    public static Connection getConnection() {

        Connection connection = null;

        // first attempt
        try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "postgres");

        } catch (SQLException e) {

            // second attempt
            try {
                connection = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/postgres",
                                "postgres", "postgres");
            } catch (SQLException ex) {

                // third attempt
                try {
                    connection = DriverManager
                            .getConnection("jdbc:postgresql://localhost:5432/postgres",
                                    "postgres", "postgres");
                } catch (SQLException exc) {
                    exc.printStackTrace();
                }
            }
        }

        return connection;
    }
}
