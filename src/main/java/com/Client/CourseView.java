package com.Client;

import com.Components.CourseItemComponent;
import com.Dashboard;
import com.Objects.CourseItem;
import com.Components.CourseItemComponent;
import com.Server.CourseServer;
import com.Server.CourseViewServer;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.w3c.dom.Text;

import javax.swing.*;
import javax.validation.constraints.Null;
import java.sql.Connection;
import java.util.ArrayList;

public class CourseView extends HorizontalLayout implements View {

    // navigator used to redirect to another page
    private Navigator navigator;

    // connection for database
    private Connection connection;

    String type = "";

    private CourseViewServer courseServer;

    //Create a button
    private Button export1 = new Button("Export");
    private Button export2 = new Button("Export");
    private Button createcourse1 =  new Button("CreateCourseView");
    private Button wola = new Button("Add");

    private TextField sampleinput = new TextField("Add Course Name");
    private TextField sampleoutput = new TextField("Add Course Id");


    // route strings - nothing special just things like qbank_exploded_war/route_name
    protected final String question = "question";
    protected final String course = "course";
    protected final String export = "export";
    protected final String createcourse = "createcourse";


    HorizontalLayout choice = new HorizontalLayout();
    HorizontalLayout lay = new HorizontalLayout();


    TextField text = new TextField();





    // navigation and content area
    final VerticalLayout navigation = new VerticalLayout();
    final VerticalLayout content = new VerticalLayout();


    public CourseView(Navigator navigator, Connection connection) {

        // we get the Apps Navigator object
        this.navigator = navigator;

        // set connection variable
        this.connection = connection;

        // set to fill browser screen
        setSizeFull();

        // set up dashboard
        Dashboard dashboard = new Dashboard(navigator);
        addComponent(dashboard);

        // set content area
        content.setSizeFull();
        addComponentsAndExpand(content);
        setUpPeople();
    }


    ////////////////////////////////////////////////////////////////////////////////////
    private void setUpPeople() {
        NativeSelect<String> selectPeople = new NativeSelect<>();
        // selectPeople.setItems(personService.findAll().toString());
        //selectPeople.setSelectedItem();


        HorizontalLayout filtering = new HorizontalLayout();

        VerticalLayout panelContent = new VerticalLayout();

        VerticalLayout verticalLayout = new VerticalLayout();
        panelContent.setSizeFull();
        verticalLayout.setSizeFull();
        verticalLayout.addComponents(export1);
        verticalLayout.addComponents(new Button(((VaadinIcons.SHARE))));

        VerticalLayout vertical = new VerticalLayout();
        vertical.setMargin(false);
        verticalLayout.addStyleName("lizo");
        vertical.addStyleName("lizo");
        verticalLayout.addComponents(new Panel("Muntu"));
        vertical.addComponents(new TextArea("Hello"));



        // filtering.addComponents(selectPeople);
        content.addComponent(filtering);
        filtering.addComponents(createcourse1);


        createcourse1.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                CourseItemComponent blah = new CourseItemComponent(courseServer, connection);
                blah.setUpSeeMoreComponent();
                filtering.addComponent(blah);
               navigator.navigateTo(createcourse);
            }
        });


    }

   /* public  void setUpCourse(){

        // get questions
        ArrayList<CourseItem> courseArrayList = courseServer.getCourses();

        // set up root question layout
        VerticalLayout verticalLayoutRoot = new VerticalLayout();
        verticalLayoutRoot.setMargin(false);


        // loop through questions and add to view
        for (CourseItem q : courseArrayList) {

            // declare new question item component
            CourseItemComponent courseItemComponent = new CourseItemComponent(courseServer);
            // set variables
            courseItemComponent.setCourseId(q.getCourseId());
            courseItemComponent.setCourseCode(q.getCourseId());
            courseItemComponent.setCourseName(q.getCourseId());

            // set up question item component
            courseItemComponent.setUpCourseItemComponent();

            // add to vertical layout root
            verticalLayoutRoot.addComponent(courseItemComponent.getThisCourseItemComponent());
        }

    }*/


    private void showButtonClickedMessage(Button.ClickEvent clickEvent) {


    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {


        //panel.addComponentDetachListener(export);
        // Notification.show("Course");
    }

    private class CourseComponent extends VerticalLayout {

        private String CourseName;
        private  String CourseCode;

        private String getCourseName(){ return  CourseName;}
        private  String getCourseCode(){ return  CourseCode;}

        private void setCourseName( String CourseName){
            this.CourseName = CourseName;
        }

        private void setCourseCode(String CourseCode){
            this.CourseCode = CourseCode;
        }

    }

}


