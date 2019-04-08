package com;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.Client.*;

import java.sql.*;

/**
 * This UI is the application entry point. QuestionItemComponent UI may either represent a browser window
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

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        // set title
        getPage().setTitle("Welcome, Qbank");

        // create a navigator to control the views
        Navigator navigator = new Navigator(this, this);

        // create connection variable and pass to views
        //Using a connecion string, this is the most important connection to the database
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "postgres");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // route strings - nothing special just things like qbank_exploded_war/#!route_name
        String question = "question";
        String course = "course";
        String export = "export";
        String createquestion = "createquestion";

        if (connection != null) {
            // create and register the views
            navigator.addView("", new SignInView(navigator, connection));
            navigator.addView(question, new QuestionView(navigator, connection));
            navigator.addView(course, new CourseView(navigator, connection));
            navigator.addView(export, new ExportView(navigator, connection));
            navigator.addView(createquestion, new CreateQuestionView(navigator, connection));
        }
        else {
            Notification.show("Reload Page");
        }
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

    }
}
