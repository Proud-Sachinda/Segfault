package com.Components;

import com.Server.QuestionViewServer;
import com.vaadin.ui.ComboBox;

import javax.validation.constraints.NotNull;
import java.sql.Connection;
import java.util.ArrayList;

public class CourseComboBox extends ComboBox<QuestionViewServer.CourseItem> {

    // attributes
    private int courseId;

    public CourseComboBox(ArrayList<QuestionViewServer.CourseItem> items) {

        // set items
        setItems(items);

        // set item caption generator
        setItemCaptionGenerator(QuestionViewServer.CourseItem::getCourseFullName);

        // set place holder
        setPlaceholder("e.g: MATH1036 or Algebra");

        // caption
        setCaption("Subject");
    }

    public int getCourseId() {
        return courseId;
    }

    public void addComboBoxValueChangeListener() {

        // add listener
        addValueChangeListener((ValueChangeListener<QuestionViewServer.CourseItem>) event -> {
            courseId = event.getValue().getCourseId();
        });
    }
}
