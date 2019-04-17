package com.Components;

import com.Objects.CourseItem;
import com.Server.QuestionViewServer;
import com.vaadin.ui.ComboBox;

import java.util.ArrayList;

public class CourseComboBox extends ComboBox<CourseItem> {

    // attributes
    private int courseId = 0;

    public CourseComboBox(ArrayList<CourseItem> items) {

        // set items
        setItems(items);

        // set item caption generator
        setItemCaptionGenerator(CourseItem::getCourseFullName);

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
        addValueChangeListener((ValueChangeListener<CourseItem>)
                event -> courseId = event.getValue().getCourseId());
    }
}
