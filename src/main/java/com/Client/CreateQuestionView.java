
package com.Client;

import com.Server.QuestionServer;
import com.Server.CourseServer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.ClassResource;
import com.vaadin.shared.ui.Orientation;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.*;
import org.vaadin.ui.NumberField;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import static com.vaadin.shared.ui.Orientation.HORIZONTAL;


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

    public  ComboBox combobox;
    private TextArea qname = new TextArea("Question");
    private Date qdate = new Date();
    private Date qlastused = new Date();
    private TextField answer = new TextField();
    private TextField mark = new TextField();

    private TextField space = new TextField("Space lines");
    //private TextField difficulty = new TextField("Difficulty");
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
    private Button square = new Button();
    private Button power = new Button();
    private Button sqrroot = new Button();
    private Button root = new Button();
    private Button frac = new Button();
    private Button log = new Button();
    private Button pi = new Button();
    private Button theta = new Button();
    private Button infinity = new Button();
    private Button integral = new Button();
    private Button derivitive = new Button("d/dx");
    String qtype = "";
    //stuff for mcq
    String choices = "";
    TextField choice = new TextField();
    TextField choice1 = new TextField();
    TextField choice2 = new TextField();
    TextField choice3 = new TextField();
    TextField choice4 = new TextField();
    private Button addChoice1 = new Button("add");
    private Button addChoice2 = new Button("add");
    private Button addChoice3 = new Button("add");
    private Button removeChoice = new Button("remove");
    private Button removeChoice1 = new Button("remove");
    private Button removeChoice2 = new Button("remove");
    private Button removeChoice3 = new Button("remove");
    private Button removeChoice4 = new Button("remove");
    //horizontal layouts for each mcq choice
    HorizontalLayout mcqchoice = new HorizontalLayout();
    HorizontalLayout mcqchoice1 = new HorizontalLayout();
    HorizontalLayout mcqchoice2 = new HorizontalLayout();
    HorizontalLayout mcqchoice3 = new HorizontalLayout();
    HorizontalLayout mcqchoice4 = new HorizontalLayout();
    //status for each choice
    boolean choicestatus1 = false;
    boolean choicestatus2 = false;
    boolean choicestatus3 = false;
    boolean choicestatus4 = false;
    boolean choicestatus5 = false;
    private ArrayList<CourseServer.Course> courseArrayList;
    private CourseServer courseServer;



    public CreateQuestionView(Navigator navigator, Connection connection) {



        // we get the Apps Navigator object
        this.navigator = navigator;

        // set connection variable
        this.connection = connection;

        // set to fill browser screen
        setSizeFull();

        // set up dashboard
        setUpDashboard();


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


      //  HorizontalLayout type = new HorizontalLayout();


        combobox = new ComboBox("Select Course:");

        courseServer = new CourseServer(this.connection);


        courseArrayList = courseServer.get();


        for(int i = 0; i < courseArrayList.size(); i++){

            combobox.setItems(courseArrayList.get(i).getCourseCode());
        }




        //Add multiple items


        HorizontalLayout done = new HorizontalLayout();


        //stuff for choosing type of question
        HorizontalLayout type = new HorizontalLayout();
        /*MenuBar questiontype = new MenuBar();
        final Label qType = new Label("Question Type");
        type.addComponent(qType);
        MenuBar.Command myCommand = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                qType.setValue(selectedItem.getText());

            }
        };
        type.addComponent(questiontype);
        questiontype.addItem("Written", myCommand );
        questiontype.addItem("MCQ", myCommand);
        questiontype.addItem("Practical", myCommand); */




        type.addComponentsAndExpand(combobox);
        type.addComponents(normal,mcq,practical);
        normal.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                extrastuff.removeAllComponents();
                extrastuff.addComponent(lines);
                qtype = "written";

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
                qtype = "practical";
            }
        });


        //stuff for mcq
        //add the first choice when the mcq button is clicked
        mcq.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                extrastuff.removeAllComponents();
                mcqchoice.removeAllComponents();
                mcqchoice1.removeAllComponents();
                mcqchoice2.removeAllComponents();
                mcqchoice3.removeAllComponents();
                mcqchoice4.removeAllComponents();
                mcqchoice.addComponents(choice,addChoice);
                extrastuff.addComponentsAndExpand(mcqchoice);
                choicestatus1 = true;
                qtype = "mcq";
            }
        });
        //add second option
        addChoice.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                choices= choices+"&"+choice.getValue();
                mcqchoice.removeComponent(addChoice);
                mcqchoice.addComponent(removeChoice);
                mcqchoice1.addComponents(choice1,addChoice1);
                choicestatus2 = true;
                extrastuff.addComponentsAndExpand(mcqchoice1);

            }
        });
        //add third option
        addChoice1.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                choices= choices+choice1.getValue();
                System.out.println(choices);
                mcqchoice1.removeComponent(addChoice1);
                mcqchoice1.addComponent(removeChoice1);
                mcqchoice2.addComponents(choice2,addChoice2);
                choicestatus3 = true;
                extrastuff.addComponentsAndExpand(mcqchoice2);

            }
        });
        //add fourth option
        addChoice2.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                choices= choices+choice2.getValue();
                System.out.println(choices);
                mcqchoice2.removeComponent(addChoice2);
                mcqchoice2.addComponent(removeChoice2);
                mcqchoice3.addComponents(choice3,addChoice3);
                choicestatus4 = true;
                extrastuff.addComponentsAndExpand(mcqchoice3);

            }
        });
        //add fifth option
        addChoice3.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                choices= choices+choice3.getValue();
                System.out.println(choices);
                mcqchoice3.removeComponent(addChoice3);
                mcqchoice3.addComponent(removeChoice3);
                mcqchoice4.addComponents(choice4,removeChoice4);
                choicestatus5 = true;
                extrastuff.addComponentsAndExpand(mcqchoice4);

            }
        });
        //remove first choice
        removeChoice.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                extrastuff.removeComponent(mcqchoice);
                choicestatus1 = false;

                if((choicestatus2 = false) & (choicestatus3 = false) & (choicestatus4 = false) &( choicestatus5 = false)){
                    extrastuff.addComponent(mcqchoice);
                }
                choice.clear();

            }
        });
        //remove second choice
        removeChoice1.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                extrastuff.removeComponent(mcqchoice1);
                choicestatus2 = false;

                if((choicestatus1 = false) & (choicestatus3 = false) & (choicestatus4 = false) &( choicestatus5 = false)){
                    extrastuff.addComponent(mcqchoice);
                }
                choice1.clear();

            }
        });
        //remove third choice
        removeChoice2.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                extrastuff.removeComponent(mcqchoice2);
                choicestatus3 = false;

                if((choicestatus2 = false) & (choicestatus1 = false) & (choicestatus4 = false) &( choicestatus5 = false)){
                    extrastuff.addComponent(mcqchoice);
                }
                choice2.clear();

            }
        });
        //remove fourth choice
        removeChoice3.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                extrastuff.removeComponent(mcqchoice3);
                choicestatus4 = false;

                if((choicestatus2 = false) & (choicestatus3 = false) & (choicestatus1 = false) &( choicestatus5 = false)){
                    extrastuff.addComponent(mcqchoice);
                }
                choice3.clear();

            }
        });
        //remove fifth choice
        removeChoice4.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                extrastuff.removeComponent(mcqchoice4);
                choicestatus5 = false;

                if((choicestatus2 = false) & (choicestatus3 = false) & (choicestatus4 = false) &( choicestatus1 = false)){
                    extrastuff.addComponent(mcqchoice);
                }
                choice4.clear();

            }
        });


        //Slider for difficulty
        VerticalLayout v = new VerticalLayout();
        HorizontalLayout difficulty = new HorizontalLayout();
        final Label diffslidervalue = new Label();
        diffslidervalue.setValue("Easy Medium Hard");
        diffslidervalue.setWidth(difficulty.getWidth(), Unit.PERCENTAGE);
        diffslidervalue.setSizeFull();
       // difficulty.addComponent(diffslidervalue);


        difficulty.setWidth(100.0f, Unit.PERCENTAGE);

        Slider diffSlider = new Slider(1,5);

        diffSlider.setOrientation(SliderOrientation.HORIZONTAL);
        diffSlider.setWidth(100.0f, Unit.PERCENTAGE);
        diffSlider.setCaption(diffslidervalue.getValue());

        difficulty.addComponent(diffSlider);





        diffSlider.addValueChangeListener(event -> {
            float value = event.getValue().floatValue();
            diffslidervalue.setValue(String.valueOf(value));

        });
        //value change listener for radio button





        //everything for adding question
        //layout for adding question
        VerticalLayout addq = new VerticalLayout();
        //latex stuff
        square.setStyleName("power");
        sqrroot.setStyleName("sqroot");
        frac.setStyleName("fraction");
        power.setStyleName("Segzy2");
        pi.setStyleName("pi");
        root.setStyleName("Segzy2");
        log.setStyleName("log");
        theta.setStyleName("theta");
        infinity.setStyleName("infinite");
        integral.setStyleName("integral");
        derivitive.setStyleName("Segzy2");
        HorizontalLayout latexstuff = new HorizontalLayout();
        //HorizontalLayout lstuff = new HorizontalLayout();
        // Label latex = new Label("latex stuff");
        latexstuff.addStyleName("Segzy3");
        latexstuff.addComponents(square,sqrroot,frac,power,pi,root,log,theta,infinity,integral,derivitive);
        latexstuff.setHeight("70px");


        //add the add question stuff
        qname.setWidth("100%");
        qname.setHeight("100px");
        qname.setPlaceholder("Type your Question here");
        answername.setWidth("100%");
        answername.setHeight("100px");
        answername.setPlaceholder("Type your Answer here");
        addq.addComponents(latexstuff,qname,answername);
        addq.setStyleName("Segzy4");



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
               /* QuestionServer.Question q = questionServer.getQuestion();

                // set question variables

                String m = mark1.getValue();
                q.setQuestionBody(qname.getValue());
                q.setQuestionAns(answername.getValue());
                q.setQuestionDate(qdate);
                q.setQuestionLastUsed(qlastused);
                q.setQuestionMark(Integer.parseInt(m));
                q.setQuestionDifficulty(difficulty.toString());*/


                if(qtype.matches("written")){
                    QuestionServer.Written q = (QuestionServer.Written) questionServer.getWritten();

                    if(diffSlider.getValue() == 1 || diffSlider.getValue() ==2){
                        q.setQuestionDifficulty("Easy");
                    }

                    else if( diffSlider.getValue() == 3 || diffSlider.getValue() == 4){
                        q.setQuestionDifficulty("Medium");
                    }

                    else{
                        q.setQuestionDifficulty("Hard");
                    }
                    String s= q.getQuestionDifficulty();

                    String m = mark1.getValue();
                    String l=lines.getValue();
                    q.setQuestionBody(qname.getValue());
                    q.setQuestionAns(answername.getValue());
                    q.setQuestionDate(qdate);
                    q.setQuestionLastUsed(qlastused);
                    q.setQuestionMark(Integer.parseInt(m));
                    q.setQuestionDifficulty(s);
                    q.setQuestionType(qtype);
                    q.setQuestion_line(Integer.parseInt(l));

                    if (questionServer.post(q)) {
                        Notification.show("Success");
                        navigator.navigateTo(question);
                    }
                    else {
                        Notification.show("Error submitting form");
                    }

                }
                else if(qtype.matches("practical")){
                    // q.setQuestionType("Practical");

                    QuestionServer.Practical q = (QuestionServer.Practical) questionServer.getPractical();
                    if(diffSlider.getValue() == 1 || diffSlider.getValue() ==2){
                        q.setQuestionDifficulty("Easy");
                    }

                    else if( diffSlider.getValue() == 3 || diffSlider.getValue() == 4){
                        q.setQuestionDifficulty("Medium");
                    }

                    else{
                        q.setQuestionDifficulty("Hard");
                    }
                    String s= q.getQuestionDifficulty();
                    String m = mark1.getValue();
                    q.setQuestionBody(qname.getValue());
                    q.setQuestionAns(answername.getValue());
                    q.setQuestionDate(qdate);
                    q.setQuestionLastUsed(qlastused);
                    q.setQuestionMark(Integer.parseInt(m));
                    q.setQuestionDifficulty(s);
                    q.setQuestionType(qtype);
                    q.setSample_input(sampleinput.getValue());
                    q.setSample_output(sampleoutput.getValue());


                    if (questionServer.post(q)) {
                        Notification.show("Success");
                        navigator.navigateTo(question);
                    }
                    else {
                        Notification.show("Error submitting form");
                    }
                }
                else if (qtype.matches("mcq")){
                    //  q.setQuestionType("Mcq");
                    QuestionServer.Mcq q = (QuestionServer.Mcq) questionServer.getMcq();
                    if(diffSlider.getValue() == 1 || diffSlider.getValue() ==2){
                        q.setQuestionDifficulty("Easy");
                    }

                    else if( diffSlider.getValue() == 3 || diffSlider.getValue() == 4){
                        q.setQuestionDifficulty("Medium");
                    }

                    else{
                        q.setQuestionDifficulty("Hard");
                    }
                    String s= q.getQuestionDifficulty();
                    String m = mark1.getValue();
                    q.setQuestionBody(qname.getValue());
                    q.setQuestionAns(answername.getValue());
                    q.setQuestionDate(qdate);
                    q.setQuestionLastUsed(qlastused);
                    q.setQuestionMark(Integer.parseInt(m));
                    q.setQuestionDifficulty(s);
                    q.setQuestionType(qtype);
                    q.setMcq_choices(choices);

                    if (questionServer.post(q)) {
                        Notification.show("Success");
                        navigator.navigateTo(question);
                    }
                    else {
                        Notification.show("Error submitting form");
                    }
                }




                // get values of textfield
               /* q.getQuestionBody();
                q.getQuestionDate();
                q.getQuestionAns();
                q.getQuestionMark();
                q.getQuestionLastUsed();
                q.getQuestionType();*/




                // TODO the rest

                // if post returned true show successful notification otherwise error
                // and redirect
                /*if (questionServer.post(q)) {
                    Notification.show("Success");
                    navigator.navigateTo(question);
                }
                else {
                    Notification.show("Error submitting form");
                }*/
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
