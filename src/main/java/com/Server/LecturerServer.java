package com.Server;

import com.CookieHandling.CookieAge;
import com.CookieHandling.CookieHandling;
import com.CookieHandling.CookieName;
import com.Objects.LecturerItem;

import javax.servlet.http.Cookie;
import javax.validation.constraints.NotNull;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LecturerServer {

    // connection variable
    private Connection connection;

    public LecturerServer(@NotNull Connection connection) {

        // initialise connection variable
        this.connection = connection;
    }

    // -------------------------------- GET METHODS (SELECT)
    private ArrayList<LecturerItem> getLecturerItems() {

        // question array
        ArrayList<LecturerItem> lecturers = new ArrayList<>();

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT DISTINCT * FROM public.lecturer";

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {

                // LecturerItem class variable
                LecturerItem lecturerItem = new LecturerItem(set.getString("lecturer_id").trim());

                // set variables
                lecturerItem.setLecturerLname(set.getString("lecturer_fname").trim());
                lecturerItem.setLecturerFname(set.getString("lecturer_lname").trim());

                // add to array list
                lecturers.add(lecturerItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lecturers;
    }

    public LecturerItem getCurrentLecturerItem() {

        // return variable
        LecturerItem lecturerItem = null;

        // get auth cookie
        Cookie cookie = CookieHandling.getCookieByName("auth");

        // if cookie exists
        if (cookie != null) {

            try {

                // get database variables
                Statement statement = connection.createStatement();

                // query
                String query = "SELECT DISTINCT * FROM public.lecturer " +
                        "WHERE lecturer_id = '" + cookie.getValue() + "'";

                // execute statement
                ResultSet set = statement.executeQuery(query);

                while(set.next()) {

                    // create lecturer item
                    lecturerItem = new LecturerItem(cookie.getValue());

                    // set variables
                    lecturerItem.setLecturerFname(set.getString("lecturer_fname").trim());
                    lecturerItem.setLecturerLname(set.getString("lecturer_lname").trim());
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return lecturerItem;
    }


    // -------------------------------- OTHER METHODS
    public boolean authenticateLecturer(String lecturer_id) {

        // return variable
        boolean success = false;

        for (LecturerItem i : getLecturerItems()) {

            if (lecturer_id.equals(i.getLecturerId())) {

                // make true
                success = true;

                // add cookie
                CookieHandling.addCookie(CookieName.AUTH, lecturer_id, CookieAge.WEEK);

                break;
            }
        }

        return success;
    }
}
