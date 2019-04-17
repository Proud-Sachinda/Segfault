package com;

import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.*;
import com.vaadin.shared.ui.MarginInfo;
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
    private VerticalLayout top = new VerticalLayout();
    private VerticalLayout bottom = new VerticalLayout();
    private VerticalLayout center = new VerticalLayout();

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
    private final Image logo = new Image(null, logoResource);
    private final Image create = new Image("", createResource);
    private final Image course = new Image("", courseResource);
    private final Image export = new Image("", exportResource);
    private final Image profile = new Image("", profileResource);
    private final Image signOut = new Image(null, signOutResource);

    public Dashboard(Navigator navigator) {

        // we get the apps Navigator object
        this.navigator = navigator;

        // set width and height
        setWidth(64.f, Sizeable.Unit.PIXELS);
        setHeight(100.f, Sizeable.Unit.PERCENTAGE);

        // set colour
        setStyleName(MyTheme.MAIN_BLUE);

        // set margin
        setMargin(false);

        // set icons height and width
        setSize(logo, 54.0f);
        setSize(create, 48.0f);
        setSize(course, 48.0f);
        setSize(export, 48.f);
        setSize(profile, 48.f);
        setSize(signOut, 48.0f);

        // set descriptions
        logo.setDescription("Home");
        create.setDescription("View Questions");
        course.setDescription("View Papers");
        export.setDescription("Export Papers");
        profile.setDescription("Edit Profile");
        signOut.setDescription("Sign out");

        // add logo
        top.setMargin(false);
        top.addComponent(logo);
        top.setWidth(64.0f, Unit.PIXELS);
        top.setHeight(64.0f, Unit.PIXELS);
        top.addStyleName(MyTheme.MAIN_CHARCOAL);

        // add to nav root then center nav root
        center.setMargin(new MarginInfo(true, false));
        center.addComponents(create, course, export);
        VerticalLayout nav = new VerticalLayout();
        nav.setMargin(false);
        nav.addComponent(center);
        nav.setComponentAlignment(center, Alignment.MIDDLE_CENTER);

        // add profile and sign out
        bottom.setMargin(new MarginInfo(false));
        bottom.addComponents(profile, signOut);

        // add links
        addComponent(top);
        addComponentsAndExpand(nav);
        addComponent(bottom);

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
        top.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);

        // create alignment
        center.setComponentAlignment(create, Alignment.MIDDLE_CENTER);

        // course alignment
        center.setComponentAlignment(course, Alignment.MIDDLE_CENTER);

        // export alignment
        center.setComponentAlignment(export, Alignment.MIDDLE_CENTER);

        // profile
        bottom.setComponentAlignment(profile, Alignment.BOTTOM_CENTER);

        // sign out component
        bottom.setComponentAlignment(signOut, Alignment.BOTTOM_CENTER);
    }

    private void addMouseListener() {

        // logo clickable
        logo.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);
        logo.addClickListener((MouseEvents.ClickListener)
                event -> getUI().getPage().open("https://github.com/Proud-Sachinda/Segfault", "GitHub", false));

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
