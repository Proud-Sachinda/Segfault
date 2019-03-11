package com.Client;

import com.MyTheme;
import com.Server.QuestionServer;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.sql.*;
import java.util.ArrayList;

@DesignRoot
public class QuestionView extends HorizontalLayout implements View {

    // navigator used to change pages
    private Navigator navigator;

    // connection for database
    private Connection connection;

    // route strings - nothing special just things like qbank_exploded_war/route_name
    protected final String question = "question";
    protected final String course = "course";
    protected final String export = "export";

    // navigation and content area
    private final VerticalLayout navigation = new VerticalLayout();
    private final HorizontalLayout content = new HorizontalLayout();

    // layouts for split panel
    private VerticalLayout paper = new VerticalLayout();
    private VerticalLayout explore = new VerticalLayout();

    // add button absolute
    private final Button add = new Button("+");

    // question server
    private QuestionServer questionServer;
    private ArrayList<QuestionServer.Question> questionArrayList;

    public QuestionView(Navigator navigator, Connection connection) {

        // we get the Apps Navigator object
        this.navigator = navigator;

        // set connection variable
        this.connection = connection;

        // set up question server
        questionServer = new QuestionServer(this.connection);

        // set to fill browser screen
        setSizeFull();

        // set up dashboard
        setUpDashboard();
        // question paper section
        paper.setSizeFull();
        paper.addStyleName("paper-border");

        // set up split
        content.addComponents(paper, explore);

        // set up question explorer
        setUpQuestionExplorer();

        // set up questions
        setUpQuestions();
    }

    @SuppressWarnings("Duplicates")
    private void setUpDashboard() {

        // set navigation size, color
        navigation.setWidth("80px");
        navigation.setHeight(100.0f, Unit.PERCENTAGE);
        navigation.setStyleName(MyTheme.MAIN_BLUE);
        addComponent(navigation);

        // set content area
        content.setSizeFull();
        addComponentsAndExpand(content);
    }

    private void setUpQuestionExplorer() {

        // set up search bar and add
        TextField search = new TextField();
        search.setPlaceholder("Search questions");
        search.addStyleName("main-flat-text-field");
        explore.addComponent(search);

        // set up add button
        add.addStyleNames("main-flat-round-button", "main-flat-round-button-position");
        add.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                navigator.navigateTo("createquestion");
            }
        });
        explore.addComponent(add);

        // set up filtering
        NativeSelect<String> selectOrder = new NativeSelect<>();
        selectOrder.setItems("Recent", "Difficulty", "Date used", "Date published");
        selectOrder.setSelectedItem("Recent");
        NativeSelect<String> selectFilter = new NativeSelect<>();
        selectFilter.setItems("None", "Subject", "Tags");
        selectFilter.setSelectedItem("None");
        HorizontalLayout filtering = new HorizontalLayout();
        Label order = new Label("Order by - ");
        Label filter = new Label("Filter by - ");
        filtering.addComponents(order, selectOrder, filter, selectFilter);
        explore.addComponent(filtering);
    }

    private void setUpQuestions() {

        // get questions
        questionArrayList = questionServer.get();

        // set up root question layout
        VerticalLayout verticalLayoutRoot = new VerticalLayout();
        verticalLayoutRoot.setMargin(false);

        // loop through questions and add to view
        for(QuestionServer.Question q : questionArrayList) {

            // set up and add horizontal layout for difficulty badge, question, date
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.setWidth(100.0f, Unit.PERCENTAGE);
            Label difficulty = new Label(q.getQuestionDifficulty());
            difficulty.addStyleNames("main-flat-badge-icon", "main-blue");
            Label question = new Label(q.getQuestionBody());
            Label date = new Label(q.getQuestionDate());
            horizontalLayout.addComponent(difficulty);
            horizontalLayout.addComponentsAndExpand(question);
            horizontalLayout.addComponent(date);
            verticalLayoutRoot.addComponent(horizontalLayout);

            // set up and add horizontal layout for course code, course name, tags
            HorizontalLayout horizontalLayoutCourse = new HorizontalLayout();
            horizontalLayoutCourse.setWidth(100.0f, Unit.PERCENTAGE);
            Label code = new Label("COMS3004");
            code.addStyleName(MyTheme.MAIN_GREY_LABEL);
            Label subject = new Label("Computer Networks");
            horizontalLayoutCourse.addComponents(code, subject);
            verticalLayoutRoot.addComponent(horizontalLayoutCourse);

        }

        // add root question layout
        explore.addComponent(verticalLayoutRoot);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //
        // Notification.show(string);
    }
}
