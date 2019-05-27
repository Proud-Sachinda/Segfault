package com.Server;

import com.AttributeHandling;
import com.Objects.LecturerItem;

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

            while (set.next()) {

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


    // -------------------------------- OTHER METHODS
    public boolean authenticateLecturer(String lecturer_id, AttributeHandling attributeHandling) {

        // return variable
        boolean success = false;

        for (LecturerItem i : getLecturerItems()) {

            if (lecturer_id.equals(i.getLecturerId())) {

                // make true
                success = true;

                attributeHandling.setLecturerItem(i);

                break;
            }
        }
        return success;
    }

        //--------------------Post Sign Up Methods------------//
        public boolean authenticationSignUp (String lecturer_id, String lecturer_fname, String lecturer_lname){

            boolean success = false;


            try {

                // insert into core table
                String query = "INSERT INTO public.lecturer(lecturer_id, lecturer_fname,lecturer_lname) VALUES" + "('" + lecturer_id + "', '" + lecturer_fname + "', '" + lecturer_lname + "' )";

                // statement
                Statement statement = connection.createStatement();

                success = statement.execute(query);


            } catch (SQLException e) {
                e.printStackTrace();
            }

            return success;
        }

    }

