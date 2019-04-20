package com.Server;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class QuestionServerTest {




    private Connection connection;


    QuestionServer myQuestionServer =  new QuestionServer(connection);

    @BeforeEach
    void setUp() {


    }

    @Test
    void myFunction(){

        assertEquals(4,myQuestionServer.myFunction(2,2));
    }


    @Test
    void get() {
       // System.out.println(myQuestionServer.get(11));
       // Assert.assertNull(myQuestionServer.get(11));
    }

    @Test
    void post() {

     //   QuestionServer.Written q = (QuestionServer.Written) myQuestionServer.getWritten();



        //myQuestionServer.post(q);
      //  assertTrue(true);
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