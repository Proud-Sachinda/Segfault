
package com.Client;

import com.Components.CourseComboBox;
import com.Components.FormItemComponent;
import com.Components.MultipleChoiceItemComponent;
import com.Components.TagItemsComponent;
import com.Dashboard;
import com.MyTheme;
import com.Objects.QuestionItem;
import com.Server.QuestionServer;
import com.vaadin.data.HasValue;
import com.vaadin.event.LayoutEvents;
import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.ui.NumberField;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;


public class CreateQuestionView extends HorizontalLayout implements View {

    // navigator used to redirect to another page
    private Navigator navigator;

    // route strings
    protected final String question = "question";
    protected final String course = "course";
    protected final String export = "export";

    // question server
    private QuestionServer questionServer;

    // layouts for split panel
    private VerticalLayout rootLayout = new VerticalLayout();
    private VerticalLayout mainQuestionFormArea = new VerticalLayout();
    private VerticalLayout otherQuestionFormArea = new VerticalLayout();

    // base path
    private String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    // latex things
    private HorizontalLayout latexRoot = new HorizontalLayout();
    private FileResource sRootResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/latex/root.svg"));
    private Image sRoot = new Image(null, sRootResource);

    // array of form item components
    private ArrayList<FormItemComponent> formItemComponents;

    // form items
    private Image cancel;
    private Label mcqHeader;
    private Label mcqAnswer;
    private Label createQuestionLabel;
    private HorizontalLayout headerLayout;
    private HorizontalLayout footerFinish;
    private FormItemComponent comboBoxItem;
    private FormItemComponent questionBodyItem;
    private FormItemComponent questionMarksItem;
    private FormItemComponent questionAnswerItem;
    private FormItemComponent questionDifficultyItem;
    private FormItemComponent questionSampleInputItem;
    private FormItemComponent questionSampleOutputItem;
    private FormItemComponent questionNumberOfLinesItem;
    private ComboBox<MultipleChoiceItemComponent> choiceItemComponentComboBox;
    private ArrayList<MultipleChoiceItemComponent> multipleChoiceItemComponents;

    // vertical layout to replace question content
    private VerticalLayout replacementLayout;

    // question item for form submission
    private QuestionItem questionItem = new QuestionItem();

    // tags item component
    private TagItemsComponent tags;

    public CreateQuestionView(Navigator navigator, Connection connection) {

        // we get the Apps Navigator object
        this.navigator = navigator;

        // set up question server
        questionServer = new QuestionServer(connection);

        // declare form item components array
        formItemComponents = new ArrayList<>();

        // by default question type is written
        questionItem.setQuestionType("written");

        // set to fill browser screen
        setSizeFull();

        // set up dashboard
        Dashboard dashboard = new Dashboard(navigator);
        addComponent(dashboard);

        // set up root layout
        addComponentsAndExpand(rootLayout);
        rootLayout.setHeightUndefined();

        // set up header
        setUpCreateQuestionHeader();

        // set up split area
        HorizontalLayout splitAreaRoot = new HorizontalLayout();
        HorizontalLayout mainQuestionFormAreaRoot = new HorizontalLayout();
        HorizontalLayout otherQuestionFormAreaRoot = new HorizontalLayout();

        // add split areas to root
        splitAreaRoot.addComponentsAndExpand(mainQuestionFormAreaRoot, otherQuestionFormAreaRoot);
        rootLayout.addComponentsAndExpand(splitAreaRoot);

        // add vertical split layouts to split areas
        mainQuestionFormAreaRoot.addComponent(mainQuestionFormArea);
        otherQuestionFormAreaRoot.addComponent(otherQuestionFormArea);

        // set up main form area
        setUpMainQuestionFormArea();

        // set up other form ara
        setUpOtherQuestionFormArea();

        // set up up footer
        setUpCreateQuestionFooter();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        // set page title
        UI.getCurrent().getPage().setTitle("Dashboard - Create question");
    }

