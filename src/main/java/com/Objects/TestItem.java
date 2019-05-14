package com.Objects;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestItem {

    // attributes
    private int test_id;
    private boolean test_is_exam;
    private boolean test_is_draft;
    private String test_draft_name;
    private int course_id;
    private int lecture_id;

    public void setUpTestItem(ResultSet set) {

        try {

            // set up variables
            this.test_id = set.getInt("test_id");
            this.test_is_exam = set.getBoolean("test_is_exam");
            this.test_is_draft= set.getBoolean("test_is_draft");
            this.test_draft_name = set.getString("test_draft_name");
            this.course_id = set.getInt("course_id");
            this.lecture_id = set.getInt("lecturer_id");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTestId() {
        return test_id;
    }

    public void setTestId(int test_id) {
        this.test_id = test_id;
    }

    public boolean isTestIsExam() {
        return test_is_exam;
    }

    public void setTestIsExam(boolean test_is_exam) {
        this.test_is_exam = test_is_exam;
    }

    public boolean isTestIsDraft() {
        return test_is_draft;
    }

    public void setTestIsDraft(boolean test_is_draft) {
        this.test_is_draft = test_is_draft;
    }

    public String getTestDraftName() {
        return test_draft_name;
    }

    public void setTestDraftName(String test_draft_name) {
        this.test_draft_name = test_draft_name;
    }

    public int getCourseId() {
        return course_id;
    }

    public void setCourseId(int course_id) {
        this.course_id = course_id;
    }

    public int getLectureId() {
        return lecture_id;
    }

    public void setLectureId(int lecture_id) {
        this.lecture_id = lecture_id;
    }
}
