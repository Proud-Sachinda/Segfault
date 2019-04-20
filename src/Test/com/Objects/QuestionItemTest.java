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
    void getQuestionId() {
        QuestionItemObject.setQuestionId(5);
        assertEquals(5 ,QuestionItemObject.getQuestionId());
    }

    @Test
    void setQuestionId() {

        QuestionItemObject.setQuestionId(5);
       assertEquals(5 ,QuestionItemObject.getQuestionId());
    }

    @Test
    void getLecturerId() {
        QuestionItemObject.setLecturerId("5");
        assertEquals("5",QuestionItemObject.getLecturerId());
    }

    @Test
    void getQuestionType() {
        QuestionItemObject.setQuestionType("MCQ");
        assertEquals("MCQ", QuestionItemObject.getQuestionType());
    }

    @Test
    void getQuestionBody() {
        QuestionItemObject.setQuestionBody("What is my name?");
        assertEquals("What is my name?",QuestionItemObject.getQuestionBody());
    }

    @Test
    void getQuestionAns() {
        QuestionItemObject.setQuestionAns("Proud man");
        assertEquals("Proud man",QuestionItemObject.getQuestionAns());
    }

    @Test
    void getQuestionDifficulty() {
        QuestionItemObject.setQuestionDifficulty(5);
        assertEquals(5,QuestionItemObject.getQuestionDifficulty());
    }

    @Test
    void getQuestionDate() {
        java.util.Date date = new java.util.Date();
        QuestionItemObject.setQuestionDate(date);
        assertEquals(date,QuestionItemObject.getQuestionDate());
    }

    @Test
    void getQuestionLastUsed() {
        java.util.Date date = new java.util.Date();
        QuestionItemObject.setQuestionLastUsed(date);
        assertEquals(date,QuestionItemObject.getQuestionLastUsed());
    }

    @Test
    void getQuestionVariance() {
        QuestionItemObject.setQuestionVariance(5);
        assertEquals(5,QuestionItemObject.getQuestionVariance());
    }

    @Test
    void getQuestionMark() {
        QuestionItemObject.setQuestionMark(5);
        assertEquals(5,QuestionItemObject.getQuestionMark());

    }

    @Test
    void setLecturerId() {
        QuestionItemObject.setLecturerId("5");
        assertEquals("5",QuestionItemObject.getLecturerId());
    }

    @Test
    void setQuestionType() {
        QuestionItemObject.setQuestionType("MCQ");
        assertEquals("MCQ", QuestionItemObject.getQuestionType());
    }

    @Test
    void setQuestionBody() {
        QuestionItemObject.setQuestionBody("What is my name?");
        assertEquals("What is my name?",QuestionItemObject.getQuestionBody());
    }

    @Test
    void setQuestionAns() {
        QuestionItemObject.setQuestionAns("Proud man");
        assertEquals("Proud man",QuestionItemObject.getQuestionAns());
    }

    @Test
    void setQuestionDifficulty() {
        QuestionItemObject.setQuestionDifficulty(5);
        assertEquals(5,QuestionItemObject.getQuestionDifficulty());
    }

    @Test
    void setQuestionDate() {
        java.util.Date date = new java.util.Date();
        QuestionItemObject.setQuestionDate(date);
        assertEquals(date,QuestionItemObject.getQuestionDate());
    }

    @Test
    void setQuestionLastUsed() {
        java.util.Date date = new java.util.Date();
        QuestionItemObject.setQuestionLastUsed(date);
        assertEquals(date,QuestionItemObject.getQuestionLastUsed());
    }

    @Test
    void setQuestionVariance() {
        QuestionItemObject.setQuestionVariance(5);
        assertEquals(5,QuestionItemObject.getQuestionVariance());
    }

    @Test
    void setQuestionMark() {
        QuestionItemObject.setQuestionMark(5);
        assertEquals(5,QuestionItemObject.getQuestionMark());
    }

    @Test
    void getCourseId() {
        QuestionItemObject.setCourseId(5);
        assertEquals(5,QuestionItemObject.getCourseId());
    }

    @Test
    void setCourseId() {
        QuestionItemObject.setCourseId(5);
        assertEquals(5,QuestionItemObject.getCourseId());
    }
}