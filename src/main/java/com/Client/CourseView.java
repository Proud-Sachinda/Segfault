package com.Client;

import com.Dashboard;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.w3c.dom.Text;

import javax.swing.*;
import javax.validation.constraints.Null;
import java.sql.Connection;

public class CourseView extends HorizontalLayout implements View {

    // navigator used to redirect to another page
    private Navigator navigator;

    // connection for database
    private Connection connection;

    String type = "";

    //Create a button
    private Button export1 = new Button("Export");
    private Button export2 = new Button("Export");
    private Button createcourse =  new Button("CreateCourse");
    private Button wola = new Button("Add");

    private TextField sampleinput = new TextField("Add Course Name");
    private TextField sampleoutput = new TextField("Add Course Id");


    // route strings - nothing special just things like qbank_exploded_war/route_name
    protected final String question = "question";
    protected final String course = "course";
    protected final String export = "export";


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
        Panel panel = new Panel();

        VerticalLayout panelContent = new VerticalLayout();
        panel.addStyleName("lizo");

        panelContent.setWidth(100.0f, Unit.PERCENTAGE);
        panelContent.setHeight(100.0f, Unit.PERCENTAGE);


        Label label = new Label();
        Label label2 = new Label();
        label2.setValue("COMS 3009");
        label2.addStyleName("lol");
        label.setValue("Software Design");
        label.addStyleName("lol");
        panelContent.addComponents(label);
        panelContent.addComponents(label2);
        //panelContent.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        //panelContent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);


        panelContent.addComponents(export2, new Button((((VaadinIcons.SHARE)))));
        panel.setContent(panelContent);

        Panel panel1 = new Panel();
        VerticalLayout verticalLayout = new VerticalLayout();

        Label label4 =  new Label();
        Label label5 =  new Label();
        label4.setValue("Real Analysis");
        label5.setValue("MATH");

        panel1.addStyleName("lizo");


        //verticalLayout.addComponents(label4);
        verticalLayout.addComponents(label5,label4);

        verticalLayout.addComponents(export1);
        verticalLayout.addComponents(new Button(((VaadinIcons.SHARE))));
        panel1.setContent(verticalLayout);

// Set the panel as the content of the UI
        //setContent(panel);

/*
        VerticalLayout verticalLayout = new VerticalLayout();
        VerticalLayout vertical = new VerticalLayout();
        verticalLayout.addStyleName("lizo");
        vertical.addStyleName("lizo");
        verticalLayout.addComponents(new Panel("Muntu"));
        vertical.addComponents(new TextArea("Hello"));
*/


        // filtering.addComponents(selectPeople);
        content.addComponent(filtering);
        export1.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                navigator.navigateTo(export);


            }
        });

        export2.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                navigator.navigateTo(export);


            }
        });

        Panel panel3 = new Panel();


        VerticalLayout vertic = new VerticalLayout();

        Label label3 = new Label();
        label3.setValue("Allows you to add course");
        label3.addStyleName("lol");
        vertic.addComponents(label3);


        vertic.addComponents();

        panel3.setContent(vertic);


        //vertic.addStyleName("esmond");

        ////vertic.addComponents(createcourse);

        sampleinput.setWidth("91%");
        sampleinput.setHeight("90px");
        sampleoutput.setHeight("90px");
        sampleoutput.setWidth("91%");


        createcourse.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                vertic.removeAllComponents();
                vertic.addComponentsAndExpand(sampleinput,sampleoutput,wola);
                type = "CreateCourse";

            }
        });

        filtering.addComponents(panel, panel1,panel3, createcourse);



    }


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


