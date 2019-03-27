package com;

import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.ui.*;

import java.io.File;

public class Dashboard extends VerticalLayout {

    // navigation links
    private final String questionNavigation = "question";
    private final String courseNavigation = "course";
    private final String exportNavigation = "export";

    // base path
    private String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    // navigation root
    private VerticalLayout nav = new VerticalLayout();

    // navigation variable
    private Navigator navigator;

    // image resources
    private FileResource logoResource = new FileResource(new File(basePath + "/WEB-INF/img/logo.svg"));
    private FileResource createResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/nav/create.svg"));
    private FileResource courseResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/nav/course.svg"));
    private FileResource exportResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/nav/export.svg"));
    private FileResource profileResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/nav/profile.svg"));
    private FileResource signOutResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/nav/sign-out.svg"));

    // navigation icons
    private final Image logo = new Image("", logoResource);
    private final Image create = new Image("", createResource);
    private final Image course = new Image("", courseResource);
    private final Image export = new Image("", exportResource);
    private final Image profile = new Image("", profileResource);
    private final Image signOut = new Image("", signOutResource);

    public Dashboard(Navigator navigator) {

        // we get the apps Navigator object
        this.navigator = navigator;

        // set width and height
        setWidth(80.f, Sizeable.Unit.PIXELS);
        setHeight(100.f, Sizeable.Unit.PERCENTAGE);

        // set colour
        setStyleName(MyTheme.MAIN_BLUE);

        // set margin
        setMargin(false);

        // set icons height and width
        setSize(logo, 64.0f);
        setSize(create, 48.0f);
        setSize(course, 48.0f);
        setSize(export, 48.f);
        setSize(profile, 48.f);
        setSize(signOut, 48.0f);

        // add to nav root then center nav root
        nav.setMargin(false);
        nav.addComponent(create);
        nav.addComponent(course);
        nav.addComponent(export);

        // add links
        addComponent(logo);
        addComponentsAndExpand(nav);
        addComponent(profile);
        addComponent(signOut);

        // align to center
        alignComponents();

        // show mouse pointer
        addMouseListener();
    }

    private void setSize(Image image, float f) {

        // logo size
        image.setWidth(f, Unit.PIXELS);
        image.setHeight(f, Unit.PIXELS);
    }

    private void alignComponents() {

        // logo component
        setComponentAlignment(logo, Alignment.TOP_CENTER);

        // create alignment
        nav.setComponentAlignment(create, Alignment.BOTTOM_CENTER);

        // course alignment
        nav.setComponentAlignment(course, Alignment.MIDDLE_CENTER);

        // export alignment
        nav.setComponentAlignment(export, Alignment.TOP_CENTER);

        // profile
        setComponentAlignment(profile, Alignment.BOTTOM_CENTER);

        // sign out component
        setComponentAlignment(signOut, Alignment.TOP_CENTER);
    }

    private void addMouseListener() {

        // logo clickable
        logo.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);

        // create clickable
        create.addStyleNames(MyTheme.MAIN_CONTROL_CLICKABLE, MyTheme.MAIN_NAVIGATION_LINK);
        create.addClickListener((MouseEvents.ClickListener) event -> navigator.navigateTo(questionNavigation));

        // course clickable
        course.addStyleNames(MyTheme.MAIN_CONTROL_CLICKABLE, MyTheme.MAIN_NAVIGATION_LINK);
        course.addClickListener((MouseEvents.ClickListener) event -> navigator.navigateTo(courseNavigation));

        // export clickable
        export.addStyleNames(MyTheme.MAIN_CONTROL_CLICKABLE, MyTheme.MAIN_NAVIGATION_LINK);
        export.addClickListener((MouseEvents.ClickListener) event -> navigator.navigateTo(exportNavigation));

        // profile clickable
        profile.addStyleNames(MyTheme.MAIN_CONTROL_CLICKABLE);

        // sign out clickable
        signOut.addStyleNames(MyTheme.MAIN_CONTROL_CLICKABLE, MyTheme.MAIN_NAVIGATION_LINK);
    }
}
