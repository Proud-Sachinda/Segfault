package com.Client;

import com.AttributeHandling;
import com.Components.*;
import com.Dashboard;
import com.MyTheme;
import com.Objects.LecturerItem;
import com.Objects.QuestionItem;
import com.Objects.TestItem;
import com.Objects.TrackItem;
import com.Server.*;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.HasValue;
import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

@DesignRoot
public class EditorView extends HorizontalLayout implements View {

    // navigator used to change pages
    private Navigator navigator;

    // attributeHandling
    private AttributeHandling attributeHandling;

    // layouts for split panel
    private Panel questionArea = new Panel();
    private VerticalLayout paper = new VerticalLayout();
    private VerticalLayout explore = new VerticalLayout();
    private VerticalLayout paperArea = new VerticalLayout();
    private HorizontalLayout pagination = new HorizontalLayout();
    private VerticalLayout verticalLayoutRoot = new VerticalLayout();
    private HorizontalLayout paginationCenter = new HorizontalLayout();

    // add button absolute
    private TextField search;
    private final Button add = new Button("+");
    public final Button finish = new Button("Finish");
    private Button addPage = new Button("+");
    private ArrayList<Button> buttons;

    // server
    private TagServer tagServer;
    private TestServer testServer;
    private TrackServer trackServer;
    private CourseServer courseServer;
    private QuestionServer questionServer;

    // lecturer
    private LecturerItem lecturerItem;

    // base path
    private String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    // paper area variables
    private int testId;
    private String draftName;

    // pagination variables
    private int currentSelectedPaginationPage;
    private ArrayList<QuestionPaginationComponent> questionPaginationComponents;

    // storage for question item components
    private ArrayList<QuestionItemComponent> questionItemComponents = new ArrayList<>();

    // empty component
    private EmptyComponent emptyComponent = new EmptyComponent(basePath);

    // dashboard
    private Dashboard dashboard;

    public EditorView(Navigator navigator, Connection connection, AttributeHandling attributeHandling) {

        // we get the Apps Navigator object
        this.navigator = navigator;

        // set attributeHandling
        this.attributeHandling = attributeHandling;

        // init arrays
        buttons = new ArrayList<>();
        questionPaginationComponents = new ArrayList<>();

        // set up servers
        tagServer = new TagServer(connection);
        testServer = new TestServer(connection);
        trackServer = new TrackServer(connection);
        courseServer = new CourseServer(connection);
        questionServer = new QuestionServer(connection);

        // set to fill browser screen
        setSizeFull();

        // set up dashboard
        dashboard = new Dashboard(navigator, connection);
        addComponent(dashboard);

        // set content area
        HorizontalLayout content = new HorizontalLayout();
        content.setSizeFull();
        addComponentsAndExpand(content);

        // question paper section
        paper.setSizeFull();
        paper.addStyleName("paper-border");

        // set up split
        content.addComponents(paper, explore);

        // set up question explorer
        setUpQuestionExplorer();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        // set page title
        UI.getCurrent().getPage().setTitle("Dashboard - Question Editor");

        // set active item
        dashboard.setActiveLink("editor");

        // if not signed in kick out
        lecturerItem =  attributeHandling.getLecturerItem();


        if (lecturerItem == null) {

            // set message
            attributeHandling.setMessage("Please Sign In");

            // navigate
            navigator.navigateTo("");
        }

        // set up paper explorer
        setUpPaperExplorer();

        // set up questions
        setUpQuestions();

        // show notification is from create question page
        if (VaadinService.getCurrentRequest().getAttribute("question-post") != null) {
            boolean success = (boolean) VaadinService.getCurrentRequest().getAttribute("question-post");
            if (success) Notification.show("SUCCESS", "Question added", Notification.Type.TRAY_NOTIFICATION);
            VaadinService.getCurrentRequest().setAttribute("question-post", false);
        }
    }

