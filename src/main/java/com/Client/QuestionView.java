package com.Client;

import com.Components.CourseComboBox;
import com.Components.EmptyQuestionsComponent;
import com.Components.QuestionItemComponent;
import com.Components.QuestionPaperItemComponent;
import com.Dashboard;
import com.MyTheme;
import com.Objects.QuestionItem;
import com.Server.CourseServer;
import com.Server.QuestionServer;
import com.Server.TagServer;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.HasValue;
import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.ui.*;
import com.vaadin.ui.dnd.DropTargetExtension;
import com.vaadin.ui.dnd.event.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

@DesignRoot
public class QuestionView extends HorizontalLayout implements View {

    // navigator used to change pages
    private Navigator navigator;

    // connection for database
    private Connection connection;

    // route strings - nothing special just things like qbank_exploded_war/route_name
    protected final String question = "question";
    protected final String course = "course";
    protected final String export = "export";

    // layouts for split panel
    private Panel questionArea = new Panel();
    private VerticalLayout paper = new VerticalLayout();
    private VerticalLayout explore = new VerticalLayout();
    private VerticalLayout paperArea = new VerticalLayout();
    private HorizontalLayout pagination = new HorizontalLayout();
    private VerticalLayout verticalLayoutRoot = new VerticalLayout();
    private HorizontalLayout paginationCenter = new HorizontalLayout();

    // add button absolute
    private final Button add = new Button("+");
    private final Button addPage = new Button("+");
    private final ArrayList<Button> buttons = new ArrayList<>();

    // server
    private TagServer tagServer;
    private CourseServer courseServer;
    private QuestionServer questionServer;

    // base path
    private String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    // paper area variables
    private int testId;
    private String draftName;

    // boolean to check if user has been on this page before
    private boolean hasEnteredPageBefore = false;

    // pagination variables
    private int currentSelectedPaginationPage;
    private ArrayList<QuestionPaginationComponent> questionPaginationComponents;

    // storage for question item components
    private ArrayList<QuestionItemComponent> questionItemComponents = new ArrayList<>();

    // empty component
    private EmptyQuestionsComponent emptyQuestionsComponent = new EmptyQuestionsComponent(basePath);

    public QuestionView(Navigator navigator, Connection connection) {

        // we get the Apps Navigator object
        this.navigator = navigator;

        // set connection variable
        this.connection = connection;

        // set up servers
        tagServer = new TagServer(this.connection);
        courseServer = new CourseServer(this.connection);
        questionServer = new QuestionServer(this.connection);

        // set to fill browser screen
        setSizeFull();

        // set up dashboard
        Dashboard dashboard = new Dashboard(navigator);
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

        // set up paper explorer
        setUpPaperExplorer();

        // set up question explorer
        setUpQuestionExplorer();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        // set page title
        UI.getCurrent().getPage().setTitle("Dashboard - Question Editor");

        // set up questions
        setUpQuestions();

        // set page as entered
        hasEnteredPageBefore = true;

        // show notification is from create question page
        if (VaadinService.getCurrentRequest().getAttribute("question-post") != null) {
            boolean success = (boolean) VaadinService.getCurrentRequest().getAttribute("question-post");
            if (success) Notification.show("SUCCESS", "Question added", Notification.Type.TRAY_NOTIFICATION);
            VaadinService.getCurrentRequest().setAttribute("question-post", false);
        }
    }

    public EmptyQuestionsComponent getEmptyQuestionsComponent() {
        return this.emptyQuestionsComponent;
    }

    private void setUpPaperExplorer() {

        // if there are no papers in the database add a create paper button
        if (true) {
            CreateDraftComponent draft = new CreateDraftComponent();
            paper.addComponent(draft);
            paper.setComponentAlignment(draft, Alignment.MIDDLE_CENTER);
        }
        else {
            // show a list of drafts and finals
            Label label = new Label("Recent");
            label.addStyleNames(MyTheme.MAIN_TEXT_SIZE_LARGE, MyTheme.MAIN_OPACITY_60, MyTheme.MAIN_TEXT_WEIGHT_900);

            // add labels to paper
            paper.addComponent(label);
        }

    }