    private void setUpCreateQuestionHeader() {

        // horizontal layout for header
        headerLayout = new HorizontalLayout();

        // set up cancel button
        FileResource cancelResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/cancel.svg"));
        cancel = new Image(null, cancelResource);
        cancel.setWidth(48.0f, Unit.PIXELS);
        cancel.setHeight(48.0f, Unit.PIXELS);
        cancel.addStyleNames(MyTheme.MAIN_CONTROL_CLICKABLE);
        cancel.addClickListener((MouseEvents.ClickListener) event -> navigator.navigateTo(question));

        // set up create question label
        createQuestionLabel = new Label("Create Question");
        createQuestionLabel.addStyleNames(MyTheme.MAIN_TEXT_SIZE_EXTRA_LARGE);

        // add to header and root
        headerLayout.addComponents(cancel, createQuestionLabel);
        headerLayout.setComponentAlignment(cancel, Alignment.MIDDLE_CENTER);
        rootLayout.addComponent(headerLayout);
    }

    private void setUpMainQuestionFormArea() {

        // set border
        mainQuestionFormArea.addStyleName(ValoTheme.LAYOUT_CARD);

        // set up latex items
        sRoot.setWidth(64.0f, Unit.PIXELS);
        sRoot.setHeight(64.0f, Unit.PIXELS);

        // add to latex root
        latexRoot.addComponent(sRoot);

        // horizontal layout for course combo box and number field marks
        HorizontalLayout courseAndMarks = new HorizontalLayout();
        courseAndMarks.setWidth(100.0f, Unit.PERCENTAGE);
        mainQuestionFormArea.addComponent(courseAndMarks);

        // add course combo box drop down
        CourseComboBox comboBox = new CourseComboBox(questionServer.getCourses());
        comboBox.setCaption(null);
        comboBox.addComboBoxValueChangeListener();
        comboBoxItem = new FormItemComponent("Subject", comboBox,
                FormItemComponent.ComponentType.COURSE_COMBO_BOX);
        formItemComponents.add(comboBoxItem);

        // add marks number field
        NumberField questionMarksNumberField = new NumberField();
        questionMarksItem = new FormItemComponent("Marks",
                questionMarksNumberField, FormItemComponent.ComponentType.NUMBER_FIELD);
        questionMarksItem.setComponentWidth(196.0f, Unit.PIXELS);
        formItemComponents.add(questionMarksItem);

        // add to horizontal layout
        courseAndMarks.addComponentsAndExpand(comboBoxItem);
        courseAndMarks.addComponent(questionMarksItem);

        // add question body
        TextArea questionBodyTextArea = new TextArea();
        questionBodyItem = new FormItemComponent("Question",
                questionBodyTextArea, FormItemComponent.ComponentType.TEXT_AREA);
        formItemComponents.add(questionBodyItem);

        // add question difficulty
        Slider questionDifficultySlider = new Slider();
        questionDifficultySlider.setMin(1);
        questionDifficultySlider.setMax(5);
        questionDifficultySlider.setValue((double) 1);
        questionDifficultySlider.setOrientation(SliderOrientation.HORIZONTAL);
        questionDifficultyItem = new FormItemComponent("Difficulty",
                questionDifficultySlider, FormItemComponent.ComponentType.SLIDER);
        formItemComponents.add(questionDifficultyItem);

        // add
        tags = new TagItemsComponent(questionServer, basePath);

        // add everything to form area
        mainQuestionFormArea.addComponents(latexRoot, courseAndMarks,
                questionBodyItem, questionDifficultyItem, tags);
    }

    private void setUpOtherQuestionFormArea() {

        // set border
        otherQuestionFormArea.addStyleName(ValoTheme.LAYOUT_CARD);

        // add menu bar
        setUpQuestionTypeMenuBar();

        // define written question components
        setUpDefaultQuestion();

        // define mcq question components
        multipleChoiceItemComponents = new ArrayList<>();

        // set up practical question
        setUpPracticalQuestion();
    }

