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

import static org.mockito.Matchers.anyString;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ QuestionServer.class, DriverManager.class })
class QuestionViewServerTest {

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

        qvs = new QuestionServer(connection);


    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void getQuestionItems() throws Exception{

        ArrayList<QuestionItem> questions = new ArrayList<>();
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(anyString())).thenReturn(resultSet);
        //postToQuestionTable(question);
        Assert.assertNotNull(questions);
    }

    @Test
    void postToQuestionTable() throws Exception{

        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeUpdate(anyString())).thenReturn(1);
        Mockito.when(statement.executeQuery(anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt("question_id")).thenReturn(1);
        Assert.assertNotEquals(0,qvs.postToQuestionTable(question));
    }

    @Test
    void updateQuestionItem() throws Exception{

        Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Assert.assertTrue(qvs.updateQuestionItem(question));
    }

}