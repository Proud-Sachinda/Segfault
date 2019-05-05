package com.Server;

import com.Objects.CourseItem;
import com.Objects.QuestionItem;
import com.Server.CourseServer;

import javax.validation.constraints.NotNull;
import java.sql.*;
import java.util.ArrayList;

public class CourseViewServer {


    // connection variable
    public ArrayList<CourseItem> getCourses() {

        // course items
        ArrayList<CourseItem> courseItems = new ArrayList<>();

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.course";

            // execute statement
            ResultSet set = statement.executeQuery(query);
            while(set.next()) {
                // CourseItem class variable
                CourseItem item = new CourseItem();

                // set variables
                item.setCourseId(set.getInt("course_id"));
                item.setCourseName(set.getString("course_name"));
                item.setCourseCode(set.getString("course_code"));

                // add to array list
                courseItems.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseItems;
    }

    private Connection connection;

    public CourseViewServer(@NotNull Connection connection) {

        // initialise connection variable
        this.connection = connection;
    }
    // -------------------------------- GET METHODS (SELECT)

    @SuppressWarnings("Duplicates")


    public boolean PostCourse(CourseItem c){
        String coursename = c.getCourseName();
        String coursecode = c.getCourseCode();

        try{
            String query = "INSERT INTO public.course(course_name, course_code) VALUES (?,?)";

            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1,coursename);
            ps.setString(2,coursecode);

            int set2 = ps.executeUpdate();

            return set2 > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public CourseItem getCourseItemById(int courseId) {

        // create return course item
        CourseItem item = new CourseItem();

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.course WHERE course_id = " + courseId;

            // execute statement
            ResultSet set = statement.executeQuery(query);
            while(set.next()) {

                // set variables
                item.setCourseId(set.getInt("course_id"));
                item.setCourseName(set.getString("course_name"));
                item.setCourseCode(set.getString("course_code"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

}
