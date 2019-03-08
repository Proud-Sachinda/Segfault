package com.view;

import com.Models.person;
import com.Models.personService;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.flow.component.select.Select;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
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
        setUpPeople();

        /////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////
    }


    private void setUpPeople(){
        NativeSelect<String> selectPeople = new NativeSelect<>();
       // selectPeople.setItems(personService.findAll().toString());
        //selectPeople.setSelectedItem();


        HorizontalLayout filtering = new HorizontalLayout();
        Label order = new Label("Order by - ");

        filtering.addComponents(order, selectPeople);
        paper.addComponent(filtering);
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

        ///////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////
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

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // Notification.show("Question View");
    }
}
