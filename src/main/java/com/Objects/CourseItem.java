package com.Objects;

import com.vaadin.ui.Label;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseItem {

    // attributes
    private int course_id;
    private String course_name;
    private String course_code;

    public CourseItem() {}

    public CourseItem(String course_code, String course_name) {

        // set attributes
        this.course_name = course_name;
        this.course_code = course_code;
    }

    public CourseItem(int course_id, String course_name, String course_code) {

        // set attributes
        this.course_id = course_id;
        this.course_name = course_name;
        this.course_code = course_code;
    }

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

    public boolean isEmpty() {
        return course_name.isEmpty() || course_code.isEmpty();
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

    public static void shortenCourseNameIfTooLong(String string, Label label) {

        // if shorter than 20 characters send back string
        if (string.length() < 20) label.setValue(string);
        else {
            // get acronym
            String [] acronym = string.split(" ");

            // null string
            StringBuilder tmp = new StringBuilder();

            for (String s : acronym)
                if (!s.toLowerCase().equals("to") && !s.toLowerCase().equals("and"))
                    tmp.append(s.charAt(0));

            // set label
            label.setValue(tmp.toString().toUpperCase().trim());
            label.setDescription(string);
        }
    }

    public static void shortenCourseNameIfTooLong(CourseItem item, Label label) {

        // course name
        String string = item.getCourseName();

        // shorten
        shortenCourseNameIfTooLong(string, label);

        // get label
        string = item.course_code + " - " + label.getValue();

        // set label
        label.setValue(string);
    }
}
