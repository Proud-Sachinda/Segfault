package com.Server;

import com.vaadin.ui.Label;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class QuestionServer {

    // connection variable
    private Connection connection;

    // question array
    ArrayList<Question> questions = new ArrayList<>();

    // question variable for post and delete
    Question question = new Question();

    public QuestionServer(Connection connection) {

        // initialise connection variable
        this.connection = connection;
    }

    public ArrayList<Question> get() {

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.question";

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {

                // Question class variable
                Question question = new Question();

                // set variables
                question.setLecturerId(set.getString("lecturer_id"));
                question.setQuestionType(set.getString("question_type"));
                question.setQuestionBody(set.getString("question_body"));
                question.setQuestionAns(set.getString("question_ans"));
                question.setQuestionDifficulty(set.getString("question_difficulty"));
                question.setQuestionDate(set.getDate("question_date"));
                question.setQuestionLastUsed(set.getDate("question_last_used"));
                question.setQuestionVariance(set.getInt("question_variance"));
                question.setQuestionMark(set.getInt("question_mark"));

                // add to array list
                questions.add(question);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this.questions;
    }

    public boolean post(Question q) {

        // TODO create-question form passes in a question variable
        // TODO if successful return true, if true redirect to question view

        // parameters
        String questionBody = q.question_body;
        String questionAns = q.question_ans;
        String questionType = q.question_type;
        String questionDiff = "Easy";
       int questionMark = q.question_mark;
        int questionVar = 0;
       // java.sql.Date questionDate = q.question_date;
        Date question_lastused = q.question_last_used;
        String lecturerID = q.lecturer_id;
        int qId = 3;

        // TODO fill in rest

        try {
            String query = "INSERT INTO public.question(question_id, lecturer_id, question_type, question_body, question_ans, question_difficulty, question_date, question_last_used, question_variance, question_mark)" +
                    "VALUES (?,?,?,?,?,?,now(),now(),?,?)";
            // statement
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, qId);
            statement.setString(2,lecturerID);
            statement.setString(3,questionType);
            statement.setString(4,questionBody);
            statement.setString(5, questionAns);
            statement.setString(6,questionDiff);
            //statement.setDate(7,questionDate);
            statement.setInt(7,questionVar);
            statement.setInt(8, questionMark);



            // query
             // TODO the rest

            int set = statement.executeUpdate();

            // if result inserted set > 0 "1 rows affected" - sql thing
            return set > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Question getQuestion() {
        return this.question;
    }

    public class Question {

        // attributes
        private String lecturer_id;
        private String question_type;
        private String question_body;
        private String question_ans;
        private String question_difficulty;
        private Date question_date;
        private Date question_last_used;
        private int question_variance;
        private int question_mark;

        public String getLecturerId() {
            return lecturer_id;
        }

        public String getQuestionType() {
            return question_type;
        }

        public String getQuestionBody() {
            return question_body;
        }

        public String getQuestionAns() {
            return question_ans;
        }

        public String getQuestionDifficulty() {
            return question_difficulty;
        }

        public Date getQuestionDate() {
            return question_date;
        }

        public Date getQuestionLastUsed() {
            return question_last_used;
        }

        public int getQuestionVariance() {
            return question_variance;
        }

        public int getQuestionMark() {
            return question_mark;
        }

        public void setLecturerId(String lecture_id) {
            this.lecturer_id = lecture_id;
        }

        public void setQuestionType(String question_type) {
            this.question_type = question_type;
        }

        public void setQuestionBody(String question_body) {
            this.question_body = question_body;
        }

        public void setQuestionAns(String question_ans) {
            this.question_ans = question_ans;
        }

        public void setQuestionDifficulty(String question_difficulty) {
            this.question_difficulty = question_difficulty;
        }

        public void setQuestionDate(Date question_date) {
            this.question_date = question_date;
        }

        public void setQuestionLastUsed(Date question_last_used) {
            this.question_last_used = question_last_used;
        }

        public void setQuestionVariance(int question_variance) {
            this.question_variance = question_variance;
        }

        public void setQuestionMark(int question_mark) {
            this.question_mark = question_mark;
        }
    }
}
