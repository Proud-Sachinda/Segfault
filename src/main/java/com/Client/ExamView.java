package com.Client;

import com.CookieHandling.CookieHandling;
import com.CookieHandling.CookieName;
import com.Dashboard;
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
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.ui.themes.ValoTheme;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.ArrayList;

public class ExamView extends HorizontalLayout implements View {

    // navigator used to redirect to another page
    private Navigator navigator;

    // connection for database
    private Connection connection;

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
                // ExportServer trackServer = new ExportServer(connection);
                System.out.println("are you coming");
                ArrayList<String> Q = new ArrayList<String>();
                ArrayList<TrackItem> tracks = retreivetracks();
                for (int i = 0; i < tracks.size(); i++) {

                    QuestionItem q = retreivequestion(tracks.get(i));
                    String qbody = q.getQuestionBody();
                    Q.add(qbody);
                    System.out.println(qbody);
                }

                CreatePDF(Q);
                Notification.show(" File saved in C:// Users// Public");


            }
        });
        // set up dashboard
        dashboard = new Dashboard(navigator);
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
        content.addComponents(coursecode, topic, date, yos, degree, faculties, internalexaminer, externalexaminer, mark, material, time, instructions, exe);

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


    }

    //methodfor retrieving question
    public QuestionItem retreivequestion(TrackItem t) {
        int id = t.getQuestionId();
        QuestionServer questionServer = new QuestionServer(connection);
        return questionServer.getQuestionItemById(id);
    }

    //method for retreiving tracks
    public ArrayList<TrackItem> retreivetracks() {
        ExportServer exportServer = new ExportServer(connection);
        ArrayList<TrackItem> tracks = exportServer.get(29);
        System.out.println(tracks);
        System.out.println("im here");
        return tracks;
    }

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

    public PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    public PdfPCell getCell2(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    public PdfPCell getCell3(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    public PdfPCell getCell4(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    public PdfPCell getCell5(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    public PdfPCell getCell6(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    public PdfPCell getCell7(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    public PdfPCell getCell8(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    public PdfPCell getCell9(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    public PdfPCell getCell10(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    public PdfPCell getCell11(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    public PdfPCell getCell12(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    public PdfPCell getCell13(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));

        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    public void CreatePDF(ArrayList<String> questions) {


        Document document = new Document();
        PdfName pdfName = new PdfName(txttopicname.getValue() + txtdate.getValue());
        document.addTitle(pdfName.toString());
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Public\\" + pdfName + ".pdf"));
            document.open();
            PdfPTable table = new PdfPTable(2);

            table.setWidthPercentage(50);
            table.setPaddingTop(100);
            table.setTotalWidth(100);


            //code for creating exam page
            table.addCell(getCell(lblcoursecode.getValue() + ": ", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell(txtcoursecode.getValue(), PdfPCell.ALIGN_MIDDLE));
            table.addCell(getCell2(lbltopicname.getValue() + ": ", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell2(txttopicname.getValue(), PdfPCell.ALIGN_MIDDLE));
            table.addCell(getCell3(lbldate.getValue() + ": ", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell3(txtdate.getValue(), PdfPCell.ALIGN_MIDDLE));
            table.addCell(getCell4(lblyos.getValue(), PdfPCell.ALIGN_LEFT));
            table.addCell(getCell4(txtyos.getValue(), PdfPCell.ALIGN_MIDDLE));
            table.addCell(getCell5(lbldegree.getValue(), PdfPCell.ALIGN_LEFT));
            table.addCell(getCell5(txtdegree.getValue(), PdfPCell.ALIGN_MIDDLE));
            table.addCell(getCell6(lblfaculties.getValue(), PdfPCell.ALIGN_LEFT));
            table.addCell(getCell6(txtfaculties.getValue(), PdfPCell.ALIGN_MIDDLE));
            table.addCell(getCell7(lblinternalexaminer.getValue(), PdfPCell.ALIGN_LEFT));
            table.addCell(getCell7(txtinternalexaminer.getValue(), PdfPCell.ALIGN_MIDDLE));
            table.addCell(getCell8(lblexternalexaminer.getValue(), PdfPCell.ALIGN_LEFT));
            table.addCell(getCell8(txtexternalexaminer.getValue(), PdfPCell.ALIGN_MIDDLE));
            table.addCell(getCell10(lblmaterial.getValue(), PdfPCell.ALIGN_LEFT));
            table.addCell(getCell10(txtmaterial.getValue(), PdfPCell.ALIGN_MIDDLE));
            table.addCell(getCell11(lbltime.getValue(), PdfPCell.ALIGN_LEFT));
            table.addCell(getCell11(txttime.getValue(), PdfPCell.ALIGN_MIDDLE));
            table.addCell(getCell12(lblmark.getValue(), PdfPCell.ALIGN_LEFT));
            table.addCell(getCell12(txtmark.getValue(), PdfPCell.ALIGN_MIDDLE));
            table.addCell(getCell13(lblinstructions.getValue(), PdfPCell.ALIGN_LEFT));
            table.addCell(getCell13(txtinstructions.getValue(), PdfPCell.ALIGN_MIDDLE));
            document.add(table);


            //adds questions to paper
            for (int i = 0; i < questions.size(); i++) {

                document.newPage();
                document.add(new Paragraph(questions.get(i) + " "));

            }


            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
            e.printStackTrace();


        }
    }
}