    private void setUpPapers() {

        // set up draft heading
        Label label = new Label(draftName + " * Draft");
        label.addStyleNames(MyTheme.MAIN_TEXT_WARNING, MyTheme.MAIN_OPACITY_60);
        label.addStyleName(MyTheme.MAIN_TEXT_SIZE_LARGE);

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
                QuestionPaginationComponent component = new QuestionPaginationComponent(buttons.size() + 1);
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
        TextField search = new TextField();
        search.setPlaceholder("Search questions");
        search.addStyleName("main-flat-text-field");
        explore.addComponent(search);

        // set up add button
        add.addStyleNames("main-flat-round-button", "main-flat-round-button-position");
        add.addClickListener((Button.ClickListener) clickEvent -> navigator.navigateTo("createquestion"));
        explore.addComponent(add);

        // set up filtering
        NativeSelect<String> selectOrder = new NativeSelect<>();
        selectOrder.setItems("Recent", "Difficulty", "Date used", "Date published");
        selectOrder.setSelectedItem("Recent");
        NativeSelect<String> selectFilter = new NativeSelect<>();
        selectFilter.setItems("None", "Subject", "Tags");
        selectFilter.setSelectedItem("None");
        HorizontalLayout filtering = new HorizontalLayout();
        Label order = new Label("Order by - ");
        Label filter = new Label("Filter by - ");
        filtering.addComponents(order, selectOrder, filter, selectFilter);
        explore.addComponent(filtering);

        // set up question area panel
        explore.addComponentsAndExpand(questionArea);
    }

    private void setUpQuestions() {

        // get questions
        questionItemComponents = new ArrayList<>();
        ArrayList<QuestionItem> questionArrayList = questionServer.getQuestionItems();

        // set up root question layout
        verticalLayoutRoot.setMargin(false);

        if (questionArrayList.isEmpty()) {

            // show user that they dont have questions in database create question
            verticalLayoutRoot.setSizeFull();

            // no questions found
            emptyQuestionsComponent.setQuestionType(EmptyQuestionsComponent.FIRST);

            // add to root
            verticalLayoutRoot.addComponent(emptyQuestionsComponent);
            verticalLayoutRoot.setComponentAlignment(emptyQuestionsComponent, Alignment.MIDDLE_CENTER);
        }
        else {
            // add questions loop through questions and add to view
            for (QuestionItem q : questionArrayList) {

                // declare new question item component
                QuestionItemComponent questionItemComponent =
                        new QuestionItemComponent(questionServer, courseServer, tagServer, navigator);
                questionItemComponent.getQuestionItem().setQuestionId(q.getQuestionId());
                questionItemComponent.getQuestionItem().setQuestionAns(q.getQuestionAns());
                questionItemComponent.getQuestionItem().setQuestionBody(q.getQuestionBody());
                questionItemComponent.getQuestionItem().setQuestionMark(q.getQuestionMark());
                questionItemComponent.getQuestionItem().setQuestionDate(q.getQuestionDate());
                questionItemComponent.getQuestionItem().setQuestionLastUsed(q.getQuestionLastUsed());
                questionItemComponent.getQuestionItem().setQuestionDifficulty(q.getQuestionDifficulty());

                // set up question item component
                questionItemComponent.setUpQuestionItemComponent();
                questionItemComponents.add(questionItemComponent);

                // add to vertical layout root
                verticalLayoutRoot.addComponent(questionItemComponent.getThisQuestionItemComponent());
            }
        }

        // add root question layout
        questionArea.addStyleName(ValoTheme.PANEL_BORDERLESS);
        questionArea.setContent(verticalLayoutRoot);
    }

    private void setAsSelectedPaginationButton(Button button) {

        for (Button p : buttons) {
            if (p.getStyleName().equals(MyTheme.MAIN_FLAT_ROUND_BUTTON_PAGINATION_SELECTED))
                p.removeStyleName(MyTheme.MAIN_FLAT_ROUND_BUTTON_PAGINATION_SELECTED);
            p.addStyleName(MyTheme.MAIN_FLAT_ROUND_BUTTON_PAGINATION);
        }

        // set current as selected
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

        // update global
        VaadinService.getCurrentRequest().setAttribute(
                "currentSelectedPaginationPage", currentSelectedPaginationPage);
    }

    private void reIncrementAllPaginationItems(int removeInt) {
        // re-increment pagination questions
        int length = questionPaginationComponents.size();
        for (int i = 0; i < length; i++) {
            // get component
            questionPaginationComponents.get(i).updatePaginationComponent(i + 1);
        }

        // re-increment pagination buttons and rerender
        length = buttons.size();
        for (int i = 1; i <= length; i++) {
            // get button
            buttons.get(i - 1).setCaption(Integer.toString(i));
        }

        currentSelectedPaginationPage = removeInt;
        changeQuestionPaginationComponent();
    }

    private QuestionItemComponent findQuestionItemComponent(int questionId) {

        // index variable
        QuestionItemComponent index = null;

        for (QuestionItemComponent i : questionItemComponents) {
            if (i.getQuestionItem().getQuestionId() == questionId) index = i;
        }

        return index;
    }

    // reusable components
    public class CreateDraftComponent extends VerticalLayout {

