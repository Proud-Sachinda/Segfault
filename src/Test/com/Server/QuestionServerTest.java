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

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ QuestionServer.class, DriverManager.class })
class QuestionServerTest {

    QuestionItem question = new QuestionItem();
    @Mock
    Connection connection;
    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    QuestionServer qvs;

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
        //question.setQuestionId(100);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeUpdate(anyString())).thenReturn(1);
        Mockito.when(statement.executeQuery(anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt("question_id")).thenReturn(10);

        Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getQuestionItemById() throws Exception{
        qvs = new QuestionServer(connection);
        qvs.postToQuestionTable(question);
        int id = question.getQuestionId();
        QuestionItem check = qvs.getQuestionItemById(id);
        Assert.assertEquals(question.getQuestionId(),check.getQuestionId());


    }

    @Test
    void getQuestionItems() throws Exception{

        ArrayList<QuestionItem> questions = new ArrayList<>();
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(anyString())).thenReturn(resultSet);
        //postToQuestionTable(question);
        qvs = new QuestionServer(connection);
        Assert.assertNotNull(questions);
    }

    @Test
    void postToQuestionTable() throws Exception{

        qvs = new QuestionServer(connection);
        qvs.postToQuestionTable(question);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Mockito.verify(statement, Mockito.times(2)).executeUpdate(anyString());
        Mockito.verify(statement, Mockito.times(1)).executeQuery(anyString());
        Mockito.verify(resultSet, Mockito.times(1)).getInt("question_id");
        Assert.assertNotEquals(0,qvs.postToQuestionTable(question));
    }

    @Test
    void updateQuestionItem() throws Exception{

        qvs = new QuestionServer(connection);
        qvs.updateQuestionItem(question);
        Mockito.verify(connection, Mockito.times(1)).prepareStatement(anyString());
        Mockito.verify(preparedStatement, Mockito.times(3)).setInt(anyInt(), anyInt());
        Mockito.verify(preparedStatement, Mockito.times(2)).setString(anyInt(), anyString());
        Mockito.verify(preparedStatement, Mockito.times(1)).executeUpdate();
        Assert.assertTrue(qvs.updateQuestionItem(question));
    }

}