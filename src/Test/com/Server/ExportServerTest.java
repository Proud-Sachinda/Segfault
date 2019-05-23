package com.Server;


//import org.junit.Test;

import com.Objects.TrackItem;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Matchers.anyString;

class ExportServerTest {

    TrackItem track = new TrackItem();
    @Mock
    Connection connection;
    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    ExportServer es;


    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);

        track.setTrackOrder(1);
        track.setQuestionNumber(1);
        track.setTestId(2);
        track.setQuestionId(10);
        track.setTrackId(5);

        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void getTrack() {
        es =  new ExportServer(connection);
        assertNotNull(es.getTrack());
    }

    @Test
    void get() throws Exception{

            es = new ExportServer(connection);
            ArrayList<TrackItem> tracks = es.get(2);
            Mockito.verify(connection, Mockito.times(1)).createStatement();
            Mockito.verify(statement, Mockito.times(1)).executeQuery(anyString());
            assertNotNull(es.getTrack());

    }
    /*
    @Test(expected = SQLException.class)
    void get1(){
        es =  new ExportServer(connection);
        ArrayList<TrackItem> tracks = es.get(2);

    }
*/

    /*
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testExpectedException() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString('Invalid age'));
        new Person('Joe', -1);
    }
    */



/*
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    void get1(){

        SQLException e = new SQLException();

        exception.expect(SQLException.class);
        exception.expectMessage(e.getMessage());


        ArrayList<TrackItem> tracks = es1.get(2);
        es1.get(1);
        Mockito.when(es1.get(1)).thenThrow(SQLException.class);
        //assertNotNull(es1.get(1));


    }
*/
}