        CreateDraftComponent() {

            // new draft form
            addStyleName(MyTheme.MAIN_FLAT_FLOATING_BOX);
            setWidth(60.0f, Unit.PERCENTAGE);

            // create draft image
            FileResource emptyResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/empty-papers.svg"));
            Image empty = new Image(null, emptyResource);
            empty.setWidth(64.0f, Unit.PIXELS);
            empty.setHeight(64.0f, Unit.PIXELS);
            empty.addStyleName(MyTheme.MAIN_OPACITY_60);

            // create draft label
            Label label = new Label("New draft");
            label.addStyleName(MyTheme.MAIN_TEXT_WARNING);
            label.addStyleName(MyTheme.MAIN_TEXT_SIZE_LARGE);

            // create draft input field
            TextField textField = new TextField();
            textField.setWidth(100.0f, Unit.PERCENTAGE);
            textField.setPlaceholder("Enter draft name");

            // course subjects
            CourseComboBox comboBox = new CourseComboBox(courseServer.getCourses());
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

                    // take to database
                    testId = questionServer.postToTestTable(true, draftName, comboBox.getCourseId());
                    if (!(testId > 0))
                        Notification.show("ERROR", "Could not create draft", Notification.Type.WARNING_MESSAGE);

                    // populate question pagination array list
                    questionPaginationComponents = new ArrayList<>();
                    questionPaginationComponents.add(new QuestionPaginationComponent(1));

                    // set current pagination page
                    currentSelectedPaginationPage = 1;

                    // set up paper
                    setUpPapers();

                    // remove box
                    paper.removeComponent(this);
                }
            });

            // add components
            addComponents(empty, label, textField, comboBox, submit);

            // align in middle
            setComponentAlignment(empty, Alignment.MIDDLE_CENTER);
            setComponentAlignment(submit, Alignment.MIDDLE_CENTER);
        }
    }

    private class QuestionPaginationComponent extends VerticalLayout {

        // attributes
        private int questionMarks;
        private int questionNumber;
        private boolean useNumeric;
        private boolean hasQuestions;

        // components
        private Label number;
        private VerticalLayout dropArea = new VerticalLayout();

        // array list of question paper item components
        ArrayList<QuestionPaperItemComponent> paperItemComponentArrayList;

        private QuestionPaginationComponent get() {
            return this;
        }

        private QuestionPaginationComponent(int questionNumber) {

            // set attributes
            this.questionMarks = 0;
            this.useNumeric = false;
            this.hasQuestions = false;
            this.questionNumber = questionNumber;

            // declare
            paperItemComponentArrayList = new ArrayList<>();

            // set up component
            setMargin(false);

            // used for the header, i.e Question 1 - (20 marks)
            HorizontalLayout header = new HorizontalLayout();
            Label marks = new Label("(" + questionMarks + " marks)");
            marks.addStyleNames(MyTheme.MAIN_TEXT_SIZE_MEDIUM, MyTheme.MAIN_TEXT_WEIGHT_900);
            this.number = new Label("Question " + this.questionNumber + " - ");
            this.number.addStyleNames(MyTheme.MAIN_TEXT_SIZE_MEDIUM, MyTheme.MAIN_TEXT_WEIGHT_900);

            // add check box and checkbox listener
            CheckBox numeric = new CheckBox("use numbers");
            numeric.addValueChangeListener((HasValue.ValueChangeListener<Boolean>) event -> {

                // set use numeric
                useNumeric = event.getValue();

                // change all question items
                for (QuestionPaperItemComponent p : paperItemComponentArrayList) {
                    p.setBullet(setQuestionPaperItemComponentValue(paperItemComponentArrayList.indexOf(p)));
                    p.updateQuestionPaperItemComponentValue();
                }
            });

            FileResource removeResource = new FileResource(
                    new File(basePath + "/WEB-INF/img/icons/close.svg"));

            // add header items to horizontal layout
            header.addComponents(number, marks);

            // close
            if (questionNumber > 1) {
                // remove question button
                Image remove = new Image(null, removeResource);
                remove.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);
                remove.setWidth(28.0f, Unit.PIXELS);
                remove.setHeight(28.0f, Unit.PIXELS);
                header.addComponent(remove);

                remove.addClickListener((MouseEvents.ClickListener) event -> {

                    // int used to remove component
                    int removeInt = questionPaginationComponents.indexOf(get());

                    // remove this component from array
                    questionPaginationComponents.remove(removeInt);

                    // remove button also
                    buttons.get(removeInt - 1).removeStyleName(MyTheme.MAIN_FLAT_ROUND_BUTTON_PAGINATION);
                    buttons.get(removeInt - 1).addStyleName(MyTheme.MAIN_FLAT_ROUND_BUTTON_PAGINATION_SELECTED);
                    paginationCenter.removeComponent(buttons.get(removeInt));
                    buttons.remove(removeInt);

                    reIncrementAllPaginationItems(removeInt);
                });
            }

            // add header and use numeric numbering
            addComponent(header);
            addComponent(numeric);

            // question items
            Panel panel = new Panel();
            panel.setSizeFull();
            dropArea.setMargin(false);
            panel.addStyleName(MyTheme.MAIN_FLAT_BORDER);
            panel.setContent(dropArea);
            addComponentsAndExpand(panel);
            dropArea.setSizeFull();

            // if panel is empty add label to show drop
            if (dropArea.getComponentCount() == 0) {
                Label dropQuestionItemHere = new Label("Drop question items here");
                dropQuestionItemHere.addStyleName(MyTheme.MAIN_TEXT_SIZE_MEDIUM);
                dropArea.addComponent(dropQuestionItemHere);
                dropArea.setComponentAlignment(dropQuestionItemHere, Alignment.MIDDLE_CENTER);
            }

            // set drop area
            DropTargetExtension<Panel> dropTargetExtension = new DropTargetExtension<>(panel);

            // the drop effect must match the allowed effect in drag source for a successful drop
            dropTargetExtension.setDropEffect(DropEffect.MOVE);

            // catch drops
            dropTargetExtension.addDropListener((DropListener<Panel>) event -> {

                // if drag source is in the same ui as target
                Optional<AbstractComponent> dragSource = event.getDragSourceComponent();

                if (dragSource.isPresent()) {

                    // remove dropQuestionItemHere label and set has questions to true
                    if (!hasQuestions) dropArea.removeAllComponents();
                    hasQuestions = true;

                    // unset height
                    dropArea.setHeightUndefined();

                    // character incrementation
                    String bulletIncrement = setQuestionPaperItemComponentValue(paperItemComponentArrayList.size());

                    // add question item
                    int qId = 1;
                    if (event.getDataTransferData("id").isPresent()) {
                        qId = Integer.parseInt(event.getDataTransferData("id").get());
                        System.out.println(qId);
                    }

                    // remove component from vertical layout root
                    if (verticalLayoutRoot.getComponentCount() == 1) {
                        emptyQuestionsComponent.setQuestionType(EmptyQuestionsComponent.NO_MORE_QUESTIONS);
                        verticalLayoutRoot.addComponent(emptyQuestionsComponent);
                        verticalLayoutRoot.setComponentAlignment(emptyQuestionsComponent, Alignment.TOP_CENTER);
                    }
                    else verticalLayoutRoot.removeComponent(findQuestionItemComponent(qId));

                    // question paper item component
                    QuestionPaperItemComponent itemComponent =
                            new QuestionPaperItemComponent(bulletIncrement, qId, questionServer, basePath,
                                    paperItemComponentArrayList, dropArea, questionItemComponents, verticalLayoutRoot,
                                    emptyQuestionsComponent);
                    paperItemComponentArrayList.add(itemComponent);
                    dropArea.addComponent(itemComponent);

                    // add marks
                    questionMarks += Integer.parseInt(event.getDataTransferText());
                    if (questionMarks == 0) marks.setValue("(no marks)");
                    else if (questionMarks == 1) marks.setValue("(" + questionMarks + " mark)");
                    else marks.setValue("(" + questionMarks + " marks)");

                    // send to track table
                    int bulletIncrementValue = getQuestionPaperItemComponentValue(findIndexOfQuestionPaperItemComponent(qId));
                    if (!questionServer.postToTrackTable(testId, qId, questionNumber, bulletIncrementValue))
                        Notification.show("ERROR", "Could not add question", Notification.Type.ERROR_MESSAGE);
                }
            });
        }

        private void updatePaginationComponent(int questionNumber) {

            // question number
            this.questionNumber = questionNumber;

            // set value
            number.setValue("Question " + this.questionNumber + " - ");
        }

        private String setQuestionPaperItemComponentValue(int index) {
            // character incrementation
            String bulletIncrement;

            if (useNumeric) bulletIncrement = questionNumber + "." + (index + 1);
            else {
                // it's alphabetic
                char alpha = 'a';
                alpha += index;
                bulletIncrement = Character.toString(alpha);
            }
            return bulletIncrement.trim();
        }

        private int getQuestionPaperItemComponentValue(int index) {

            // return variable
            int bulletIncrementValue;

            if (useNumeric) bulletIncrementValue = (index +  1);
            else {
                // it's alphabetic
                char alpha = 'a';
                alpha += index;
                bulletIncrementValue = Character.getNumericValue(alpha);
                bulletIncrementValue -= (bulletIncrementValue - index - 1);
            }

            return bulletIncrementValue;
        }

        private int findIndexOfQuestionPaperItemComponent(int qId) {

            int index = 0;

            for (QuestionPaperItemComponent i : paperItemComponentArrayList) {
                if (i.getQuestionId() == qId) index = paperItemComponentArrayList.indexOf(i);
            }
            return index;
        }
    }
}
