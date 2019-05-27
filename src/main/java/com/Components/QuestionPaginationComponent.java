package com.Components;

import com.MyTheme;
import com.Objects.QuestionItem;
import com.Objects.TrackItem;
import com.Server.CourseServer;
import com.Server.QuestionServer;
import com.Server.TagServer;
import com.Server.TrackServer;
import com.vaadin.data.HasValue;
import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.ui.*;
import com.vaadin.ui.dnd.DropTargetExtension;
import com.vaadin.ui.dnd.event.DropListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class QuestionPaginationComponent extends VerticalLayout {

    // attributes
    private int questionMarks;
    private int questionNumber;
    private boolean useNumeric;
    private boolean hasQuestions;

    // external attributes
    private int testId;
    private String basePath;
    private Navigator navigator;
    private int currentSelectedPaginationPage;

    // components
    private Label marks;
    private Label number;
    private Label dropQuestionItemHere;
    private VerticalLayout dropArea = new VerticalLayout();

    // external components
    private VerticalLayout paperArea;
    private EmptyComponent emptyComponent;
    private VerticalLayout verticalLayoutRoot;

    // servers
    private TagServer tagServer;
    private TrackServer trackServer;
    private CourseServer courseServer;
    private QuestionServer questionServer;

    // array list of question paper item components
    private ArrayList<QuestionPaperItemComponent> paperItemComponentArrayList;

    // external array lists
    private ArrayList<Button> buttons;
    private ArrayList<QuestionItemComponent> questionItemComponents;
    private ArrayList<QuestionPaginationComponent> questionPaginationComponents;

    public QuestionPaginationComponent(int questionNumber, int testId, int currentSelectedPaginationPage, String basePath,
                                       CourseServer courseServer, QuestionServer questionServer, TagServer tagServer, TrackServer trackServer,
                                       EmptyComponent emptyComponent, HorizontalLayout paginationCenter,
                                       VerticalLayout verticalLayoutRoot, VerticalLayout paperArea,
                                       ArrayList<Button> buttons, ArrayList<QuestionPaginationComponent> questionPaginationComponents,
                                       ArrayList<QuestionItemComponent> questionItemComponents, Navigator navigator) {

        // set attributes
        this.questionMarks = 0;
        this.useNumeric = false;
        this.hasQuestions = false;
        this.questionNumber = questionNumber;

        // set servers
        this.tagServer = tagServer;
        this.trackServer = trackServer;
        this.courseServer = courseServer;
        this.questionServer = questionServer;

        // declare
        paperItemComponentArrayList = new ArrayList<>();

        // set external attributes
        this.testId = testId;
        this.basePath = basePath;
        this.navigator = navigator;
        this.currentSelectedPaginationPage = currentSelectedPaginationPage;

        // set external components
        this.paperArea = paperArea;
        this.emptyComponent = emptyComponent;
        this.verticalLayoutRoot = verticalLayoutRoot;

        // set external array lists
        this.buttons = buttons;
        this.questionItemComponents = questionItemComponents;
        this.questionPaginationComponents = questionPaginationComponents;

        // set up component
        setMargin(false);

        // used for the header, i.e Question 1 - (20 marks)
        HorizontalLayout header = new HorizontalLayout();
        marks = new Label("(" + questionMarks + " marks)");
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

                // remove tracks
                for (QuestionPaperItemComponent c : paperItemComponentArrayList) {

                    QuestionItemComponent component = c.findQuestionItemComponentById(c.questionId, questionItemComponents);

                    if (component != null) {

                        // remove in database
                        if (!trackServer.deleteTrackItemFromTrackTable(c.getTrackItem()))
                            Notification.show("ERROR", "Could not delete", Notification.Type.ERROR_MESSAGE);
                        else {

                            // set height to undefined
                            verticalLayoutRoot.setHeightUndefined();

                            // remove from draft side
                            paperItemComponentArrayList.remove(c.getThisQuestionPaperItemComponent());
                            dropArea.removeComponent(c.getThisQuestionPaperItemComponent());

                            // remove in view
                            c.resetQuestionPaperItemComponent(component);
                        }
                    }
                }

                // remove from data base
                if (!trackServer.deleteTrackItemFromTrackTable(testId, questionNumber))
                    Notification.show("ERROR", "Could not delete page", Notification.Type.ERROR_MESSAGE);
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
            dropQuestionItemHere = new Label("Drop question items here");
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
                dropArea.removeComponent(dropQuestionItemHere);

                // character incrementation
                String bulletIncrement = setQuestionPaperItemComponentValue(paperItemComponentArrayList.size());

                // add question item
                int qId = 1;
                if (event.getDataTransferData("id").isPresent()) {
                    qId = Integer.parseInt(event.getDataTransferData("id").get());
                }

                // remove component from vertical layout root
                if (verticalLayoutRoot.getComponentCount() == 1) {
                    emptyComponent.setType(EmptyComponent.NO_MORE_QUESTIONS);
                    verticalLayoutRoot.setSizeFull();
                    verticalLayoutRoot.addComponent(emptyComponent);
                    verticalLayoutRoot.setComponentAlignment(emptyComponent, Alignment.TOP_CENTER);

                    // remove drag source
                    verticalLayoutRoot.removeComponent(dragSource.get());
                }
                else {
                    verticalLayoutRoot.setHeightUndefined();
                    // remove drag source
                    verticalLayoutRoot.removeComponent(dragSource.get());
                }

                // question paper item component
                QuestionPaperItemComponent itemComponent =
                        new QuestionPaperItemComponent(bulletIncrement, qId);
                paperItemComponentArrayList.add(itemComponent);

                dropArea.addComponent(itemComponent);

                // add marks
                questionMarks += Integer.parseInt(event.getDataTransferText());
                if (questionMarks == 0) marks.setValue("(no marks)");
                else if (questionMarks == 1) marks.setValue("(" + questionMarks + " mark)");
                else marks.setValue("(" + questionMarks + " marks)");

                // send to track table
                int bulletIncrementValue = getQuestionPaperItemComponentValue(findIndexOfQuestionPaperItemComponent(qId));
                TrackItem trackItem = trackServer.postToTrackTable(testId, qId, questionNumber, bulletIncrementValue);
                if (trackItem == null)
                    Notification.show("ERROR", "Could not add question", Notification.Type.ERROR_MESSAGE);
                else itemComponent.setTrackItem(trackItem);
            }
        });
    }

    public void addQuestionPaperItemComponent(TrackItem trackItem) {

        // set height undefined
        dropArea.setHeightUndefined();
        dropArea.removeComponent(dropQuestionItemHere);

        if (paperItemComponentArrayList != null) {

            // character incrementation
            String bulletIncrement = setQuestionPaperItemComponentValue(paperItemComponentArrayList.size());

            // question paper item component
            QuestionPaperItemComponent itemComponent =
                    new QuestionPaperItemComponent(bulletIncrement, trackItem.getQuestionId());
            itemComponent.setTrackItem(trackItem);
            paperItemComponentArrayList.add(itemComponent);
            dropArea.addComponent(itemComponent);

            hasQuestions = true;

            // add marks
            QuestionItem item = questionServer.getQuestionItemById(trackItem.getQuestionId());
            questionMarks += item.getQuestionMark();
            if (questionMarks == 0) marks.setValue("(no marks)");
            else if (questionMarks == 1) marks.setValue("(" + questionMarks + " mark)");
            else marks.setValue("(" + questionMarks + " marks)");
        }
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    private void updatePaginationComponent(int questionNumber) {

        // old question number
        int oldQuestionNumber = this.questionNumber;

        // question number
        this.questionNumber = questionNumber;

        // set value
        number.setValue("Question " + this.questionNumber + " - ");

        // ship to data base
        if (!trackServer.updateTrackItemQuestionNumber(testId, oldQuestionNumber, questionNumber))
            Notification.show("ERROR", "Could not update", Notification.Type.ERROR_MESSAGE);
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

    private void reIncrementAllPaginationItems(int removeInt) {

        // re-increment pagination questions
        for (int i = 0; i < questionPaginationComponents.size(); i++) {
            // get component
            questionPaginationComponents.get(i).updatePaginationComponent(i + 1);
        }

        // re-increment pagination buttons and rerender
        for (int i = 1; i <= buttons.size(); i++) {
            // get button
            buttons.get(i - 1).setCaption(Integer.toString(i));
        }

        currentSelectedPaginationPage = removeInt;
        changeQuestionPaginationComponent();
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

    private QuestionPaginationComponent get() {
        return this;
    }

    private class QuestionPaperItemComponent extends VerticalLayout {

        // attributes
        private int questionId;
        private String bullet;
        private TrackItem trackItem;

        // components
        private Image close;
        private final Label bulletLabel = new Label();

        QuestionPaperItemComponent(
                String bullet, int questionId) {

            // set up variables
            this.bullet = bullet;
            this.questionId = questionId;

            // set up root
            setMargin(false);
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.setWidth(100.0f, Unit.PERCENTAGE);

            // get question item
            QuestionItem item = questionServer.getQuestionItemById(this.questionId);

            // set up question label and bullet
            Label questionLabel = new Label();
            questionLabel.setValue(item.getQuestionBody());
            if (bullet.length() > 1) bulletLabel.setValue(bullet);
            else bulletLabel.setValue("(" + bullet + ")");
            horizontalLayout.addComponent(bulletLabel);
            horizontalLayout.addComponentsAndExpand(questionLabel);

            // set up controls
            VerticalLayout controlsLayout = new VerticalLayout();
            controlsLayout.setMargin(false);
            controlsLayout.setWidthUndefined();
            FileResource closeResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/close.svg"));
            close = new Image(null, closeResource);

            // set width and height of controls
            close.setWidth(22f, Unit.PIXELS);
            close.setHeight(22f, Unit.PIXELS);

            // set up buttons
            setUpControlButtons();

            // add to controls layout
            controlsLayout.addComponents(close);
            horizontalLayout.addComponent(controlsLayout);

            addComponent(horizontalLayout);
        }

        private void setUpControlButtons() {

            // styles
            close.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);

            // click listeners
            close.addClickListener((MouseEvents.ClickListener) event -> {

                // add question item component to question explorer
                QuestionItemComponent component = findQuestionItemComponentById(questionId, questionItemComponents);

                verticalLayoutRoot.removeComponent(emptyComponent);

                if (component != null) removeQuestionPaperItemComponent(component);
                else {

                    // create new
                    component = new QuestionItemComponent(questionServer, courseServer, tagServer, navigator,
                            verticalLayoutRoot, questionItemComponents, emptyComponent);
                    questionItemComponents.add(component);

                    component.setQuestionItem(questionServer.getQuestionItemById(questionId));

                    removeQuestionPaperItemComponent(component);
                }
            });
        }

        private void removeQuestionPaperItemComponent(QuestionItemComponent component) {
            // remove in database
            if (!trackServer.deleteTrackItemFromTrackTable(trackItem))
                Notification.show("ERROR", "Could not delete", Notification.Type.ERROR_MESSAGE);
            else {

                // set height to undefined
                verticalLayoutRoot.setHeightUndefined();

                // subtract marks
                QuestionItem questionItem = questionServer.getQuestionItemById(questionId);
                questionMarks -= questionItem.getQuestionMark();

                if (questionMarks == 0) marks.setValue("(no marks)");
                else if (questionMarks == 1) marks.setValue("(" + questionMarks + " mark)");
                else marks.setValue("(" + questionMarks + " marks)");

                // remove from draft side
                paperItemComponentArrayList.remove(getThisQuestionPaperItemComponent());
                dropArea.removeComponent(getThisQuestionPaperItemComponent());

                // if drop empty
                if (dropArea.getComponentCount() == 0) {
                    dropArea.setSizeFull();
                    dropArea.addComponent(dropQuestionItemHere);
                    dropArea.setComponentAlignment(dropQuestionItemHere, Alignment.MIDDLE_CENTER);
                }

                // remove in view
                resetQuestionPaperItemComponent(component);

                // change all question items
                for (QuestionPaperItemComponent p : paperItemComponentArrayList) {
                    // update order in view
                    p.setBullet(setQuestionPaperItemComponentValue(paperItemComponentArrayList.indexOf(p)));
                    p.updateQuestionPaperItemComponentValue();

                    // update track item
                    TrackItem t = p.getTrackItem();
                    t.setTrackOrder(paperItemComponentArrayList.indexOf(p) + 1);

                    // update in data base
                    if (!trackServer.updateTrackItemTrackOrder(t))
                        Notification.show("ERROR", "Could not reorder", Notification.Type.ERROR_MESSAGE);
                }
            }
        }

        private TrackItem getTrackItem() {
            return trackItem;
        }

        void setTrackItem(TrackItem trackItem) {
            this.trackItem = trackItem;
        }

        private QuestionItemComponent findQuestionItemComponentById(
                int questionId, ArrayList<QuestionItemComponent> questionItemComponents) {

            // return variable
            QuestionItemComponent component = null;

            for (QuestionItemComponent i : questionItemComponents) {
                QuestionItem item = i.getQuestionItem();
                if (questionId == item.getQuestionId()) component = i;
            }

            return component;
        }

        private QuestionPaperItemComponent getThisQuestionPaperItemComponent() {
            return this;
        }

        void setBullet(String bullet) {
            this.bullet = bullet;
        }

        public int getQuestionId() {
            return questionId;
        }

        void updateQuestionPaperItemComponentValue() {
            if (bullet.length() > 1) bulletLabel.setValue(bullet);
            else bulletLabel.setValue("(" + bullet + ")");
        }

        private void resetQuestionPaperItemComponent(QuestionItemComponent component) {

            if (verticalLayoutRoot.getComponentCount() == 1)
                verticalLayoutRoot.removeComponent(emptyComponent);

            component.removeAllComponents();
            component.getFirstRow().removeAllComponents();
            component.getThirdRow().removeAllComponents();
            component.getDateAndEdit().removeAllComponents();
            component.getUpdateQuestionControls().removeAllComponents();
            component.setUpQuestionItemComponent();
            component.setUpSeeMoreSeeLessClickListener();
            component.setUpUpdateQuestionClickListeners();
            verticalLayoutRoot.addComponentAsFirst(component);
        }
    }

}