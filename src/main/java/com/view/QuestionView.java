package com.view;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;

@DesignRoot
public class QuestionView extends HorizontalLayout implements View {

    // navigator used to change pages
    private Navigator navigator;

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
    private final AbsoluteLayout absoluteLayout = new AbsoluteLayout();
    private final Button add = new Button("awe");

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
    }

    private void setUpQuestionExplorer() {

        // set up search bar and add
        TextField search = new TextField();
        search.setPlaceholder("Type in a phrase");
        search.addStyleName("main-flat-text-field");
        explore.addComponent(search);

        // set up add button
        add.setIcon(new ExternalResource("../"));
        absoluteLayout.setWidth("48px");
        absoluteLayout.setHeight("48px");
        absoluteLayout.addComponent(add, "right: 48px; bottom: 48px;");
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

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // Notification.show("Question View");
    }
}
