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
    Button latex1 = new Button("");
    Button latex2 = new Button("");
    Button latex3 = new Button("");
    Button latex4 = new Button("");
    Button latex5 = new Button("");
    Button latex6 = new Button("");
    Button latex0 = new Button("");
    Button latex7 = new Button("");
    Button latex8 = new Button("");
    Button latex9 = new Button("");
    Button latex10 = new Button("");
    Button latex11 = new Button("");
    private Button latex12 = new Button("");
    private Label caption = new Label("Create Question ");
    private Label qlabel = new Label("Add Question ");
    private TextField qname = new TextField();
    private TextField mark = new TextField();
    private TextField space = new TextField("Space lines");
    private TextField difficulty = new TextField("Difficulty");
    private TextField answer = new TextField("Answer");
    private Button addLatex = new Button("+");
    private Button increase = new Button("+");
    private Button decrease = new Button("-");
    private Label marklabel = new Label("Mark Allocation");





    public CreateQuestionView(Navigator navigator) {

        // we get the Apps Navigator object
        this.navigator = navigator;

        // set to fill browser screen
        setSizeFull();

        // set up dashboard
        setUpDashboard();

        //everything for adding question
        //layout for adding question
        VerticalLayout addq = new VerticalLayout();
        //latex stuff
        HorizontalLayout latexstuff = new HorizontalLayout();
        HorizontalLayout lstuff = new HorizontalLayout();
        //latexstuff.setHeight("30px");
        latex1.addStyleName("Segzy2");
        latex2.addStyleName("Segzy2");
        latex3.addStyleName("Segzy2");
        latex4.addStyleName("Segzy2");
        latex5.addStyleName("Segzy2");
        latex0.addStyleName("Segzy2");
        latex6.addStyleName("Segzy2");
        latex7.addStyleName("Segzy2");
        latex8.addStyleName("Segzy2");
        latex9.addStyleName("Segzy2");
        latex10.addStyleName("Segzy2");
        latex11.addStyleName("Segzy2");
        latex12.addStyleName("Segzy2");
        latexstuff.addStyleName("Segzy3");
        latexstuff.addComponents(latex1,latex2,latex3,latex4,latex5,latex0,latex6,latex7,latex8,latex9,latex10,latex11);
        addLatex.setWidth("40px");
        addLatex.setHeight("40px");
        addLatex.setStyleName("main-flat-round-button");
        lstuff.setWidth("100%");
        lstuff.addComponents(latexstuff,addLatex);
        //add the add question stuff
        qname.setWidth("55%");
        qname.setHeight("60px");
        qname.setPlaceholder("Type your Question here");
        addq.addComponents(qlabel,lstuff,qname);
        addq.setStyleName("Segzy4");

        //everything for the mark
        HorizontalLayout addmark = new HorizontalLayout();
        mark.setWidth("40px");
        increase.setStyleName("Segzy5-increase");
        mark.setStyleName("Segzy5-text");
        decrease.setStyleName("Segzy5-decrease");
        addmark.addComponents(marklabel,increase,mark,decrease);


        // code after the dashboard is setup
        VerticalLayout form = new VerticalLayout();
        form.setSizeFull();

        caption.addStyleName("Segzy");
        //set content area
        form.addComponents(caption,addq,addmark);
        content.addComponents(form);
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