    private void setUpPaperExplorer() {

        // remove any existing elements
        paper.removeAllComponents();
        paperArea.removeAllComponents();
        while (!questionPaginationComponents.isEmpty())
            questionPaginationComponents.remove(0);

        //get test item
        TestItem testItem = attributeHandling.getTestItem();

        if (testItem == null) {

            // create a draft
            CreateDraftComponent draft = new CreateDraftComponent();
            paper.addComponent(draft);
            paper.setComponentAlignment(draft, Alignment.MIDDLE_CENTER);
        }
        else {

            // set test id
            testId = testItem.getTestId();

            // set draft name
            String s = (Character.toString(testItem.getTestDraftName().charAt(0))).toUpperCase();
            draftName = s.concat(testItem.getTestDraftName().substring(1));

            // populate question pagination components
            int num = trackServer.getQuestionCount(testItem.getTestId());

            if (num == 0) questionPaginationComponents
                    .add(new QuestionPaginationComponent(1, testId, currentSelectedPaginationPage,
                            basePath, courseServer, questionServer, tagServer, trackServer, emptyComponent, paginationCenter,
                            verticalLayoutRoot, paperArea, buttons, questionPaginationComponents, questionItemComponents, navigator));
            else {

                // add pagination
                for (int i = 0; i < num; i++) questionPaginationComponents
                        .add(new QuestionPaginationComponent(i + 1, testId, currentSelectedPaginationPage,
                                basePath, courseServer, questionServer, tagServer, trackServer, emptyComponent, paginationCenter,
                                verticalLayoutRoot, paperArea, buttons, questionPaginationComponents, questionItemComponents, navigator));

                // add tracks
                ArrayList<TrackItem> items = trackServer.getTrackItemsByTestId(testId);

                if (items.size() > 0) {

                    for (QuestionPaginationComponent p : questionPaginationComponents) {

                        for (TrackItem t : items) {

                            if (t.getQuestionNumber() == p.getQuestionNumber()) {
                                p.addQuestionPaperItemComponent(t);
                            }
                        }
                    }
                }
            }


            // set current pagination page
            currentSelectedPaginationPage = questionPaginationComponents.size();

            // papers
            setUpPapers(testItem.isTestIsDraft());
        }
    }

    private void setUpPapers(boolean isDraft) {

        // empty elements
        while (!buttons.isEmpty()) buttons.remove(0);
        addPage = new Button("+");
        paginationCenter.removeAllComponents();

        // set up draft heading
        Label label = new Label();
        if (isDraft) {
            label.setValue(draftName + " * Draft");
            label.addStyleName(MyTheme.MAIN_TEXT_WARNING);
        }
        else {
            label.setValue(draftName);
            label.addStyleName(MyTheme.MAIN_TEXT_CHARCOAL);
        }
        label.addStyleNames(MyTheme.MAIN_OPACITY_60, MyTheme.MAIN_TEXT_SIZE_LARGE);

        // add components to paper section
        paper.addComponent(label);
        paperArea.setMargin(false);
        paper.addComponentsAndExpand(paperArea);

        // add pagination
        pagination.setWidth(100.0f, Unit.PERCENTAGE);

        // add number of questions
        int length = questionPaginationComponents.size();
        for (int i = 0; i < length; i++) {

            // page button
            Button page = new Button(Integer.toString(i + 1));

            // make last button the selected one and set pagination page
            if (i == length - 1) {
                // button selected
                page.addStyleName(MyTheme.MAIN_FLAT_ROUND_BUTTON_PAGINATION_SELECTED);

                // pagination selected
                changeQuestionPaginationComponent();
            }
            else page.addStyleName(MyTheme.MAIN_FLAT_ROUND_BUTTON_PAGINATION);

            // add to array list
            buttons.add(page);

            // add click listener
            page.addClickListener((Button.ClickListener) event -> {

                // unset color of selected button
                setAsSelectedPaginationButton(page);

                // get current page
                currentSelectedPaginationPage = Integer.parseInt(page.getCaption());
                changeQuestionPaginationComponent();
            });

            // add to pagination
            paginationCenter.addComponent(page);
        }

        // add a question pagination component
        addPage.addStyleNames(MyTheme.MAIN_FLAT_ROUND_BUTTON_PAGINATION_ADD, MyTheme.MAIN_BLUE);
        paginationCenter.addComponent(addPage);

        addPage.addClickListener((Button.ClickListener) event -> {

            if (buttons.size() == 12) {
                Notification.show("Maximum Reached",
                        "Can not add anymore questions", Notification.Type.WARNING_MESSAGE);
            }
            else {
                // add question pagination component
                QuestionPaginationComponent component = new QuestionPaginationComponent(
                        buttons.size() + 1, testId, currentSelectedPaginationPage,
                        basePath, courseServer, questionServer, tagServer, trackServer, emptyComponent, paginationCenter,
                        verticalLayoutRoot, paperArea, buttons, questionPaginationComponents, questionItemComponents, navigator);
                questionPaginationComponents.add(component);
                currentSelectedPaginationPage = buttons.size() + 1;

                // new button
                Button p = new Button(Integer.toString(buttons.size() + 1));

                // add button to array list
                buttons.add(p);

                // add click listener
                p.addClickListener((Button.ClickListener) event1 -> {

                    // unset color of selected button
                    setAsSelectedPaginationButton(p);

                    // change pagination page
                    currentSelectedPaginationPage = Integer.parseInt(p.getCaption());
                    changeQuestionPaginationComponent();
                });

                // set style
                setAsSelectedPaginationButton(p);
                changeQuestionPaginationComponent();

                paginationCenter.removeComponent(addPage);
                paginationCenter.addComponent(p);
                paginationCenter.addComponent(addPage);
            }
        });
        pagination.addComponent(paginationCenter);
        pagination.setComponentAlignment(paginationCenter, Alignment.MIDDLE_CENTER);
        paper.addComponent(pagination);
    }

