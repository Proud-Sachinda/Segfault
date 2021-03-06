package com.Server;

import com.AttributeHandling;
import com.Objects.LecturerItem;

import javax.validation.constraints.NotNull;
import java.sql.*;
import java.util.ArrayList;

public class LecturerServer {

    // connection variable
    private Connection connection;

    public LecturerServer(@NotNull Connection connection) {

        // initialise connection variable
        this.connection = connection;
    }

    // -------------------------------- GET METHODS (SELECT)
    public ArrayList<LecturerItem> getLecturerItems() {

        // question array
        ArrayList<LecturerItem> lecturers = new ArrayList<>();

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT DISTINCT * FROM public.lecturer";

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while (set.next()) {

                // LecturerItem class variable
                LecturerItem lecturerItem = new LecturerItem(set.getString("lecturer_id").trim());

                // set variables
                lecturerItem.setLecturerLname(set.getString("lecturer_fname").trim());
                lecturerItem.setLecturerFname(set.getString("lecturer_lname").trim());
                lecturerItem.setLecturer_password(set.getString("lecturer_password"));

                // add to array list
                lecturers.add(lecturerItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lecturers;
    }


    // -------------------------------- OTHER METHODS
    public boolean authenticateLecturer(String lecturer_id, String lecturer_password, AttributeHandling attributeHandling) {

        // return variable
        boolean success = false;

        for (LecturerItem i : getLecturerItems()) {

            if (lecturer_id.equals(i.getLecturerId()) && lecturer_password.equals(i.getLecturer_password())) {

                // make true
                success = true;

                attributeHandling.setLecturerItem(i);

                break;
            }
        }
        return success;
    }

        //--------------------Post Sign Up Methods------------//
        public boolean authenticationSignUp (String lecturer_id, String lecturer_fname, String lecturer_lname, String lecturer_password){

            boolean success = false;


            try {

                // insert into core table
                String query = "INSERT INTO public.lecturer(lecturer_id, lecturer_fname,lecturer_lname, lecturer_password) VALUES" + "('" + lecturer_id + "', '" + lecturer_fname + "', '" + lecturer_lname +"','" + lecturer_password +"' )";

                // statement

                Statement statement = connection.createStatement();

                statement.executeUpdate(query);
                //System.out.println(statement.executeUpdate(query));
                success = true;


            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(success);

            return success;
        }

    }