    private void setUpDefaultQuestion() {

        // default question
        TextArea questionAnswerTextArea = new TextArea();
        questionAnswerItem = new FormItemComponent("Answer",
                questionAnswerTextArea, FormItemComponent.ComponentType.TEXT_AREA);
        formItemComponents.add(questionAnswerItem);

        // add marks number field
        NumberField questionNumberOfLinesNumberField = new NumberField();
        questionNumberOfLinesItem = new FormItemComponent("Number of lines",
                questionNumberOfLinesNumberField, FormItemComponent.ComponentType.NUMBER_FIELD);
        questionNumberOfLinesItem.setComponentWidth(196.0f, Unit.PIXELS);
        formItemComponents.add(questionNumberOfLinesItem);

        // default question type
        replacementLayout.addComponents(questionAnswerItem, questionNumberOfLinesItem);
    }

    private void setUpPracticalQuestion() {

        // default question
        TextArea questionSampleInputTextArea = new TextArea();
        questionSampleInputTextArea.setHeight(64.0f, Unit.PIXELS);
        questionSampleInputItem = new FormItemComponent("Sample Input",
                questionSampleInputTextArea, FormItemComponent.ComponentType.TEXT_AREA);

        // default question
        TextArea questionSampleOutputTextArea = new TextArea();
        questionSampleOutputTextArea.setHeight(64.0f, Unit.PIXELS);
        questionSampleOutputItem = new FormItemComponent("Sample Output",
                questionSampleOutputTextArea, FormItemComponent.ComponentType.TEXT_AREA);
    }

    private void setUpCreateQuestionFooter() {

        // horizontal layout for header
        HorizontalLayout footerLayout = new HorizontalLayout();
        footerLayout.setWidth(100.0f, Unit.PERCENTAGE);

        // set up finish button
        FileResource finishResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/finish.svg"));
        Image finish = new Image(null, finishResource);
        finish.setWidth(40.0f, Unit.PIXELS);
        finish.setHeight(40.0f, Unit.PIXELS);

        // set up create question label
        Label finishQuestionLabel = new Label("FINISH");
        finishQuestionLabel.addStyleNames(MyTheme.MAIN_TEXT_SIZE_MEDIUM);

        // create a button thing
        footerFinish = new HorizontalLayout();
        footerFinish.addComponents(finishQuestionLabel, finish);
        footerFinish.setComponentAlignment(finish, Alignment.MIDDLE_CENTER);
        footerFinish.setComponentAlignment(finishQuestionLabel, Alignment.MIDDLE_CENTER);
        footerFinish.addStyleNames(MyTheme.MAIN_FLAT_FINISH_BUTTON, MyTheme.MAIN_CONTROL_CLICKABLE);

        // add finish click listener
        submitQuestionForm();

        // add to header and root
        footerLayout.addComponent(footerFinish);
        footerLayout.setComponentAlignment(footerFinish, Alignment.MIDDLE_RIGHT);
        rootLayout.addComponent(footerLayout);
    }

