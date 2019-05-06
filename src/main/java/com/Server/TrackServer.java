package com.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TrackServer {

    // connection variable
    private Connection connection;

    public TrackServer(Connection connection) {

        // initialise connection variable
        this.connection = connection;
    }

    // -------------------------------- POST METHODS (INSERT)
    public boolean postToTrackTable(int testId, int questionId, int questionNumber, int trackOrder) {

        // return variable
        boolean success = false;

        try {

            // query
            String query = "INSERT INTO public.track" +
                    "(test_id, question_id, question_number, track_order) VALUES" +
                    "(" + testId + ", '" + questionId + "', " + questionNumber + ", " + trackOrder + ")";

            // statement
            Statement statement = connection.createStatement();

            // execute statement
            int set = statement.executeUpdate(query);

            if (set > 0) success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }
}
