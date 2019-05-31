package com.Server;

import com.Objects.TestItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Matchers.anyString;

class TestServerTest {

    @Mock
    Connection connection;
    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    TestServer ts;

    TestItem ti;

    @BeforeEach
    void setUp() throws  Exception{
        MockitoAnnotations.initMocks(this);

        /*test.setLectureId(1);
        test.setCourseId(1);
        test.setTes=tDraftName("draft name");
        test.setTestIsDraft(true);
        test.setTestId(2);
        test.setTestIsExam(true);*/

        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeUpdate(anyString())).thenReturn(1);
        Mockito.when(statement.executeQuery(anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt("test_id")).thenReturn(1);
    }

    @Test
    void postToTestTable() throws Exception{
        TestItem testitem = new TestItem();
        ts = new TestServer(connection);
        int s = (int) ts.postToTestTable(testitem,1,"1");
        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Mockito.verify(statement, Mockito.times(1)).executeUpdate(anyString());
        Mockito.verify(statement, Mockito.times(1)).executeQuery(anyString());
        Mockito.verify(resultSet, Mockito.times(1)).getInt(anyString());
        assertNotEquals(0,s);


    }
    @Test
    void getTestItemById()throws Exception{

        ts = new TestServer(connection);
        TestItem t = ts.getTestItemById(1);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Mockito.verify(statement, Mockito.times(1)).executeQuery(anyString());
        Mockito.verify(resultSet, Mockito.times(1)).getInt(anyString());
    }

    @Test
    void getTestItemsByCourseId(){
        ts = new TestServer(connection);

    }

    @Test
    void updateTestItemTestIsExam(){
        ts = new TestServer(connection);

    }

    @Test
    void updateTestItemTestIsDraft(){
        ts = new TestServer(connection);

    }

    @Test
    void updateTestItemTestDraftName(){
        ts = new TestServer(connection);

    }

    @Test
    void updateTestItemQuestionLastUsedDates(){
        ts = new TestServer(connection);

    }

    @Test
    void deleteTestItemFromTestTable(){
        ts = new TestServer(connection);

    }
}