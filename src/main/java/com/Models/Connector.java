package com.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

    // database variables
    private final String url = "jdbc:postgresql://localhost:5432/qbank";
    private final String user = "postgres";
    private final String password = "postgres";

    // get connection to sql database
    public Connection get() {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            return null;
        }

        return connection;
    }
}
