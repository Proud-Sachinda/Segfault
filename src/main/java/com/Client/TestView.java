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
import com.itextpdf.text.pdf.*;
import com.vaadin.data.Binder;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import com.itextpdf.text.*;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.ArrayList;


public class TestView extends HorizontalLayout implements View {
    // navigator used to redirect to another page
    private Navigator navigator;

    // connection for database
    private Connection connection;

    // navigation and content area
    final VerticalLayout navigation = new VerticalLayout();
    final VerticalLayout content = new VerticalLayout();
    final HorizontalLayout coursecode = new HorizontalLayout();
    final HorizontalLayout coursename = new HorizontalLayout();
    final HorizontalLayout testdate = new HorizontalLayout();
    final HorizontalLayout testmark = new HorizontalLayout();
    final HorizontalLayout examiner = new HorizontalLayout();
    final Label lblcourse = new Label("Course code: ");
    final Label lblcoursename = new Label("Course name: ");
    final Label lbldate = new Label("Test date: ");
    final Label lblexaminer = new Label("Examiner: ");
    final Label lblmark = new Label("Marks: ");
    final TextField txtcourse = new TextField();
    final TextField txtcoursename = new TextField();
    final TextField txtdate = new TextField();
    final TextField txtexaminer = new TextField();
    final TextField txtmark = new TextField();
    final Button exe = new Button("Export");



    // lecturer
    private LecturerItem lecturerItem;
    private LecturerServer lecturerServer;

    // dashboard
    private Dashboard dashboard;

    public TestView(Navigator navigator, Connection connection) {

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

        coursecode.addComponents(lblcourse, txtcourse);
        coursename.addComponents(lblcoursename, txtcoursename);
        testdate.addComponents(lbldate, txtdate);
        examiner.addComponents(lblexaminer, txtexaminer);
        testmark.addComponents(lblmark, txtmark);
        content.addComponents(coursecode, coursename, testdate, examiner, testmark, exe);

        content.setComponentAlignment(coursecode, Alignment.MIDDLE_CENTER);
        content.setComponentAlignment(coursename, Alignment.MIDDLE_CENTER);
        content.setComponentAlignment(testdate, Alignment.MIDDLE_CENTER);
        content.setComponentAlignment(examiner, Alignment.MIDDLE_CENTER);
        content.setComponentAlignment(testmark, Alignment.MIDDLE_CENTER);
        content.setComponentAlignment(exe, Alignment.MIDDLE_CENTER);

        content.setSizeFull();
        addComponentsAndExpand(content);
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
        lecturerItem = lecturerServer.getCurrentLecturerItem();

        if (lecturerItem == null) {

            // set message
            VaadinService.getCurrentRequest().setAttribute("message", "Please Sign In");

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


    public void CreatePDF(ArrayList<String> questions) {


        Document document = new Document();
        PdfName pdfName = new PdfName(txtcoursename.getValue() + txtdate.getValue());
        document.addTitle(pdfName.toString());
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Public\\" + pdfName + ".pdf"));
            document.open();
            document.newPage();
            //PdfPHeaderCell header = new PdfPHeaderCell();
            //header.setName(txtcourse.getValue() + "\n"+ txtcoursename.getValue() + "\n" + txtdate.getValue() + "\n" + txtexaminer.getValue() + "\n" + txtmark.getValue());

            Paragraph p = new Paragraph(txtcourse.getValue());
            p.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p);
            Paragraph p2 = new Paragraph(txtcoursename.getValue());
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            Paragraph p3 = new Paragraph(txtdate.getValue());
            p3.setAlignment(Paragraph.ALIGN_CENTER);
            Paragraph p4 = new Paragraph(txtexaminer.getValue());
            p4.setAlignment(Paragraph.ALIGN_CENTER);
            Paragraph p5 = new Paragraph("Marks: "+ txtmark.getValue());
            p5.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p2);
            document.add(p3);
            document.add(p4);
            document.add(p5);


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

