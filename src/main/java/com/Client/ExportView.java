package com.Client;

import com.Dashboard;
import com.Objects.QuestionItem;
import com.Objects.TrackItem;
import com.Server.ExportServer;
import com.Server.QuestionServer;
import com.vaadin.data.Binder;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.util.ArrayList;

public class ExportView extends HorizontalLayout implements View {

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
    final HorizontalLayout coursecode = new HorizontalLayout();
    final HorizontalLayout topicname = new HorizontalLayout();
    final HorizontalLayout date = new HorizontalLayout();
    final HorizontalLayout yos = new HorizontalLayout();
    final HorizontalLayout degree = new HorizontalLayout();
    final HorizontalLayout faculties = new HorizontalLayout();
    final HorizontalLayout internalexaminer = new HorizontalLayout();
    final HorizontalLayout externalexaminer = new HorizontalLayout();
    final HorizontalLayout material = new HorizontalLayout();
    final HorizontalLayout time = new HorizontalLayout();
    final HorizontalLayout topic = new HorizontalLayout();
    final HorizontalLayout instructions = new HorizontalLayout();
    final HorizontalLayout mark = new HorizontalLayout();
    final Label lblcoursecode = new Label("Course of Topic No(s)");
    final Label lbltopicname = new Label("Course or topic name(s)\n" +
            "Paper Number & title");
    final Label lbldate = new Label("Test to be\n" +
            "held during the months(s) of");
    final Label lblyos = new Label("Year of Study");
    final Label lbldegree = new Label("Degrees/Diplomas for which\n" +
            "this course is prescribed");
    final Label lblfaculties = new Label("Faculty/ies presenting\n" +
            "candidates");
    final Label lblinternalexaminer = new Label("Internal examiner(s)\n" +
            "and telephone extension\n" +
            "number(s)");
    final Label lblexternalexaminer = new Label("External examiner(s)");
    final Label lblmaterial = new Label("Special materials required");
    final Label lbltime = new Label("Time allowance");
    final Label lblinstructions = new Label("Instructions");
    final Label lblmark = new Label("Mark");
    final TextField txtcoursecode = new TextField();
    final TextField txttopicname = new TextField();
    final TextField txtdate = new TextField();
    final TextField txtyos = new TextField();
    final TextField txtdegree = new TextField();
    final TextField txtfaculties = new TextField();
    final TextField txtinternalexaminer = new TextField();
    final TextField txtexternalexaminer = new TextField();
    final TextField txtmaterial = new TextField();
    final TextField txttime = new TextField();
    final TextField txtmark = new TextField();
    final TextArea txtinstructions = new TextArea();
    final Button exe = new Button("export");
    final Binder<ExportView> binder = new Binder<>();


    public ExportView(Navigator navigator, Connection connection) {

        // we get the Apps Navigator object
        this.navigator = navigator;

        // set connection variable
        this.connection = connection;

        // set to fill browser screen
        setSizeFull();


        //click listener

        exe.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                method();


            }
        });



        // set up dashboard
        Dashboard dashboard = new Dashboard(navigator);
        addComponent(dashboard);

        //add components under each respective layout
        coursecode.addComponents(lblcoursecode, txtcoursecode);
        txtcoursecode.setRequiredIndicatorVisible(true);
        txttopicname.setRequiredIndicatorVisible(true);
        txtdate.setRequiredIndicatorVisible(true);
        txtyos.setRequiredIndicatorVisible(true);
        txtdegree.setRequiredIndicatorVisible(true);
        txtfaculties.setRequiredIndicatorVisible(true);
        txtinternalexaminer.setRequiredIndicatorVisible(true);
        txtexternalexaminer.setRequiredIndicatorVisible(true);
        txtmark.setRequiredIndicatorVisible(true);
        txttime.setRequiredIndicatorVisible(true);
        topic.addComponents(lbltopicname, txttopicname);
        date.addComponents(lbldate, txtdate);
        yos.addComponents(lblyos, txtyos);
        degree.addComponents(lbldegree, txtdegree);
        faculties.addComponents(lblfaculties, txtfaculties);
        internalexaminer.addComponents(lblinternalexaminer, txtinternalexaminer);
        externalexaminer.addComponents(lblexternalexaminer, txtexternalexaminer);
        material.addComponents(lblmaterial, txtmaterial);
        time.addComponents(lbltime, txttime);
        mark.addComponents(lblmark, txtmark);
        instructions.addComponents(lblinstructions, txtinstructions);
        content.addComponents(coursecode, topic, date, yos, degree, faculties, internalexaminer, externalexaminer, mark, material, time, instructions);

        // set content area
        //content.addComponent(exe);
        content.setSizeFull();
        addComponentsAndExpand(content, exe);
    }

    //methodfor retrieving question
    public QuestionItem retreivequestion(TrackItem t) {
        int id = t.getQuestionId();
        QuestionServer questionServer = new QuestionServer(connection);
        return questionServer.getQuestionItemById(id);
    }
    public void method() {
        String str = "Hello";

        File f = new File("C:\\Users\\User\\Desktop\\aaa");
        try{
            if(f.mkdir()) {
                System.out.println("Directory Created");
                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\User\\Desktop\\aaa\\hi.tex"));
                writer.write(str);
                writer.close();
                writer.close();
            } else {
                System.out.println("Directory is not created");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //method for retreiving tracks
    public ArrayList<TrackItem> retreivetracks() {
        ExportServer exportServer = new ExportServer(connection);
        ArrayList<TrackItem> tracks = exportServer.get(29);
        System.out.println(tracks);
        return tracks;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // Notification.show("Export View");
    }


}

