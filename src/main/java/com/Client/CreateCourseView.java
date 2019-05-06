package com.Client;

import com.Components.CourseItemComponent;
import com.Objects.CourseItem;
import com.Server.CourseServer;
import com.Server.CourseServer.Course;
import com.Server.CourseViewServer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import static javax.print.attribute.standard.MediaSize.Engineering.C;
import static org.postgresql.jdbc2.EscapedFunctions.INSERT;

public class CreateCourseView extends HorizontalLayout implements View {

    // navigator used to redirect to another page
    private Navigator navigator;

    // connection for database
    public Connection connection;

    // route strings - nothing special just things like qbank_exploded_war/route_name
    //protected final String Course = "Course";

    private Date qdate = new Date();


    private TextField addcourse = new TextField("Course Name");
    private TextField coursecode  = new TextField("Course Code");

    protected final String course = "course";



    // navigation and content area
    private final VerticalLayout navigation = new VerticalLayout();
    private final VerticalLayout content = new VerticalLayout();
    private final HorizontalLayout h1 = new HorizontalLayout();
    private final HorizontalLayout h2 =  new HorizontalLayout();

    //private Button addnew = new Button("Add New Course");
    private Button addChoice = new Button("Add");

    String tp = "";


    public CreateCourseView(Navigator navigator, Connection connection){



        // we get the Apps Navigator object
        this.navigator = navigator;

        // set connection variable
        this.connection = connection;

        // set to fill browser screen
        setSizeFull();

        // set up dashboard
        setUpDashboard();

        // code after the dashboard is setup
        VerticalLayout V1 = new VerticalLayout();
        VerticalLayout V2 = new VerticalLayout();
        V1.setSizeFull();
        V2.setSizeFull();
        VerticalLayout vec = new VerticalLayout();
        vec.setMargin(false);


        HorizontalLayout lay = new HorizontalLayout();
       // lay.addComponents(addnew);

        addcourse.setWidth("40%");
        addcourse.setHeight("40px");
        coursecode.setHeight("40px");
        coursecode.setWidth("40%");
       /* addnew.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                vec.removeAllComponents();
                vec.addComponentsAndExpand(addcourse,coursecode);
                tp = "addnew";

            }
        });
        */

        addChoice.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

               //// CourseViewServer courseServer = new CourseViewServer(connection);
               // ArrayList<CourseItem> q = courseServer.getCourses();


               // CourseItemComponent blah = new CourseItemComponent(courseServer, connection);
              //  blah.setUpSeeMoreComponent();
              //  V1.addComponent(blah);

                CourseServer courseServer =  new CourseServer(connection);
                CourseServer.Course q = (CourseServer.Course) courseServer.getCourse();

                q.setCourseName(addcourse.getValue());
                q.setCourseCode(coursecode.getValue());




                if (courseServer.PostCourse(q)) {
                    Notification.show("Success");
                    navigator.navigateTo(course);
                }
                else {
                    Notification.show("Error submitting form");
                }

            }
        });

        content.addComponents(addcourse,coursecode,addChoice);


    }
    @SuppressWarnings("Duplicates")
    private void setUpDashboard() {

        // set navigation size, color
        navigation.setWidth("80px");
        navigation.setHeight(100.0f, Unit.PERCENTAGE);
        navigation.setStyleName("main-blue");
        addComponent(navigation);

        // set content area
        addComponentsAndExpand(content);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        addcourse.setValue("");
        coursecode.setValue("");
    }
}
