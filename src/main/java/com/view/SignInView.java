package com.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

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

    // route strings - nothing special just things like qbank_exploded_war/route_name
    protected final String question = "question";

    public SignInView(Navigator navigator) {

        // we get the Apps Navigator object
        this.navigator = navigator;

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




        // redirect to dashboard
        Button button = new Button("Sign In", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                navigator.navigateTo(question);
            }
        });
        //.addComponent(verticalLayout);
       // component.addComponents(button);




        // add button component
        addComponents(component);
        setComponentAlignment(component, Alignment.MIDDLE_CENTER);
    }

    private boolean authentication(LoginForm.LoginEvent e) {
        String username = e.getLoginParameter("username");
        String password = e.getLoginParameter("password");
        return username.equals("username") && password.equals("password");
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // Notification.show("Welcome to Qbank");
    }
}
