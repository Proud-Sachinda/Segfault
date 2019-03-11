package com.Server;

import com.vaadin.ui.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
                question.setQuestionDate(set.getString("question_date"));
                question.setQuestionLastUsed(set.getString("question_last_used"));
                question.setQuestionVariance(set.getString("question_variance"));
                question.setQuestionMark(set.getString("question_mark"));

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
        // TODO fill in rest

        try {
            // statement
            Statement statement = connection.createStatement();

            // query
            String query = "INSERT INTO public.question(question_body, question_ans)" +
                    "VALUES ('blah blah blah', 'ans')"; // TODO the rest

            int set = statement.executeUpdate(query);

            // if result inserted set > 0 "1 rows affected" - sql thing
            return set > 0;
        } catch (SQLException e) {
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
        private String question_date;
        private String question_last_used;
        private String question_variance;
        private String question_mark;

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

        public String getQuestionDate() {
            return question_date;
        }

        public String getQuestionLastUsed() {
            return question_last_used;
        }

        public String getQuestionVariance() {
            return question_variance;
        }

        public String getQuestionMark() {
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

        public void setQuestionDate(String question_date) {
            this.question_date = question_date;
        }

        public void setQuestionLastUsed(String question_last_used) {
            this.question_last_used = question_last_used;
        }

        public void setQuestionVariance(String question_variance) {
            this.question_variance = question_variance;
        }

        public void setQuestionMark(String question_mark) {
            this.question_mark = question_mark;
        }
    }
}
