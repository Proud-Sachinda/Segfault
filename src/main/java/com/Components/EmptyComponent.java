package com.Components;

import com.MyTheme;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;

import java.io.File;

public class EmptyComponent extends VerticalLayout {

    // image component
    private Image emptyLibrary;
    private Image emptyQuestions;

    // empty type
    public static final String FIRST = "FIRST";
    public static final String CREATE_A_TEST = "CREATE_A_TEST";
    public static final String NO_MORE_QUESTIONS = "NO_MORE_QUESTIONS";
    public static final String NO_QUESTIONS_FOUND = "NO_QUESTIONS_FOUND";

    /**
     * initialise empty component with basePath set type later
     * @param basePath images are loaded from path
     */
    public EmptyComponent(String basePath) {

        // set up
        setWidthUndefined();
        addStyleName(MyTheme.MAIN_FLAT_FLOATING_BOX);

        // create empty question image
        FileResource emptyQuestionsResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/empty-questions.svg"));
        emptyQuestions = new Image(null, emptyQuestionsResource);
        emptyQuestions.setWidth(64.0f, Unit.PIXELS);
        emptyQuestions.setHeight(64.0f, Unit.PIXELS);

        // create empty library image
        FileResource emptyLibraryResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/empty-library.svg"));
        emptyLibrary = new Image(null, emptyLibraryResource);
        emptyLibrary.setWidth(64.0f, Unit.PIXELS);
        emptyLibrary.setHeight(64.0f, Unit.PIXELS);
    }

    /**
     * set up empty component with basePath and type
     * @param basePath used for images
     * @param type type of empty
     */
    EmptyComponent(String basePath, String type) {

        // set up
        setWidthUndefined();
        addStyleName(MyTheme.MAIN_FLAT_FLOATING_BOX);

        // create empty question image
        FileResource emptyQuestionsResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/empty-questions.svg"));
        emptyQuestions = new Image(null, emptyQuestionsResource);
        emptyQuestions.setWidth(64.0f, Unit.PIXELS);
        emptyQuestions.setHeight(64.0f, Unit.PIXELS);

        // create empty library image
        FileResource emptyLibraryResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/empty-library.svg"));
        emptyLibrary = new Image(null, emptyLibraryResource);
        emptyLibrary.setWidth(64.0f, Unit.PIXELS);
        emptyLibrary.setHeight(64.0f, Unit.PIXELS);

        // set type
        setType(type);
    }

    public void setType(String type) {

        // change label depending on first entry
        switch (type) {
            case FIRST: {
                setUpFirstEmptyComponent();
                break;
            }
            case CREATE_A_TEST: {
                setUpCreateATestEmptyComponent();
                break;
            }
            case NO_QUESTIONS_FOUND: {
                setUpNoQuestionsFoundEmptyComponent();
                break;
            }
            case NO_MORE_QUESTIONS: {
                setUpNoMoreQuestionsEmptyComponent();
                break;
            }
        }
    }

    private void setUpFirstEmptyComponent() {

        // remove everything
        removeAllComponents();

        // add image
        addComponent(emptyQuestions);
        setComponentAlignment(emptyQuestions, Alignment.TOP_CENTER);

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

    private void setUpCreateATestEmptyComponent() {

        // remove everything
        removeAllComponents();

        // add image
        addComponent(emptyLibrary);
        setComponentAlignment(emptyLibrary, Alignment.TOP_CENTER);

        // label create questions
        Label create = new Label("Create A Test");
        create.addStyleNames(MyTheme.MAIN_TEXT_WEIGHT_900,
                MyTheme.MAIN_TEXT_SIZE_LARGE, MyTheme.MAIN_TEXT_GREEN);

        // description
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Label description = new Label("To create a test click the bottom right");
        description.addStyleName(MyTheme.MAIN_TEXT_SIZE_SMALL);

        // icon
        Label icon = new Label("+");
        icon.addStyleName(MyTheme.MAIN_FLAT_ROUND_BUTTON_MINI);

        // word icon
        Label word = new Label("icon");
        word.addStyleName(MyTheme.MAIN_TEXT_SIZE_SMALL);

        // add components
        horizontalLayout.addComponents(description, icon, word);

        // align
        horizontalLayout.setComponentAlignment(description, Alignment.MIDDLE_LEFT);
        horizontalLayout.setComponentAlignment(icon, Alignment.MIDDLE_LEFT);
        horizontalLayout.setComponentAlignment(word, Alignment.MIDDLE_LEFT);

        // add
        addComponents(create, horizontalLayout);
        setComponentAlignment(create, Alignment.MIDDLE_CENTER);
        setComponentAlignment(horizontalLayout, Alignment.BOTTOM_CENTER);
    }

    private void setUpNoMoreQuestionsEmptyComponent() {

        // remove everything
        removeAllComponents();

        // add image
        addComponent(emptyQuestions);
        setComponentAlignment(emptyQuestions, Alignment.TOP_CENTER);

        Label none = new Label("No More Questions");
        none.addStyleNames(MyTheme.MAIN_TEXT_WEIGHT_900,
                MyTheme.MAIN_TEXT_SIZE_LARGE);

        // add
        addComponents(none);
        setComponentAlignment(none, Alignment.MIDDLE_CENTER);
    }

    private void setUpNoQuestionsFoundEmptyComponent() {

        // remove everything
        removeAllComponents();

        // add image
        addComponent(emptyQuestions);
        setComponentAlignment(emptyQuestions, Alignment.TOP_CENTER);

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