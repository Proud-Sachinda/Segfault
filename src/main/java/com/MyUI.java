package com;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    // root layout
    final HorizontalLayout root = new HorizontalLayout();
    // navigation layout
    final VerticalLayout navigation = new VerticalLayout();
    // content area
    final VerticalLayout content = new VerticalLayout();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        // set size of horizontal layout since it is not by default defined
        root.setSizeFull();

        // set up navigation and content area
        navigation.setWidth("64px");
        navigation.setHeight(100.0F, Unit.PERCENTAGE);
        navigation.setStyleName("main-blue");
        content.setSizeFull();

        // add navigation and content area to root layout
        root.addComponent(navigation);
        root.addComponentsAndExpand(content);
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            content.addComponent(new Label("Thanks " + name.getValue()
                    + ", it works!"));
        });
        
        content.addComponents(name, button);
        
        setContent(root);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
