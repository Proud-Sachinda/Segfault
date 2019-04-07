package com.Server;

import com.Objects.CourseItem;
import com.Objects.QuestionItem;

import javax.validation.constraints.NotNull;
import java.sql.*;
import java.util.ArrayList;

public class QuestionViewServer {

    // connection variable
    private Connection connection;

    public QuestionViewServer(@NotNull Connection connection) {

        // initialise connection variable
        this.connection = connection;
    }

    // -------------------------------- GET METHODS (SELECT)
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
                question.setCourseId(set.getInt("course_id"));

                // add to array list
                questions.add(question);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    public CourseItem getCourseItemById(int courseId) {

        // create return course item
        CourseItem item = new CourseItem();

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.course WHERE course_id = " + courseId;

            // execute statement
            ResultSet set = statement.executeQuery(query);
            while(set.next()) {

                // set variables
                item.setCourseId(set.getInt("course_id"));
                item.setCourseName(set.getString("course_name"));
                item.setCourseCode(set.getString("course_code"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }


    // -------------------------------- POST METHODS (INSERT)
    public int postToTestTable(boolean testIsDraft, String testDraftName, int courseId) {

        // return variable
        int testId = 0;

        try {

            // query
            String query = "INSERT INTO public.test" +
                    "(test_is_draft, test_draft_name, course_id) VALUES" +
                    "(" + testIsDraft + ", '" + testDraftName + "', " + courseId + ")";

            // statement
            Statement statement = connection.createStatement();

            // execute statement
            testId = statement.executeUpdate(query);

            // get test id
            query = "SELECT test_id FROM test ORDER BY test_id DESC LIMIT 1";

            // execute statement
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                testId = resultSet.getInt("test_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return testId;
    }

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


    // -------------------------------- PUT METHODS (UPDATE)
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
}
