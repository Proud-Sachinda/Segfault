package com.Client;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

import java.sql.Connection;

/**
 * This is the first page the app shows
 *
 * For now this page will just have a button that redirects
 * to the Dashboard
 *
 * Later onwards we will use this page as the Sign In page
 *
 * to read more
 * following tutorial : https://vaadin.com/docs/v8/framework/advanced/advanced-navigator.html
 */

public class SignInView extends VerticalLayout implements View {

    // navigator used to redirect to another page
    private Navigator navigator;

    // connection used for database
    private Connection connection;

    // route strings - nothing special just things like qbank_exploded_war/route_name
    protected final String question = "question";

    public SignInView(Navigator navigator, Connection connection) {

        // we get the apps Navigator object
        this.navigator = navigator;

        // connector object for database
        this.connection = connection;

        // set to fill browser screen
        setSizeFull();

        // set wallpaper
        addStyleName("main-sign-in-page-background");

        // create login form
        LoginForm component = new LoginForm();
        component.addLoginListener(e -> {
            boolean isAuthenticated = authentication(e);
            if (isAuthenticated) {
                navigator.navigateTo(question);

            } else {
                Notification.show("Incorrect credentials", Notification.Type.ERROR_MESSAGE);
            }

        });

        // add button component
        addComponents(component);
        setComponentAlignment(component, Alignment.MIDDLE_CENTER);
    }

    private boolean authentication(LoginForm.LoginEvent e) {
        String username = e.getLoginParameter("username");
        String password = e.getLoginParameter("password");
        return username.equals("username") && password.equals("password");
    }
}
