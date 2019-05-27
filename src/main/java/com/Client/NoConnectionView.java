package com.Client;

import com.AttributeHandling;
import com.NavigationStates;
import com.Objects.LecturerItem;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.io.File;
import java.sql.Connection;

public class NoConnectionView extends VerticalLayout implements View {

    // navigator
    private Navigator navigator;

    // connection
    private Connection connection;

    // attribute
    private AttributeHandling attributeHandling;

    public NoConnectionView(Navigator navigator, Connection connection, AttributeHandling attributeHandling) {

        // init
        this.navigator = navigator;
        this.connection = connection;
        this.attributeHandling = attributeHandling;

        // set margin
        setMargin(false);

        // set size full
        setSizeFull();

        // set default component alignment
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        // no connection file resource
        String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        FileResource noResource = new FileResource(new File(basePath + "/WEB-INF/images/no-connection.svg"));

        // no connection image
        Image no = new Image(null, noResource);
        no.setWidth(360f, Unit.PIXELS);
        no.setHeight(314f, Unit.PIXELS);

        // cover
        VerticalLayout cover = new VerticalLayout();
        cover.setWidthUndefined();
        cover.setMargin(new MarginInfo(false, true, true, false));

        // add everything
        cover.addComponent(no);
        cover.setComponentAlignment(no, Alignment.MIDDLE_CENTER);
        addComponent(cover);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        // set page title
        UI.getCurrent().getPage().setTitle("No connection");

        // get lecturer item
        LecturerItem item = attributeHandling.getLecturerItem();

        if (connection != null) {
            if (item != null) navigator.navigateTo(NavigationStates.EDITOR);
            else navigator.navigateTo(NavigationStates.HOME);
        }
    }
}
