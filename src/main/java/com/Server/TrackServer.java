package com.Server;

import com.Objects.TestItem;
import com.Objects.TrackItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class TrackServer {

    // connection variable
    private Connection connection;

    public TrackServer(Connection connection) {

        // initialise connection variable
        this.connection = connection;
    }
    // -------------------------------- GET METHODS (SELECT)
    public int getQuestionCount(int test_id) {

        // return variable
        int ret = 0;

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT question_number FROM public.track " +
                    "WHERE test_id = " + test_id + " ORDER BY question_number";

            // execute statement
            ResultSet set = statement.executeQuery(query);

            // hash set
            LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();

            while(set.next())
                linkedHashSet.add(set.getInt("question_number"));

            ret = linkedHashSet.size();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public ArrayList<TrackItem> getTrackItemsByTestId(int testId) {

        // return variable
        ArrayList<TrackItem> trackItems = new ArrayList<>();

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.track WHERE test_id = " + testId;

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {

                // track item
                TrackItem trackItem = new TrackItem();
                trackItem.setUpTrackItem(set);

                // add to array
                trackItems.add(trackItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trackItems;
    }

    public ArrayList<TrackItem> getTrackItemsByQuestionId(int questionId) {

        // return variable
        ArrayList<TrackItem> trackItems = new ArrayList<>();

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.track WHERE question_id = " + questionId;

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {

                // track item
                TrackItem trackItem = new TrackItem();
                trackItem.setUpTrackItem(set);

                // add to array
                trackItems.add(trackItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trackItems;
    }

    // -------------------------------- PUT METHODS (UPDATE)
    public boolean updateTrackItemTrackOrder(TrackItem item) {

        // return variable
        boolean success = false;

        try {

            // query
            String query = "UPDATE public.track" +
                    " SET track_order = " + item.getTrackOrder() +
                    " WHERE track_id = " + item.getTrackId();

            // statement
            Statement statement = connection.createStatement();

            // execute statement
            if (statement.executeUpdate(query) > 0) success = true;

        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    public boolean updateTrackItemQuestionNumber(int testId, int oldQuestionNumber, int newQuestionNumber) {

        // return variable
        boolean success = false;

        try {

            // query
            String query = "UPDATE public.track SET question_number = " + newQuestionNumber +
                    " WHERE test_id = " + testId + " AND question_number = " + oldQuestionNumber;

            // statement
            Statement statement = connection.createStatement();

            // execute statement
            if (statement.executeUpdate(query) > 0) success = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }


    // -------------------------------- POST METHODS (INSERT)
    public TrackItem postToTrackTable(int testId, int questionId, int questionNumber, int trackOrder) {

        // return variable
        TrackItem trackItem = null;

        try {

            // query
            String query = "INSERT INTO public.track" +
                    "(test_id, question_id, question_number, track_order) VALUES" +
                    "(" + testId + ", '" + questionId + "', " + questionNumber + ", " + trackOrder + ")";

            // statement
            Statement statement = connection.createStatement();

            // execute statement
            int set = statement.executeUpdate(query);

            if (set > 0) {

                // init
                trackItem = new TrackItem();

                // get question id
                query = "SELECT * FROM public.track ORDER BY track_id DESC LIMIT 1";

                // execute statement
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    trackItem.setUpTrackItem(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trackItem;
    }


    // -------------------------------- DELETE METHODS (DELETE)
    public boolean deleteTrackItemFromTrackTable(TrackItem trackItem) {

        // return variable
        boolean success = false;

        try {

            // query
            String query = "DELETE FROM track WHERE track_id = " + trackItem.getTrackId();

            // statement
            Statement statement = connection.createStatement();

            // delete tracks first
            int res = statement.executeUpdate(query);

            if (res >= 0) success = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    public boolean deleteTrackItemFromTrackTable(int test_id, int question_number) {

        // return variable
        boolean success = false;

        try {

            // query
            String query = "DELETE FROM track WHERE test_id = " + test_id +
                    " AND question_number = " + question_number;

            // statement
            Statement statement = connection.createStatement();

            // delete tracks first
            int res = statement.executeUpdate(query);

            if (res >= 0) success = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }
}
