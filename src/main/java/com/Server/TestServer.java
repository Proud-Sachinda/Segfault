package com.Server;

import com.Objects.CourseItem;
import com.Objects.LecturerItem;
import com.Objects.TestItem;

import javax.validation.constraints.NotNull;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class TestServer {

    // connection variable
    private Connection connection;


    public TestServer(Connection connection) {

        // initialise connection variable
        this.connection = connection;
    }

    // -------------------------------- GET METHODS (SELECT)
    public TestItem getTestItemById(int testId) {

        // create return question item
        TestItem item = new TestItem();

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.test WHERE test_id = " + testId;

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {

                // set variables
                item.setTestId(set.getInt("test_id"));
                item.setTestIsDraft(set.getBoolean("test_is_draft"));
                item.setTestIsExam(set.getBoolean("test_is_exam"));
                item.setTestDraftName(set.getString("test_draft_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    public ArrayList<TestItem> getTestItemsByCourseId(int courseId, String lecturerId) {

        // test array
        ArrayList<TestItem> items = new ArrayList<>();

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT DISTINCT * FROM test WHERE course_id = " +
                    courseId + " AND lecturer_id = '" + lecturerId + "'";

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {

                // TestItem class variable
                TestItem item = new TestItem();

                // set variables
                item.setUpTestItem(set);

                // add to array list
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // -------------------------------- POST METHODS (INSERT)
    public int postToTestTable(@NotNull TestItem testItem, int courseId, String lecturerId) {

        // return variable
        int testId = 0;

        try {

            // query
            String query = "INSERT INTO public.test" +
                    "(test_is_exam, test_is_draft, test_draft_name, course_id, lecturer_id) VALUES" +
                    "(" + testItem.isTestIsExam() + ", " + testItem.isTestIsDraft() + ", '" +
                    testItem.getTestDraftName() + "', " + courseId + ", '" + lecturerId + "')";

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


    // -------------------------------- PUT METHODS (UPDATE)

    public boolean updateTestItemTestIsExam(@NotNull TestItem testItem) {

        // return variable
        boolean success = false;

        try {

            // query
            String query = "UPDATE test SET test_is_exam = " + testItem.isTestIsExam() +
                    " WHERE test_id = " + testItem.getTestId();

            // statement
            Statement statement = connection.createStatement();

            // execute
            if (statement.executeUpdate(query) > 0) success = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    public boolean updateTestItemTestIsDraft(@NotNull TestItem testItem) {

        // return variable
        boolean success = false;

        try {

            // query
            String query = "UPDATE test SET test_is_draft = " + testItem.isTestIsDraft() +
                    " WHERE test_id = " + testItem.getTestId();

            // statement
            Statement statement = connection.createStatement();

            // execute
            if (statement.executeUpdate(query) > 0) success = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    public boolean updateTestItemTestDraftName(@NotNull TestItem testItem) {

        // return variable
        boolean success = false;

        try {

            // query
            String query = "UPDATE test SET test_draft_name = '" + testItem.getTestDraftName() +
                    "' WHERE test_id = " + testItem.getTestId();

            // statement
            Statement statement = connection.createStatement();

            // execute
            if (statement.executeUpdate(query) > 0) success = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    public boolean updateTestItemQuestionLastUsedDates(@NotNull boolean isNow, int testId) {

        // return variable
        boolean success = false;

        try {

            // query
            String query = "SELECT * FROM track WHERE test_id = " + testId;

            // statement
            Statement statement = connection.createStatement();

            // execute statement
            ResultSet set = statement.executeQuery(query);

            // array of question id's
            StringBuilder questions = new StringBuilder();

            while(set.next()) {
                // append
                questions.append(set.getInt("question_id"));
                questions.append(",");
            }

            // delete trailing comma
            questions.deleteCharAt(questions.length() - 1);

            // update query
            if (isNow) query = "UPDATE public.question SET question_last_used = now() " +
                    "WHERE question_id IN (" + questions.toString() + ")";
            else query = "UPDATE public.question SET question_last_used = NULL " +
                    "WHERE question_id IN (" + questions.toString() + ")";

            // execute
            if (statement.executeUpdate(query) > 0) success = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }


    // -------------------------------- DELETE METHODS (DELETE)
    public boolean deleteTestItemFromTestTable(TestItem testItem) {

        // return variable
        boolean success = false;

        try {

            // query
            String query = "DELETE FROM track WHERE test_id = " + testItem.getTestId();

            // statement
            Statement statement = connection.createStatement();

            // delete tracks first
            int res = statement.executeUpdate(query);

            if (res >= 0) {

                query = "DELETE FROM test WHERE test_id = " + testItem.getTestId();

                res = statement.executeUpdate(query);

                if (res > 0) success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }
}