    private void setUpQuestionExplorer() {

        // set up search bar and add
        search = new TextField();
        search.setPlaceholder("Search questions");
        search.addStyleName("main-flat-text-field");
        addSearchBarListener();
        explore.addComponent(search);

        // set up add button
        add.addStyleNames("main-flat-round-button", "main-flat-round-button-position");
        add.addClickListener((Button.ClickListener) clickEvent -> navigator.navigateTo("create"));
        explore.addComponent(add);

        // set up question area panel
        explore.addComponentsAndExpand(questionArea);
    }

    private void addSearchBarListener() {

        search.addValueChangeListener((HasValue.ValueChangeListener<String>)
                event -> setUpQuestions(event.getValue()));
    }

    private void setUpQuestions() {

        // remove all components
        verticalLayoutRoot.removeAllComponents();

        // get questions
        questionItemComponents = new ArrayList<>();

        //get test item
        TestItem testItem = attributeHandling.getTestItem();

        ArrayList<QuestionItem> questionArrayList;

        if (testItem == null) questionArrayList = questionServer.getQuestionItems();
        else questionArrayList = questionServer.getQuestionItemsByTestId(testItem.getTestId());

        // set up root question layout
        verticalLayoutRoot.setMargin(false);

        if (questionArrayList.isEmpty()) addEmptyComponent(EmptyComponent.FIRST);
        else addQuestionItems(questionArrayList);

        // add root question layout
        questionArea.addStyleName(ValoTheme.PANEL_BORDERLESS);
        questionArea.setContent(verticalLayoutRoot);
    }

    private void setUpQuestions(String query) {

        // remove all components
        verticalLayoutRoot.removeAllComponents();

        // get questions
        questionItemComponents = new ArrayList<>();

        //get test item
        TestItem testItem = attributeHandling.getTestItem();

        ArrayList<QuestionItem> questionArrayList = questionServer.getQuestionItemsBySearch(query, testItem);

        // set up root question layout
        verticalLayoutRoot.setMargin(false);

        if (questionArrayList.isEmpty()) addEmptyComponent(EmptyComponent.NO_QUESTIONS_FOUND);
        else addQuestionItems(questionArrayList);
    }

    private void addEmptyComponent(String type) {

        // show user that they dont have questions in database create question
        verticalLayoutRoot.setSizeFull();

        // no questions found
        emptyComponent.setType(type);

        // add to root
        verticalLayoutRoot.addComponent(emptyComponent);
        verticalLayoutRoot.setComponentAlignment(emptyComponent, Alignment.MIDDLE_CENTER);
    }

    private void addQuestionItems(ArrayList<QuestionItem> questionArrayList) {

        // remove empty question component
        verticalLayoutRoot.setHeightUndefined();
        verticalLayoutRoot.removeComponent(emptyComponent);

        // add questions loop through questions and add to view
        for (QuestionItem q : questionArrayList) {

            // declare new question item component
            QuestionItemComponent questionItemComponent =
                    new QuestionItemComponent(questionServer, courseServer, tagServer, navigator,
                            verticalLayoutRoot, questionItemComponents, emptyComponent);
            questionItemComponent.setQuestionItem(q);

            // set up question item component
            questionItemComponent.setUpQuestionItemComponent();
            questionItemComponents.add(questionItemComponent);

            // add to vertical layout root
            verticalLayoutRoot.addComponent(questionItemComponent.getThisQuestionItemComponent());
        }
    }

    private void setAsSelectedPaginationButton(Button button) {

        for (Button p : buttons) {
            if (p.getStyleName().equals(MyTheme.MAIN_FLAT_ROUND_BUTTON_PAGINATION_SELECTED))
                p.removeStyleName(MyTheme.MAIN_FLAT_ROUND_BUTTON_PAGINATION_SELECTED);
            p.addStyleName(MyTheme.MAIN_FLAT_ROUND_BUTTON_PAGINATION);
        }

        // set current as selected
        button.removeStyleName(MyTheme.MAIN_FLAT_ROUND_BUTTON_PAGINATION);
        button.addStyleName(MyTheme.MAIN_FLAT_ROUND_BUTTON_PAGINATION_SELECTED);
    }

