package com.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.vaadin.ui.NumberField;


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
    final HorizontalLayout forms = new HorizontalLayout();
    final HorizontalLayout title = new HorizontalLayout();

    //caption for whole page
    private Label caption = new Label("Create Question ");



    private TextField qname = new TextField("Question");
    private TextField answer = new TextField();
    private TextField mark = new TextField();
    private TextField space = new TextField("Space lines");
    private TextField difficulty = new TextField("Difficulty");
    private TextField answername = new TextField("Answer");
    private Button addLatex = new Button("+");
    private Button decrease = new Button("-");
    private Button increase = new Button("+");
    private Label marklabel = new Label("Mark Allocation");
    private Button normal = new Button("Normal");
    private Button mcq = new Button("MCQ");
    private Button practical = new Button("Practical");
    private TextField sampleinput = new TextField("Sample Input");
    private TextField sampleoutput = new TextField("Sample Output");
    private Button back = new Button("back");
    private Button submit = new Button("submit");


    public CreateQuestionView(Navigator navigator) {

        // we get the Apps Navigator object
        this.navigator = navigator;

        // set to fill browser screen
        setSizeFull();

        // set up dashboard
        setUpDashboard();

        //number field for adding mark
        NumberField mark1 = new NumberField("Add Mark");

        //numberfield for adding answer lines
        NumberField lines = new NumberField("Answer Lines");

        back.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                navigator.navigateTo(question);
            }
        });

        // code after the dashboard is setup
        VerticalLayout form = new VerticalLayout();
        VerticalLayout form1 = new VerticalLayout();
        form.setSizeFull();
        form1.setSizeFull();
        VerticalLayout right = new VerticalLayout();
        VerticalLayout extrastuff = new VerticalLayout();
        extrastuff.setMargin(false);

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
        practical.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                extrastuff.removeAllComponents();
                extrastuff.addComponentsAndExpand(sampleinput,sampleoutput);
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


        //everything for the mark
       /* HorizontalLayout addmark = new HorizontalLayout();
        mark.setWidth("40px");
        increase.setStyleName("Segzy5-increase");
        mark.setStyleName("Segzy5-text");
        decrease.setStyleName("Segzy5-decrease");
        mark.setStyleName("segzyfield");
        //increase .setIcon(new ClassResource("C:\\Users\\User\\IdeaProjects\\Segfault\\Extra Resources\\images\\add.png"));
        addmark.addComponents(marklabel,decrease,mark,increase);*/




        title.addComponents(back,caption);
        title.setComponentAlignment(back,Alignment.TOP_LEFT);

        caption.addStyleName("Segzy");
        //set content area
        form.addComponents(addq);
        form1.addComponents(type,mark1,difficulty,extrastuff,submit);
        forms.addComponents(form,form1);
        forms.setWidth("100%");
        content.addComponents(title,forms);
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
