package com.Objects;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TrackItemTest {

    @Mock
    private ResultSet resultSet;

    TrackItem track = new TrackItem();
    @Test
    void getTrackId() {
        track.setTrackId(5);
        assertEquals(5,track.getTrackId());
    }

    @Test
    void setTrackId() {
        track.setTrackId(5);
        assertEquals(5,track.getTrackId());
    }

    @Test
    void getQuestionId() {
        track.setQuestionId(2);
        assertEquals(2,track.getQuestionId());
    }

    @Test
    void setQuestionId() {
        track.setQuestionId(2);
        assertEquals(2,track.getQuestionId());
    }

    @Test
    void getTestId() {
        track.setTestId(6);
        assertEquals(6,track.getTestId());
    }

    @Test
    void setTestId() {
        track.setTestId(6);
        assertEquals(6,track.getTestId());
    }

    @Test
    void getQuestionNumber() {
        track.setQuestionNumber(7);
        assertEquals(7,track.getQuestionNumber());
    }

    @Test
    void setQuestionNumber() {
        track.setQuestionNumber(7);
        assertEquals(7,track.getQuestionNumber());
    }

    @Test
    void getTrackOrder() {
        track.setTrackOrder(3);
        assertEquals(3,track.getTrackOrder());
    }

    @Test
    void setTrackOrder() {
        track.setTrackOrder(3);
        assertEquals(3,track.getTrackOrder());
    }

    @Test
    void setUpTrackItem() throws SQLException {
      /*  MockitoAnnotations.initMocks(this);

        resultSet.insertRow();

        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt("track_id")).thenReturn(1);
        Mockito.when(resultSet.getInt("question_id")).thenReturn(1);
        Mockito.when(resultSet.getInt("test_id")).thenReturn(1);
        Mockito.when(resultSet.getInt("question_number")).thenReturn(1);
        Mockito.when(resultSet.getInt("track_order")).thenReturn(1);



        track.setUpTrackItem(resultSet);
*/
        //Mockito.verify(resultSet,Mockito.times(1));
        //  Mockito.verify(resultSet.getInt("test_id"), Mockito.times(1));
        // Mockito.verify(resultSet.getBoolean("test_is_exam"), Mockito.times(1));
        // Mockito.verify(resultSet.getBoolean("test_is_draft"), Mockito.times(1));
        // Mockito.verify(resultSet.getString("test_draft_name"), Mockito.times(1));
    }

}