    private void changeQuestionPaginationComponent() {

        // remove all components
        paperArea.removeAllComponents();

        // add question item components
        paperArea.addComponent(
                questionPaginationComponents.get(currentSelectedPaginationPage - 1));
    }

    // reusable components
    public class CreateDraftComponent extends VerticalLayout {

        CreateDraftComponent() {

            // new draft form
            addStyleName(MyTheme.MAIN_FLAT_FLOATING_BOX);
            setWidth(60.0f, Unit.PERCENTAGE);

            // create draft image
            FileResource emptyResource = new FileResource(new File(basePath + "/WEB-INF/images/empty-papers.svg"));
            Image empty = new Image(null, emptyResource);
            empty.setWidth(64.0f, Unit.PIXELS);
            empty.setHeight(64.0f, Unit.PIXELS);
            empty.addStyleName(MyTheme.MAIN_OPACITY_60);

            // create draft label
            HorizontalLayout header = new HorizontalLayout();
            header.setWidth(100f, Unit.PERCENTAGE);
            Label label = new Label("New draft");
            header.addComponent(label);
            label.addStyleName(MyTheme.MAIN_TEXT_WARNING);
            label.addStyleName(MyTheme.MAIN_TEXT_SIZE_LARGE);

            // check box for test/exam
            CheckBox isTest = new CheckBox("Test");
            isTest.addStyleName(ValoTheme.LAYOUT_CARD);
            header.addComponent(isTest);

            // align header items
            header.setComponentAlignment(label, Alignment.MIDDLE_LEFT);
            header.setComponentAlignment(isTest, Alignment.MIDDLE_RIGHT);

            // create draft input field
            TextField textField = new TextField();
            textField.setMaxLength(25);
            textField.setWidth(100.0f, Unit.PERCENTAGE);
            textField.setPlaceholder("Enter draft name");

            // course subjects
            CourseComboBox comboBox = new CourseComboBox(courseServer.getCourseItems());
            comboBox.setWidth(100.0f, Unit.PERCENTAGE);
            comboBox.addComboBoxValueChangeListener();

            // submit button
            Button submit = new Button("Create");
            submit.addClickListener((Button.ClickListener) event -> {

                // check if text field is empty
                if (textField.isEmpty() || !(comboBox.getCourseId() > 0)) {
                    textField.setCaption("*fill all fields");
                }
                else {

                    // set title of heading
                    String textFieldValue = textField.getValue().trim();
                    String s = (Character.toString(textFieldValue.charAt(0))).toUpperCase();
                    draftName = s.concat(textFieldValue.substring(1));

                    // create test item
                    TestItem testItem = new TestItem(true, !isTest.getValue(), draftName);

                    // take to database
                    testId = testServer.postToTestTable(testItem, comboBox.getCourseId(), lecturerItem.getLecturerId());
                    if (!(testId > 0))
                        Notification.show("ERROR", "Could not create draft", Notification.Type.WARNING_MESSAGE);

                    // populate question pagination array list
                    int length = questionPaginationComponents.size();
                    for (int i = 0; i < length; i++) {
                        QuestionPaginationComponent q = questionPaginationComponents.get(i);
                        questionPaginationComponents.remove(q);
                    }
                    questionPaginationComponents.add(new QuestionPaginationComponent(
                            1, testId, currentSelectedPaginationPage,
                            basePath, courseServer, questionServer, tagServer, trackServer, emptyComponent, paginationCenter,
                            verticalLayoutRoot, paperArea, buttons, questionPaginationComponents, questionItemComponents, navigator));

                    // set current pagination page
                    currentSelectedPaginationPage = 1;


                    // add finish click listener
                    setUpQuestions();

                    // set up paper
                    setUpPapers(true);

                    // get test item
                    TestItem t = testServer.getTestItemById(testId);
                    attributeHandling.setTestItem(t);

                    // set up questions


                    // remove box
                    paper.removeComponent(this);
                }
            });

            // add components
            addComponents(empty, header, textField, comboBox, submit);

            // align in middle
            setComponentAlignment(empty, Alignment.MIDDLE_CENTER);
            setComponentAlignment(submit, Alignment.MIDDLE_CENTER);
        }
    }
}
