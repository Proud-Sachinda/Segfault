package com.Server;

import javax.validation.constraints.NotNull;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class QuestionViewServer {

    // connection variable
    private Connection connection;

    // dummy question item variable
    private QuestionItem questionItem;

    public QuestionViewServer(@NotNull Connection connection) {

        // initialise connection variable
        this.connection = connection;

        // initialise dummy question item
        questionItem = new QuestionItem();
    }

    public boolean postToTestTable(boolean testIsDraft, String testDraftName, int courseId) {

        // return variable
        boolean success = false;

        try {

            // query
            String query = "INSERT INTO public.test" +
                    "(test_is_draft, test_draft_name, course_id) VALUES" +
                    "(" + testIsDraft + ", '" + testDraftName + "', " + courseId + ")";

            // statement
            Statement statement = connection.createStatement();

            // execute statement
            int set = statement.executeUpdate(query);

            // execute statement
            success = set > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    public class QuestionItem {

        // attributes
        private int question_id;
        private String lecturer_id;
        private String question_type;
        private String question_body;
        private String question_ans;
        private int question_difficulty;
        private Date question_date;
        private Date question_last_used;
        private int question_variance;
        private int question_mark;

        public int getQuestionId() {
            return question_id;
        }

        public void setQuestionId(int question_id) {
            this.question_id = question_id;
        }

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

        public int getQuestionDifficulty() {
            return question_difficulty;
        }

        public Date getQuestionDate() {
            return question_date;
        }

        public Date getQuestionLastUsed() {
            return question_last_used;
        }

        int getQuestionVariance() {
            return question_variance;
        }

        public int getQuestionMark() {
            return question_mark;
        }

        void setLecturerId(String lecture_id) {
            this.lecturer_id = lecture_id;
        }

        void setQuestionType(String question_type) {
            this.question_type = question_type;
        }

        public void setQuestionBody(String question_body) {
            this.question_body = question_body;
        }

        public void setQuestionAns(String question_ans) {
            this.question_ans = question_ans;
        }

        public void setQuestionDifficulty(int question_difficulty) {
            this.question_difficulty = question_difficulty;
        }

        void setQuestionDate(Date question_date) {
            this.question_date = question_date;
        }

        void setQuestionLastUsed(Date question_last_used) {
            this.question_last_used = question_last_used;
        }

        void setQuestionVariance(int question_variance) {
            this.question_variance = question_variance;
        }

        void setQuestionMark(int question_mark) {
            this.question_mark = question_mark;
        }
    }

    public QuestionItem getQuestionItem() {
        return this.questionItem;
    }

    @SuppressWarnings("Duplicates")
    public ArrayList<QuestionItem> getQuestionItems() {

        // question array
        ArrayList<QuestionItem> questions = new ArrayList<>();

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.question";

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {

                // QuestionItem class variable
                QuestionItem question = new QuestionItem();

                // set variables
                question.setQuestionId(set.getInt("question_id"));
                question.setLecturerId(set.getString("lecturer_id"));
                question.setQuestionType(set.getString("question_type"));
                question.setQuestionBody(set.getString("question_body"));
                question.setQuestionAns(set.getString("question_ans"));
                question.setQuestionDifficulty(set.getInt("question_difficulty"));
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

        return questions;
    }

    public boolean updateQuestionItem(QuestionItem item) {

        // return variable
        boolean success = false;

        try {

            // query
            String query = "UPDATE public.question"
                    + " SET question_difficulty = ?," +
                    " question_body = ?," +
                    " question_ans = ?" +
                    " WHERE question_id = ?";

            // statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // set strings
            preparedStatement.setInt(1, item.getQuestionDifficulty());
            preparedStatement.setString(2, item.getQuestionBody());
            preparedStatement.setString(3, item.getQuestionAns());
            preparedStatement.setInt(4, item.getQuestionId());

            // execute statement
            if (preparedStatement.executeUpdate() > 0) success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    public class CourseItem {

        // attributes
        private int course_id;
        private String course_name;
        private String course_code;


        public int getCourseId() {
            return course_id;
        }

        void setCourseId(int course_id) {
            this.course_id = course_id;
        }

        String getCourseName() {
            return course_name;
        }

        void setCourseName(String course_name) {
            this.course_name = course_name;
        }

        String getCourseCode() {
            return course_code;
        }

        void setCourseCode(String course_code) {
            this.course_code = course_code;
        }

        public String getCourseFullName() {
            return getCourseCode() + " - " + getCourseName();
        }
    }

    public ArrayList<CourseItem> getCourses() {

        // course items
        ArrayList<CourseItem> courseItems = new ArrayList<>();

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.course";

            // execute statement
            ResultSet set = statement.executeQuery(query);
            while(set.next()) {
                // CourseItem class variable
                CourseItem item = new CourseItem();

                // set variables
                item.setCourseId(set.getInt("course_id"));
                item.setCourseName(set.getString("course_name"));
                item.setCourseCode(set.getString("course_code"));

                // add to array list
                courseItems.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseItems;
    }
}
