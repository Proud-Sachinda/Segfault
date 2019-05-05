package com.Server;

import com.Objects.CourseItem;
import com.Objects.QuestionItem;
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
    void setUp() {

        MockitoAnnotations.initMocks(this);

        courseItem.setCourseId(1);
        courseItem.setCourseCode("MATH1012");
        courseItem.setCourseName("yona eo");

        cs = new CourseServer(connection);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void get() throws Exception{
        ArrayList<QuestionItem> courses = new ArrayList<>();
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(anyString())).thenReturn(resultSet);
        Assert.assertNotNull(courses);
    }

    @Test
    void showCourse() throws Exception{
        Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Assert.assertTrue(cs.ShowCourse(courseItem));
    }

    @Test
    void postCourse() throws Exception{
        Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Assert.assertTrue(cs.PostCourse(courseItem));
    }

    @Test
    void PostCoursefalse() throws Exception{
        CourseItem courseItem1 = new CourseItem();
       // Assert.assertFalse(cs.PostCourse(courseItem1));
    }
    @Test
    void getCourseItemByQuestionId() {

    }

    @Test
    void getCourse() {
        Assert.assertNotNull(cs.getCourse());
    }
}