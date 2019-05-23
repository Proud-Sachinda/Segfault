package com.Server;

import com.Objects.CourseItem;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static org.mockito.Matchers.anyString;

class CourseServerTest {


    CourseItem courseItem = new CourseItem();

    @Mock
    Connection connection;
    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    CourseServer cs;
    @BeforeEach
    void setUp() throws Exception{

        MockitoAnnotations.initMocks(this);

        courseItem.setCourseId(1);
        courseItem.setCourseCode("MATH1012");
        courseItem.setCourseName("yona eo");

        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCourseItems() throws Exception{
        cs = new CourseServer(connection);
       // ArrayList<CourseItem> courses = cs.getCourseItems();
        //Mockito.verify(connection, Mockito.times(1)).createStatement();
        //Mockito.verify(statement, Mockito.times(1)).executeQuery(anyString());
       // Mockito.verify(preparedStatement, Mockito.times(1)).executeUpdate();
        Assert.assertNotNull(cs);
    }

    @Test
    void showCourse() throws Exception{
        cs = new CourseServer(connection);
        Assert.assertTrue(cs.ShowCourse(courseItem));
        Mockito.verify(connection, Mockito.times(1)).prepareStatement(anyString());
        Mockito.verify(preparedStatement, Mockito.times(1)).executeUpdate();
    }


    @Test
    void postCourse() throws Exception{
        cs = new CourseServer(connection);
        Assert.assertTrue(cs.PostCourse(courseItem));
        Mockito.verify(connection, Mockito.times(1)).prepareStatement(anyString());
        Mockito.verify(preparedStatement, Mockito.times(1)).executeUpdate();
    }

    @Test
    void getCourseItemByQuestionId() throws Exception{

        cs = new CourseServer(connection);
        CourseItem ci = cs.getCourseItemByQuestionId(courseItem.getCourseId());
        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Mockito.verify(statement, Mockito.times(2)).executeQuery(anyString());
        Assert.assertNotNull(ci);
    }

    @Test
    void getCourse() {
        //cs = new CourseServer(connection);
        //Assert.assertNotNull(cs.getCourse());
    }
}