package com.Client;

import com.AttributeHandling;
import com.ComponentToolkit;
import com.MyTheme;
import com.NavigationStates;
import com.Server.LecturerServer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import java.io.File;
import java.security.MessageDigest;
import java.sql.Connection;

//import static org.graalvm.compiler.loop.InductionVariable.Direction.Up;

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

public class SignInUpView extends VerticalLayout implements View {

    // navigator used to redirect to another page
    private Navigator navigator;

    // set attributeHandling
    private AttributeHandling attributeHandling;

    // entry
    private boolean entry = false;

    // server
    private LecturerServer lecturerServer;
    private final Button sign = new Button("Sign Up");

    // base path
    private String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    // sign components
    private SignInComponent signInComponent;
    private SignUpComponent signUpComponent;

    public SignInUpView(Navigator navigator, Connection connection, AttributeHandling attributeHandling) {

        // we get the apps Navigator object
        this.navigator = navigator;

        // attributeHandling
        this.attributeHandling = attributeHandling;

        // initiate server
        this.lecturerServer = new LecturerServer(connection);

        // set to fill browser screen
        setSizeFull();

        // set wallpaper
        addStyleName("main-sign-in-page-background");

        // sign in component
        signInComponent = new SignInComponent();

        // sign up component
        signUpComponent = new SignUpComponent();

        // add sign up component
        addComponent(signInComponent);
        setComponentAlignment(signInComponent, Alignment.MIDDLE_CENTER);
    }

    private void setCurrentSignComponent(VerticalLayout component) {

        // remove all
        removeAllComponents();

        // add component
        addComponent(component);
        setComponentAlignment(component, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        // set page title
        UI.getCurrent().getPage().setTitle("Welcome, Question Bank");

        // check if there is a message
        String message = attributeHandling.getMessage();

        // if message is not null
        if (!message.isEmpty() && entry) Notification.show("ERROR", message, Notification.Type.WARNING_MESSAGE);

        // user has entered before
        entry = true;
    }

    private class SignInComponent extends VerticalLayout {

        // components
        private Image user;
        private Image pass;
        private final TextField username = new TextField();
        private final TextField password = new TextField();
        private final Button signIn = new Button("Sign in");
        private final Button signUp = new Button("Sign up");

        private SignInComponent() {

            // set margin true
            setMargin(true);

            // set width undefined
            setWidthUndefined();

            // set style
            addStyleName(MyTheme.GLASS_BORDER);

            // set component alignment
            setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

            // file resources
            FileResource witsResource = new FileResource(new File(basePath + "/WEB-INF/images/WITS-logo.png"));
            FileResource usernameResource = new FileResource(new File(basePath + "/WEB-INF/images/username.svg"));
            FileResource passwordVisibleResource = new FileResource(
                    new File(basePath + "/WEB-INF/images/password-visible.svg"));
            FileResource passwordNotVisibleResource = new FileResource(
                    new File(basePath + "/WEB-INF/images/password-not-visible.svg"));

            // image
            Image wits = new Image(null, witsResource);
            wits.setWidth(64f, Unit.PIXELS);
            wits.setHeight(64f, Unit.PIXELS);
            addComponent(wits);

            // set borderless
            username.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
            password.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);

            // user name components
            username.setMaxLength(12);
            Label userLabel = new Label("Username");
            userLabel.addStyleNames(MyTheme.MAIN_TEXT_SIZE_MEDIUM,
                    MyTheme.MAIN_TEXT_WEIGHT_800, MyTheme.MAIN_TEXT_CHARCOAL);
            user = new Image(null, usernameResource);
            HorizontalLayout userLayout = new HorizontalLayout();
            setUpHorizontalLayout(userLayout, username, user);

            // pass word components
            Label passLabel = new Label("Password");
            passLabel.addStyleNames(MyTheme.MAIN_TEXT_SIZE_MEDIUM,
                    MyTheme.MAIN_TEXT_WEIGHT_800, MyTheme.MAIN_TEXT_CHARCOAL);
            pass = new Image(null, passwordNotVisibleResource);
            HorizontalLayout passLayout = new HorizontalLayout();
            setUpHorizontalLayout(passLayout, password, pass);

            // set widths and heights
            ComponentToolkit.setMultipleImage(2.5f, Unit.REM, user, pass);

            // set button styles
            signIn.addStyleName(MyTheme.MAIN_SIGN_IN_BUTTON);
            signUp.addStyleName(MyTheme.MAIN_SIGN_UP_BUTTON);

            // add components
            addComponents(wits, userLabel, userLayout, passLabel, passLayout, signIn, signUp);
            setComponentAlignment(userLabel, Alignment.MIDDLE_LEFT);
            setComponentAlignment(passLabel, Alignment.MIDDLE_LEFT);

            // add button listeners
            setUpSignInUpListeners();
        }

        private void setUpSignInUpListeners() {

            signIn.addClickListener((Button.ClickListener) event -> {

                if (username.getValue().trim().isEmpty() ||
                        password.getValue().trim().isEmpty()) {
                    Notification.show("Fill in all fields", Notification.Type.WARNING_MESSAGE);
                }
                else {

                    // authentication variable
                    boolean auth =  lecturerServer.authenticateLecturer(username.getValue().trim(),password.getValue(), attributeHandling);

                    if (auth) {

                        // empty fields
                        username.setValue("");
                        password.setValue("");

                        // navigate to page
                        navigator.navigateTo(NavigationStates.EDITOR);

                    } else Notification.show("Incorrect Credentials", Notification.Type.ERROR_MESSAGE);
                }
            });

            signUp.addClickListener((Button.ClickListener)
                    event -> setCurrentSignComponent(signUpComponent));
        }

        private void setUpHorizontalLayout(HorizontalLayout horizontalLayout, TextField t, Image i) {

            horizontalLayout.addStyleName(MyTheme.MAIN_FLAT_SIGN_IN_FORM);
            horizontalLayout.addComponents(t, i);
            horizontalLayout.setComponentAlignment(i, Alignment.MIDDLE_CENTER);
            horizontalLayout.setComponentAlignment(t, Alignment.MIDDLE_CENTER);
        }
    }

