package com.Objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class QuestionItem {

    // attributes
    private int question_id;
    private int question_mark;
    private String lecturer_id;
    private Date question_date;
    private String question_ans;
    private String question_type;
    private String question_body;
    private int question_variance;
    private int question_difficulty;
    private Date question_last_used;
    private String question_body_full;
    private String question_mcq_choices;
    private int question_written_no_of_lines;
    private String question_practical_sample_input;
    private String question_practical_sample_output;

    public QuestionItem() {
    }

    public void setUpQuestionItem(ResultSet set) {

        try {

            // set up variables
            this.question_type = set.getString("question_type");
            this.question_id = set.getInt("question_id");
            this.lecturer_id = set.getString("lecturer_id");
            this.question_body = set.getString("question_body");
            this.question_ans = set.getString("question_ans");
            this.question_difficulty = set.getInt("question_difficulty");
            this.question_date = set.getDate("question_date");
            this.question_last_used = set.getDate("question_last_used");
            this.question_variance = set.getInt("question_variance");
            this.question_mark = set.getInt("question_mark");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public void setLecturerId(String lecture_id) {
        this.lecturer_id = lecture_id;
    }

    public void setQuestionType(String question_type) {
        this.question_type = question_type;
    }

    public void setQuestionBody(String question_body) {
        if (question_body.length() > 170) this.question_body = question_body.substring(0, 170) + " ...";
        else this.question_body = question_body;

        setQuestionBodyFull(question_body);
    }

    public String getQuestionBodyFull() {
        return this.question_body_full;
    }

    public void setQuestionBodyFull(String question_body_full) {
        this.question_body_full = question_body_full;
    }

    public void setQuestionAns(String question_ans) {
        this.question_ans = question_ans;
    }

    public void setQuestionDifficulty(int question_difficulty) {
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

    public String getQuestionMcqChoices() {
        return question_mcq_choices;
    }

    public void setQuestionMcqChoices(String question_mcq_choices) {
        this.question_mcq_choices = question_mcq_choices;
    }

    public String getQuestionPracticalSampleInput() {
        return question_practical_sample_input;
    }

    public void setQuestionPracticalSampleInput(String question_practical_sample_input) {
        this.question_practical_sample_input = question_practical_sample_input;
    }

    public String getQuestionPracticalSampleOutput() {
        return question_practical_sample_output;
    }

    public void setQuestionPracticalSampleOutput(String question_practical_sample_output) {
        this.question_practical_sample_output = question_practical_sample_output;
    }

    public int getQuestionWrittenNoOfLines() {
        return question_written_no_of_lines;
    }

    public void setQuestionWrittenNoOfLines(int question_written_no_of_lines) {
        this.question_written_no_of_lines = question_written_no_of_lines;
    }
}
