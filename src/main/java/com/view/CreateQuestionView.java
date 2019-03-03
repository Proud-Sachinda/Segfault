package com.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

public class CreateQuestionView extends HorizontalLayout implements View {

    // navigator used to redirect to another page
    private Navigator navigator;

    // route strings - nothing special just things like qbank_exploded_war/route_name
    protected final String question = "question";
    protected final String course = "course";
    protected final String export = "export";

    // navigation and content area
    final VerticalLayout navigation = new VerticalLayout();
    final VerticalLayout content = new VerticalLayout();

    //components for the create question form
    Button latex1 = new Button("1");
    Button latex2 = new Button("2");
    Button latex3 = new Button("3");
    Button latex4 = new Button("4");
    Button latex5 = new Button("5");
    private Label caption = new Label("Create Question ");
    private Label qlabel = new Label("Create Question ");
    private TextField qname = new TextField();
    private TextField mark = new TextField("Mark");
    private TextField space = new TextField("Space lines");
    private TextField difficulty = new TextField("Difficulty");
    private TextField answer = new TextField("Answer");
    private Button addLatex = new Button("+");



    public CreateQuestionView(Navigator navigator) {

        // we get the Apps Navigator object
        this.navigator = navigator;

        // set to fill browser screen
        setSizeFull();

        // set up dashboard
        setUpDashboard();


        HorizontalLayout latexstuff = new HorizontalLayout();
        HorizontalLayout lstuff = new HorizontalLayout();
        qname.setWidth("55%");
        qname.setHeight("15%");
        latex1.addStyleName("Segzy2");
        latex2.addStyleName("Segzy2");
        latex3.addStyleName("Segzy2");
        latex4.addStyleName("Segzy2");
        latex5.addStyleName("Segzy2");
        latexstuff.addStyleName("Segzy3");
        latexstuff.addComponents(latex1,latex2,latex3,latex4,latex5);
        addLatex.setStyleName("Segzy4");
        lstuff.setWidth("100%");
        lstuff.addComponents(latexstuff,addLatex);
        // code after the dashboard is setup
        VerticalLayout form = new VerticalLayout();
        form.setSizeFull();
        caption.addStyleName("Segzy");
        //set content area
        form.addComponents(caption,qlabel,lstuff,qname,mark,difficulty,space,answer);
        content.addComponentsAndExpand(form);
    }


    @SuppressWarnings("Duplicates")
    private void setUpDashboard() {

        // set navigation size, color
        navigation.setWidth("80px");
        navigation.setHeight(100.0f, Unit.PERCENTAGE);
        navigation.setStyleName("main-blue");
        addComponent(navigation);

        // set content area
        addComponentsAndExpand(content);
    }
}
