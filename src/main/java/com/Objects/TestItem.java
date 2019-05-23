package com.Objects;

import javax.validation.constraints.NotNull;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestItem {

    // attributes
    private int test_id;
    private boolean test_is_exam;
    private boolean test_is_draft;
    private String test_draft_name;

    // object attributes
    private CourseItem courseItem;
    private LecturerItem lecturerItem;

    /**
     * Empty Constructor for tests
     */
    public TestItem() {}

    /**
     * create test item with test item
     * @param testItem
     */
    public TestItem(TestItem testItem) {

        // set attributes
        this.test_id = testItem.getTestId();
        this.test_is_exam = testItem.isTestIsExam();
        this.test_is_draft = testItem.isTestIsDraft();
        this.test_draft_name = testItem.getTestDraftName();
    }

    /**
     * create test item with attributes set up
     * @param test_is_draft draft/final
     * @param test_is_exam exam/test
     * @param test_draft_name test draft name
     */
    @NotNull
    public TestItem(boolean test_is_draft, boolean test_is_exam, String test_draft_name) {

        // set attributes
        this.test_is_exam = test_is_exam;
        this.test_is_draft = test_is_draft;
        this.test_draft_name = test_draft_name;
    }

    public void setUpTestItem(ResultSet set) {

        try {

            // set up variables
            this.test_id = set.getInt("test_id");
            this.test_is_exam = set.getBoolean("test_is_exam");
            this.test_is_draft= set.getBoolean("test_is_draft");
            this.test_draft_name = set.getString("test_draft_name");

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

    public CourseItem getCourseItem() {
        return courseItem;
    }

    public void setCourseItem(CourseItem courseItem) {
        this.courseItem = courseItem;
    }

    public LecturerItem getLecturerItem() {
        return lecturerItem;
    }

    public void setLecturerItem(LecturerItem lecturerItem) {
        this.lecturerItem = lecturerItem;
    }
}
