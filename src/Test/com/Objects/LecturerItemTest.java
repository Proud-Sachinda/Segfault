package com.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LecturerItemTest {

    LecturerItem lecturerItem = new LecturerItem("1");

    @BeforeEach
    void setUp() {
    }

    @Test
    void getLecturerId() {

        lecturerItem.setLecturerId("5");
        assertEquals("5",lecturerItem.getLecturerId());
    }

    @Test
    void setLecturerId() {
        lecturerItem = new LecturerItem("5");
        assertEquals("5",lecturerItem.getLecturerId());
    }

    @Test
    void getLecturerFname() {

        lecturerItem.setLecturerFname("Terence");
        assertEquals("Terence",lecturerItem.getLecturerFname());
    }

    @Test
    void setLecturerFname() {

        lecturerItem.setLecturerFname("Terence");
        assertEquals("Terence",lecturerItem.getLecturerFname());
    }

    @Test
    void getLecturerLname() {
        lecturerItem.setLecturerLname("Van Zyl");
        assertEquals("Van Zyl",lecturerItem.getLecturerLname());
    }

    @Test
    void setLecturerLname() {
        lecturerItem.setLecturerLname("Van Zyl");
        assertEquals("Van Zyl",lecturerItem.getLecturerLname());
    }
    @Test
    void getLecturer_password(){
        lecturerItem.setLecturer_password("123abc");
        assertEquals("123abc",lecturerItem.getLecturer_password());
    }
    @Test
    void setLecturer_password(){
        lecturerItem.setLecturer_password("123abc");
        assertEquals("123abc",lecturerItem.getLecturer_password());
    }

}