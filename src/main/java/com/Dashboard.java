package com;

import com.CookieHandling.CookieHandling;
import com.CookieHandling.CookieName;
import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.*;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import javax.servlet.http.Cookie;
import java.io.File;
import java.sql.Connection;

public class Dashboard extends VerticalLayout {

    // connection
    private Connection connection;

    // navigation links
    private final String editorNavigation = "editor";
    private final String libraryNavigation = "library";
    private final String exportNavigation = "export";
    private  final String testNavigation = "test";

    // base path
    private String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    // navigation root
    private VerticalLayout top = new VerticalLayout();
    private VerticalLayout bottom = new VerticalLayout();
    private VerticalLayout center = new VerticalLayout();

    // boxes
    private final VerticalLayout editorLayout = new VerticalLayout();
    private final VerticalLayout libraryLayout = new VerticalLayout();
    private final VerticalLayout exportLayout = new VerticalLayout();
    private final VerticalLayout testLayout = new VerticalLayout();

    // navigation variable
    private Navigator navigator;

    // image resources
    private FileResource logoResource = new FileResource(new File(basePath + "/WEB-INF/img/logo.svg"));
    private FileResource editorResource = new FileResource(new File(basePath + "/WEB-INF/images/create.svg"));
    private FileResource libraryResource = new FileResource(new File(basePath + "/WEB-INF/images/course.svg"));
    private FileResource exportResource = new FileResource(new File(basePath + "/WEB-INF/images/export.svg"));
    private FileResource profileResource = new FileResource(new File(basePath + "/WEB-INF/images/profile.svg"));
    private FileResource signOutResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/nav/sign-out.svg"));

    // navigation icons
    private final Image logo = new Image(null, logoResource);
    private final Image editor = new Image(null, editorResource);
    private final Image library = new Image(null, libraryResource);
    private final Image export = new Image(null, exportResource);
    private final Image profile = new Image(null, profileResource);
    private final Image signOut = new Image(null, signOutResource);

    public Dashboard(Navigator navigator, Connection connection) {

        // we get the apps Navigator object
        this.navigator = navigator;

        // connection
        this.connection = connection;

        // set width and height
        setWidth(64.f, Sizeable.Unit.PIXELS);
        setHeight(100.f, Sizeable.Unit.PERCENTAGE);

        // set colour
        setStyleName(MyTheme.MAIN_BLUE);

        // set margin
        setMargin(false);

        // set icons height and width
        setSize(logo, 54.0f);
        ComponentToolkit.setMultipleImage(48f, Unit.PIXELS, editor, library, export, profile, signOut);

        // set descriptions
        logo.setDescription("Home");
        editor.setDescription("Edit Questions");
        library.setDescription("View Papers");
        export.setDescription("Export Papers");
        profile.setDescription("Edit Profile");
        signOut.setDescription("Sign out");

        // add logo
        top.setMargin(false);
        top.addComponent(logo);
        top.setWidth(64.0f, Unit.PIXELS);
        top.setHeight(64.0f, Unit.PIXELS);
        top.addStyleName(MyTheme.MAIN_CHARCOAL);

        // navigation labels
        Label editorLabel = new Label("Editor");
        Label libraryLabel = new Label("Library");
        Label exportLabel = new Label("Export");

        // make text small
        editorLabel.addStyleNames(MyTheme.MAIN_TEXT_SIZE_SMALL, MyTheme.MAIN_TEXT_WEIGHT_500, MyTheme.MAIN_TEXT_WHITE);
        libraryLabel.addStyleNames(MyTheme.MAIN_TEXT_SIZE_SMALL, MyTheme.MAIN_TEXT_WEIGHT_500, MyTheme.MAIN_TEXT_WHITE);
        exportLabel.addStyleNames(MyTheme.MAIN_TEXT_SIZE_SMALL, MyTheme.MAIN_TEXT_WEIGHT_500, MyTheme.MAIN_TEXT_WHITE);

        // add style names
        editorLayout.setMargin(false);
        libraryLayout.setMargin(false);
        exportLayout.setMargin(false);
        editorLayout.addStyleName(MyTheme.MAIN_NAVIGATION_LINK);
        libraryLayout.addStyleName(MyTheme.MAIN_NAVIGATION_LINK);
        exportLayout.addStyleName(MyTheme.MAIN_NAVIGATION_LINK);

        // add to create layout
        editorLayout.addComponents(editor, editorLabel);
        editorLayout.setComponentAlignment(editor, Alignment.MIDDLE_CENTER);
        editorLayout.setComponentAlignment(editorLabel, Alignment.TOP_CENTER);

        // add to create layout
        libraryLayout.addComponents(library, libraryLabel);
        libraryLayout.setComponentAlignment(library, Alignment.MIDDLE_CENTER);
        libraryLayout.setComponentAlignment(libraryLabel, Alignment.TOP_CENTER);

        // add to create layout
        exportLayout.addComponents(export, exportLabel);
        exportLayout.setComponentAlignment(export, Alignment.MIDDLE_CENTER);
        exportLayout.setComponentAlignment(exportLabel, Alignment.TOP_CENTER);

        // add to nav root then center nav root
        center.setMargin(new MarginInfo(true, false));
        center.addComponents(editorLayout, libraryLayout, exportLayout);

        // create alignment
        center.setComponentAlignment(editorLayout, Alignment.MIDDLE_CENTER);

        // course alignment
        center.setComponentAlignment(libraryLayout, Alignment.MIDDLE_CENTER);

        // export alignment
        center.setComponentAlignment(exportLayout, Alignment.MIDDLE_CENTER);

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

        // profile
        bottom.setComponentAlignment(profile, Alignment.BOTTOM_CENTER);

        // sign out component
        bottom.setComponentAlignment(signOut, Alignment.TOP_CENTER);
    }

