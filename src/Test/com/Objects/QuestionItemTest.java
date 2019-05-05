package com.Objects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionItemTest {

    QuestionItem QuestionItemObject = new QuestionItem();


    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
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
    void getCourseIdTest() {
        QuestionItemObject.setCourseId(5);
        assertEquals(5,QuestionItemObject.getCourseId());
    }

    @Test
    void setCourseIdTest() {
        QuestionItemObject.setCourseId(5);
        assertEquals(5,QuestionItemObject.getCourseId());
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
}