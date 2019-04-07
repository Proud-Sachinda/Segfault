package com.Client;

import com.Components.CourseComboBox;
import com.Dashboard;
import com.DateConvert;
import com.MyTheme;
import com.Server.QuestionViewServer;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.data.HasValue;
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
    private QuestionViewServer questionViewServer;

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

    QuestionView() {
        // for unit tests
    }

    public QuestionView(Navigator navigator, Connection connection) {

        // we get the Apps Navigator object
        this.navigator = navigator;

        // set connection variable
        this.connection = connection;

        // set up question server
        questionViewServer = new QuestionViewServer(this.connection);

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
        ArrayList<QuestionViewServer.QuestionItem> questionArrayList = questionViewServer.getQuestionItems();

        // set up root question layout
        VerticalLayout verticalLayoutRoot = new VerticalLayout();
        verticalLayoutRoot.setMargin(false);

        // loop through questions and add to view
        for (QuestionViewServer.QuestionItem q : questionArrayList) {

            // declare new question item component
            QuestionItemComponent questionItemComponent = new QuestionItemComponent();
            // set variables
            questionItemComponent.setQuestionId(q.getQuestionId());
            questionItemComponent.setQuestionAns(q.getQuestionAns());
            questionItemComponent.setQuestionBody(q.getQuestionBody());
            questionItemComponent.setQuestionMark(q.getQuestionMark());
            questionItemComponent.setQuestionDate(q.getQuestionDate());
            questionItemComponent.setQuestionLastUsed(q.getQuestionLastUsed());
            questionItemComponent.setQuestionDifficulty(q.getQuestionDifficulty());

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

            // course subjects
            CourseComboBox comboBox = new CourseComboBox(questionViewServer.getCourses());
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
                    if (!questionViewServer.postToTestTable(true, draftName, comboBox.getCourseId()))
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

    private class QuestionItemComponent extends VerticalLayout {

        // attributes
        private int id;
        private int questionMark;
        private Date questionDate;
        private String questionAns;
        private String questionType;
        private String questionBody;
        private Date questionLastUsed;
        private String questionBodyFull;
        private int questionDifficulty;

        // components
        Label difficultyLabel;
        Label questionBodyLabel;
        Slider difficultySlider = new Slider();
        VerticalLayout dateAndEdit = new VerticalLayout();
        HorizontalLayout firstRow = new HorizontalLayout();
        HorizontalLayout thirdRow = new HorizontalLayout();
        TextArea editQuestionAnsTextField = new TextArea();
        TextArea editQuestionBodyTextField = new TextArea();
        Button seeMoreSeeLess = new Button("see more");
        VerticalLayout seeMoreComponent = new VerticalLayout();
        HorizontalLayout updateQuestionControls = new HorizontalLayout();

        // file resource for images
        FileResource saveResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/save.svg"));
        FileResource editResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/edit.svg"));
        FileResource trashResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/trash.svg"));

        // edit trash
        Image save = new Image(null, saveResource);
        Image edit = new Image(null, editResource);
        Image trash = new Image(null, trashResource);

        private int getQuestionId() {
            return id;
        }

        private void setQuestionId(int id) {
            this.id = id;
        }

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

            if (questionBody.length() > 140) this.questionBody = questionBody.substring(0, 140) + " ...";
            else this.questionBody = questionBody;

            setQuestionBodyFull(questionBody);
        }

        private Date getQuestionLastUsed() {
            return questionLastUsed;
        }

        private void setQuestionLastUsed(Date questionLastUsed) {
            this.questionLastUsed = questionLastUsed;
        }

        private int getQuestionDifficulty() {
            return questionDifficulty;
        }

        private void setQuestionDifficulty(int questionDifficulty) {
            this.questionDifficulty = questionDifficulty;
        }

        private String getQuestionBodyFull() {
            return questionBodyFull;
        }

        private void setQuestionBodyFull(String questionBodyFull) {
            this.questionBodyFull = questionBodyFull;
        }

        private QuestionItemComponent getThisQuestionItemComponent() {
            return this;
        }

        private void setUpQuestionItemComponent() {

            // set margin to false
            setMargin(false);

            // first row ------------------------------------------------------
            // set up and add horizontal layout for difficulty badge, question, date
            firstRow.setWidth(100.0f, Unit.PERCENTAGE);

            // image buttons for question body
            edit.setWidth(22.0f, Unit.PIXELS);
            edit.setHeight(22.0f, Unit.PIXELS);
            save.setWidth(22.0f, Unit.PIXELS);
            save.setHeight(22.0f, Unit.PIXELS);
            trash.setWidth(22.0f, Unit.PIXELS);
            trash.setHeight(22.0f, Unit.PIXELS);
            edit.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);
            save.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);
            trash.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);

            difficultyLabel = new Label(Integer.toString(this.questionDifficulty));
            difficultyLabel.addStyleNames("main-flat-badge-icon", MyTheme.MAIN_BLUE);
            questionBodyLabel = new Label(this.getQuestionBody());
            questionBodyLabel.addStyleName(MyTheme.MAIN_TEXT_WEIGHT_500);
            Label dateLabel = new Label(DateConvert.convertNumericDateToMinimumAlphabeticDate(this.questionLastUsed));
            dateAndEdit.addComponent(dateLabel);
            dateAndEdit.setWidthUndefined();
            dateAndEdit.setMargin(false);
            dateLabel.addStyleName(MyTheme.MAIN_TEXT_WEIGHT_700);
            updateQuestionControls.addComponents(edit, trash);
            dateAndEdit.addComponent(updateQuestionControls);
            dateAndEdit.setComponentAlignment(updateQuestionControls, Alignment.MIDDLE_RIGHT);

            // add difficulty and question body and date
            firstRow.addComponent(difficultyLabel);
            firstRow.addComponentsAndExpand(questionBodyLabel);
            firstRow.addComponent(dateAndEdit);

            // set up listeners
            setUpUpdateQuestionClickListeners();

            // add first row to ui
            addComponent(firstRow);

            // second row -----------------------------------------------------
            // set up see more, question statistics and answer
            seeMoreSeeLess.addStyleNames(MyTheme.MAIN_TEXT_GREEN, MyTheme.MAIN_TEXT_WEIGHT_900, "main-flat-button");
            addComponent(seeMoreSeeLess);

            // third row ------------------------------------------------------
            // set up subject label, tags and question marks
            Label courseCode = new Label("COMS3004");
            courseCode.addStyleName(MyTheme.MAIN_GREY_LABEL);
            Label subject = new Label("Computer Networks");
            thirdRow.addComponents(courseCode, subject);

            // add third row to ui
            addComponent(thirdRow);

            // see more see less on click listener
            seeMoreSeeLess.addClickListener((Button.ClickListener) event -> {
                if (seeMoreSeeLess.getCaption().equals("see more")) {

                    // remove any instance of it if present
                    removeComponent(seeMoreComponent);

                    // set caption
                    seeMoreSeeLess.setCaption("see less");

                    // question body to full
                    questionBodyLabel.setValue(getQuestionBodyFull());

                    // add see more component
                    setUpSeeMoreComponent();
                    removeComponent(thirdRow);
                    addComponents(seeMoreComponent, thirdRow);
                }
                else {
                    // remove any instance of it if present
                    removeComponent(seeMoreComponent);
                    seeMoreSeeLess.setCaption("see more");
                    questionBodyLabel.setValue(getQuestionBody());
                    seeMoreComponent.removeAllComponents();
                    removeComponent(seeMoreComponent);
                }
            });

            // set as draggable
            DragSourceExtension<AbstractComponent> dragSourceExtension = new DragSourceExtension<>(this);
            dragSourceExtension.setEffectAllowed(EffectAllowed.MOVE);
            dragSourceExtension.setDataTransferText(Integer.toString(questionMark));

            // drag start listener
            dragSourceExtension.addDragStartListener((DragStartListener<AbstractComponent>) event -> {
                removeAllComponents();
                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.setWidth(100.0f, Unit.PERCENTAGE);
                Label label = new Label(questionBodyFull);
                horizontalLayout.addComponentsAndExpand(label);
                addComponent(horizontalLayout);
            });
            // drag end listener
            dragSourceExtension.addDragEndListener((DragEndListener<AbstractComponent>) event -> {
                if (event.isCanceled()) {
                    removeAllComponents();
                    setUpQuestionItemComponent();
                }
                else {
                    dragSourceExtension.remove();
                }
            });
        }

        private void setUpUpdateQuestionClickListeners() {

            // add click listeners
            edit.addClickListener((MouseEvents.ClickListener) event -> {
                // remove question body and label
                firstRow.removeComponent(dateAndEdit);
                firstRow.removeComponent(questionBodyLabel);

                // add save button
                updateQuestionControls.removeComponent(edit);
                updateQuestionControls.addComponentAsFirst(save);

                // replace first row
                editQuestionBodyTextField.setValue(questionBodyFull);
                firstRow.addComponentsAndExpand(editQuestionBodyTextField);
                editQuestionBodyTextField.focus();
                firstRow.addComponent(dateAndEdit);

                // remove third row and see more
                removeComponent(thirdRow);
                removeComponent(seeMoreSeeLess);
                seeMoreSeeLess.setCaption("see more");
                removeComponent(seeMoreComponent);


                // add difficulty and answer
                difficultySlider.setMin(1);
                difficultySlider.setMax(5);
                setUpQuestionDifficultySlider(questionDifficulty);
                difficultySlider.setValue((double) questionDifficulty);
                difficultySlider.setWidth(100.0f, Unit.PERCENTAGE);
                difficultySlider.addValueChangeListener((HasValue.ValueChangeListener<Double>) event1 -> {
                    int x = event1.getValue().intValue();
                    questionDifficulty = x;
                    setUpQuestionDifficultySlider(x);
                });
                editQuestionAnsTextField.setValue(questionAns);
                editQuestionAnsTextField.setCaption("Answer");
                editQuestionAnsTextField.setWidth(100.0f, Unit.PERCENTAGE);

                // add slider, answer, see more see less button and third row
                addComponents(difficultySlider, editQuestionAnsTextField, seeMoreSeeLess, thirdRow);
            });
            save.addClickListener((MouseEvents.ClickListener) event -> {

                // update variables
                String s = editQuestionBodyTextField.getValue();
                this.questionBodyFull = s;
                this.questionAns = editQuestionAnsTextField.getValue();
                this.questionDifficulty = difficultySlider.getValue().intValue();
                if (s.length() > 140) this.questionBody = s.substring(0, 140) + " ...";
                else this.questionBody = s;

                // remove text areas and slider
                removeComponent(difficultySlider);
                firstRow.removeComponent(dateAndEdit);
                removeComponent(editQuestionAnsTextField);
                updateQuestionControls.removeComponent(save);
                firstRow.removeComponent(editQuestionBodyTextField);

                // update labels
                difficultyLabel.setValue(Integer.toString(this.questionDifficulty));
                questionBodyLabel.setValue(this.questionBody);
                updateQuestionControls.addComponentAsFirst(edit);

                // add label
                firstRow.addComponentsAndExpand(questionBodyLabel);
                firstRow.addComponent(dateAndEdit);

                // take to database
                QuestionViewServer.QuestionItem item = questionViewServer.getQuestionItem();
                item.setQuestionDifficulty(getQuestionDifficulty());
                item.setQuestionBody(getQuestionBodyFull());
                item.setQuestionAns(getQuestionAns());
                item.setQuestionId(getQuestionId());

                if (questionViewServer.updateQuestionItem(item))
                    Notification.show("SUCCESS", "Question updated", Notification.Type.TRAY_NOTIFICATION);
                else Notification.show("ERROR", "Something went wrong", Notification.Type.ERROR_MESSAGE);
            });
            trash.addClickListener((MouseEvents.ClickListener) event -> {
               // delete question
            });
        }

        private void setUpQuestionDifficultySlider(int x) {

            // check
            if (x == 1) difficultySlider.setCaption("Difficulty - Beginner");
            else if (x == 2) difficultySlider.setCaption("Difficulty - Basic");
            else if (x == 3) difficultySlider.setCaption("Difficulty - Intermediate");
            else if (x == 4) difficultySlider.setCaption("Difficulty - Advanced");
            else if (x == 5) difficultySlider.setCaption("Difficulty - Expert");
        }

        private void setUpSeeMoreComponent() {

            // root of see more component
            seeMoreComponent.setMargin(false);

            // add answer row
            HorizontalLayout answerRow = new HorizontalLayout();
            Label ans = new Label("ANSWER: ");
            ans.addStyleNames(MyTheme.MAIN_TEXT_SIZE_SMALL, MyTheme.MAIN_TEXT_WARNING,
                    MyTheme.MAIN_TEXT_WEIGHT_900);
            Label answer = new Label(this.questionAns);
            answer.addStyleNames(MyTheme.MAIN_TEXT_SIZE_SMALL, MyTheme.MAIN_TEXT_CHARCOAL);
            answerRow.addComponents(ans, answer);
            seeMoreComponent.addComponentsAndExpand(answerRow);

            // add variance row
            HorizontalLayout usageStatisticsRow = new HorizontalLayout();
            Label statistics = new Label("USAGE STATISTICS:");
            statistics.addStyleNames(MyTheme.MAIN_OPACITY_60,
                    MyTheme.MAIN_TEXT_SIZE_SMALL, MyTheme.MAIN_TEXT_WEIGHT_900);
            usageStatisticsRow.addComponent(statistics);
            Label variantUsed = new Label("A variant of this was used");
            variantUsed.addStyleName(MyTheme.MAIN_TEXT_SIZE_SMALL);
            usageStatisticsRow.addComponent(variantUsed);
            seeMoreComponent.addComponent(usageStatisticsRow);

            // other details
            HorizontalLayout otherRow = new HorizontalLayout();
            otherRow.setWidth(100.0f, Unit.PERCENTAGE);
            HorizontalLayout usedColumn = new HorizontalLayout();
            VerticalLayout variance = new VerticalLayout();
            VerticalLayout published = new VerticalLayout();

            // add to other row
            otherRow.addComponentsAndExpand(usedColumn, variance, published);

            // add other row elements
            Label usedXLabel = new Label("used");
            Label usedXNumberLabel = new Label("10x");
            usedXLabel.addStyleNames(MyTheme.MAIN_TEXT_WEIGHT_900,
                    MyTheme.MAIN_TEXT_SIZE_LARGE, MyTheme.MAIN_OPACITY_40);
            usedXNumberLabel.addStyleNames(MyTheme.MAIN_TEXT_SIZE_EXTRA_LARGE,
                    MyTheme.MAIN_TEXT_WEIGHT_900, MyTheme.MAIN_TEXT_GREEN);
            usedColumn.addComponents(usedXLabel, usedXNumberLabel);
            Label isVariantLabel = new Label("is variant");
            isVariantLabel.addStyleNames(MyTheme.MAIN_TEXT_WEIGHT_900,
                    MyTheme.MAIN_TEXT_SIZE_MEDIUM, MyTheme.MAIN_OPACITY_40);
            Label isVariantLabelGoto = new Label("GOTO");
            isVariantLabelGoto.addStyleNames(MyTheme.MAIN_TEXT_SIZE_MEDIUM,
                    MyTheme.MAIN_TEXT_WEIGHT_900, MyTheme.MAIN_TEXT_GREEN, MyTheme.MAIN_CONTROL_CLICKABLE);
            variance.addComponents(isVariantLabel, isVariantLabelGoto);
            variance.setMargin(false);
            variance.addStyleName(MyTheme.MAIN_FLAT_DARK_LEFT_RIGHT_BORDER);
            Label datePublishedLabel = new Label("date published");
            datePublishedLabel.addStyleNames(MyTheme.MAIN_TEXT_WEIGHT_900,
                    MyTheme.MAIN_TEXT_SIZE_MEDIUM, MyTheme.MAIN_OPACITY_40);
            Label datePublishedDateLabel = new Label(DateConvert.convertNumericDateToAlphabeticDate(questionDate));
            datePublishedDateLabel.addStyleNames(MyTheme.MAIN_TEXT_SIZE_MEDIUM,
                    MyTheme.MAIN_TEXT_WEIGHT_900, MyTheme.MAIN_TEXT_GREEN);
            published.setMargin(false);
            published.addComponents(datePublishedLabel, datePublishedDateLabel);
            published.setComponentAlignment(datePublishedLabel, Alignment.MIDDLE_CENTER);
            published.setComponentAlignment(datePublishedDateLabel, Alignment.MIDDLE_CENTER);

            // align
            usedColumn.setComponentAlignment(usedXLabel, Alignment.MIDDLE_LEFT);
            variance.setComponentAlignment(isVariantLabel, Alignment.MIDDLE_CENTER);
            variance.setComponentAlignment(isVariantLabelGoto, Alignment.MIDDLE_CENTER);
            otherRow.setComponentAlignment(usedColumn, Alignment.MIDDLE_CENTER);

            // add other row
            seeMoreComponent.addStyleName(MyTheme.MAIN_FLAT_DARK_TOP_BOTTOM_BORDER);
            seeMoreComponent.addComponent(otherRow);
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
                    QuestionPaperItemComponent itemComponent =
                            new QuestionPaperItemComponent(bulletIncrement, dragSource.get());
                    paperItemComponentArrayList.add(itemComponent);
                    dropArea.addComponent(itemComponent);

                    // add marks
                    questionMarks += Integer.parseInt(event.getDataTransferText());
                    if (questionMarks == 0) marks.setValue("(no marks)");
                    else if (questionMarks == 1) marks.setValue("(" + questionMarks + " mark)");
                    else marks.setValue("(" + questionMarks + " marks)");
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
    }

    private class QuestionPaperItemComponent extends VerticalLayout {

        // attributes
        private String bullet;

        // components
        private Label bulletLabel;

        private QuestionPaperItemComponent(String bullet, AbstractComponent source) {

            // set up variables
            this.bullet = bullet;

            // set up root
            setMargin(false);
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.setWidth(100.0f, Unit.PERCENTAGE);

            // set up components
            if (bullet.length() > 1) bulletLabel = new Label(bullet);
            else bulletLabel = new Label("(" + bullet + ")");
            horizontalLayout.addComponent(bulletLabel);
            horizontalLayout.addComponentsAndExpand(source);

            addComponent(horizontalLayout);
        }

        private void setBullet(String bullet) {
            this.bullet = bullet;
        }

        private void updateQuestionPaperItemComponentValue() {
            if (bullet.length() > 1) bulletLabel.setValue(bullet);
            else bulletLabel.setValue("(" + bullet + ")");
        }
    }

    public int myfunction(int num1,int num2){

        return num1+num2;
    }
}