    private void setUpQuestionTypeMenuBar() {

        // menu bar layout replace
        replacementLayout = new VerticalLayout();
        replacementLayout.setMargin(false);

        // written question command
        MenuBar.Command writtenQuestionCommand = (MenuBar.Command) selectedItem -> {

            // if question type is not written replace content
            if (!questionItem.getQuestionType().equals("written")) {

                // remove replacement components
                replacementLayout.removeAllComponents();

                // remove all mcq
                removeAllMultipleChoiceItemsFromArrayList();

                // remove all practical
                formItemComponents.remove(questionSampleInputItem);
                formItemComponents.remove(questionSampleOutputItem);

                // add question answer item
                unsetComponentWarningLabelAndReset(questionAnswerItem, questionNumberOfLinesItem);
                addToFormItemComponentArrayList(questionAnswerItem, questionNumberOfLinesItem);
                replacementLayout.addComponents(questionAnswerItem, questionNumberOfLinesItem);
            }

            // set question type
            questionItem.setQuestionType("written");
        };

        // mcq question command
        MenuBar.Command multipleChoiceQuestionCommand = (MenuBar.Command) selectedItem -> {

            // if question type is not written replace content
            if (!questionItem.getQuestionType().equals("mcq")) {

                // remove replacement components
                replacementLayout.removeAllComponents();

                // remove all mcq items
                removeAllMultipleChoiceItemsFromArrayList();

                // remove answer item
                formItemComponents.remove(questionAnswerItem);
                formItemComponents.remove(questionNumberOfLinesItem);

                // remove all practical
                formItemComponents.remove(questionSampleInputItem);
                formItemComponents.remove(questionSampleOutputItem);

                // add mcq item
                setUpMultipleChoiceToReplacementLayout();
            }
            // set question type
            questionItem.setQuestionType("mcq");
        };

        // mcq question command
        MenuBar.Command practicalQuestionCommand = (MenuBar.Command) selectedItem -> {

            // if question type is not written replace content
            if (!questionItem.getQuestionType().equals("practical")) {

                // remove replacement components
                replacementLayout.removeAllComponents();

                // remove answer item
                formItemComponents.remove(questionAnswerItem);
                formItemComponents.remove(questionNumberOfLinesItem);

                // add practical items
                unsetComponentWarningLabelAndReset(questionAnswerItem,
                        questionSampleInputItem, questionSampleOutputItem);
                addToFormItemComponentArrayList(questionAnswerItem,
                        questionSampleInputItem, questionSampleOutputItem);

                // remove all mcq components
                removeAllMultipleChoiceItemsFromArrayList();

                replacementLayout.addComponents(questionSampleInputItem,
                        questionSampleOutputItem, questionAnswerItem);
            }

            // set question type
            questionItem.setQuestionType("practical");
        };

        // create menu bar and add items
        MenuBar menuBar = new MenuBar();
        menuBar.addItem("Written", writtenQuestionCommand);
        menuBar.addItem("Multiple Choice", multipleChoiceQuestionCommand);
        menuBar.addItem("Practical", practicalQuestionCommand);

        // add to other form
        otherQuestionFormArea.addComponent(menuBar);
        otherQuestionFormArea.addComponent(replacementLayout);
        otherQuestionFormArea.setComponentAlignment(menuBar, Alignment.TOP_CENTER);
    }

    private boolean areAnyFormItemComponentsEmpty() {

        // return variable
        boolean empty = false;

        // check if everything is filled
        for (FormItemComponent i : formItemComponents) {
            if (i.isEmpty()) {
                empty = true;
                break;
            }
        }

        return empty;
    }

    private boolean areAnyMultipleChoiceItemsEmpty() {

        // return variable
        boolean empty = false;

        // if any of the mcq stuff is empty
        for (MultipleChoiceItemComponent i : multipleChoiceItemComponents) {
            if (i.isEmpty()) {
                empty = true;
                break;
            }
        }

        // mcq answer
        if (multipleChoiceItemComponents.size() > 0) {
            if (choiceItemComponentComboBox.getValue() == null) {
                mcqAnswer.addStyleName(MyTheme.MAIN_TEXT_WARNING);
            }
            else mcqAnswer.removeStyleName(MyTheme.MAIN_TEXT_WARNING);
        }

        return empty;
    }

