package com.Objects;

import java.sql.SQLException;

public class LecturerItem {

    // attributes
    private String lecturer_id;
    private String lecturer_fname;
    private String lecturer_lname;
    private String lecturer_password;

    public LecturerItem(String lecturer_id) {

        // set attributes
        this.lecturer_id = lecturer_id;
    }

    public String getLecturerId() {
        return lecturer_id;
    }

    public void setLecturerId(String lecturer_id) {
        this.lecturer_id = lecturer_id;
    }

    public String getLecturerFname() {
        return lecturer_fname;
    }

    public void setLecturerFname(String lecturer_fname) {
        this.lecturer_fname = lecturer_fname;
    }

    public String getLecturerLname() {
        return lecturer_lname;
    }

    public void setLecturerLname(String lecturer_lname) {
        this.lecturer_lname = lecturer_lname;
    }

    public String getLecturer_password() {
        return lecturer_password;
    }

    public void setLecturer_password(String lecturer_password) {
        this.lecturer_password = lecturer_password;
    }
}
