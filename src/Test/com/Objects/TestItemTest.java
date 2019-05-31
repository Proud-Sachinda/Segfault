package com.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TestItemTest {

    @Mock
    private ResultSet resultSet;


    TestItem test = new TestItem();

    @BeforeEach
    void setUp() {
    }

    @Test
    void firstConstructor(){
        TestItem testItem = new TestItem(test);
    }

    @Test
    void secondConstructor(){
        boolean test_is_draft = true;
        boolean test_is_exam = false;
        String test_draft_name = "Yes";
        TestItem testItem = new TestItem(test_is_draft,test_is_exam,test_draft_name);
    }

    @Test
    void setUpTestItem() throws SQLException {
        MockitoAnnotations.initMocks(this);


        resultSet.insertRow();

        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt("test_id")).thenReturn(1);
        Mockito.when(resultSet.getBoolean("test_is_exam")).thenReturn(true);
        Mockito.when(resultSet.getBoolean("test_is_draft")).thenReturn(true);
        Mockito.when(resultSet.getString("test_draft_name")).thenReturn("March_test");



        test.setUpTestItem(resultSet);
//        Mockito.verify(resultSet,Mockito.times(1));
        //  Mockito.verify(resultSet.getInt("test_id"), Mockito.times(1));
        // Mockito.verify(resultSet.getBoolean("test_is_exam"), Mockito.times(1));
        // Mockito.verify(resultSet.getBoolean("test_is_draft"), Mockito.times(1));
        // Mockito.verify(resultSet.getString("test_draft_name"), Mockito.times(1));


    }

    @Test
    void getTestId() {
        test.setTestId(5);
        assertEquals(5,test.getTestId());
    }

    @Test
    void setTestId() {
        test.setTestId(5);
        assertEquals(5,test.getTestId());
    }

    @Test
    void isTestIsExam() {
        test.setTestIsDraft(true);
        assertTrue(test.isTestIsDraft());
    }

    @Test
    void setTestIsExam() {
        test.setTestIsExam(true);
        assertTrue(test.isTestIsExam());
    }

    @Test
    void isTestIsDraft() {
        test.setTestIsDraft(true);
        assertTrue(test.isTestIsDraft());
    }

    @Test
    void setTestIsDraft() {
        test.setTestIsDraft(true);
        assertTrue(test.isTestIsDraft());
    }

    @Test
    void getTestDraftName() {
        test.setTestDraftName("coms1");
        assertEquals("coms1",test.getTestDraftName());
    }

    @Test
    void setTestDraftName() {
        test.setTestDraftName("coms1");
        assertEquals("coms1",test.getTestDraftName());
    }

    @Test
    void getCourseItem() {
        CourseItem courseItem = new CourseItem();
        test.setCourseItem(courseItem);
        assertEquals(courseItem,test.getCourseItem());
    }

    @Test
    void setCourseItem() {
        CourseItem courseItem = new CourseItem();
        test.setCourseItem(courseItem);
        assertEquals(courseItem,test.getCourseItem());
    }

    @Test
    void getLecturerItem() {
        LecturerItem lecturerItem = new LecturerItem("1");
        test.setLecturerItem(lecturerItem);
       assertEquals(lecturerItem,test.getLecturerItem());
    }

    @Test
    void setLectureId() {
        LecturerItem lecturerItem = new LecturerItem("1");
        test.setLecturerItem(lecturerItem);
        assertEquals(lecturerItem,test.getLecturerItem());
    }


}