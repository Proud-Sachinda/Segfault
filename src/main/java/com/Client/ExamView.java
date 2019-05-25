package com.Client;

import com.CookieHandling.CookieHandling;
import com.CookieHandling.CookieName;
import com.Dashboard;
import com.Objects.ExportItem;
import com.Objects.LecturerItem;
import com.Objects.QuestionItem;
import com.Objects.TrackItem;
import com.Server.ExportServer;
import com.Server.LecturerServer;
import com.Server.QuestionServer;
import com.vaadin.data.Binder;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;

import javax.servlet.http.Cookie;
import java.sql.Connection;

public class ExamView extends HorizontalLayout implements View {

    // navigator used to redirect to another page
    private Navigator navigator;

    // connection for database
    private Connection connection;

    // route strings - nothing special just things like qbank_exploded_war/route_name
    protected final String question = "question";
    protected final String course = "course";
    protected final String export = "export";

    ExportItem ex = new ExportItem();
    ExportServer es ;

    // navigation and content area
    final VerticalLayout navigation = new VerticalLayout();
    final VerticalLayout content = new VerticalLayout();
    final VerticalLayout labels = new VerticalLayout();
    final HorizontalLayout venue = new HorizontalLayout();
    final HorizontalLayout coursecode = new HorizontalLayout();
    final HorizontalLayout topicname = new HorizontalLayout();
    final HorizontalLayout date = new HorizontalLayout();
    final HorizontalLayout yos = new HorizontalLayout();
    final HorizontalLayout degree = new HorizontalLayout();
    final HorizontalLayout faculties = new HorizontalLayout();
    final HorizontalLayout internalexaminer = new HorizontalLayout();
    final HorizontalLayout externalexaminer = new HorizontalLayout();
    final HorizontalLayout material = new HorizontalLayout();
    final HorizontalLayout time = new HorizontalLayout();
    final HorizontalLayout topic = new HorizontalLayout();
    final HorizontalLayout instructions = new HorizontalLayout();
    final HorizontalLayout mark = new HorizontalLayout();
    final Label lblcoursecode = new Label("Course of Topic No(s)");
    final Label lbltopicname = new Label("Course or topic name(s)\n" +
            "Paper Number & title");
    final Label lbldate = new Label("Test to be\n" +
            "held during the months(s) of");
    final Label lblyos = new Label("Year of Study");
    final Label lbldegree = new Label("Degrees/Diplomas for which\n" +
            "this course is prescribed");
    final Label lblfaculties = new Label("Faculty/ies presenting\n" +
            "candidates");
    final Label lblinternalexaminer = new Label("Internal examiner(s)\n" +
            "and telephone extension\n" +
            "number(s)");
    final Label lblexternalexaminer = new Label("External examiner(s)");
    final Label lblmaterial = new Label("Special materials required");
    final Label lbltime = new Label("Time allowance");
    final Label lblinstructions = new Label("Instructions");
    final Label lblmark = new Label("Mark");
    final TextField txtcoursecode = new TextField();
    final TextField txttopicname = new TextField();
    final TextField txtdate = new TextField();
    final TextField txtyos = new TextField();
    final TextField txtdegree = new TextField();
    final TextField txtfaculties = new TextField();
    final TextField txtinternalexaminer = new TextField();
    final TextField txtexternalexaminer = new TextField();
    final TextField txtmaterial = new TextField();
    final TextField txttime = new TextField();
    final TextField txtmark = new TextField();
    final TextField txtVenue = new TextField();
    final Label lblVenue = new Label("Venue");
    final TextArea txtinstructions = new TextArea();
    final Button exe = new Button("export");
    final Binder<ExamView> binder = new Binder<>();

    // lecturer
    private LecturerItem lecturerItem;
    private LecturerServer lecturerServer;

    // dashboard
    private Dashboard dashboard;

    public ExamView(Navigator navigator, Connection connection) {

        es = new ExportServer(connection);
        // we get the Apps Navigator object
        this.navigator = navigator;

        // set connection variable
        this.connection = connection;

        // server
        this.lecturerServer = new LecturerServer(connection);

        // set to fill browser screen
        setSizeFull();


        //click listener

        exe.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                ex = getExport();
                int tId = 0;
                Cookie testcookie = CookieHandling.getCookieByName(CookieName.EDIT);
                if(testcookie != null){
                    tId = Integer.parseInt(testcookie.getValue());
                    System.out.println(tId+" hello");
                }
                es.method(ex, tId);


            }
        });



        // set up dashboard
        dashboard = new Dashboard(navigator, connection);
        addComponent(dashboard);


        //add components under each respective layout
        venue.addComponent(txtVenue);
        coursecode.addComponents(txtcoursecode);
        topic.addComponents( txttopicname);
        date.addComponents( txtdate);
        yos.addComponents( txtyos);
        degree.addComponents( txtdegree);
        faculties.addComponents( txtfaculties);
        internalexaminer.addComponents( txtinternalexaminer);
        externalexaminer.addComponents( txtexternalexaminer);
        material.addComponents( txtmaterial);
        time.addComponents( txttime);
        mark.addComponents( txtmark);
        instructions.addComponents( txtinstructions);
        labels.addComponents(lblVenue,lblcoursecode,lbltopicname,lbldate,lblyos,lbldegree,lblfaculties,lblinternalexaminer,lblexternalexaminer,lblmaterial,lbltime,lblmark,lblinstructions);
        content.addComponents(venue,coursecode, topic, date, yos, degree, faculties, internalexaminer, externalexaminer, mark, material, time, instructions, exe);

