package com.Client;

import com.Server.person;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.sql.Connection;

public class CourseView extends HorizontalLayout implements View {

    // navigator used to redirect to another page
    private Navigator navigator;

    // connection for database
    private Connection connection;

    // route strings - nothing special just things like qbank_exploded_war/route_name
    protected final String question = "question";
    protected final String course = "course";
    protected final String export = "export";

    // navigation and content area
    final VerticalLayout navigation = new VerticalLayout();
    final VerticalLayout content = new VerticalLayout();

//////////////////////////////////////////////////////////////////////////
    person myperson = new person();



////////////////////////////////////////////////////////////////////////





    public CourseView(Navigator navigator, Connection connection) {

        // we get the Apps Navigator object
        this.navigator = navigator;

        // set connection variable
        this.connection = connection;

        // set to fill browser screen
        setSizeFull();

        // set up dashboard
        setUpDashboard();

        //////////////////////////////////////////////proud//////check////
        ComboBox<person> comboBox = new ComboBox<>();
       // comboBox.setItemLabelGenerator();

        Grid<person> grid = new Grid<>();
        grid.addColumn(person::getName);
        grid.addColumn(person::getSurname);

        // Grid<personService> grid = new Grid<>();
       //grid.setItems((personService) personService.findAll());
       //grid.addColumn(personService -> person.class.getName()).setCaption("name");

       //grid.addColumn(personService -> person.class.getSurname).setCaption("Surname");
        //////////////////////////////////
        setUpPeople();

        //////////////////////
    }


    ////////////////////////////////////////////////////////////////////////////////////
    private void setUpPeople() {
        NativeSelect<String> selectPeople = new NativeSelect<>();
        // selectPeople.setItems(personService.findAll().toString());
        //selectPeople.setSelectedItem();


        HorizontalLayout filtering = new HorizontalLayout();
        Label order = new Label("Order by - ");

        filtering.addComponents(order, selectPeople);
       // paper.addComponent(filtering);
    }
    ////////////////////////////////////////////////////////////////////////


    @SuppressWarnings("Duplicates")
    private void setUpDashboard() {

        // set navigation size, color
        navigation.setWidth("80px");
        navigation.setHeight(100.0f, Unit.PERCENTAGE);
        navigation.setStyleName("main-blue");
        addComponent(navigation);

        // set content area
        content.setSizeFull();
        addComponent(content);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // Notification.show("Course");
    }
}
