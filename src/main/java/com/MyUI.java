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

        // create attributeHandling
        AttributeHandling attributeHandling = new AttributeHandling();

        // get database connection
        Connection connection = ConnectToDatabase.getConnection();

        // create and register the views
        navigator.addView(NavigationStates.NO_CONNECTION, new NoConnectionView(navigator, null, attributeHandling));
        navigator.addView("", new SignInUpView(navigator, connection, attributeHandling));
        navigator.addView(NavigationStates.EDITOR, new EditorView(navigator, connection, attributeHandling));
        navigator.addView(NavigationStates.LIBRARY, new LibraryView(navigator, connection, attributeHandling));
        navigator.addView(NavigationStates.EXPORT, new ExamView(navigator, connection, attributeHandling));
        navigator.addView(NavigationStates.CREATE, new CreateView(navigator, connection, attributeHandling));
        navigator.addView(NavigationStates.TEST, new TestView(navigator, connection, attributeHandling));

        // navigate to no connection page
        if (connection == null) navigator.navigateTo(NavigationStates.NO_CONNECTION);
        // navigate to home
        else navigator.navigateTo(NavigationStates.HOME);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

    }
}