// Alignment of components

        content.setComponentAlignment(coursecode, Alignment.MIDDLE_LEFT);
        content.setComponentAlignment(mark,Alignment.MIDDLE_LEFT);
        content.setComponentAlignment(time, Alignment.MIDDLE_LEFT);
        content.setComponentAlignment(faculties, Alignment.MIDDLE_LEFT);
        content.setComponentAlignment(degree, Alignment.MIDDLE_LEFT);
        content.setComponentAlignment(instructions, Alignment.MIDDLE_LEFT);
        content.setComponentAlignment(internalexaminer, Alignment.MIDDLE_LEFT);
        content.setComponentAlignment(externalexaminer, Alignment.MIDDLE_LEFT);
        content.setComponentAlignment(material, Alignment.MIDDLE_LEFT);
        content.setComponentAlignment(yos, Alignment.MIDDLE_LEFT);
        content.setComponentAlignment(topic, Alignment.MIDDLE_LEFT);
        content.setComponentAlignment(date, Alignment.MIDDLE_LEFT);
        content.setComponentAlignment(exe, Alignment.BOTTOM_RIGHT);


        // set content area
        labels.setSizeFull();
        //addComponentsAndExpand(labels);
        content.setSizeFull();
        addComponentsAndExpand(labels,content);
        exe.setWidth(100.0f, Unit.PIXELS);
        txttopicname.setWidth(300.0f, Unit.PIXELS);
        txtVenue.setWidth(300.0f, Unit.PIXELS);
        txtcoursecode.setWidth(300.0f, Unit.PIXELS);
        txtdate.setWidth(300.0f, Unit.PIXELS);
        txtdegree.setWidth(300.0f, Unit.PIXELS);
        txtexternalexaminer.setWidth(300.0f, Unit.PIXELS);
        txtfaculties.setWidth(300.0f, Unit.PIXELS);
        txtinstructions.setWidth(300.0f, Unit.PIXELS);



    }

    //methodfor retrieving question
    public QuestionItem retreivequestion(TrackItem t) {
        int id = t.getQuestionId();
        QuestionServer questionServer = new QuestionServer(connection);
        return questionServer.getQuestionItemById(id);
    }

    public ExportItem getExport(){
      ex.setCoursecode(txtcoursecode.getValue());
      ex.setDate(txtdate.getValue());
      ex.setDegree(txtdegree.getValue());
      ex.setExternalexaminer(txtexternalexaminer.getValue());
      ex.setFaculties(txtfaculties.getValue());
      ex.setInstructions(txtinstructions.getValue());
      ex.setInternalexaminer(txtinternalexaminer.getValue());
      ex.setMark(txtmark.getValue());
      ex.setMaterial(txtmaterial.getValue());
      ex.setTime(txttime.getValue());
      ex.setTopicname(txttopicname.getValue());
      ex.setYos(txtyos.getValue());

      return  ex;

    }

   /* public void method(ExportItem ex) {

        File f = new File("C:\\Users\\User\\Desktop\\aaa");
       // Path sourceDirectory = Paths.get("/Users/umesh/personal/tutorials/source/Variation_Relations.csv");
        //Path targetDirectory = Paths.get("C:\\Users\\User\\Desktop\\aaa\\");

        //copy source to target using Files Class
        //Files.copy(sourceDirectory, targetDirectory);
        try{
            if(f.mkdir()) {
                System.out.println("Directory Created");
                Path sourceDirectory = Paths.get("C:\\Users\\User\\IdeaProjects\\Segfault\\Wits packages\\wits_code.sty");
                Path sourceDirectory1 = Paths.get("C:\\Users\\User\\IdeaProjects\\Segfault\\Wits packages\\wits_exam.sty");
                Path sourceDirectory2 = Paths.get("C:\\Users\\User\\IdeaProjects\\Segfault\\Wits packages\\wits_question.sty");
                Path sourceDirectory3 = Paths.get("C:\\Users\\User\\IdeaProjects\\Segfault\\Wits packages\\wits_flowchart.sty");
                Path sourceDirectory4 = Paths.get("C:\\Users\\User\\IdeaProjects\\Segfault\\Wits packages\\wits_pseudocode.sty");

                Path targetDirectory = Paths.get("C:\\Users\\User\\Desktop\\aaa\\wits_code.sty");
                Path targetDirectory1 = Paths.get("C:\\Users\\User\\Desktop\\aaa\\wits_exam.sty");
                Path targetDirectory2 = Paths.get("C:\\Users\\User\\Desktop\\aaa\\wits_question.sty");
                Path targetDirectory3 = Paths.get("C:\\Users\\User\\Desktop\\aaa\\wits_flowchart.sty");
                Path targetDirectory4 = Paths.get("C:\\Users\\User\\Desktop\\aaa\\wits_pseudocode.sty");

                Files.copy(sourceDirectory, targetDirectory);
                Files.copy(sourceDirectory1, targetDirectory1);
                Files.copy(sourceDirectory2, targetDirectory2);
                Files.copy(sourceDirectory3, targetDirectory3);
                Files.copy(sourceDirectory4, targetDirectory4);


                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\User\\Desktop\\aaa\\hi.tex"));
                writer.write(GenerateLatex(ex));
                writer.close();
                writer.close();
            } else {
                System.out.println("Directory is not created");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }*/

   /* public String GenerateLatex(ExportItem ex){

        String Venue = "Old Mutual Sports Hall";
        String setup = "\\documentclass[a4paper,11pt]{article}\n" +
                "\\usepackage{xcolor}\n" +
                "\\usepackage{wits_code}\n" +
                "\\usepackage{wits_exam}\n" +
                "\\usepackage{float}\n" +
                "\\usepackage{graphicx}\n" +
                "\\usepackage{array}\n" +
                "%\\usepackage{wits_flowchart}\n" +
                "%\\usepackage{wits_pseudocode}\n" +
                "\\usepackage{amsmath}\n" +
                "\\usepackage{charter}\n" +
                "\\usepackage{algpseudocode}\n" +
                "\n" +
                "\\usepackage[]{color}\n" +
                "\\usepackage{float}\n" +
                "\\usepackage{subcaption}\n" +
                "\\usepackage{varioref}\n" +
                "\\usepackage{hyperref}\n" +
                "\\usepackage{cleveref}\n" +
                "\n" +
                "\\definecolor{darkgreen}{rgb}{0.0,0.7,0.0}\n" +
                "\\hypersetup{\n" +
                "  colorlinks   = true,              %Colours links instead of ugly boxes\n" +
                "  urlcolor     = blue,              %Colour for external hyperlinks\n" +
                "  linkcolor    = blue,              %Colour of internal links\n" +
                "  citecolor    = darkgreen                %Colour of citations\n" +
                "}";
        String FrontPage ="\\newcommand{\\todo}{\\textbf{TODO}}\n" +
                "\\titleHeadTime{" + ex.getTime() + "}\n"+
                "\\titleHeadDay{"+ ex.getDate()+"}\n"+
                "\\titleHeadMonth{"+ex.getDate()+"}\n"+
                "\\titleHeadYear{"+ex.getDate()+"}\n"+
                "\\titleHeadVenue{"+Venue+"}\n"+
                "\\courseno{"+ex.getCoursecode()+"}\n"+
                "\\papertitle{"+ex.getTopicname()+"}\n"+
                "\\testmonth{"+ex.getDate()+"}\n"+
                "\\degrees{"+ex.getDegree()+"}\n"+
                "\\faculties{"+ex.getFaculties()+"}\n"+
                "\\internalexaminer{"+ex.getInternalexaminer()+ "}\n"+
                "\\externalexaminer{"+ex.getInternalexaminer()+"}\n"+
                "\\specialmaterial{"+ex.getMaterial()+"}\n"+
                "\\hoursallowance{"+ex.getTime()+"}\n"+
                "\\instructions{"+ex.getInstructions()+"}\n"+
                "\\usepackage{titling}\n" +
                "\\setlength{\\droptitle}{-7em}   % This is your set screw\n" +
                "\n" +
                "\\begin{document}\n" +
                "\\makeexamcover";


        return setup+"\n"+FrontPage;
    }*/

    //method for retreiving tracks
   /* public ArrayList<TrackItem> retreivetracks() {
        ExportServer exportServer = new ExportServer(connection);
        ArrayList<TrackItem> tracks = exportServer.get(29);
        System.out.println(tracks);
        System.out.println("im here");
        return tracks;
    }*/

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        // set page title
        UI.getCurrent().getPage().setTitle("Dashboard - Export Questions");

        // set active item
        dashboard.setActiveLink("export");

        // set nav cookie
        CookieHandling.addCookie(CookieName.NAV, "export", -1);

        // if not signed in kick out
        lecturerItem =  lecturerServer.getCurrentLecturerItem();

        if (lecturerItem == null) {

            // set message
            VaadinService.getCurrentRequest().setAttribute("message","Please Sign In");

            // navigate
            navigator.navigateTo("");
        }
    }

}

