package com.Server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class QuestionServerTest {

    QuestionServer myQuestionServer;


    private Connection connection;


    @BeforeEach
    void setUp() {

        myQuestionServer = new QuestionServer(connection);
        //QuestionServer questionServer = new QuestionServer(connection);


    }

    @Test
    void Testget() {

        //assertNotNull(myQuestionServer.get());
    }

    @Test
    void Testpost() {
/*
        QuestionServer.Written q = (QuestionServer.Written) myQuestionServer.getWritten();


        q.setQuestionBody("hello");
        q.setQuestionAns("hi");
        //q.setQuestionDate('2014-02-12');
        // q.setQuestionLastUsed();
        q.setQuestionMark(1);
      //  q.setQuestionDifficulty("easy");
        q.setQuestionType("written");
        q.setQuestion_line(1);

        //myQuestionServer.post(q);
        assertTrue(true);
        */
    }

    @Test
    void Testpost1() {
    }

    @Test
    void Testpost2() {
    }

    @Test
    void TestgetQuestion() {
    }

    @Test
    void TestgetWritten() {
    }

    @Test
    void TestgetPractical() {
    }

    @Test
    void TestgetMcq() {
    }
}