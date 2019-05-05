package com.Objects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseItemTest {

  CourseItem courseItem = new CourseItem();

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
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
}