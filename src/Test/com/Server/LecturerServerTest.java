package com.Server;

import com.Objects.LecturerItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static org.mockito.Matchers.anyString;

class LecturerServerTest {


    @Mock
    Connection connection;
    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;
    @Mock
            LecturerItem li;




    LecturerServer lecturerServer;

    @BeforeEach
    void setUp() throws Exception{


        MockitoAnnotations.initMocks(this);
        LecturerItem lecturerItem = new LecturerItem("1");
        lecturerItem.setLecturerId("1");
        lecturerItem.setLecturerLname("James");
        lecturerItem.setLecturerFname("Steve");



         Mockito.when(connection.createStatement()).thenReturn(statement);
         Mockito.when(statement.executeQuery(anyString())).thenReturn(resultSet);
         Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getLecturerItems() throws Exception{

         Mockito.when(resultSet.next()).thenReturn(false);
        lecturerServer = new LecturerServer(connection);
        ArrayList<LecturerItem> lecturers = lecturerServer.getLecturerItems();

        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Mockito.verify(statement, Mockito.times(1)).executeQuery(anyString());
        Mockito.verify(resultSet, Mockito.times(1)).next();

    }

    @Test
    void authenticateLecturer() throws Exception{
        lecturerServer = new LecturerServer(connection);
    }

    @Test
    void authenticationSignUp() throws Exception{
        lecturerServer = new LecturerServer(connection);
    }
}