package com.Objects;

import java.util.Date;

public class QuestionItem {

    // attributes
    private int course_id;
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

    public int getCourseId() {
        return course_id;
    }

    public void setCourseId(int course_id) {
        this.course_id = course_id;
    }
}
