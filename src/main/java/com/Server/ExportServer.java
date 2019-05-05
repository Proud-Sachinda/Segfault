package com.Server;

import java.sql.*;
import java.util.ArrayList;

public class ExportServer {
    // connection variable
    private Connection connection;

    Track tracky = new Track();

    public ExportServer(Connection connection) {

        // initialise connection variable
        this.connection = connection;
    }

    public Track getTrack(){
        return this.tracky;
    }

    public ArrayList<Track> get(int testid) {
        //arraylist to store  tracks
        ArrayList<Track> tracks = new ArrayList<>();
        try{
            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.track where test_id =" +testid;

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {

                // Question class variable
                Track track = new Track();

                // set variables
                track.setTrack_id(set.getInt("track_id"));
                track.setQuestion_id(set.getInt("question_id"));
                track.setTest_id(set.getInt("test_id"));
                track.setQuestion_number(set.getInt("question_number"));
                track.setTrack_order(set.getInt("track_order"));


                // add to array list
                tracks.add(track);
                System.out.println("i am");
            }
            for(int i=0;i<tracks.size();i++){
                System.out.println(tracks.get(i).getTrackId());
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return tracks;
    }


    public class Track{
        private int track_id;
        private int question_id;
        private int test_id;
        private int question_number;
        private int track_order;
        //private String question_description;

        Track() {

        }

        public int getTrackId() {
            return track_id;
        }

        public int getQuestionId() {
            return question_id;
        }

        public int getTestId() {
            return test_id;
        }

        public int getQuestionNumber() {
            return question_number;
        }

        public int getTrackOrder() {
            return track_order;
        }



        public void setTrack_id(int trackid) {
            this.track_id = trackid;
        }
        public void setQuestion_id(int questionId) {
            this.question_id = questionId;
        }
        public void setTest_id(int testid) {
            this.test_id = testid;
        }
        public void setQuestion_number(int questionnum) {
            this.question_number = questionnum;
        }
        public void setTrack_order(int trackorder) {
            this.track_order = trackorder;
        }

    }
}
