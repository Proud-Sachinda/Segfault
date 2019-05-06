package com.Objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrackItemTest {

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
}