package com.Server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignInViewServer {

    private Connection connection;
    private String username;
    private String password;


    public SignInViewServer(Connection connection) {
        this.connection = connection;
    }

    public boolean authenticate() {
        boolean sign = false;

       // if (username.equals("username") && password.equals("password")) {
          try{
              // get database variables
              Statement statement = connection.createStatement();

              // query
              String query = "SELECT * FROM public.lecturer";

              // execute statement
              ResultSet set = statement.executeQuery(query);


              while(set.next()){
                //  username.setLecturerId(set.getString("lecturer_id"));
                  String lid = set.getString("lecturer_id");
                  sign = username.equals(lid);

              }

          }
          catch (SQLException e){
              e.printStackTrace();
          }

            return sign;


    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

