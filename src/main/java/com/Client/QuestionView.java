package com.Client;

import com.Dashboard;
import com.MyTheme;
import com.Server.QuestionServer;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.HasValue;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.event.ContextClickEvent;
import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.ui.*;
import com.vaadin.ui.dnd.DragSourceExtension;
import com.vaadin.ui.dnd.DropTargetExtension;
import com.vaadin.ui.dnd.event.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
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
    private HorizontalLayout paginationCenter = new HorizontalLayout();

    // add button absolute
    private final Button add = new Button("+");
    private final Button addPage = new Button("+");
    private final ArrayList<Button> buttons = new ArrayList<>();

    // question server
    private QuestionServer questionServer;
    private ArrayList<QuestionServer.Question> questionArrayList;

    // base path
    private String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    // paper area variables
    private String draftName;

    // boolean to check if user has been on this page before
    private boolean hasEnteredPageBefore = false;

    // question item variables
    private ArrayList<QuestionItemComponent> questionItemComponents;

    // pagination variables
    private int currentSelectedPaginationPage;
    private ArrayList<QuestionPaginationComponent> questionPaginationComponents;

    public QuestionView(Navigator navigator, Connection connection) {

        // we get the Apps Navigator object
        this.navigator = navigator;

        // set connection variable
        this.connection = connection;

        // set up question server
        questionServer = new QuestionServer(this.connection);

        // set to fill browser screen
        setSizeFull();

        // set up dashboard
        Dashboard dashboard = new Dashboard(navigator);
        addComponent(dashboard);

        // set content area
        // navigation and content area]
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

        // set up questions
        setUpQuestions();

        // set page as entered
        hasEnteredPageBefore = true;
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
            label.addStyleNames(MyTheme.MAIN_TEXT_SIZE_LARGE, MyTheme.MAIN_OPACITY_60);
        }

    }

    @SuppressWarnings("Duplicates")
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
        questionArrayList = questionServer.get();

        // set up root question layout
        VerticalLayout verticalLayoutRoot = new VerticalLayout();
        verticalLayoutRoot.setMargin(false);

        // loop through questions and add to view
        for (QuestionServer.Question q : questionArrayList) {

            // declare new question item component
            QuestionItemComponent questionItemComponent = new QuestionItemComponent();
            // set variables
            questionItemComponent.setQuestionDifficulty(q.getQuestionDifficulty());
            questionItemComponent.setQuestionBody(q.getQuestionBody());
            questionItemComponent.setQuestionDate(q.getQuestionDate());

            // set up question item component
            questionItemComponent.setUpQuestionItemComponent();

            // add to vertical layout root
            verticalLayoutRoot.addComponent(questionItemComponent.getThisQuestionItemComponent());
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

    // reusable components
    private class CreateDraftComponent extends VerticalLayout {

        CreateDraftComponent() {

            // new draft form
            addStyleName(MyTheme.MAIN_FLAT_FLOATING_BOX);
            setWidth(60.0f, Unit.PERCENTAGE);

            // create draft image
            FileResource emptyResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/empty.svg"));
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

            // create sections
            CheckBox checkBox = new CheckBox("Use sections");

            // submit button
            Button submit = new Button("Create");
            submit.addClickListener((Button.ClickListener) event -> {

                // check if text field is empty
                if (textField.isEmpty()) {
                    textField.setCaption("*required field");
                }
                else {

                    // set title of heading
                    draftName = textField.getValue().trim();

                    // TODO take these values to database test table

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
            addComponents(empty, label, textField, checkBox, submit);

            // align in middle
            setComponentAlignment(empty, Alignment.MIDDLE_CENTER);
            setComponentAlignment(submit, Alignment.MIDDLE_CENTER);
        }
    }

    private class QuestionItemComponent extends VerticalLayout {

        // attributes
        private int questionMark;
        private Date questionDate;
        private String questionAns;
        private String questionType;
        private String questionBody;
        private Date questionLastUsed;
        private String questionBodyFull;
        private String questionDifficulty;

        // component
        Component fullComponent;

        private int getQuestionMark() {
            return questionMark;
        }

        private void setQuestionMark(int questionMark) {
            this.questionMark = questionMark;
        }

        private Date getQuestionDate() {
            return questionDate;
        }

        private void setQuestionDate(Date questionDate) {
            this.questionDate = questionDate;
        }

        private String getQuestionAns() {
            return questionAns;
        }

        private void setQuestionAns(String questionAns) {
            this.questionAns = questionAns;
        }

        private String getQuestionType() {
            return questionType;
        }

        private void setQuestionType(String questionType) {
            this.questionType = questionType;
        }

        private String getQuestionBody() {
            return questionBody;
        }

        private void setQuestionBody(String questionBody) {

            if (questionBody.length() > 120) this.questionBody = questionBody.substring(0, 120) + " ...";
            else this.questionBody = questionBody;

            setQuestionBodyFull(questionBody);
        }

        private Date getQuestionLastUsed() {
            return questionLastUsed;
        }

        private void setQuestionLastUsed(Date questionLastUsed) {
            this.questionLastUsed = questionLastUsed;
        }

        private String getQuestionDifficulty() {
            return questionDifficulty;
        }

        private void setQuestionDifficulty(String questionDifficulty) {

            if (questionDifficulty.matches("easy")) this.questionDifficulty = "1";
            else if (questionDifficulty.matches("medium")) this.questionDifficulty = "3";
            else this.questionDifficulty = "5";
        }

        private String getQuestionBodyFull() {
            return questionBodyFull;
        }

        private void setQuestionBodyFull(String questionBodyFull) {
            this.questionBodyFull = questionBodyFull;
        }

        private void setUpQuestionItemComponent() {

            // set margin to false
            setMargin(false);

            // first row ------------------------------------------------------
            // set up and add horizontal layout for difficulty badge, question, date
            HorizontalLayout firstRow = new HorizontalLayout();
            firstRow.setWidth(100.0f, Unit.PERCENTAGE);

            Label difficultyLabel = new Label(this.questionDifficulty);
            difficultyLabel.addStyleNames("main-flat-badge-icon", "main-blue");
            Label questionBodyLabel = new Label(this.getQuestionBody());
            Label dateLabel = new Label(this.questionDate + "");

            // add difficulty and question body and date
            firstRow.addComponent(difficultyLabel);
            firstRow.addComponentsAndExpand(questionBodyLabel);
            firstRow.addComponent(dateLabel);

            // add first row to ui
            addComponent(firstRow);

            // second row -----------------------------------------------------
            // set up see more, question statistics and answer
            Button seeMoreSeeLess = new Button("see more");
            seeMoreSeeLess.addStyleNames(MyTheme.MAIN_TEXT_GREEN, MyTheme.MAIN_TEXT_WEIGHT_900, "main-flat-button");
            addComponent(seeMoreSeeLess);

            // see more see less on click listener
            seeMoreSeeLess.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    if (seeMoreSeeLess.getCaption().equals("see more")) {
                        seeMoreSeeLess.setCaption("see less");
                        questionBodyLabel.setValue(getQuestionBodyFull());
                    }
                    else {
                        seeMoreSeeLess.setCaption("see more");
                        questionBodyLabel.setValue(getQuestionBody());
                    }
                }
            });

            // third row ------------------------------------------------------
            // set up subject label, tags and question marks
            HorizontalLayout thirdRow = new HorizontalLayout();

            Label courseCode = new Label("COMS3004");
            courseCode.addStyleName(MyTheme.MAIN_GREY_LABEL);
            Label subject = new Label("Computer Networks");
            thirdRow.addComponents(courseCode, subject);

            // add third row to ui
            addComponent(thirdRow);

            // set as draggable
            DragSourceExtension<AbstractComponent> dragSourceExtension = new DragSourceExtension<>(this);
            dragSourceExtension.setEffectAllowed(EffectAllowed.MOVE);
            dragSourceExtension.setDataTransferText("hello receiver");
            dragSourceExtension.setDataTransferData("text/html", "<label>hello receiver</label>");

            // drag start listener
            dragSourceExtension.addDragStartListener(new DragStartListener<AbstractComponent>() {

                @Override
                public void dragStart(DragStartEvent<AbstractComponent> event) {
                    removeAllComponents();
                    VerticalLayout verticalLayout = new VerticalLayout();
                    verticalLayout.setWidth("300px");
                    verticalLayout.setMargin(false);
                    verticalLayout.addComponent(new Label(questionBodyFull));
                    addComponent(verticalLayout);
                }
            });

            // drag end listener
            dragSourceExtension.addDragEndListener(new DragEndListener<AbstractComponent>() {
                @Override
                public void dragEnd(DragEndEvent<AbstractComponent> event) {
                    if (event.isCanceled()) {
                        removeAllComponents();
                        setUpQuestionItemComponent();
                    }
                }
            });

            //
        }

        private QuestionItemComponent getThisQuestionItemComponent() {
            return this;
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

        private QuestionPaginationComponent(int questionNumber) {

            // set attributes
            this.questionMarks = 0;
            this.useNumeric = false;
            this.hasQuestions = false;
            this.questionNumber = questionNumber;

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
                useNumeric = event.getValue();
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

                remove.addClickListener(new MouseEvents.ClickListener() {
                    @Override
                    public void click(MouseEvents.ClickEvent event) {

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
                    }
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

                    // add question item
                    HorizontalLayout horizontalLayout = new HorizontalLayout();
                    horizontalLayout.addComponent(new Label("(a)"));
                    horizontalLayout.addComponent(dragSource.get());
                    dropArea.addComponent(horizontalLayout);

                    // add marks
                    questionMarks = 2;
                    marks.setValue("(" + questionMarks + " marks)");
                }
            });
        }

        private void updatePaginationComponent(int questionNumber) {

            // question number
            this.questionNumber = questionNumber;

            // set value
            number.setValue("Question " + this.questionNumber + " - ");
        }

        private QuestionPaginationComponent get() {
            return this;
        }
    }
}
