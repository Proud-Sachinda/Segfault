package com;

import com.Objects.CourseItem;
import com.Objects.ExportItem;
import com.Objects.LecturerItem;
import com.Objects.TestItem;

public class AttributeHandling {

    // attributes
    private String message;
    private TestItem testItem;
    private CourseItem courseItem;
    private LecturerItem lecturerItem;
    private ExportItem exportItem;

    public String getMessage() {

        // temporary message
        String tmp = message;

        // set message to empty
        message = "";

        return tmp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TestItem getTestItem() {
        return testItem;
    }

    public void setTestItem(TestItem testItem) {
        this.testItem = testItem;
    }

    public ExportItem getExportItem() {
        return exportItem;
    }

    public void setExportItem(ExportItem exportItem) {
        this.exportItem = exportItem;
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
