package com;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.Views.*;



/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 *
 * !!!
 * following tutorial : https://vaadin.com/docs/v8/framework/advanced/advanced-navigator.html
 */
@Theme("mytheme")
public class MyUI extends UI {



    // navigator is used for changing pages
    Navigator navigator;

    // route strings - nothing special just things like qbank_exploded_war/route_name
    protected final String question = "question";
    protected final String course = "course";
    protected final String export = "export";
    protected final String createquestion = "createquestion";

    String string;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        // set title
        getPage().setTitle("Welcome, Qbank");

        // Create a navigator to control the views
        navigator = new Navigator(this, this);

        // Create and register the views
        navigator.addView("", new SignInView(navigator));
        navigator.addView(question, new QuestionView(navigator));
        navigator.addView(course, new CourseView(navigator));
        navigator.addView(export, new ExportView(navigator));
        navigator.addView(createquestion, new CreateQuestionView(navigator));

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

    }
}
