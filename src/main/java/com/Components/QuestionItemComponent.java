package com.Components;

import com.DateConvert;
import com.MyTheme;
import com.Objects.QuestionItem;
import com.Server.QuestionViewServer;
import com.vaadin.data.HasValue;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.ui.*;
import com.vaadin.ui.dnd.DragSourceExtension;
import com.vaadin.ui.dnd.event.DragEndListener;
import com.vaadin.ui.dnd.event.DragStartListener;

import java.io.File;
import java.util.Date;

public class QuestionItemComponent extends VerticalLayout {

    // attributes
    private int id;
    private int courseId;
    private int questionMark;
    private Date questionDate;
    private String courseCode;
    private String courseName;
    private String questionAns;
    private String questionType;
    private String questionBody;
    private Date questionLastUsed;
    private String questionBodyFull;
    private int questionDifficulty;

    // components
    private Label difficultyLabel;
    private Label questionBodyLabel;
    private Slider difficultySlider = new Slider();
    private VerticalLayout dateAndEdit = new VerticalLayout();
    private HorizontalLayout firstRow = new HorizontalLayout();
    private HorizontalLayout thirdRow = new HorizontalLayout();
    private TextArea editQuestionAnsTextField = new TextArea();
    private TextArea editQuestionBodyTextField = new TextArea();
    private Button seeMoreSeeLess = new Button("see more");
    private VerticalLayout seeMoreComponent = new VerticalLayout();
    private HorizontalLayout updateQuestionControls = new HorizontalLayout();

    // base path
    private String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    // file resource for images
    private FileResource saveResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/save.svg"));
    private FileResource editResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/edit.svg"));
    private FileResource trashResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/trash.svg"));

    // edit trash
    private Image save = new Image(null, saveResource);
    private Image edit = new Image(null, editResource);
    private Image trash = new Image(null, trashResource);

    // question view server
    private QuestionViewServer questionViewServer;

    public QuestionItemComponent(QuestionViewServer questionViewServer) {

        // initialise question view server
        this.questionViewServer = questionViewServer;
    }

    private int getQuestionId() {
        return id;
    }

    public void setQuestionId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    private int getQuestionMark() {
        return questionMark;
    }

    public void setQuestionMark(int questionMark) {
        this.questionMark = questionMark;
    }

    private String getCourseCode() {
        return this.courseCode;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = questionViewServer.getCourseItemById(courseCode).getCourseCode();
    }

    private String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(int courseCode) {
        this.courseName = questionViewServer.getCourseItemById(courseCode).getCourseName();
    }

    private Date getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(Date questionDate) {
        this.questionDate = questionDate;
    }

    private String getQuestionAns() {
        return questionAns;
    }

    public void setQuestionAns(String questionAns) {
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

    public void setQuestionBody(String questionBody) {

        if (questionBody.length() > 170) this.questionBody = questionBody.substring(0, 170) + " ...";
        else this.questionBody = questionBody;

        setQuestionBodyFull(questionBody);
    }

    private Date getQuestionLastUsed() {
        return questionLastUsed;
    }

    public void setQuestionLastUsed(Date questionLastUsed) {
        this.questionLastUsed = questionLastUsed;
    }

    private int getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(int questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    private String getQuestionBodyFull() {
        return questionBodyFull;
    }

    private void setQuestionBodyFull(String questionBodyFull) {
        this.questionBodyFull = questionBodyFull;
    }

    public QuestionItemComponent getThisQuestionItemComponent() {
        return this;
    }

    public void setUpQuestionItemComponent() {

        // set margin to false
        setMargin(false);

        // first row ------------------------------------------------------
        // set up and add horizontal layout for difficulty badge, question, date
        firstRow.setWidth(100.0f, Unit.PERCENTAGE);

        // image buttons for question updating and copying
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
        Label courseCode = new Label(getCourseCode());
        courseCode.addStyleName(MyTheme.MAIN_GREY_LABEL);
        Label subject = new Label(getCourseName());
        thirdRow.addComponents(courseCode, subject);

        // add third row to ui
        addComponent(thirdRow);

        // see more see less on click listener
        setUpSeeMoreSeeLessClickListener();

        // set as draggable
        DragSourceExtension<AbstractComponent> dragSourceExtension = new DragSourceExtension<>(this);
        dragSourceExtension.setEffectAllowed(EffectAllowed.MOVE);
        dragSourceExtension.setDataTransferText(Integer.toString(questionMark));
        dragSourceExtension.setDataTransferData("id", Integer.toString(id));

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
                firstRow.removeAllComponents();
                thirdRow.removeAllComponents();
                dateAndEdit.removeAllComponents();
                updateQuestionControls.removeAllComponents();
                setUpQuestionItemComponent();
                setUpSeeMoreSeeLessClickListener();
                setUpUpdateQuestionClickListeners();
            }
            else {
                // remove listener
                dragSourceExtension.remove();
            }
        });
    }

    private void setUpSeeMoreSeeLessClickListener() {
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
            if (s.length() > 170) this.questionBody = s.substring(0, 170) + " ...";
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
            QuestionItem item = new QuestionItem();
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
