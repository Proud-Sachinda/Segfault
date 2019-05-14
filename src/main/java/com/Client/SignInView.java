package com.Client;

import com.CookieHandling.CookieHandling;
import com.CookieHandling.CookieName;
import com.Server.LecturerServer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;

import javax.servlet.http.Cookie;
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

    // server
    private LecturerServer lecturerServer;

    public SignInView(Navigator navigator, Connection connection) {

        // we get the apps Navigator object
        this.navigator = navigator;

        // initiate server
        this.lecturerServer = new LecturerServer(connection);

        // set to fill browser screen
        setSizeFull();

        // set wallpaper
        addStyleName("main-sign-in-page-background");

        // create login form
        LoginForm component = new LoginForm();

        // component listener
        component.addLoginListener((LoginForm.LoginListener) event -> {

            // authentication variable
            boolean auth = lecturerServer.authenticateLecturer(event.getLoginParameter("username").trim());

            if (auth) {
                // navigate to page
                navigator.navigateTo("editor");

            } else Notification.show("Incorrect Credentials", Notification.Type.ERROR_MESSAGE);
        });

        // add button component
        addComponents(component);
        setComponentAlignment(component, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        // set page title
        UI.getCurrent().getPage().setTitle("Welcome, Question Bank");

        // check if there is a message
        String message = (String) VaadinService.getCurrentRequest().getAttribute("message");
        VaadinService.getCurrentRequest().removeAttribute("message");

        // if message is not null
        if (message != null) Notification.show(message, Notification.Type.ERROR_MESSAGE);

        // if lecturer has been signed in navigate
        Cookie auth = CookieHandling.getCookieByName(CookieName.AUTH);

        if (auth != null) {

            // check if nav cookie exists
            Cookie nav = CookieHandling.getCookieByName("nav");

            // navigate to question page by default or nav
            if (nav != null) navigator.navigateTo(nav.getValue());
            else navigator.navigateTo("editor");
        }
    }
}
