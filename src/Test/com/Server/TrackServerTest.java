package com.Server;

import com.Objects.TrackItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;

class TrackServerTest {
    @Mock
    Connection connection;
    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    TrackServer trackServer;

    @BeforeEach
    void setUp() throws Exception{

        MockitoAnnotations.initMocks(this);

        //trackServer.setCourseId(1);
       // trackServer.setCourseCode("MATH1012");
        //trackServer.setCourseName("math");

        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        //Mockito.when(preparedStatement.executeUpdate()).thenReturn(trackServer.getCourseId());
        Mockito.when(statement.executeQuery(anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(statement.executeUpdate(anyString())).thenReturn(2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getQuestionCount() throws Exception{
        trackServer = new TrackServer(connection);
        trackServer.getQuestionCount(1);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
    }

    @Test
    void getTrackItemsByTestId() throws Exception{
        trackServer = new TrackServer(connection);
        ArrayList<TrackItem> trackItems =  trackServer.getTrackItemsByTestId(1);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
    }

    @Test
    void getTrackItemsByQuestionId() throws Exception{
        trackServer = new TrackServer(connection);
        ArrayList<TrackItem> trackItems =  trackServer.getTrackItemsByQuestionId(1);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
    }

    @Test
    void updateTrackItemTrackOrder() throws Exception{
        trackServer = new TrackServer(connection);
        TrackItem item = new TrackItem();
        boolean success =  trackServer.updateTrackItemTrackOrder(item);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
    }

    @Test
    void updateTrackItemQuestionNumber() throws  Exception{
        trackServer = new TrackServer(connection);
        boolean success =  trackServer.updateTrackItemQuestionNumber(1,2,5);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
    }

    @Test
    void postToTrackTable() throws Exception{
        trackServer = new TrackServer(connection);
        TrackItem item ;
         item =  trackServer.postToTrackTable(1,2,5,3);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
    }

    @Test
    void deleteTrackItemFromTrackTable() throws Exception {
        trackServer = new TrackServer(connection);
        TrackItem item = new TrackItem();
        boolean success =  trackServer.deleteTrackItemFromTrackTable(item);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
    }

    @Test
    void deleteTrackItemFromTrackTable1() throws Exception{

        trackServer = new TrackServer(connection);
        TrackItem item = new TrackItem();
        boolean success =  trackServer.deleteTrackItemFromTrackTable(1,5);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
    }
}