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
                    .getConnection("jdbc:postgresql://qbanksd.postgres.database.azure.com:5432/qbank?sslmode=require","postgres@qbanksd," ,"Bullsh1t");

        } catch (SQLException e) {

            // second attempt
            try {
                connection = DriverManager
                        .getConnection("jdbc:postgresql://qbanksd.postgres.database.azure.com:5432/qbank?sslmode=require","postgres@qbanksd," ,"Bullsh1t");
            } catch (SQLException ex) {

                // third attempt
                try {
                    connection = DriverManager
                            .getConnection("jdbc:postgresql://qbanksd.postgres.database.azure.com:5432/qbank?sslmode=require","postgres@qbanksd," ,"Bullsh1t");
                } catch (SQLException exc) {
                    exc.printStackTrace();
                }
            }
        }

        return connection;
    }
}