    private void submitQuestionForm() {

        // check if everything is filled
        footerFinish.addLayoutClickListener((LayoutEvents.LayoutClickListener) event -> {

            // empty variable
            boolean emptyFormItems = areAnyFormItemComponentsEmpty();
            boolean emptyMultipleChoiceItems = areAnyMultipleChoiceItemsEmpty();

            if (emptyFormItems) {

                // fill in all fields label
                Label fillInLabel = new Label("* fill in all fields");
                fillInLabel.addStyleNames(MyTheme.MAIN_TEXT_SIZE_EXTRA_LARGE, MyTheme.MAIN_TEXT_WARNING);
                headerLayout.removeAllComponents();
                headerLayout.addComponents(cancel, createQuestionLabel, fillInLabel);
                headerLayout.setComponentAlignment(cancel, Alignment.MIDDLE_CENTER);

                // unset warning labels
                for (FormItemComponent i : formItemComponents) {
                    if (i.isEmpty()) i.setWarningLabel();
                    else i.unsetWarningLabel();
                }
            }

            if (multipleChoiceItemComponents.size() > 0) {
                if (emptyMultipleChoiceItems) mcqHeader.addStyleName(MyTheme.MAIN_TEXT_WARNING);
                else mcqHeader.removeStyleName(MyTheme.MAIN_TEXT_WARNING);
            }

            // send to database if both aren't empty
            if (!emptyFormItems && !emptyMultipleChoiceItems) {

                // remove fill in all fields
                headerLayout.removeAllComponents();
                headerLayout.addComponents(cancel, createQuestionLabel);
                headerLayout.setComponentAlignment(cancel, Alignment.MIDDLE_CENTER);

                // unset warning labels
                for (FormItemComponent i : formItemComponents) {
                    if (i.isEmpty()) i.setWarningLabel();
                    else i.unsetWarningLabel();
                }
                if (multipleChoiceItemComponents.size() > 0)
                    mcqHeader.removeStyleName(MyTheme.MAIN_TEXT_WARNING);

                // set up core variables
                // questionItem.setCourseId(comboBoxItem.getValeOfComponent());
                questionItem.setQuestionBody(questionBodyItem.getValueOfComponent());
                questionItem.setQuestionMark(questionMarksItem.getValeOfComponent());
                questionItem.setQuestionDifficulty(questionDifficultyItem.getValeOfComponent());

                // set up specialised variables
                switch (questionItem.getQuestionType()) {
                    case "written":
                        // get answer and number of lines
                        questionItem.setQuestionAns(questionAnswerItem.getValueOfComponent());
                        break;
                    case "mcq":
                        // choices
                        StringBuilder stringBuilder = new StringBuilder();
                        // iterate choices
                        int length = multipleChoiceItemComponents.size();
                        for (int i = 0; i < length; i++) {
                            if (i != length - 1) {
                                stringBuilder.append(multipleChoiceItemComponents.get(i).getTextField().getValue());
                                stringBuilder.append(";");
                            } else stringBuilder.append(multipleChoiceItemComponents.get(i).getTextField().getValue());
                        }
                        // get choices and answer
                        if (choiceItemComponentComboBox.getValue() != null)
                            questionItem.setQuestionAns(choiceItemComponentComboBox.getValue().getNumberValue());
                        questionItem.setQuestionMcqChoices(stringBuilder.toString());
                        break;
                    case "practical":
                        // get sample input/output and answer
                        questionItem.setQuestionAns(questionAnswerItem.getValueOfComponent());
                        questionItem.setQuestionPracticalSampleInput(questionSampleInputItem.getValueOfComponent());
                        questionItem.setQuestionPracticalSampleOutput(questionSampleOutputItem.getValueOfComponent());
                        break;
                }

                // post
                if (questionServer.postToQuestionTable(questionItem) > 0) {

                    // also post tags
                    questionServer.postToTagTable(tags);

                    // set variable for notification
                    VaadinService.getCurrentRequest().setAttribute("question-post", true);

                    // navigate to question view
                    navigator.navigateTo(question);
                }
                else {
                    Notification.show("ERROR", "Could not add question", Notification.Type.ERROR_MESSAGE);
                }
            }
        });
    }

    private void unsetComponentWarningLabelAndReset(FormItemComponent... components) {

        // unset and reset
        for (FormItemComponent i : components) {
            i.resetItem();
            i.unsetWarningLabel();
        }
    }

    private void addToFormItemComponentArrayList(FormItemComponent... components) {

        // add all
        formItemComponents.addAll(Arrays.asList(components));
    }

