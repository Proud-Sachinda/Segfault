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

    private HorizontalLayout root;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        setupLayout();
        addSidePanel();
        addContentPanel();

        /*
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });
        
        layout.addComponents(name, button);*/
    }

    private void setupLayout() {
        root = new HorizontalLayout();
        setContent(root);
    }
    private void addSidePanel(){
        Button button = new Button("Click Me");
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidth("96px");
        verticalLayout.setHeight("100%");
        verticalLayout.addComponent(button);
        root.addComponent(verticalLayout);

    }
    private void addContentPanel(){
        VerticalLayout verticalLayout = new VerticalLayout();
        Button button = new Button("Click Me");
        verticalLayout.addComponent(button);
        root.addComponent(verticalLayout);
    }

    public int multiply(int x, int y) {
        return x * y;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
