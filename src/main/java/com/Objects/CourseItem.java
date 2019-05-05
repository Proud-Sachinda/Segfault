package com.Objects;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseItem {

    // attributes
    private int course_id;
    private String course_name;
    private String course_code;

    public void setUpCourseItem(ResultSet set) {

        try {

            // set up variables
            this.course_id = set.getInt("course_id");
            this.course_code = set.getString("course_code");
            this.course_name = set.getString("course_name");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCourseId() {
        return course_id;
    }

    public void setCourseId(int course_id) {
        this.course_id = course_id;
    }

    public String getCourseName() {
        return course_name;
    }

    public void setCourseName(String course_name) {
        this.course_name = course_name;
    }

    public String getCourseCode() {
        return course_code;
    }

    public void setCourseCode(String course_code) {
        this.course_code = course_code;
    }

    public String getCourseFullName() {
        return getCourseCode() + " - " + getCourseName();
    }
}
