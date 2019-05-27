package com.Server;

import com.Objects.ExportItem;
import com.Objects.QuestionItem;
import com.Objects.TestItem;
import com.Objects.TrackItem;
import com.vaadin.server.VaadinService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ExportServer {
    // connection variable
    private Connection connection;
    private String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    TrackItem tracky = new TrackItem();
    TestItem ti = new TestItem();
    QuestionServer qs ;
    ExportServer es;
    TestServer ts ;

    public ExportServer(Connection connection) {

        // initialise connection variable
        this.connection = connection;
    }



    public TrackItem getTrack(){
        return this.tracky;
    }

    public String latexQuestion(ArrayList<TrackItem> trackzy){
        qs  = new QuestionServer(connection);
       // System.out.println("latex");
        String lq = "\\question{"+"}\n" +
                "\n" +
                "\\begin{enumerate}\n";
        //System.out.println(lq);
        //System.out.println(trackzy.size());
        for(int i = 0;i<trackzy.size();i++){
            TrackItem ti = trackzy.get(i);
            QuestionItem s = qs.getQuestionItemById(ti.getQuestionId());
            //written question
            if(s.getQuestionType().equals("written")){
                String lines = "\\ansline";
                for(int x=0;x<=s.getQuestionWrittenNoOfLines();x++){
                    lines = lines+" \\ansline";
                }
                lq = lq+"\\item "+ s.getQuestionBody()+ "\\mk{"+ s.getQuestionMark()+"}\n"+lines+"\n\n";
                //System.out.println("ifffffS");
            }
            else if(s.getQuestionType().equals("practical")){
                String Input = qs.getInputById(s.getQuestionId());
                String Output = qs.getOutputById(s.getQuestionId());
                lq = lq+"\\item "+ s.getQuestionBody()+ "\\mk{"+ s.getQuestionMark()+"}\n"+"\nSample Input\n"+"\\begin{enumerate}\n"+
                        "\\item \\texttt{"+Input+"}\n"+"\\end{enumerate}"+ "Sample Output\n"+"\\begin{enumerate}\n"+"\\item \\texttt{"+ Output+"}"+"\\end{enumerate}";

            }
            else if(s.getQuestionType().equals("mcq")){
                String choice = qs.getMCQchoiceById(s.getQuestionId());
                String[] ch = choice.split(";");
                String choices = "\\item \\texttt{"+ch[0]+"}";
                System.out.println(s.getQuestionId()+" qid " + ch[0]);
                for(int x=1;x<ch.length;x++){
                    choices = "\n"+choices+"\\item \\texttt{"+ch[x]+"}";
                }

                lq = lq+"\\item "+ s.getQuestionBody()+ "\\mk{"+ s.getQuestionMark()+"}\n"+"\\begin{enumerate}"+choices+"\n"+"\\end{enumerate}";
            }

        }

        lq=lq+"\\end{enumerate}\n"+"\\newpage\n";
        //System.out.println(lq);
        return lq;
    }

    public void method(ExportItem ex, int tId) {
        es = new ExportServer(connection);

        ts = new TestServer(connection);
        ti = ts.getTestItemById(tId);
        String testname = ti.getTestDraftName();
        File f = new File("C:\\"+testname);
        try{
            if(f.mkdir()) {
                System.out.println("Directory Created");
                Path sourceDirectory = Paths.get(basePath + "/WEB-INF/Wits packages/wits_code.sty");
                Path sourceDirectory1 = Paths.get(basePath + "/WEB-INF/Wits packages/wits_exam.sty");
                Path sourceDirectory2 = Paths.get(basePath + "/WEB-INF/Wits packages/wits_question.sty");
                Path sourceDirectory3 = Paths.get(basePath + "/WEB-INF/Wits packages/wits_flowchart.sty");
                Path sourceDirectory4 = Paths.get(basePath + "/WEB-INF/Wits packages/wits_pseudocode.sty");

                Path targetDirectory = Paths.get("C:\\"+testname+"\\wits_code.sty");
                Path targetDirectory1 = Paths.get("C:\\"+testname+"\\wits_exam.sty");
                Path targetDirectory2 = Paths.get("C:\\"+testname+"\\wits_question.sty");
                Path targetDirectory3 = Paths.get("C:\\"+testname+"\\wits_flowchart.sty");
                Path targetDirectory4 = Paths.get("C:\\"+testname+"\\wits_pseudocode.sty");

                Files.copy(sourceDirectory, targetDirectory);
                Files.copy(sourceDirectory1, targetDirectory1);
                Files.copy(sourceDirectory2, targetDirectory2);
                Files.copy(sourceDirectory3, targetDirectory3);
                Files.copy(sourceDirectory4, targetDirectory4);
                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\"+testname+"\\"+testname+".tex"));
                String str = "not specifies whether is test of exam";
                if(ti.isTestIsExam() == true){
                     str = GenerateLatex(ex);
                }
                else if(ti.isTestIsExam() == false){
                    str = GenerateLatexTest(ex);
                }

                int questionNoCount = getTestITemQuestionCount(tId);
                for(int y=1;y<questionNoCount+1;y++){
                    str = str+ latexQuestion(get1(tId,y));
                }
                str = str + "\\end{document}";
                //System.out.println(latexQuestion(get(25)));
                //System.out.println(str);
                writer.write(str);
               // writer.write(latexQuestion(get(28)));
               // writer.write("\\end{document}");
                writer.close();
                writer.close();
            } else {
                System.out.println("Directory is not created");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public String GenerateLatexTest(ExportItem exp){
        ExportItem ex = exp;
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
        String frontpage = "\\newcommand{\\todo}{\\textbf{TODO}}\n" +
                "\\titleHeadTime{"+ex.getTime()+"}\n" +
                "\\titleHeadDay{1}\n" +
                "\\titleHeadMonth{Mar}\n" +
                "\\titleHeadYear{2019}\n" +
                "\\titleHeadVenue{"+ex.getVenue()+"}\n" +
                "% First set up fieled that the exams office wants\n" +
                "\\courseno{"+ex.getCoursecode()+"}\n" +
                "\\papertitle{"+ex.getTopicname()+"}\n" +
                "\\testmonth{7 March}\n" +
                "\\testyear{2019}\n" +
                "%\\yearofstudy{"+ex.getYos()+"} % None for Arts and Science\n" +
                "\\degrees{"+ex.getDegree()+"}\n" +
                "\\faculties{"+ex.getFaculties()+"}\n" +
                "\\internalexaminer{"+ex.getInternalexaminer()+"}\n" +
                "\\externalexaminer{"+ex.getExternalexaminer()+"}\n" +
                "\\specialmaterial{"+ex.getMaterial()+"}\n" +
                "\\hoursallowance{"+ex.getTime()+"}\n" +
                "\\instructions{\\thetotalmarks\\ Marks available. \\thetotalmarks\\ marks = 100\\%.\\\\ "+ex.getInstructions()+"}\n" +
                "%% Following field not needed for \\examcover but for \\simpleexamhead\n" +
                "%\\department{Computer Science}\n" +
                "%\\solutiontitle{Mark scheme}\n" +
                "\n" +
                "%\\newcommand{\\ansline}{\\\\[15pt]\\rule{\\linewidth}{0.1pt}}\n" +
                "%\\newcommand{\\anslineL}{\\rule{\\linewidth}{0.1pt}}\n" +
                "    \n" +
                "\\usepackage{titling}\n" +
                "\\setlength{\\droptitle}{-7em}   % This is your set screw\n" +
                "\\title{"+ex.getTopicname()+"}\n" +
                "\\date{"+ex.getDate()+"}";

        return setup+"\n"+frontpage;
    }
    public String GenerateLatex(ExportItem exp){
        ExportItem ex = exp;
        //System.out.println("generate");
       // String Venue = "Old Mutual Sports Hall";
        //System.out.println(Venue);
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
        //System.out.println(Venue);
        //System.out.println(setup);
        String FrontPage ="\\newcommand{\\todo}{\\textbf{TODO}}\n" +
                "\\titleHeadTime{" + ex.getTime() + "}\n"+
                "\\titleHeadDay{"+ ex.getDate()+"}\n"+
                "\\titleHeadMonth{"+ex.getDate()+"}\n"+
                "\\titleHeadYear{"+ex.getDate()+"}\n"+
                "\\titleHeadVenue{"+ex.getVenue()+"}\n"+
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

       // System.out.println(Venue);

        String s = setup+"\n"+FrontPage;
        //System.out.println(s);


        return s;
    }

    public ArrayList<TrackItem> get(int testid) {
        //arraylist to store  tracks
        ArrayList<TrackItem> tracks = new ArrayList<>();
        try{
            //int testid=0;
            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.track where test_id =" +testid ;

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

    public ArrayList<TrackItem> get1(int testid, int qnum) {
        //arraylist to store  tracks
        ArrayList<TrackItem> tracks = new ArrayList<>();
        try{
            //int testid=0;
            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.track where test_id =" +testid +"and question_number = "+qnum;

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
