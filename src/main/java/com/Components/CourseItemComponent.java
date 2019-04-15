package com.Components;

import com.Client.CreateCourseView;
import com.MyTheme;
import com.Server.CourseViewServer;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.io.File;

public class CourseItemComponent extends VerticalLayout {

    //attributes
    private int courseId;
    private String courseCode;
    private String courseName;

    // base path
    private String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    // file resource for images
    private FileResource saveResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/save.svg"));
    private FileResource editResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/edit.svg"));
    private FileResource trashResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/trash.svg"));

    // edit trash
    private Image save = new Image(null, saveResource);
    private Image edit = new Image(null, editResource);
    private Image trash = new Image(null, trashResource);

    //Components
    private HorizontalLayout firstRow = new HorizontalLayout();
    private HorizontalLayout thirdRow = new HorizontalLayout();



    // course view server
    private CourseViewServer courseViewServer;

    public CourseItemComponent(CourseViewServer courseViewServer){

        // initialise course view server
        this.courseViewServer = courseViewServer;

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

        // image buttons for question updating and copying
        edit.setWidth(22.0f, Unit.PIXELS);
        edit.setHeight(22.0f, Unit.PIXELS);
        save.setWidth(22.0f, Unit.PIXELS);
        save.setHeight(22.0f, Unit.PIXELS);
        trash.setWidth(22.0f, Unit.PIXELS);
        trash.setHeight(22.0f, Unit.PIXELS);
        edit.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);
        save.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);
        trash.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);


        // set up listeners
       // setUpUpdateCourseClickListeners();

        // add first row to ui
        addComponent(firstRow);

        // third row ------------------------------------------------------
        // set up subject label, tags and question marks
        Label courseCode = new Label(getCourseCode());
        courseCode.addStyleName(MyTheme.MAIN_GREY_LABEL);
        Label subject = new Label(getCourseName());
        thirdRow.addComponents(courseCode, subject);

        // add third row to ui
        addComponent(thirdRow);


    }



}
