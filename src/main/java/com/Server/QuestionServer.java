package com.Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

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
            //Statement statement1 = connection.createStatement();

            // query
            //String query = "SELECT * FROM public.question";
            String  query3 = "SELECT * FROM public.written_question";
            //String query3 = "SELECT * FROM mcq_question";

            // execute statement
            //ResultSet set = statement.executeQuery(query);
            //ResultSet set1 = statement.executeQuery(query2);
            ResultSet set = statement.executeQuery(query3);

            while(set.next()) {

                 /*if(set2.next()){

                    // Question class variable
                    Question question = new Question();

                    // set variables
                    question.setQuestion_id(set.getInt("question_id"));
                    question.setMcq_choices(set.getString("mcq_choices"));
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

                } */


                // Question class variable
                Question question = new Question();

                // set variables
                //question.setQuestion_id(set.getInt("question_id"));
               // question.setMcq_choices(set.getString("mcq_choices"));
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

/*    public  boolean postMCQ(Question q){
        String questionBody = q.question_body;
        String questionAns = q.question_ans;
        String questionType = "MCQ";
        //questionType = q.question_type;
        String questionDiff = "Easy";
        int questionMark = q.question_mark;
        int questionVar = 0;
        //java.sql.Date questionDate = q.question_date;
        Date question_lastused = q.question_last_used;
        String lecturerID = q.lecturer_id;
        int mcqID = q.mcq_id;
        String mcqchoices = q.mcq_choices;

        try{
            String query2 = "INSERT into public.mcq_question(lecturer_id, question_type, question_body, question_ans, question_difficulty, question_date, question_last_used, question_variance, question_mark, mcq_choices) " +
                    "VALUES (?,?,?,?,?,now(),now(),?,?,?)";
            PreparedStatement statement1 = connection.prepareStatement(query2);

            statement1.setString(1, lecturerID);
            statement1.setString(2,questionType);
            statement1.setString(3,questionBody);
            statement1.setString(4,questionAns);
            statement1.setString(5,questionDiff);
            statement1.setInt(6, questionVar);
            statement1.setInt(7,questionMark);
            statement1.setString(8,mcqchoices);

            int set1 = statement1.executeUpdate();

            // if result inserted set > 0 "1 rows affected" - sql thing
            return set1 > 0;


    } catch (SQLException e) {
        System.out.println(e.getMessage());
        return false;
    }


    } */

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
        //java.sql.Date questionDate = q.question_date;
        Date question_lastused = q.question_last_used;
        String lecturerID = q.lecturer_id;
        //int mcqID = q.mcq_id;
        int wqID = q.written_question_id;
        //String mcqchoices = q.mcq_choices;

       Random random = new Random();

       int qId =  q.question_id;//random.nextInt(1000);



        // TODO fill in rest

        try {
            String query = "INSERT INTO public.written_question( lecturer_id, question_type, question_body, question_ans, question_difficulty, question_date, question_last_used, question_variance, question_mark)" +
                    "VALUES (?,?,?,?,?,now(),now(),?,?)";

            // statement
            PreparedStatement statement = connection.prepareStatement(query);

            //statement.setInt(1,qId);
            statement.setString(1,lecturerID);
            statement.setString(2,questionType);
            statement.setString(3,questionBody);
            statement.setString(4, questionAns);
            statement.setString(5,questionDiff);
          //  statement.setDate(7,questionDate);
            statement.setInt(6,questionVar);
            statement.setInt(7, questionMark);




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
        private int written_question_id;
        private int mcq_id;
        private String mcq_choices;
        private int question_id;
        private String lecturer_id;
        private String question_type;
        private String question_body;
        private String question_ans;
        private String question_difficulty;
        private Date question_date;
        private Date question_last_used;
        private int question_variance;
        private int question_mark;

        public int getWritten_question_id(){ return  written_question_id;}
        public void setWritten_question_id(int written_question_id){ this.written_question_id = written_question_id;}

        public String getMcq_choices(){return mcq_choices;}

        public void setMcq_id(int mcq_id){ this.mcq_id = mcq_id;}
        public int getMcq_id(){return mcq_id;}

        public void setMcq_choices(String mcq_choices){ this.mcq_choices = mcq_choices;}


        public int getQuestion_id(){ return question_id;}

        public void setQuestion_id(int question_id){ this.question_id = question_id;}

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
