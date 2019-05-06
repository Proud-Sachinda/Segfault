package com.Components;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public CourseItemComponent(Connection connection){




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

    private String getCourseName() {
        return this.courseName;
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