    private void setUpMultipleChoiceToReplacementLayout() {

        // add Label
        mcqHeader = new Label("Choices");
        mcqHeader.addStyleNames(MyTheme.MAIN_TEXT_SIZE_MEDIUM, MyTheme.MAIN_TEXT_WEIGHT_500);
        replacementLayout.addComponent(mcqHeader);

        // create mcq item
        int index = multipleChoiceItemComponents.size();

        if (index == 0) {

            // you must have at minimum two choices
            MultipleChoiceItemComponent item = new MultipleChoiceItemComponent(index + 1);
            MultipleChoiceItemComponent item2 = new MultipleChoiceItemComponent(index + 2);

            // add to array list
            multipleChoiceItemComponents.add(item);
            multipleChoiceItemComponents.add(item2);

            // set on click listeners
            item.getAddButton().addClickListener((Button.ClickListener)
                    event -> addMultipleChoiceToReplacementLayout());
            item2.getAddButton().addClickListener((Button.ClickListener)
                    event -> addMultipleChoiceToReplacementLayout());
            item.getTextField().addValueChangeListener((HasValue.ValueChangeListener<String>)
                    event -> choiceItemComponentComboBox.setItems(multipleChoiceItemComponents));
            item2.getTextField().addValueChangeListener((HasValue.ValueChangeListener<String>)
                    event -> choiceItemComponentComboBox.setItems(multipleChoiceItemComponents));

            // add to replacement layout
            replacementLayout.addComponents(item, item2);

            // combo box for answer
            choiceItemComponentComboBox = new ComboBox<>();
            choiceItemComponentComboBox.setWidth(100.0f, Unit.PERCENTAGE);
            choiceItemComponentComboBox.setPlaceholder("Select Multiple Choice answer");
            choiceItemComponentComboBox.setItems(multipleChoiceItemComponents);
            choiceItemComponentComboBox.setItemCaptionGenerator(MultipleChoiceItemComponent::getItemValue);

            // add listener
            choiceItemComponentComboBox.addValueChangeListener((HasValue.ValueChangeListener<MultipleChoiceItemComponent>)
                    event -> questionItem.setQuestionAns(event.getValue().getNumberValue()));

            // add combo box
            mcqAnswer = new Label("Answer");
            mcqAnswer.addStyleNames(MyTheme.MAIN_TEXT_SIZE_MEDIUM, MyTheme.MAIN_TEXT_WEIGHT_500);
            replacementLayout.addComponents(mcqAnswer, choiceItemComponentComboBox);
        }
    }

    private void addMultipleChoiceToReplacementLayout() {

        // create mcq item
        int index = multipleChoiceItemComponents.size();

        if (2 <= index && index < 5) {

            // add with a remove option
            MultipleChoiceItemComponent item = new MultipleChoiceItemComponent(index + 1);

            // add to array list
            multipleChoiceItemComponents.add(item);

            // set on click listeners
            item.getAddButton().addClickListener((Button.ClickListener)
                    event -> addMultipleChoiceToReplacementLayout());
            item.getRemoveButton().addClickListener((Button.ClickListener)
                    event -> removeMultipleChoiceFromReplacementLayout(item));
            item.getTextField().addValueChangeListener((HasValue.ValueChangeListener<String>)
                    event -> choiceItemComponentComboBox.setItems(multipleChoiceItemComponents));

            // get index
            int i = replacementLayout.getComponentCount();

            // set combo box
            choiceItemComponentComboBox.setItems(multipleChoiceItemComponents);

            // add to replacement layout
            replacementLayout.addComponent(item, i - 2);
        }
        else {
            Notification.show("ERROR", "Maximum choices reached", Notification.Type.WARNING_MESSAGE);
        }
    }

    private void removeMultipleChoiceFromReplacementLayout(MultipleChoiceItemComponent itemComponent) {

        // remove
        multipleChoiceItemComponents.remove(itemComponent);
        replacementLayout.removeComponent(itemComponent);

        // re number
        for (int i = 0; i < multipleChoiceItemComponents.size(); i++) {

            // get char
            char c = 'a';
            c += i;

            // number
            multipleChoiceItemComponents.get(i).setNumberValue(Character.toString(c));

            // set remove id
            multipleChoiceItemComponents.get(i).setRemoveButtonId(Character.toString(c));
        }

        // set combo box
        choiceItemComponentComboBox.setItems(multipleChoiceItemComponents);
    }

    private void removeAllMultipleChoiceItemsFromArrayList() {

        int i = multipleChoiceItemComponents.size();
        while (i > 0) {
            multipleChoiceItemComponents.remove(i - 1);

            i  = multipleChoiceItemComponents.size();
        }
    }
}
