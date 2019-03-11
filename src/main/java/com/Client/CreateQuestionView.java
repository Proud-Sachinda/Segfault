package com.Client;

import com.Server.QuestionServer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.*;
import org.vaadin.ui.NumberField;

import java.sql.Connection;
import java.util.Date;


public class CreateQuestionView extends HorizontalLayout implements View {

    // navigator used to redirect to another page
    private Navigator navigator;

    // connection for database
    private Connection connection;

    // route strings - nothing special just things like qbank_exploded_war/route_name
    protected final String question = "question";
    protected final String course = "course";
    protected final String export = "export";

    // navigation and content area
    private final VerticalLayout navigation = new VerticalLayout();
    private final VerticalLayout content = new VerticalLayout();
    private final HorizontalLayout forms = new HorizontalLayout();
    private final HorizontalLayout title = new HorizontalLayout();

    //caption for whole page
    private Label caption = new Label("Create Question ");


    private TextArea qname = new TextArea("Question");
    private Date qdate = new Date();
    private Date qlastused = new Date();
    private TextField answer = new TextField();
    private TextField mark = new TextField();

    private TextField space = new TextField("Space lines");
    private TextField difficulty = new TextField("Difficulty");
    private TextArea answername = new TextArea("Answer");
    private Button addLatex = new Button("+");
    private Button decrease = new Button("-");
    private Button increase = new Button("+");
    private Label marklabel = new Label("Mark Allocation");
    private Button normal = new Button("Written");
    private Button mcq = new Button("MCQ");
    private Button practical = new Button("Practical");
    private TextField sampleinput = new TextField("Sample Input");
    private TextField sampleoutput = new TextField("Sample Output");
    private Button back = new Button("back");
    private Button submit = new Button("submit");
    private Button addChoice = new Button("add");
    


    public CreateQuestionView(Navigator navigator, Connection connection) {
         final String t;


        // we get the Apps Navigator object
        this.navigator = navigator;

        // set connection variable
        this.connection = connection;

        // set to fill browser screen
        setSizeFull();

        // set up dashboard
        setUpDashboard();

        //

        //number field for adding mark
        NumberField mark1 = new NumberField("Add Mark");

        // number field for adding answer lines
        NumberField lines = new NumberField("Answer Lines");

        // code after the dashboard is setup
        VerticalLayout form = new VerticalLayout();
        VerticalLayout form1 = new VerticalLayout();
        form.setSizeFull();
        form1.setSizeFull();
        VerticalLayout right = new VerticalLayout();
        VerticalLayout extrastuff = new VerticalLayout();
        extrastuff.setMargin(false);

        HorizontalLayout done = new HorizontalLayout();


        //stuff for choosing type of question
        HorizontalLayout type = new HorizontalLayout();
        type.addComponents(normal,mcq,practical);
        normal.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                extrastuff.removeAllComponents();
               extrastuff.addComponent(lines);

            }
        });

        sampleinput.setWidth("91%");
        sampleinput.setHeight("90px");
        //sampleinput.setPlaceholder("Sample Input");
        //sampleoutput.setPlaceholder("Sample Output");
        sampleoutput.setHeight("90px");
        sampleoutput.setWidth("91%");
        practical.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                extrastuff.removeAllComponents();
                extrastuff.addComponentsAndExpand(sampleinput,sampleoutput);
            }
        });

        addChoice.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                TextField choice = new TextField();
                HorizontalLayout mcqchoices = new HorizontalLayout();
                mcqchoices.addComponents(choice,addChoice);
                extrastuff.addComponentsAndExpand(mcqchoices);
            }
        });
        mcq.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                extrastuff.removeAllComponents();
                TextField choice = new TextField();
                HorizontalLayout mcqchoices = new HorizontalLayout();
                mcqchoices.addComponents(choice,addChoice);
                extrastuff.addComponentsAndExpand(mcqchoices);
            }
        });

        //radio buttons for difficulty
        RadioButtonGroup<String> group = new RadioButtonGroup<>();
        group.setItems("easy", "medium", "hard");
        group.setCaption("Difficulty");
        HorizontalLayout difficulty = new HorizontalLayout();
        difficulty.addComponent(group);




        //everything for adding question
        //layout for adding question
        VerticalLayout addq = new VerticalLayout();
        //latex stuff
        HorizontalLayout latexstuff = new HorizontalLayout();
        HorizontalLayout lstuff = new HorizontalLayout();
        Label latex = new Label("latex stuff");
        latexstuff.addStyleName("Segzy3");
        latexstuff.addComponent(latex);
        latexstuff.setHeight("70px");
        addLatex.setWidth("40px");
        addLatex.setHeight("40px");
        addLatex.setStyleName("main-flat-round-button");
        lstuff.setWidth("100%");
        lstuff.addComponents(latexstuff);
        //add the add question stuff
        qname.setWidth("100%");
        qname.setHeight("100px");
        qname.setPlaceholder("Type your Question here");
        answername.setWidth("100%");
        answername.setHeight("100px");
        answername.setPlaceholder("Type your Answer here");
        addq.addComponents(lstuff,qname,answername);
        addq.setStyleName("Segzy4");

        //back.setIcon(new ClassResource("left-arrow.png"));
        //back.setIcon(new ClassResource("C:\\Users\\User\\IdeaProjects\\Segfault\\Extra Resources\\images\\left-arrow.png"));
        //everything for the mark
        /* HorizontalLayout addmark = new HorizontalLayout();
        mark.setWidth("40px");
        increase.setStyleName("Segzy5-increase");
        mark.setStyleName("Segzy5-text");
        decrease.setStyleName("Segzy5-decrease");
        mark.setStyleName("segzyfield");
        //increase .setIcon(new ClassResource("C:\\Users\\User\\IdeaProjects\\Segfault\\Extra Resources\\images\\add.png"));
        addmark.addComponents(marklabel,decrease,mark,increase);
        */

        back.setStyleName("Segzy6");
        submit.setStyleName("Segzy5");
        // set caption
        title.addComponents(back,caption);
        title.setComponentAlignment(back,Alignment.TOP_LEFT);
        caption.addStyleName("Segzy");

        //set content area
        form.addComponents(addq);
       // done.addComponents(submit);
        form1.addComponents(type,mark1,difficulty,extrastuff,done);
        forms.addComponents(form,form1);
        forms.setWidth("100%");
        content.addComponents(title,forms);
        back.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                navigator.navigateTo(question);
            }
        });

        // set submit button listener
        submit.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                // TODO check if fields are empty before submitting (LATER)

                // create question variable and send to database
                QuestionServer questionServer = new QuestionServer(connection);
                QuestionServer.Question q = questionServer.getQuestion();

                // set question variables

                String m = mark1.getValue();
                q.setQuestionBody(qname.getValue());
                q.setQuestionAns(answername.getValue());
                q.setQuestionDate(qdate);
                q.setQuestionLastUsed(qlastused);
                q.setQuestionMark(Integer.parseInt(m));
                q.setQuestionDifficulty(difficulty.toString());
                q.setQuestionType("Normal");




                // get values of textfield
                q.getQuestionBody();
                q.getQuestionDate();
                q.getQuestionAns();
                q.getQuestionMark();
                q.getQuestionLastUsed();
                q.getQuestionType();




                // TODO the rest

                // if post returned true show successful notification otherwise error
                // and redirect
                if (questionServer.post(q)) {
                    Notification.show("Success");
                    navigator.navigateTo(question);
                }
                else {
                    Notification.show("Error submitting form");
                }
            }
        });
        content.addComponents(title,forms,back,submit);
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
