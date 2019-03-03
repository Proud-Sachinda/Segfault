package com.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
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

        // redirect to dashboard
        Button button = new Button("Open Dashboard", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                navigator.navigateTo(question);

            }
        });

        TextField t = new TextField("Username"){
            //@Override
            //public void
        };


        // add button component
        addComponent(button);
        setComponentAlignment(button, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // Notification.show("Welcome to Qbank");
    }
}
