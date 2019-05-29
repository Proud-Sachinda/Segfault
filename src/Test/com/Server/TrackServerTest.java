package com.Server;

import com.Objects.TrackItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
    TrackServer ts;
    TrackItem ti;

    @BeforeEach
    void setUp() throws  Exception{
        MockitoAnnotations.initMocks(this);
       /* ti.setTrackId(1);
        ti.setQuestionId(1);
        ti.setTestId(1);
        ti.setQuestionNumber(1);
        ti.setTrackOrder(1);*/
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeUpdate(anyString())).thenReturn(1);

    }

    @Test
    void postToTrackTable() throws Exception{

       /* ts = new TrackServer(connection);
        ti = ts.postToTrackTable(1,1,1,1);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Mockito.verify(statement, Mockito.times(1)).executeUpdate(anyString());*/
    }
}