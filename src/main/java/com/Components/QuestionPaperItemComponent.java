package com.Components;

import com.MyTheme;
import com.Objects.QuestionItem;
import com.Server.QuestionServer;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.FileResource;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.io.File;
import java.util.ArrayList;

public class QuestionPaperItemComponent extends VerticalLayout {

    // attributes
    private int questionId;
    private String bullet;

    // components
    private Image close;
    private Image moveUp;
    private Image moveDown;
    private final Label bulletLabel = new Label();

    public QuestionPaperItemComponent(
            String bullet, int questionId, QuestionServer questionServer, String basePath,
            ArrayList<QuestionPaperItemComponent> paperItemComponents, VerticalLayout questionPaperArea,
            ArrayList<QuestionItemComponent> questionItemComponents, VerticalLayout questionExplorerArea,
            EmptyComponent emptyComponent) {

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
        FileResource moveUpResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/move-up.svg"));
        moveUp = new Image(null, moveUpResource);
        FileResource moveDownResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/move-down.svg"));
        moveDown = new Image(null, moveDownResource);

        // set width and height of controls
        close.setWidth(22f, Unit.PIXELS);
        close.setHeight(22f, Unit.PIXELS);
        moveUp.setWidth(22f, Unit.PIXELS);
        moveUp.setHeight(22f, Unit.PIXELS);
        moveDown.setWidth(22f, Unit.PIXELS);
        moveDown.setHeight(22f, Unit.PIXELS);

        // set up buttons
        setUpControlButtons(paperItemComponents, questionPaperArea,
                questionItemComponents, questionExplorerArea, emptyComponent);

        // add to controls layout
        controlsLayout.addComponents(close, moveUp, moveDown);
        horizontalLayout.addComponent(controlsLayout);

        addComponent(horizontalLayout);
    }

    private void setUpControlButtons(ArrayList<QuestionPaperItemComponent> paperItemComponents, VerticalLayout questionPaperArea,
                                     ArrayList<QuestionItemComponent> questionItemComponents, VerticalLayout questionExplorerArea,
                                     EmptyComponent emptyComponent) {

        // styles
        close.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);
        moveUp.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);
        moveDown.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);

        // click listeners
        close.addClickListener((MouseEvents.ClickListener) event -> {

            // remove from draft side
            paperItemComponents.remove(getThisQuestionPaperItemComponent());
            questionPaperArea.removeComponent(getThisQuestionPaperItemComponent());

            // add question item component to question explorer
            QuestionItemComponent component = findQuestionItemComponentById(questionId, questionItemComponents);
            if (component != null) {

                // remove in view
                if (questionExplorerArea.getComponentCount() == 1)
                    questionExplorerArea.removeComponent(emptyComponent);
                component.removeAllComponents();
                component.getFirstRow().removeAllComponents();
                component.getThirdRow().removeAllComponents();
                component.getDateAndEdit().removeAllComponents();
                component.getUpdateQuestionControls().removeAllComponents();
                component.setUpQuestionItemComponent();
                component.setUpSeeMoreSeeLessClickListener();
                component.setUpUpdateQuestionClickListeners();
                questionExplorerArea.addComponentAsFirst(component);

                // remove in database
            }
        });
        moveUp.addClickListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent event) {

                // if it is the first don't move it
                if (paperItemComponents.get(0) != getThisQuestionPaperItemComponent()) {

                    // index of prior
                    int prior = paperItemComponents.indexOf(getThisQuestionPaperItemComponent());
                    prior--;

                    // get prior component
                    QuestionPaperItemComponent item = paperItemComponents.get(prior);

                    // get prior bullet
                    String priorBullet = item.getBullet();
                    String currentBullet = bullet;

                    // swap bullets
                    setBullet(priorBullet);
                    item.setBullet(currentBullet);

                    // swap in view
                    questionPaperArea.removeComponent(getThisQuestionPaperItemComponent());
                    questionPaperArea.addComponent(getThisQuestionPaperItemComponent(), prior);
                }
            }
        });
    }

    private QuestionItemComponent findQuestionItemComponentById(
            int questionId,ArrayList<QuestionItemComponent> questionItemComponents) {

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

    public void setBullet(String bullet) {
        this.bullet = bullet;
    }

    public String getBullet() { return this.bullet; }

    public int getQuestionId() {
        return questionId;
    }

    public void updateQuestionPaperItemComponentValue() {
        if (bullet.length() > 1) bulletLabel.setValue(bullet);
        else bulletLabel.setValue("(" + bullet + ")");
    }
}
