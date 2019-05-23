package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDatabase {

    // remote strings
    private static final String REMOTE_URL =
            "jdbc:postgresql://qbanksd.postgres.database.azure.com:5432/qbank";
    private static final String REMOTE_USER = "postgres@qbanksd";
    private static final String REMOTE_PWD = "Bullsh1t";

    // local strings
    private static final String LOCAL_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOCAL_USER = "postgres";
    private static final String LOCAL_PWD = "postgres";

    public static Connection getConnection() {

        Connection connection = null;

        // first attempt
        try {
            connection = DriverManager
                    .getConnection(REMOTE_URL, REMOTE_USER,REMOTE_PWD);

        } catch (SQLException e) {

            // second attempt
            try {
                connection = DriverManager
                        .getConnection(REMOTE_URL, REMOTE_USER,REMOTE_PWD);
            } catch (SQLException ex) {

                // third attempt
                try {
                    connection = DriverManager
                            .getConnection(REMOTE_URL, REMOTE_USER,REMOTE_PWD);
                } catch (SQLException exc) {
                    exc.printStackTrace();
                }
            }
        }

        return connection;
    }
}
