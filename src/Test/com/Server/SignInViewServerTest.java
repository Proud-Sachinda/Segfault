package com.Server;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.mockito.Matchers.anyString;

class SignInViewServerTest {
    SignInViewServer sivs;

    @Mock
    Connection connection;
    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;


    private String username = "username";
    private String password = "password";

    @BeforeEach
    void setUp() throws Exception{

        MockitoAnnotations.initMocks(this);

        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(anyString())).thenReturn(resultSet);
       // Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
      //  Mockito.when(resultSet.getString("lecturer_id")).thenReturn("username");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void authenticate() throws Exception{
        sivs = new SignInViewServer(connection);
        boolean signedin = sivs.authenticate();
        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Mockito.verify(statement, Mockito.times(1)).executeQuery(anyString());
        Assert.assertFalse(signedin);//will change when we authenticate/

    }

    @Test
    void setUsername() {
        sivs = new SignInViewServer(connection);
        sivs.setUsername(username);
    }

    @Test
    void setPassword() {
        sivs = new SignInViewServer(connection);
        sivs.setPassword(password);

    }
}