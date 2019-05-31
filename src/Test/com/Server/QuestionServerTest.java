package com.Server;

import com.Objects.LecturerItem;
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
    @Mock
    QuestionItem qi;
    @Mock
    LecturerItem li;
    @Mock
    ArrayList<QuestionItem> items = new ArrayList<>();

    @BeforeEach
    void setUp() throws Exception{

        MockitoAnnotations.initMocks(this);


        items.add(qi);
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
    void getQuestionItemNumberOfTimesUsed() throws  Exception{
        qvs = new QuestionServer(connection);
        int var = qvs.getQuestionItemNumberOfTimesUsed(1);
        QuestionItem item = qvs.getQuestionItemById(1);
        Mockito.verify(connection, Mockito.times(2)).createStatement();
        Mockito.verify(statement, Mockito.times(2)).executeQuery(anyString());
        Mockito.verify(resultSet, Mockito.times(3)).next();

    }

    @Test
    void getQuestionItemById() throws Exception{
        qvs = new QuestionServer(connection);
        QuestionItem item = qvs.getQuestionItemById(1);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Mockito.verify(statement, Mockito.times(1)).executeQuery(anyString());
        Mockito.verify(resultSet, Mockito.times(2)).next();


    }

    @Test
    void getQuestionItemVariants()throws Exception{
        qvs = new QuestionServer(connection);
        ArrayList<QuestionItem> it = qvs.getQuestionItemVariants(1);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Mockito.verify(statement, Mockito.times(1)).executeQuery(anyString());
        Mockito.verify(resultSet, Mockito.times(2)).next();
    }

    @Test
    void getQuestionItems() throws Exception{

        qvs = new QuestionServer(connection);
        ArrayList<QuestionItem> questions = qvs.getQuestionItems();
        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Mockito.verify(statement, Mockito.times(1)).executeQuery(anyString());
        Mockito.verify(resultSet, Mockito.times(2)).next();
        Assert.assertNotNull(questions);
    }

    @Test
    void getQuestionItemsByTestId() throws  Exception{
        Mockito.when(resultSet.getInt(anyString())).thenReturn(1);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        qvs = new QuestionServer(connection);
        ArrayList<QuestionItem> it = qvs.getQuestionItemsByTestId(1);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Mockito.verify(statement, Mockito.times(2)).executeQuery(anyString());
        Mockito.verify(resultSet, Mockito.times(5)).next();
    }

    @Test
    void postToQuestionTableWritten() throws Exception{
        Mockito.when(qi.getQuestionType()).thenReturn("written");
        qvs = new QuestionServer(connection);
        qvs.postToQuestionTable(qi,li,1);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Mockito.verify(statement, Mockito.times(3)).executeUpdate(anyString());
        Mockito.verify(statement, Mockito.times(1)).executeQuery(anyString());
        Mockito.verify(resultSet, Mockito.times(2)).next();
        Mockito.verify(resultSet, Mockito.times(1)).getInt(anyString());
        //Assert.assertNotEquals(0,qvs.postToQuestionTable(question)); */
    }

    @Test
    void postToQuestionTableMCQ() throws Exception{
        Mockito.when(qi.getQuestionType()).thenReturn("mcq");
        qvs = new QuestionServer(connection);
        qvs.postToQuestionTable(qi,li,1);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Mockito.verify(statement, Mockito.times(4)).executeUpdate(anyString());
        Mockito.verify(statement, Mockito.times(1)).executeQuery(anyString());
        Mockito.verify(resultSet, Mockito.times(2)).next();
        Mockito.verify(resultSet, Mockito.times(1)).getInt(anyString());
        //Assert.assertNotEquals(0,qvs.postToQuestionTable(question)); */
    }

    @Test
    void postToQuestionTablePractical() throws Exception{
        Mockito.when(qi.getQuestionType()).thenReturn("practical");
        qvs = new QuestionServer(connection);
        qvs.postToQuestionTable(qi,li,1);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Mockito.verify(statement, Mockito.times(4)).executeUpdate(anyString());
        Mockito.verify(statement, Mockito.times(1)).executeQuery(anyString());
        Mockito.verify(resultSet, Mockito.times(2)).next();
        Mockito.verify(resultSet, Mockito.times(1)).getInt(anyString());
        //Assert.assertNotEquals(0,qvs.postToQuestionTable(question)); */
    }


    @Test
    void incrementQuestionItemVariance()  throws Exception{

        qvs = new QuestionServer(connection);
        qvs.incrementQuestionItemVariance(5);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Assert.assertTrue(qvs.incrementQuestionItemVariance(5));
    }

    @Test
    void updateQuestionItem() throws Exception{

        qvs = new QuestionServer(connection);
        qvs.updateQuestionItem(question);
        Mockito.verify(connection, Mockito.times(1)).prepareStatement(anyString());
        Mockito.verify(preparedStatement, Mockito.times(4)).setInt(anyInt(), anyInt());
        Mockito.verify(preparedStatement, Mockito.times(2)).setString(anyInt(), anyString());
        Mockito.verify(preparedStatement, Mockito.times(1)).executeUpdate();
        Assert.assertTrue(qvs.updateQuestionItem(question));
    }

}