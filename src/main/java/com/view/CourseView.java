package com.view;

import com.Models.person;
import com.Models.personService;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import java.util.List;
import java.util.stream.Collectors;

public class CourseView extends HorizontalLayout implements View {

    // navigator used to redirect to another page
    private Navigator navigator;

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





    public CourseView(Navigator navigator) {

        // we get the Apps Navigator object
        this.navigator = navigator;

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
        addComponent(content);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // Notification.show("Course");
    }
}
