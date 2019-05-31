package com.Objects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.awt.*;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

class CourseItemTest {


    @Mock
    private ResultSet resultSet;


    CourseItem courseItem = new CourseItem();
    CourseItem courseItem1 = new CourseItem("COMS3007", "Machine Learning");
    @Mock
    CourseItem courseItem2 = new CourseItem(1, "Software Design", "COMS 3009");


    @BeforeEach
    void setUp(){

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shortenCourseNameIfTooLong(){
        //The method is a static method it probably needs a different approach
        /*
        String name = "Proud man";
        Label mylabel;
        mylabel = new Label(name);
       // Mockito.when(CourseItem.shortenCourseNameIfTooLong(name,mylabel)).getMock();

*/

    }

    @Test
    void getCourseId() {
        courseItem.setCourseId(5);
        assertEquals(5,courseItem.getCourseId());

    }

    @Test
    void setCourseId() {
        courseItem.setCourseId(5);
        assertEquals(5,courseItem.getCourseId());
    }

    @Test
    void getCourseName() {
        courseItem.setCourseName("COMS 3009");
        assertEquals("COMS 3009",courseItem.getCourseName());
    }

    @Test
    void setCourseName() {
        courseItem.setCourseName("Software Design");
        assertEquals("Software Design",courseItem.getCourseName());
    }

    @Test
    void getCourseCode() {
        courseItem.setCourseCode("COMS 3009");
        assertEquals("COMS 3009", courseItem.getCourseCode());
    }

    @Test
    void setCourseCode() {
        courseItem.setCourseCode("COMS 3009");
        assertEquals("COMS 3009", courseItem.getCourseCode());
    }

    @Test
    void getCourseFullName() {
        courseItem.setCourseName("Machine Learning");
        courseItem.setCourseCode("COMS 3007");
        assertEquals("COMS 3007" + " - " + "Machine Learning",courseItem.getCourseFullName());

    }

    @Test
    void isEmpty(){
        courseItem.setCourseName("Software Design");
        courseItem.setCourseCode("COMS3009");
        assertFalse(courseItem.isEmpty());
    }
}