    private void addMouseListener() {

        // logo clickable
        logo.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);
        logo.addClickListener((MouseEvents.ClickListener)
                event -> getUI().getPage()
                        .open("https://github.com/Proud-Sachinda/Segfault", "GitHub", false));

        // create clickable
        editor.addStyleNames(MyTheme.MAIN_CONTROL_CLICKABLE);
        editor.addClickListener((MouseEvents.ClickListener) event -> navigator.navigateTo(editorNavigation));

        // course clickable
        library.addStyleNames(MyTheme.MAIN_CONTROL_CLICKABLE);
        library.addClickListener((MouseEvents.ClickListener) event -> navigator.navigateTo(libraryNavigation));

        // export clickable
        export.addStyleNames(MyTheme.MAIN_CONTROL_CLICKABLE);
        export.addClickListener((MouseEvents.ClickListener) event -> navigator.navigateTo(exportNavigation));

        // profile clickable
        profile.addStyleNames(MyTheme.MAIN_CONTROL_CLICKABLE);

        // sign out clickable
        signOut.addStyleNames(MyTheme.MAIN_CONTROL_CLICKABLE, MyTheme.MAIN_NAVIGATION_LINK);
        signOut.addClickListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent clickEvent) {
                    // VaadinSession.getCurrent().close();
                    //SecurityContextHolder.clearContext();
                    navigator.navigateTo("");

            }
        });

        System.out.println(signOut.getData());

        signOut.addClickListener((MouseEvents.ClickListener) event -> {

            CookieHandling.removeCookie(CookieName.AUTH);
            CookieHandling.removeCookie(CookieName.EDIT);
            CookieHandling.removeCookie(CookieName.LIB);
            CookieHandling.removeCookie(CookieName.NAV);

            navigator.navigateTo("");
        });
    }

    public void setActiveLink(String link) {

        // remove existing
        editorLayout.removeStyleName(MyTheme.MAIN_NAVIGATION_LINK);
        libraryLayout.removeStyleName(MyTheme.MAIN_NAVIGATION_LINK);
        exportLayout.removeStyleName(MyTheme.MAIN_NAVIGATION_LINK);

        if (link.equals(editorNavigation)) {
            libraryLayout.addStyleName(MyTheme.MAIN_NAVIGATION_LINK);
            exportLayout.addStyleName(MyTheme.MAIN_NAVIGATION_LINK);
        }
        else if (link.equals(libraryNavigation)) {
            editorLayout.addStyleName(MyTheme.MAIN_NAVIGATION_LINK);
            exportLayout.addStyleName(MyTheme.MAIN_NAVIGATION_LINK);
        }
        else {
            editorLayout.addStyleName(MyTheme.MAIN_NAVIGATION_LINK);
            libraryLayout.addStyleName(MyTheme.MAIN_NAVIGATION_LINK);
        }
    }
}
