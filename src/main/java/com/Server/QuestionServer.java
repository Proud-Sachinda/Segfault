package com.Server;

import com.Objects.LecturerItem;
import com.Objects.QuestionItem;
import com.Objects.TestItem;

import javax.validation.constraints.NotNull;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class QuestionServer {

    // connection variable
    private Connection connection;

    public QuestionServer(@NotNull Connection connection) {

        // initialise connection variable
        this.connection = connection;
    }

    // -------------------------------- GET METHODS (SELECT)
    public int getQuestionItemNumberOfTimesUsed(int question_id) {

        int i = 0;

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.track WHERE question_id = " + question_id;

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()) i++;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return i;
    }

    public ArrayList<QuestionItem> getQuestionItems() {

        // question array
        ArrayList<QuestionItem> questions = new ArrayList<>();

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT DISTINCT * FROM public.question";

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {

                // QuestionItem class variable
                QuestionItem question = new QuestionItem();

                // set variables
                question.setUpQuestionItem(set);

                // add to array list
                questions.add(question);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    public ArrayList<QuestionItem> getQuestionItemVariants(int question_id) {
        // question array
        ArrayList<QuestionItem> questions = new ArrayList<>();

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT DISTINCT * FROM public.question WHERE question_variance = " +
                    question_id + " AND question_is_variant = true";

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {

                // QuestionItem class variable
                QuestionItem question = new QuestionItem();

                // set variables
                question.setUpQuestionItem(set);

                // add to array list
                questions.add(question);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    public ArrayList<QuestionItem> getQuestionItemsByTestId(int testId) {

        // question array
        ArrayList<QuestionItem> questions = new ArrayList<>();

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT course_id FROM public.test WHERE test_id = " + testId;

            // execute statement
            ResultSet set = statement.executeQuery(query);

            // get course
            int courseId = 0;

            while(set.next()) courseId = set.getInt("course_id");

            if (courseId > 0) {

                // query
                query = "SELECT DISTINCT * FROM public.question WHERE question_id " +
                        "NOT IN (SELECT question_id FROM track WHERE " +
                        "test_id = " + testId + ") AND course_id = " + courseId;

                // execute statement
                set = statement.executeQuery(query);

                while(set.next()) {

                    // QuestionItem class variable
                    QuestionItem question = new QuestionItem();

                    // set variables
                    question.setUpQuestionItem(set);

                    // add to array list
                    questions.add(question);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    public ArrayList<QuestionItem> getQuestionItemsBySearch(String search, TestItem testItem) {

        // question array
        ArrayList<QuestionItem> questions = new ArrayList<>();

        try {

            // get statement
            Statement statement = connection.createStatement();

            // get query
            if (testItem == null) {

                // query
                String query = "SELECT DISTINCT question.question_id FROM public.question LEFT JOIN tag " +
                        "ON question.question_id = tag.question_id WHERE question_body LIKE '%" + search + "%' " +
                        "OR question_ans LIKE '%" + search + "%' OR tag.tag_name LIKE '%" + search + "%'";

                // execute statement
                ResultSet set = statement.executeQuery(query);

                // for uniqueness
                LinkedHashSet<Integer> hashSet = new LinkedHashSet<>();

                // populate hash set
                while (set.next()) hashSet.add(set.getInt("question_id"));

                query = getUniqueString(hashSet);

                // execute
                set = statement.executeQuery(query);

                while(set.next()) {

                    // QuestionItem class variable
                    QuestionItem question = new QuestionItem();

                    // set variables
                    question.setUpQuestionItem(set);

                    // add to array list
                    questions.add(question);
                }
            }
            else {

                // query
                String query = "SELECT course_id FROM public.test WHERE test_id = " + testItem.getTestId();

                // execute statement
                ResultSet set = statement.executeQuery(query);

                // get course
                int courseId = 0;

                while(set.next()) courseId = set.getInt("course_id");

                if (courseId > 0) {

                    query = "SELECT DISTINCT question_id FROM public.question WHERE question_id NOT IN " +
                            "(SELECT question_id FROM track WHERE test_id = " + testItem.getTestId() + ") AND " +
                            "course_id = " + courseId;

                    // execute statement
                    set = statement.executeQuery(query);

                    // get question items array
                    StringBuilder stringBuilder = new StringBuilder();

                    while (set.next()) {
                        stringBuilder.append(set.getInt("question_id"));
                        stringBuilder.append(",");
                    }

                    if (stringBuilder.toString().replaceAll(",", "").length() == 0) {
                        // update
                        query = "SELECT question.question_id FROM public.question LEFT JOIN tag " +
                                "ON question.question_id = tag.question_id WHERE (question_body LIKE '%" + search + "%' " +
                                "OR question_ans LIKE '%" + search + "%' OR tag.tag_name LIKE '%" + search + "%') AND " +
                                "question.question_id IN (0)";
                    }
                    else {
                        // remove trailing comma
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

                        // update query
                        query = "SELECT question.question_id FROM public.question LEFT JOIN tag " +
                                "ON question.question_id = tag.question_id WHERE (question_body LIKE '%" + search + "%' " +
                                "OR question_ans LIKE '%" + search + "%' OR tag.tag_name LIKE '%" + search + "%') AND " +
                                "question.question_id IN (" + stringBuilder.toString() + ")";
                    }

                    // execute
                    set = statement.executeQuery(query);

                    // for uniqueness
                    LinkedHashSet<Integer> hashSet = new LinkedHashSet<>();

                    // populate hash set
                    while (set.next()) hashSet.add(set.getInt("question_id"));

                    // empty string builder
                    stringBuilder = new StringBuilder();

                    for (Integer i : hashSet) {
                        stringBuilder.append(i);
                        stringBuilder.append(",");
                    }

                    // remove trailing comma
                    if (hashSet.size() > 0) {
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                        // update query
                        query = "SELECT DISTINCT * FROM public.question WHERE question_id " +
                                "IN (" + stringBuilder.toString() + ")";
                    }
                    else {
                        query = "SELECT DISTINCT * FROM public.question WHERE question_id " + "IN (0)";
                    }

                    // execute
                    set = statement.executeQuery(query);

                    while(set.next()) {

                        // QuestionItem class variable
                        QuestionItem question = new QuestionItem();

                        // set variables
                        question.setUpQuestionItem(set);

                        // add to array list
                        questions.add(question);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    public QuestionItem getQuestionItemById(int questionId) {

        // create return question item
        QuestionItem item = new QuestionItem();

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.question WHERE question_id = " + questionId;

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {

                // set variables
                item.setUpQuestionItem(set);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    public String getMCQchoiceById(int questionId) {

        // create return question item
        String choices = "";

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "select mcq_choices from mcq_question where question_id =" + questionId;

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {

                // set variables
                choices = set.getString("mcq_choices");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return choices;
    }

    public String getInputById(int questionId) {

        // create return question item
        String Input = "";

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "select sample_input from practical_question where question_id =" + questionId;

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {

                // set variables
                Input = set.getString("sample_input");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Input;
    }

    public String getOutputById(int questionId) {

        // create return question item
        String Output = "";

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "select sample_output from practical_question where question_id =" + questionId;

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {

                // set variables
                Output = set.getString("sample_output");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Output;
    }


    // -------------------------------- POST METHODS (INSERT)
    public int postToQuestionTable(QuestionItem questionItem, LecturerItem lecturerItem, int course_id) {

        // return variable
        int questionId = 0;

        try {

            // insert into core table
            String query = "INSERT INTO public.question" +
                    "(lecturer_id, question_type, question_body, question_ans, question_date, " +
                    "question_mark, question_difficulty, course_id, question_is_variant, question_variance)" +
                    "VALUES" +
                    "('" + lecturerItem.getLecturerId() + "', '" + questionItem.getQuestionType() + "', '"
                    + questionItem.getQuestionBody() + "', '" + questionItem.getQuestionAns() + "', now(), "
                    + questionItem.getQuestionMark() + ", " + questionItem.getQuestionDifficulty() +
                    ", " + course_id + ", " + questionItem.getQuestionIsVariant() + ", " +
                    questionItem.getQuestionVariance() + ")";

            // statement
            Statement statement = connection.createStatement();

            // execute statement
            questionId = statement.executeUpdate(query);

            // get question id
            query = "SELECT question_id FROM public.question ORDER BY question_id DESC LIMIT 1";

            // execute statement
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                questionId = resultSet.getInt("question_id");
            }

            if (questionId > 0) {

                // post to other question tables
                if (questionItem.getQuestionType().equals("written")) {

                    // query for written table
                    query = "INSERT INTO public.written_question" +
                            "(question_id, lecturer_id, question_type, question_body, question_ans, question_date, " +
                            "question_mark, question_difficulty, course_id) " +
                            "SELECT question_id, lecturer_id, question_type, question_body, question_ans, question_date, " +
                            "question_mark, question_difficulty, course_id FROM public.question " +
                            "WHERE question_id = " + questionId;


                    // execute statement
                    statement.executeUpdate(query);
                }
                else if (questionItem.getQuestionType().equals("mcq")) {

                    // query for mcq table
                    query = "INSERT INTO public.mcq_question" +
                            "(question_id, lecturer_id, question_type, question_body, question_ans, question_date, " +
                            "question_mark, question_difficulty, course_id)" +
                            "SELECT question_id, lecturer_id, question_type, question_body, question_ans, question_date, " +
                            "question_mark, question_difficulty, course_id FROM public.question " +
                            "WHERE question_id = " + questionId;

                    // execute statement
                    statement.executeUpdate(query);

                    // set mcq choices
                    query = "UPDATE public.mcq_question " +
                            "SET mcq_choices = '" + questionItem.getQuestionMcqChoices() + "' " +
                            "WHERE question_id = " + questionId;

                    // execute statement
                    statement.executeUpdate(query);
                }
                else {

                    // query for mcq table
                    query = "INSERT INTO public.practical_question" +
                            "(question_id, lecturer_id, question_type, question_body, question_ans, question_date, " +
                            "question_mark, question_difficulty, course_id)" +
                            "SELECT question_id, lecturer_id, question_type, question_body, question_ans, question_date, " +
                            "question_mark, question_difficulty, course_id FROM public.question " +
                            "WHERE question_id = " + questionId;

                    // execute statement
                    statement.executeUpdate(query);

                    // set mcq choices
                    query = "UPDATE public.practical_question " +
                            "SET sample_input = '" + questionItem.getQuestionPracticalSampleInput() + "', " +
                            "sample_output = '" + questionItem.getQuestionPracticalSampleOutput() + "' " +
                            "WHERE question_id = " + questionId;

                    // execute statement
                    statement.executeUpdate(query);
                }

                // set mcq choices
                query = "UPDATE public.question " +
                        "SET question_is_variant = " + questionItem.getQuestionIsVariant() + ", " +
                        "question_variance = " + questionItem.getQuestionVariance() + " " +
                        "WHERE question_id = " + questionId;

                // execute statement
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questionId;
    }


    // -------------------------------- PUT METHODS (UPDATE)
    public boolean updateQuestionItem(QuestionItem item) {

        // return variable
        boolean success = false;

        try {

            // query
            String query = "UPDATE public.question" +
                    " SET question_difficulty = ?, " +
                    "question_mark = ?, " +
                    "question_body = ?, " +
                    "question_variance = ?, " +
                    "question_is_variant = ?, " +
                    "question_ans = ? " +
                    " WHERE question_id = ?";

            // statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // set strings
            preparedStatement.setInt(1, item.getQuestionDifficulty());
            preparedStatement.setInt(2, item.getQuestionMark());
            preparedStatement.setString(3, item.getQuestionBody());
            preparedStatement.setInt(4, item.getQuestionVariance());
            preparedStatement.setBoolean(5, item.getQuestionIsVariant());
            preparedStatement.setString(6, item.getQuestionAns());
            preparedStatement.setInt(7, item.getQuestionId());

            // execute statement
            if (preparedStatement.executeUpdate() > 0) success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    public boolean incrementQuestionItemVariance(int question_id) {

        // return variable
        boolean success = false;

        try {

            // query
            String query = "UPDATE public.question" +
                    " SET question_variance = question_variance + 1 " +
                    "WHERE question_id = " + question_id;

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


    // -------------------------------- DELETE METHODS (DELETE)
public boolean deleteQuestionItem(QuestionItem questionItem) {

        // return variable
        boolean success = false;

        try {

            // query
            String query;

            // res
            int res;

            // statement
            Statement statement = connection.createStatement();

            // if it is a variant delete
            if (questionItem.getQuestionIsVariant()) {

                // decrement
                query = "UPDATE public.question SET question_variance = question_variance - 1" +
                        " WHERE question_id = " + questionItem.getQuestionVariance();
                statement.executeUpdate(query);

                // delete from tag
                deleteFromTables(questionItem.getQuestionId(), statement);

                // delete from question
                query = "DELETE FROM public.question WHERE question_id = " + questionItem.getQuestionId();
                res = statement.executeUpdate(query);

                if (res > 0) success = true;

            }
            else {

                // check for variances
                query = "SELECT DISTINCT * FROM question WHERE question_variance = " + questionItem.getQuestionId();

                // execute query
                ResultSet set = statement.executeQuery(query);

                int count = 0;

                while (set.next()) count++;

                if (count == 0 || questionItem.getQuestionVariance() == 0) {

                    // delete from tag
                    deleteFromTables(questionItem.getQuestionId(), statement);

                    // delete from question
                    query = "DELETE FROM public.question WHERE question_id = " + questionItem.getQuestionId();
                    res = statement.executeUpdate(query);

                    if (res > 0) success = true;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }


    // --------------------------------------- OTHER METHODS
    public String getUniqueString(LinkedHashSet<Integer> hashSet) {

        // query
        String query;

        // empty string builder
        StringBuilder stringBuilder = new StringBuilder();

        for (Integer i : hashSet) {
            stringBuilder.append(i);
            stringBuilder.append(",");
        }

        // remove trailing comma
        if (hashSet.size() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            // update query
            query = "SELECT DISTINCT * FROM public.question WHERE question_id " +
                    "IN (" + stringBuilder.toString() + ")";
        }
        else {
            query = "SELECT DISTINCT * FROM public.question WHERE question_id " + "IN (0)";
        }

        return query;
    }

    public void deleteFromTables(int qId, Statement statement) throws SQLException {

        // query
        String query;

        // delete from tag
        query = "DELETE FROM public.tag WHERE question_id = " + qId;
        statement.executeUpdate(query);

        // delete from track
        query = "DELETE FROM public.track WHERE question_id = " + qId;
        statement.executeUpdate(query);
    }
}
