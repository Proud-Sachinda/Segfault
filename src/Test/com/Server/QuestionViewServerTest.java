package com.Server;

import com.Objects.QuestionItem;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Matchers.anyString;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ QuestionViewServer.class, DriverManager.class })
class QuestionViewServerTest {

    QuestionItem question = new QuestionItem();
    @Mock
    Connection connection;
    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @Mock
    QuestionViewServer qvs;

    @BeforeEach
    void setUp() throws Exception{

        MockitoAnnotations.initMocks(this);
        String sDate1="31/12/1998";
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        question = new QuestionItem();
        question.setLecturerId("1");
        question.setQuestionAns("hey you");
        question.setQuestionBody("hey");
        question.setQuestionDate(date1);
        question.setQuestionDifficulty(1);
        question.setQuestionLastUsed(date1);
        question.setQuestionMark(2);
        question.setQuestionType("written");
        question.setQuestionVariance(1);

        qvs = new QuestionViewServer(connection);

        Mockito.when(connection.createStatement()).thenReturn(statement);

        Mockito.when(statement.executeUpdate(anyString())).thenReturn(1);
        Mockito.when(statement.executeQuery(anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt("question_id")).thenReturn(1);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void getQuestionItems() {
    }

    @Test
    void postToQuestionTable() throws Exception{
        Assert.assertNotEquals(0,qvs.postToQuestionTable(question));
    }

    @Test
    void updateQuestionItem() throws Exception{

    }

}