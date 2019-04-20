package com.Server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CourseServerTest {

    private Connection connection;

    CourseServer courseObject = new CourseServer(connection);


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void get() {
       // assertNull(courseObject.get());
    }

    @Test
    void showCourse() {
    }

    @Test
    void postCourse() {
    }

    @Test
    void getCourse() {
    }
}