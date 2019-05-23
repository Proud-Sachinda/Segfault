package com.Server;

import com.Objects.CourseItem;

import javax.validation.constraints.NotNull;
import java.sql.*;
import java.util.ArrayList;

public class CourseServer {

    private Connection connection;
    ArrayList<CourseItem> courses = new ArrayList<>();

    CourseItem course = new CourseItem();

    public CourseServer(Connection connection){

        // initialise connection variable
        this.connection = connection;
    }

    public ArrayList<CourseItem> getCourseItems() {

        // course items
        ArrayList<CourseItem> courseItems = new ArrayList<>();

        try {

            // get database variables


            // query
            String query = "SELECT DISTINCT * FROM public.course";
            PreparedStatement statement = connection.prepareStatement(query);

            // execute statement
            ResultSet set = statement.executeQuery();

            while(set.next()) {
                // CourseItem class variable
                CourseItem item = new CourseItem();

                // set variables
                item.setUpCourseItem(set);

                // add to array list
                courseItems.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseItems;
    }

    // -------------------------------- GET METHODS (SELECT)
    public boolean ShowCourse(CourseItem c){
        String coursename = c.getCourseName();
        String coursecode = c.getCourseCode();

        try{
            String query = "SELECT course_code FROM public.course";
            PreparedStatement ps = connection.prepareStatement(query);

            int set2 = ps.executeUpdate();

            return set2 > 0;

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }


    }

    //public boolean PostCourse(CourseItem c){
    public CourseItem getCourseItemByQuestionId(int questionId) {

        // create return course item
        CourseItem item = new CourseItem();

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.question WHERE question_id = " + questionId;

            // execute statement
            ResultSet set = statement.executeQuery(query);

            int courseId = 0;

            while(set.next()) {

                // set variables
                courseId = set.getInt("course_id");
            }

            // query
            query = "SELECT * FROM public.course WHERE course_id = " + courseId;

            // execute statement
            set = statement.executeQuery(query);

            while(set.next()) {

                // get course item
                item.setUpCourseItem(set);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    // -------------------------------- POST METHODS (INSERT)
    public boolean PostCourse(CourseItem c){

        // course variables
        String courseName = c.getCourseName();
        String courseCode = c.getCourseCode();

        try {

            // query
            String query = "INSERT INTO public.course(course_name, course_code) VALUES (?,?)";

            // statement
            PreparedStatement ps = connection.prepareStatement(query);

            // set statement strings
            ps.setString(1,courseName);
            ps.setString(2,courseCode);

            // execute
            int set = ps.executeUpdate();

            return set > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int postToCourseTable(CourseItem item) {

        //
        // return variable
        int courseId = 0;

        try {

            // query
            String query = "INSERT INTO public.course(course_name, course_code) VALUES " +
                    "('" + item.getCourseName() + "', '" + item.getCourseCode() + "')";

            // statement
            Statement statement = connection.createStatement();

            // execute statement
            courseId = statement.executeUpdate(query);

            // get test id
            if (courseId > 0) {

                query = "SELECT course_id FROM course ORDER BY course_id DESC LIMIT 1";

                // execute statement
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    courseId = resultSet.getInt("course_id");
                }
            }
            else courseId = 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseId;
    }


    public CourseItem getCourse(){

        return this.course;
    }

}






