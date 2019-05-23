package com.Server;

import com.Objects.ExportItem;
import com.Objects.LecturerItem;
import com.Objects.QuestionItem;
import com.Objects.TrackItem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ExportServer {
    // connection variable
    private Connection connection;

    TrackItem tracky = new TrackItem();
    QuestionServer qs ;

    public ExportServer(Connection connection) {

        // initialise connection variable
        this.connection = connection;
    }



    public TrackItem getTrack(){
        return this.tracky;
    }

    public String latexQuestion(ArrayList<TrackItem> trackzy){
        qs  = new QuestionServer(connection);
        String lq = "\\question{Short Questions}\n" +
                "\n" +
                "\\begin{enumerate}\n";
        for(int i = 0;i<trackzy.size();i++){
            TrackItem ti = trackzy.get(i);
            QuestionItem s = qs.getQuestionItemById(ti.getQuestionId());
            String lines = "\\ansline";
            for(int x=0;x<=s.getQuestionWrittenNoOfLines();i++){
                lines = lines+" \\ansline";

            }
            if(s.getQuestionType() == "written"){
                lq = lq+"\\item "+ s.getQuestionBody()+ "\\mk{"+ s.getQuestionMark()+"}\n"+lines+"\n\n";

            }
        }

        lq=lq+"\\end{enumerate}\n";
        return lq;
    }

    public void method(ExportItem ex) {

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
                //writer.write(latexQuestion());
                writer.write("\\end{document}");
                writer.close();
                writer.close();
            } else {
                System.out.println("Directory is not created");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public String GenerateLatex(ExportItem exp){
        ExportItem ex = exp;
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
    }

    public ArrayList<TrackItem> get(int testid) {
        //arraylist to store  tracks
        ArrayList<TrackItem> tracks = new ArrayList<>();
        try{
            //int testid=0;
            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.track where test_id =" +testid;

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {

                // Question class variable
                TrackItem track = new TrackItem();

                // set variables
                track.setTrackId(set.getInt("track_id"));
                track.setQuestionId(set.getInt("question_id"));
                track.setTestId(set.getInt("test_id"));
                track.setQuestionNumber(set.getInt("question_number"));
                track.setTrackOrder(set.getInt("track_order"));


                // add to array list
                tracks.add(track);
                System.out.println("i am");
            }
            for(int i=0;i<tracks.size();i++){
                System.out.println(tracks.get(i).getTrackId());
            }

        }catch (SQLException e){

            System.out.println(e.getMessage());
        }
        return tracks;
    }

    public int getTestITemQuestionCount(int testId) {

        // return variable
        int count = 0;

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT DISTINCT * FROM public.track WHERE test_id = " + testId;

            // execute statement
            ResultSet set = statement.executeQuery(query);

            LinkedHashSet<Integer> counter = new LinkedHashSet<>();

            while(set.next()) {
                counter.add(set.getInt("question_number"));
            }

            count = counter.size();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
}
