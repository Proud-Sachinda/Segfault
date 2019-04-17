package com.Components;

import com.MyTheme;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;

import java.io.File;

public class EmptyQuestionsComponent extends VerticalLayout {

    public EmptyQuestionsComponent(String basePath, boolean isFirst) {

        // set up
        setWidth(60.0f, Unit.PERCENTAGE);
        addStyleName(MyTheme.MAIN_FLAT_FLOATING_BOX);

        // create draft image
        FileResource emptyResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/empty-questions.svg"));
        Image empty = new Image(null, emptyResource);
        empty.setWidth(64.0f, Unit.PIXELS);
        empty.setHeight(64.0f, Unit.PIXELS);
        addComponent(empty);
        setComponentAlignment(empty, Alignment.TOP_CENTER);

        // change label depending on first entry
        if (isFirst) {

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
        else {

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
}
