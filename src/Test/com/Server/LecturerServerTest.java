package com.Server;

import com.Objects.LecturerItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;

class LecturerServerTest {


    @Mock
    Connection connection;
    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    LecturerServer lecturerServer;

    @BeforeEach
    void setUp() {


        LecturerItem lecturerItem = new LecturerItem("1");
        lecturerItem.setLecturerId("1");
        lecturerItem.setLecturerLname("James");
        lecturerItem.setLecturerFname("Steve");

        //Mockito.when(connection.createStatement()).thenReturn(statement);
        //Mockito.when(statement.executeQuery(anyString())).thenReturn(resultSet);
        //Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCurrentLecturerItem() {
        lecturerServer = new LecturerServer(connection);

        LecturerItem lecturerItem;
       // lecturerItem = lecturerServer.getCurrentLecturerItem();

        //assertNotNull(lecturerItem);
    }

    @Test
    void authenticateLecturer() {
    }

    @Test
    void authenticationSignUp() {
    }
}