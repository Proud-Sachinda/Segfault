package com.Client;


import com.CookieHandling.CookieHandling;
import com.CookieHandling.CookieName;
import com.Dashboard;
import com.Server.LecturerServer;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import com.vaadin.navigator.Navigator;

import javax.servlet.http.Cookie;
import java.sql.Connection;


public class SignUpView extends VerticalLayout implements View {

    // navigator used to redirect to another pag
    private Navigator navigator;
    private Dashboard dashboard;

    //Creating TextField
    private final Button create = new Button("Create Account");
    private final TextField tex1 = new TextField("Lecture Id");
    private final TextField tex2 =  new TextField("Username");
    private final TextField tex3 =  new TextField("Password");
    private final TextField tex4 =  new TextField("Confirm Password");

    public SignUpView(Navigator navigator, Connection connection) {

        // we get the apps Navigator object
        this.navigator = navigator;

        // set to fill browser screen
        setSizeFull();

        // set wallpaper
        addStyleName("main-sign-in-page-background");

        // create login form


        //Creating Layouts
        VerticalLayout verticalLayout =  new VerticalLayout();
        verticalLayout.addComponents(tex1,tex2,tex3,tex4,create);
        verticalLayout.setComponentAlignment(tex1, Alignment.MIDDLE_CENTER);
        verticalLayout.setComponentAlignment(tex2, Alignment.MIDDLE_CENTER);
        verticalLayout.setComponentAlignment(tex3, Alignment.MIDDLE_CENTER);
        verticalLayout.setComponentAlignment(tex4, Alignment.MIDDLE_CENTER);
        verticalLayout.setComponentAlignment(create, Alignment.MIDDLE_CENTER);

        addComponent(verticalLayout);

        create.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                boolean success = false;

                LecturerServer lecturerServer = new LecturerServer(connection);
                lecturerServer.authenticationSignUp(tex1.getValue(),tex2.getValue(),tex3.getValue());

                    //Check if the fields are empty or not
                    if (tex1.getValue().isEmpty()) {
                        success = false;
                    }
                    else success = true;

                    if (tex2.getValue().isEmpty()) {
                        success =  false;
                    }
                    else success = true;
                    if (tex3.getValue().isEmpty()) {
                        success =  false;
                    }
                    else success = true;
                    if (tex4.getValue().isEmpty()) {
                        success = false;
                    }
                    else success = true;

                    if (success) {
                        Notification.show("Success");
                        navigator.navigateTo("editor");
                    }
                    else {
                    Notification.show("Required Field", Notification.Type.ERROR_MESSAGE);
                }
                    System.out.println(tex1.getValue());
                    System.out.println(tex2.getValue());
                    System.out.println(tex3.getValue());
                    System.out.println(tex4.getValue());




            }

        });
    }
}