    private class SignUpComponent extends VerticalLayout {

        //Creating TextField
        private final TextField tex1 = new TextField("Username");
        private final TextField tex2 =  new TextField("First Names");
        private final TextField tex3 =  new TextField("Last Name");
        private final TextField tex4 =  new TextField("Password");
        private final TextField tex5 =  new TextField("Confirm Password");
        private final Button signIn = new Button("Sign in");
        private final Button signUp = new Button("Sign up");

        private SignUpComponent() {

            //Creating Layouts
            VerticalLayout verticalLayout =  new VerticalLayout();
            verticalLayout.addComponents(tex1,tex2,tex3,tex4,tex5,signUp,signIn);

            signUp.addStyleName(MyTheme.MAIN_TEXT_SIZE_NORMAL);
            signUp.setWidth(80f, Unit.PERCENTAGE);

            // set width undefined
            setWidthUndefined();

            // set margin
            setMargin(true);

            // TODO set max lengths for all components, compare with database max lengths
            tex1.setMaxLength(12);

            verticalLayout.setComponentAlignment(tex1, Alignment.MIDDLE_CENTER);
            verticalLayout.setComponentAlignment(tex2, Alignment.MIDDLE_CENTER);
            verticalLayout.setComponentAlignment(tex3, Alignment.MIDDLE_CENTER);
            verticalLayout.setComponentAlignment(tex4, Alignment.MIDDLE_CENTER);
            verticalLayout.setComponentAlignment(tex5, Alignment.MIDDLE_CENTER);
            verticalLayout.setComponentAlignment(signUp, Alignment.MIDDLE_CENTER);
            verticalLayout.setComponentAlignment(signIn, Alignment.MIDDLE_CENTER);

            // set button styles
            signIn.addStyleName(MyTheme.MAIN_SIGN_IN_BUTTON);
            signUp.addStyleName(MyTheme.MAIN_SIGN_UP_BUTTON);

            addComponent(verticalLayout);

            signUp.addClickListener((Button.ClickListener) clickEvent -> {

                boolean valid = true;
                String password = tex4.getValue();

               // MessageDigest md = null;
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    byte [] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

                    StringBuilder sb = new StringBuilder();
                    for (byte b: hashInBytes){
                        sb.append(String.format("%02x",b));
                    }
                    System.out.println(sb.toString());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                //Check if the fields are empty or not
                if (tex1.getValue().trim().isEmpty() || tex2.getValue().trim().isEmpty() ||
                        tex3.getValue().trim().isEmpty() || tex4.getValue().trim().isEmpty() || tex5.getValue().isEmpty()) {
                    Notification.show("Fill in all fields", Notification.Type.WARNING_MESSAGE);
                }
                else {
                    boolean success = lecturerServer.authenticationSignUp(tex1.getValue(),tex2.getValue(),tex3.getValue(), tex4.getValue());
                   // System.out.println(success);

                    if (success) {
                        Notification.show("SUCCESS", "Welcome user", Notification.Type.TRAY_NOTIFICATION);
                        navigator.navigateTo(NavigationStates.EDITOR);
                    }
                    else {
                        Notification.show("Could not register", Notification.Type.WARNING_MESSAGE);

                    }
                }
            });
            signIn.addClickListener((Button.ClickListener)
                    event -> setCurrentSignComponent(signInComponent));
        }
    }
}
