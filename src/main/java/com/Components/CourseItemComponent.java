package com.Components;

import com.MyTheme;
import com.Objects.CourseItem;
import com.Server.CourseServer;
import com.Server.CourseViewServer;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CourseItemComponent extends VerticalLayout {

    //attributes
    private int courseId;
    private String courseCode;
    private String courseName;

    // base path
    private String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();



    //Components
    private HorizontalLayout firstRow = new HorizontalLayout();
    private HorizontalLayout thirdRow = new HorizontalLayout();
    private VerticalLayout seeMoreComponent = new VerticalLayout();

    String courseN = "";
    String courseC = "";



    // course view server
    private CourseViewServer courseViewServer;

    public CourseItemComponent(CourseViewServer courseViewServer, Connection connection){
        //String courseN = "";

        CourseViewServer server = new CourseViewServer(connection);
        ArrayList<CourseItem> courses = server.getCourses();




            try{

                Statement statement = connection.createStatement();
                //courseN = statement.executeQuery(query);
                String query = "SELECT course_name,course_code FROM public.course ORDER BY course_id DESC LIMIT 1";

                ResultSet set = statement.executeQuery(query);
                //courseC =  statement.executeQuery(query);

                while(set.next()) {
                    courseN =  set.getString("course_name");
                    courseC = set.getString("course_code");

                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
            //return courseN;

        // initialise course view server
        this.courseViewServer = courseViewServer;

        setSizeFull();

        seeMoreComponent.addStyleName("esmond");
        setUpCourseItemComponent();
        setUpSeeMoreComponent();
        addComponent(seeMoreComponent);
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    private String getCourseCode() {
        return this.courseCode;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseViewServer.getCourseItemById(courseCode).getCourseCode();
    }

    private String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(int courseCode) {
        this.courseName = courseViewServer.getCourseItemById(courseCode).getCourseName();
    }


    public CourseItemComponent getThisCourseItemComponent() {
        return this;
    }


    public void setUpCourseItemComponent(){

        // set margin to false
        setMargin(false);

        // first row ------------------------------------------------------
        // set up and add horizontal layout for difficulty badge, question, date
        firstRow.setWidth(100.0f, Unit.PERCENTAGE);
        //firstRow.addStyleName("lizo");


        // add first row to ui
        addComponent(firstRow);

        // third row ------------------------------------------------------
        // set up subject label, tags and question marks
        Label courseCode = new Label(getCourseCode());
        courseCode.setValue(courseN + " " + courseC);
        Label subject = new Label(getCourseName());
        thirdRow.addComponents(courseCode, subject);


        // add third row to ui
        setUpSeeMoreComponent();
        addComponent(thirdRow);


    }

    public void setUpSeeMoreComponent() {

        // root of see more component
        seeMoreComponent.setMargin(false);

        // add answer row
        HorizontalLayout answerRow = new HorizontalLayout();
        Label ans = new Label(this.courseName);
        Label code =  new Label(this.courseCode);
        ans.addStyleName("esmond");
        answerRow.addComponents(ans,code);
        answerRow.addStyleName("esmond");
        seeMoreComponent.addStyleName("esmond");
        setWidth(100.0f,Unit.PIXELS);
        setHeight(100.0f,Unit.PIXELS);
        seeMoreComponent.addComponentsAndExpand(answerRow);

        //System.out.println(answerRow.getComponentCount());
        System.out.println(courseN);
        System.out.println(courseC);

    }



}
