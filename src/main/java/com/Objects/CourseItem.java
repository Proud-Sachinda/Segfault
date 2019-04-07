package com.Objects;

public class CourseItem {

    // attributes
    private int course_id;
    private String course_name;
    private String course_code;


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
