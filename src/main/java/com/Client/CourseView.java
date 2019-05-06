package com.Client;

import com.Dashboard;
import com.MyTheme;
import com.Objects.CourseItem;
import com.Objects.TestItem;
import com.Server.CourseServer;
import com.Server.TestServer;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Connection;
import java.util.ArrayList;

public class CourseView extends HorizontalLayout implements View {

    // navigator used to redirect to another page
    private Navigator navigator;

    // connection for database
    private Connection connection;

    String type = "";

    //Create a button
    private Button export1 = new Button("Export");
    private Button export2 = new Button("Export");
    private Button createcourse1 =  new Button("Add Course");
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

    CourseServer myCourseServer;

    private final VerticalLayout paperExplorer = new VerticalLayout();
    private final VerticalLayout courseList = new VerticalLayout();
    private final Panel courseListPanel = new Panel();
    private final VerticalLayout courseListVerticalLayoutRoot = new VerticalLayout();


    // navigation and content area
    final VerticalLayout navigation = new VerticalLayout();
    final VerticalLayout content = new VerticalLayout();


    public CourseView(Navigator navigator, Connection connection) {

        // we get the Apps Navigator object
        this.navigator = navigator;

        // course server
        this.myCourseServer = new CourseServer(connection);

        // set connection variable
        this.connection = connection;

        // set to fill browser screen
        setSizeFull();

        // set up dashboard
        Dashboard dashboard = new Dashboard(navigator);
        addComponent(dashboard);

        // set content area
        content.setSizeFull();
        content.addStyleName("paper-border");
        addComponentsAndExpand(content);
        setUpCourseList();
    }

    private void setUpCourseList() {

        TestServer testServer = new TestServer(connection);
        ArrayList<TestItem> Items = testServer.getTestItems();

        for(TestItem i : Items) {
            Label labela = new Label(i.getTestDraftName());
            paperExplorer.addComponent(labela);
            
        }


        content.addComponentsAndExpand(paperExplorer);
        paperExplorer.setHeightUndefined();



        sampleinput.addStyleName(MyTheme.MAIN_TEXT_WEIGHT_900);
        sampleoutput.addStyleName(MyTheme.MAIN_TEXT_WEIGHT_900);

        courseListVerticalLayoutRoot.setMargin(new MarginInfo(true, false));
        courseListVerticalLayoutRoot.setWidth(300.0f,Unit.PIXELS);
        //courseListVerticalLayoutRoot.addStyleName(MyTheme.MAIN_BLUE);
        courseListPanel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        addComponent(courseListVerticalLayoutRoot);
        courseListVerticalLayoutRoot.addComponents(createcourse1, courseListPanel);

        courseListPanel.setContent(courseList);
        courseListPanel.setHeight(100.0f, Unit.PERCENTAGE);

        courseList.setWidth(100.0f, Unit.PERCENTAGE);

        ArrayList<CourseItem> list = myCourseServer.getCourseItems();

        for (CourseItem i : list) {

            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.setWidth(100.0f, Unit.PERCENTAGE);
            Label label = new Label(i.getCourseFullName());
            label.addStyleName(MyTheme.MAIN_TEXT_WEIGHT_900);
            horizontalLayout.addComponent(label);
            courseList.addComponent(horizontalLayout);
        }

        createcourse1.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                courseList.addComponent(sampleinput,0);
                courseList.addComponent(sampleoutput, 1);
                courseList.addComponent(wola, 2);
            }
        });

        wola.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

               String input = sampleinput.getValue();
               String  out =  sampleoutput.getValue();

               sampleinput.setValue("");
               sampleoutput.setValue("");

               CourseItem courseItem =  new CourseItem();
               courseItem.setCourseName(input);
               courseItem.setCourseCode(out);

               myCourseServer.PostCourse(courseItem);

                courseList.removeComponent(sampleinput);
                courseList.removeComponent(sampleoutput);
                courseList.removeComponent(wola);

                HorizontalLayout horizontal = new HorizontalLayout();
                horizontal.setWidth(100.0f, Unit.PERCENTAGE);
                Label lab = new Label(courseItem.getCourseFullName());
                lab.addStyleName(MyTheme.MAIN_TEXT_WEIGHT_900);
                horizontal.addComponent(lab);
                courseList.addComponent(horizontal);
            }
        });


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


