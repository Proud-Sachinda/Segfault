package com.Server;

import com.Objects.LecturerItem;
import com.Objects.QuestionItem;

import javax.validation.constraints.NotNull;
import java.sql.*;
import java.util.ArrayList;

public class QuestionServer {

    // connection variable
    private Connection connection;

    public QuestionServer(@NotNull Connection connection) {

        // initialise connection variable
        this.connection = connection;
    }

    // -------------------------------- GET METHODS (SELECT)
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
                            "SET mcq_choices = " + questionItem.getQuestionMcqChoices() + " " +
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
}
