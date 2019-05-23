package com.Components;

import com.Objects.CourseItem;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class CreateCourseComponent extends HorizontalLayout {

    // components
    private final TextField courseNameTextField = new TextField();
    private final TextField courseCodeTextField = new TextField();

    public CreateCourseComponent() {

        // set up components
        courseCodeTextField.setPlaceholder("Course code");
        courseNameTextField.setPlaceholder("Course name");

        // add components
        addComponents(courseCodeTextField, courseNameTextField);
    }

    public CourseItem getCourseItemFromComponent() {

        // create course component
        String courseCode = courseCodeTextField.getValue();
        String courseName = courseNameTextField.getValue();

        return new CourseItem(courseCode, courseName);
    }

    public void emptyCreateCourseComponent() {

        // empty values
        courseCodeTextField.setValue("");
        courseNameTextField.setValue("");
    }
}
