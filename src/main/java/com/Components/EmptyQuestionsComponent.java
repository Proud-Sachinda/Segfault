package com.Components;

import com.MyTheme;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;

import java.io.File;

public class EmptyQuestionsComponent extends VerticalLayout {

    // image component
    private Image empty;

    // empty type
    public static final String FIRST = "FIRST";
    public static final String NO_MORE_QUESTIONS = "NO_MORE_QUESTIONS";
    public static final String NO_QUESTIONS_FOUND = "NO_QUESTIONS_FOUND";

    public EmptyQuestionsComponent(String basePath) {

        // set up
        setWidth(60.0f, Unit.PERCENTAGE);
        addStyleName(MyTheme.MAIN_FLAT_FLOATING_BOX);

        // create draft image
        FileResource emptyResource = new FileResource(new File(basePath + "/WEB-INF/images/empty-questions.svg"));
        empty = new Image(null, emptyResource);
        empty.setWidth(64.0f, Unit.PIXELS);
        empty.setHeight(64.0f, Unit.PIXELS);
    }

    public void setQuestionType(String type) {

        // change label depending on first entry
        switch (type) {
            case FIRST: {
                setUpFirstEmptyQuestionComponent();
                break;
            }
            case NO_QUESTIONS_FOUND: {
                setUpNoQuestionsFoundEmptyQuestionComponent();
                break;
            }
            case NO_MORE_QUESTIONS: {
                setUpNoMoreQuestionsEmptyQuestionComponent();
                break;
            }
        }
    }

    public void setUpFirstEmptyQuestionComponent() {

        // remove everything
        removeAllComponents();

        // add image
        addComponent(empty);
        setComponentAlignment(empty, Alignment.TOP_CENTER);

        // label create questions
        Label create = new Label("Create A Question");
        create.addStyleNames(MyTheme.MAIN_TEXT_WEIGHT_900,
                MyTheme.MAIN_TEXT_SIZE_LARGE, MyTheme.MAIN_TEXT_GREEN);

        // description
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Label description = new Label("To create a question click the bottom right");
        description.addStyleName(MyTheme.MAIN_TEXT_SIZE_SMALL);
        horizontalLayout.addComponent(description);

        // icon
        Label icon = new Label("+");
        icon.addStyleName(MyTheme.MAIN_FLAT_ROUND_BUTTON_MINI);
        horizontalLayout.addComponent(icon);

        // word icon
        Label word = new Label("icon");
        word.addStyleName(MyTheme.MAIN_TEXT_SIZE_SMALL);
        horizontalLayout.addComponent(word);

        // align
        horizontalLayout.setComponentAlignment(description, Alignment.MIDDLE_LEFT);
        horizontalLayout.setComponentAlignment(icon, Alignment.MIDDLE_LEFT);
        horizontalLayout.setComponentAlignment(word, Alignment.MIDDLE_LEFT);

        // add
        addComponents(create, horizontalLayout);
        setComponentAlignment(create, Alignment.MIDDLE_CENTER);
        setComponentAlignment(horizontalLayout, Alignment.BOTTOM_CENTER);
    }

    public void setUpNoMoreQuestionsEmptyQuestionComponent() {

        // remove everything
        removeAllComponents();

        // add image
        addComponent(empty);
        setComponentAlignment(empty, Alignment.TOP_CENTER);

        Label none = new Label("No More Questions");
        none.addStyleNames(MyTheme.MAIN_TEXT_WEIGHT_900,
                MyTheme.MAIN_TEXT_SIZE_LARGE);

        // add
        addComponents(none);
        setComponentAlignment(none, Alignment.MIDDLE_CENTER);
    }

    public void setUpNoQuestionsFoundEmptyQuestionComponent() {

        // remove everything
        removeAllComponents();

        // add image
        addComponent(empty);
        setComponentAlignment(empty, Alignment.TOP_CENTER);

        // label no questions
        Label none = new Label("No Questions Found");
        none.addStyleNames(MyTheme.MAIN_TEXT_WEIGHT_900,
                MyTheme.MAIN_TEXT_SIZE_LARGE, MyTheme.MAIN_TEXT_WARNING);

        // description
        Label description = new Label("Please try another phrase");
        description.addStyleName(MyTheme.MAIN_TEXT_SIZE_SMALL);

        // add
        addComponents(none, description);
        setComponentAlignment(none, Alignment.MIDDLE_CENTER);
        setComponentAlignment(description, Alignment.BOTTOM_CENTER);
    }
}
