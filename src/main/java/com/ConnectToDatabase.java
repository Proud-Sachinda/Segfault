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
                    .getConnection("jdbc:postgresql://ec2-54-75-245-196.eu-west-1.compute.amazonaws.com:5432/d78v0ec8r1pino?sslmode=require","ockwjgxlnqiboa" ,"f0d9966767f07b2031f1d942c128cf3e16e2fd93d575c640f735bca0ac8b3681");

        } catch (SQLException e) {

            // second attempt
            try {
                connection = DriverManager
                        .getConnection("jdbc:postgresql://ec2-54-75-245-196.eu-west-1.compute.amazonaws.com:5432/d78v0ec8r1pino?sslmode=require","ockwjgxlnqiboa" ,"f0d9966767f07b2031f1d942c128cf3e16e2fd93d575c640f735bca0ac8b3681");
            } catch (SQLException ex) {

                // third attempt
                try {
                    connection = DriverManager
                            .getConnection("jdbc:postgresql://ec2-54-75-245-196.eu-west-1.compute.amazonaws.com:5432/d78v0ec8r1pino?sslmode=require","ockwjgxlnqiboa" ,"f0d9966767f07b2031f1d942c128cf3e16e2fd93d575c640f735bca0ac8b3681");
                } catch (SQLException exc) {
                    exc.printStackTrace();
                }
            }
        }

        return connection;
    }
}
