package com.view;

import com.Controllers.QuestionController;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.ArrayList;

@DesignRoot
public class QuestionView extends HorizontalLayout implements View {

    // navigator used to change pages
    private Navigator navigator;

    // route strings - nothing special just things like qbank_exploded_war/route_name
    protected final String question = "question";
    protected final String course = "course";
    protected final String export = "export";

    // question controller - used to populate view
    private final QuestionController questionController = new QuestionController();
    private ArrayList<QuestionController.Question> questionArrayList;

    // navigation and content area
    private final VerticalLayout navigation = new VerticalLayout();
    private final HorizontalLayout content = new HorizontalLayout();

    // layouts for split panel
    private VerticalLayout paper = new VerticalLayout();
    private VerticalLayout explore = new VerticalLayout();

    // add button absolute
    private final Button add = new Button("+");

    public QuestionView(Navigator navigator) {

        // we get the Apps Navigator object
        this.navigator = navigator;

        // set to fill browser screen
        setSizeFull();

        // set up dashboard
        setUpDashboard();

        // question paper section
        paper.addComponent(new Label("awe"));
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
        navigation.setStyleName("main-blue");
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

        // set up root question layout
        VerticalLayout verticalLayoutRoot = new VerticalLayout();
        verticalLayoutRoot.setMargin(false);

        // set up and add horizontal layout for difficulty badge, question, date
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth(100.f, Unit.PERCENTAGE);
        Label difficulty = new Label("5");
        difficulty.addStyleNames("main-flat-badge-icon", "main-blue");
        Label question = new Label("What is a Client Program? What is a Server Program? Does a Server Program request");
        Label date = new Label("13 Mar 2018");
        horizontalLayout.addComponent(difficulty);
        horizontalLayout.addComponentsAndExpand(question);
        horizontalLayout.addComponent(date);
        verticalLayoutRoot.addComponent(horizontalLayout);

        // add root question layout
        explore.addComponent(verticalLayoutRoot);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // Notification.show("Question View");
    }
}
