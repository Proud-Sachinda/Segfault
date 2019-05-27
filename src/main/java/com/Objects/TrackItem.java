package com.Objects;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackItem {

    // attributes
    private int track_id;
    private int question_id;
    private int test_id;
    private int question_number;
    private int track_order;

    public void setUpTrackItem(ResultSet set) {

        try {

            // set up variables
            this.track_id = set.getInt("track_id");
            this.question_id = set.getInt("question_id");
            this.test_id = set.getInt("test_id");
            this.question_number = set.getInt("question_number");
            this.track_order = set.getInt("track_order");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTrackId() {
        return track_id;
    }

    public void setTrackId(int track_id) {
        this.track_id = track_id;
    }

    public int getQuestionId() {
        return question_id;
    }

    public void setQuestionId(int question_id) {
        this.question_id = question_id;
    }

    public int getTestId() {
        return test_id;
    }

    public void setTestId(int test_id) {
        this.test_id = test_id;
    }

    public int getQuestionNumber() {
        return question_number;
    }

    public void setQuestionNumber(int question_number) {
        this.question_number = question_number;
    }

    public int getTrackOrder() {
        return track_order;
    }

    public void setTrackOrder(int track_order) {
        this.track_order = track_order;
    }
}
