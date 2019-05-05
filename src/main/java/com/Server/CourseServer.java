package com.Server;

import com.Objects.CourseItem;

import java.sql.*;
import java.util.ArrayList;

public class CourseServer {

    private Connection connection;
    ArrayList<CourseItem> courses = new ArrayList<>();

    CourseItem course = new CourseItem();


    public CourseServer(Connection connection){
        this.connection = connection;
    }

    public ArrayList<CourseItem> get(){
        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.course";

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()){

                //NativeSelect<Course> courseNativeSelect = new NativeSelect<>("Course");
                CourseItem course = new CourseItem();


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


    public CourseItem getCourse(){

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






