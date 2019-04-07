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

    }

    @Test
    void get() {
        assertNotNull(myQuestionServer.get());
    }

    @Test
    void post() {
    }

    @Test
    void post1() {
    }

    @Test
    void post2() {
    }

    @Test
    void getQuestion() {
    }

    @Test
    void getWritten() {
    }

    @Test
    void getPractical() {
    }

    @Test
    void getMcq() {
    }
}