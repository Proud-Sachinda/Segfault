package com.Objects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class QuestionItemTest {

    @Mock
    private ResultSet resultSet;

    QuestionItem QuestionItemObject = new QuestionItem();


    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setUpQuestionItem() throws SQLException {
        MockitoAnnotations.initMocks(this);

        resultSet.insertRow();


        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt("question_id")).thenReturn(1);
        Mockito.when(resultSet.getString("question_type")).thenReturn("Practical");
        Mockito.when(resultSet.getString("lecturer_id")).thenReturn("1");
        Mockito.when(resultSet.getString("question_body")).thenReturn("What is meant by software is complex");
        Mockito.when(resultSet.getString("question_ans")).thenReturn("Comprises of many simple parts");
        Mockito.when(resultSet.getInt("question_difficulty")).thenReturn(2);
        Mockito.when(resultSet.getDate("question_date")).thenReturn(new java.sql.Date(1221222L));
        Mockito.when(resultSet.getBoolean("question_is_variant")).thenReturn(true);
        Mockito.when(resultSet.getDate("question_last_used")).thenReturn(new java.sql.Date(1221222L));
        Mockito.when(resultSet.getInt("question_variance")).thenReturn(1);
        Mockito.when(resultSet.getInt("question_mark")).thenReturn(8);

        //Mockito.doReturn(resultSetMock).when(callableStatementMock).executeQuery();
        //Mockito.doReturn(resultSet).when()
        // test.setTestId(1);
        //test.setTestIsExam(true);
        //test.setTestIsDraft(true);
        //test.setTestDraftName("March_test");

        QuestionItemObject.setUpQuestionItem(resultSet);

        Mockito.verify(resultSet,Mockito.times(1));
        //  Mockito.verify(resultSet.getInt("test_id"), Mockito.times(1));
        // Mockito.verify(resultSet.getBoolean("test_is_exam"), Mockito.times(1));
        // Mockito.verify(resultSet.getBoolean("test_is_draft"), Mockito.times(1));
        // Mockito.verify(resultSet.getString("test_draft_name"), Mockito.times(1));
    }


    @Test
    void getQuestionIdTest() {
        QuestionItemObject.setQuestionId(5);
        assertEquals(5 ,QuestionItemObject.getQuestionId());
    }

    @Test
    void setQuestionIdTest() {

        QuestionItemObject.setQuestionId(5);
       assertEquals(5 ,QuestionItemObject.getQuestionId());
    }

    @Test
    void getLecturerIdTest() {
        QuestionItemObject.setLecturerId("5");
        assertEquals("5",QuestionItemObject.getLecturerId());
    }

    @Test
    void getQuestionTypeTest() {
        QuestionItemObject.setQuestionType("MCQ");
        assertEquals("MCQ", QuestionItemObject.getQuestionType());
    }

    @Test
    void getQuestionBodyTest() {
        QuestionItemObject.setQuestionBody("What is my name?");
        assertEquals("What is my name?",QuestionItemObject.getQuestionBody());
    }

    @Test
    void getQuestionAnsTest() {
        QuestionItemObject.setQuestionAns("Proud man");
        assertEquals("Proud man",QuestionItemObject.getQuestionAns());
    }

    @Test
    void getQuestionDifficultyTest() {
        QuestionItemObject.setQuestionDifficulty(5);
        assertEquals(5,QuestionItemObject.getQuestionDifficulty());
    }

    @Test
    void getQuestionDateTest() {
        java.util.Date date = new java.util.Date();
        QuestionItemObject.setQuestionDate(date);
        assertEquals(date,QuestionItemObject.getQuestionDate());
    }

    @Test
    void getQuestionLastUsedTest() {
        java.util.Date date = new java.util.Date();
        QuestionItemObject.setQuestionLastUsed(date);
        assertEquals(date,QuestionItemObject.getQuestionLastUsed());
    }

    @Test
    void getQuestionVarianceTest() {
        QuestionItemObject.setQuestionVariance(5);
        assertEquals(5,QuestionItemObject.getQuestionVariance());
    }

    @Test
    void getQuestionMarkTest() {
        QuestionItemObject.setQuestionMark(5);
        assertEquals(5,QuestionItemObject.getQuestionMark());

    }

    @Test
    void setLecturerIdTest() {
        QuestionItemObject.setLecturerId("5");
        assertEquals("5",QuestionItemObject.getLecturerId());
    }

    @Test
    void setQuestionTypeTest() {
        QuestionItemObject.setQuestionType("MCQ");
        assertEquals("MCQ", QuestionItemObject.getQuestionType());
    }

    @Test
    void setQuestionBodyTest() {
        QuestionItemObject.setQuestionBody("What is my name?");
        assertEquals("What is my name?",QuestionItemObject.getQuestionBody());
    }

    @Test
    void setQuestionAnsTest() {
        QuestionItemObject.setQuestionAns("Proud man");
        assertEquals("Proud man",QuestionItemObject.getQuestionAns());
    }

    @Test
    void setQuestionDifficultyTest() {
        QuestionItemObject.setQuestionDifficulty(5);
        assertEquals(5,QuestionItemObject.getQuestionDifficulty());
    }

    @Test
    void setQuestionDateTest() {
        java.util.Date date = new java.util.Date();
        QuestionItemObject.setQuestionDate(date);
        assertEquals(date,QuestionItemObject.getQuestionDate());
    }

    @Test
    void setQuestionLastUsedTest() {
        java.util.Date date = new java.util.Date();
        QuestionItemObject.setQuestionLastUsed(date);
        assertEquals(date,QuestionItemObject.getQuestionLastUsed());
    }

    @Test
    void setQuestionVarianceTest() {
        QuestionItemObject.setQuestionVariance(5);
        assertEquals(5,QuestionItemObject.getQuestionVariance());
    }

    @Test
    void setQuestionMarkTest() {
        QuestionItemObject.setQuestionMark(5);
        assertEquals(5,QuestionItemObject.getQuestionMark());
    }

    @Test
    void getQuestionMcqChoicesTest(){
        QuestionItemObject.setQuestionMcqChoices("a, b, c");
        assertEquals("a, b, c", QuestionItemObject.getQuestionMcqChoices());
    }

    @Test
    void setQuestionMcqChoicesTest(){
        QuestionItemObject.setQuestionMcqChoices("a, b, c");
        assertEquals("a, b, c", QuestionItemObject.getQuestionMcqChoices());
    }

    @Test
    void getQuestionPracticalSampleInputTest(){
        QuestionItemObject.setQuestionPracticalSampleInput("Object object = new Object()");
        assertEquals("Object object = new Object()", QuestionItemObject.getQuestionPracticalSampleInput());

    }

    @Test
    void setQuestionPracticalSampleInputTest(){
        QuestionItemObject.setQuestionPracticalSampleInput("Object object = new Object()");
        assertEquals("Object object = new Object()", QuestionItemObject.getQuestionPracticalSampleInput());
    }

    @Test
    void getQuestionPracticalSampleOutputTest(){
    QuestionItemObject.setQuestionPracticalSampleOutput("An object has been created");
    assertEquals("An object has been created", QuestionItemObject.getQuestionPracticalSampleOutput());
    }

    @Test
    void setQuestionPracticalSampleOutputTest(){
        QuestionItemObject.setQuestionPracticalSampleOutput("An object has been created");
        assertEquals("An object has been created", QuestionItemObject.getQuestionPracticalSampleOutput());
    }

    @Test
    void getQuestionWrittenNoOfLinesTest(){
    QuestionItemObject.setQuestionWrittenNoOfLines(5);
    assertEquals(5, QuestionItemObject.getQuestionWrittenNoOfLines());
    }

    @Test
    void setQuestionWrittenNoOfLinesTest(){
        QuestionItemObject.setQuestionWrittenNoOfLines(5);
        assertEquals(5, QuestionItemObject.getQuestionWrittenNoOfLines());
    }

    @Test
    void getShortQuestionBody(){
        QuestionItemObject.setQuestionBody("What is a test stub");
        assertEquals("What is a test stub",QuestionItemObject.getShortQuestionBody());
    }

}