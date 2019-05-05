package com.Server;

import java.sql.*;
import java.util.ArrayList;

import com.Objects.CourseItem;
import com.vaadin.ui.NativeSelect;

public class CourseServer {

    private Connection connection;
    private ArrayList<Course> courses = new ArrayList<>();

    private Course course = new Course();

    // -------------------------------- GET METHODS (SELECT)

    public CourseServer(Connection connection){
        this.connection = connection;
    }

    public ArrayList<Course> get(){
        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.course";

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()){

                //NativeSelect<Course> courseNativeSelect = new NativeSelect<>("Course");
                Course course = new Course();


                course.setCourseName(set.getString("course_name"));
                course.setCourseCode(set.getString("course_code"));


                courses.add(course);
                //System.out.println(course.getCourseCode());
            }


    }
        catch (SQLException e) {
            e.printStackTrace();
        }

            return this.courses;

}

    public boolean ShowCourse(Course c){
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

    public boolean PostCourse(Course c){
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

    public boolean PostCourse2(Course g){
        String coursename1 = g.getCourseName();
        String coursecode1 = g.getCourseCode();

        try{
            String query = "INSERT INTO public.course(course_name, course_code) VALUES (?,?)";

            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1,coursename1);
            ps.setString(2,coursecode1);

            int set2 = ps.executeUpdate();

            return set2 > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public Course getCourse(){

        return this.course;
    }

    public static class Course{

        public String CourseName;
        public  String CourseCode;

        public String getCourseName(){ return  CourseName;}
        public  String getCourseCode(){ return  CourseCode;}

        public void setCourseName(String CourseName){
            this.CourseName = CourseName;
        }

        public void setCourseCode(String CourseCode){
            this.CourseCode = CourseCode;
        }



    }
}






