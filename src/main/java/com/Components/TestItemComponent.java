package com.Components;

import com.CookieHandling.CookieAge;
import com.CookieHandling.CookieHandling;
import com.CookieHandling.CookieName;
import com.MyTheme;
import com.NavigationStates;
import com.Objects.TestItem;
import com.Server.TestServer;
import com.vaadin.event.LayoutEvents;
import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;

import javax.validation.constraints.NotNull;
import java.io.File;

public class TestItemComponent extends VerticalLayout {

    // attributes
    private String basePath;
    private boolean isRename;
    private TestItem testItem;
    private Navigator navigator;

    // servers
    private TestServer testServer;

    // roots
    private VerticalLayout verticalLayout;
    private HorizontalLayout horizontalLayout;

    // components
    private Image edit;
    private Image export;
    private Image delete;
    private Image rename;
    private final Label testIsExam = new Label();
    private final Label testIsDraftLabel = new Label();
    private final Label testDraftNameLabel = new Label();
    private final TextField testDraftNameTextField = new TextField();
    private final HorizontalLayout controlsLayout = new HorizontalLayout();
    private final HorizontalLayout draftNameLayout = new HorizontalLayout();
    private final HorizontalLayout examOrTestLayout = new HorizontalLayout();
    private final HorizontalLayout draftOrFinalLayout = new HorizontalLayout();

    /**
     * component for a test item on the library view page
     * @param testItem test item of component
     * @param basePath base path of pictures
     * @param testServer used for databasing
     * @param verticalLayout vertical layout in library view
     */
    @NotNull
    public TestItemComponent(TestItem testItem, String basePath, TestServer testServer,
                             VerticalLayout verticalLayout, Navigator navigator) {

        // set attributes
        this.isRename = false;
        this.basePath = basePath;
        this.testItem = testItem;
        this.navigator = navigator;

        // set servers
        this.testServer = testServer;

        // set roots
        this.verticalLayout = verticalLayout;

        // add border
        addStyleName(MyTheme.CARD_BORDER);

        // set alignment
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        // file resources
        FileResource editResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/edit.svg"));
        FileResource exportResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/share.svg"));
        FileResource deleteResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/trash.svg"));
        FileResource renameResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/rename.svg"));

        // add layouts
        addComponent(draftOrFinalLayout);
        addComponent(draftNameLayout);
        addComponent(examOrTestLayout);
        addComponent(controlsLayout);

        // draft or final
        testIsDraftLabel.addStyleNames(MyTheme.MAIN_TEXT_WEIGHT_500, MyTheme.MAIN_TEXT_SIZE_SMALL);
        draftOrFinalLayout.addComponent(testIsDraftLabel);
        if (testItem.isTestIsDraft()) {

            // set value
            testIsDraftLabel.setValue("draft");

            // add style
            testIsDraftLabel.addStyleName(MyTheme.MAIN_TEXT_WARNING);
        }
        else {

            // set value
            testIsDraftLabel.setValue("final");

            // add style
            testIsDraftLabel.addStyleName(MyTheme.MAIN_TEXT_CHARCOAL);
        }

        // set draft name
        rename = new Image(null, renameResource);
        rename.setWidth(12f, Unit.PIXELS);
        rename.setHeight(12f, Unit.PIXELS);
        rename.addStyleName(MyTheme.MAIN_OPACITY_20);
        testDraftNameLabel.setValue(testItem.getTestDraftName());
        testDraftNameLabel.addStyleNames(MyTheme.MAIN_TEXT_SIZE_MEDIUM, MyTheme.MAIN_TEXT_WEIGHT_800);
        draftNameLayout.addComponents(testDraftNameLabel, rename);
        draftNameLayout.setComponentAlignment(rename, Alignment.MIDDLE_CENTER);

        // set exam
        examOrTestLayout.addComponent(testIsExam);
        testIsExam.addStyleNames(MyTheme.MAIN_TEXT_SIZE_EXTRA_SMALL,
                MyTheme.MAIN_FLAT_TAG_LABEL, MyTheme.MAIN_TEXT_WEIGHT_500);
        if (testItem.isTestIsExam()) testIsExam.setValue("exam");
        else testIsExam.setValue("test");

        // controls
        edit = new Image(null, editResource);
        edit.setWidth(20f, Unit.PIXELS);
        edit.setHeight(20f, Unit.PIXELS);
        export = new Image(null, exportResource);
        export.setWidth(20f, Unit.PIXELS);
        export.setHeight(20f, Unit.PIXELS);
        delete = new Image(null, deleteResource);
        delete.setWidth(20f, Unit.PIXELS);
        delete.setHeight(20f, Unit.PIXELS);
        controlsLayout.addComponents(edit, delete);

        // rename text field
        testDraftNameTextField.setMaxLength(25);

        // if test is not draft make exportable
        if (!testItem.isTestIsDraft()) controlsLayout.addComponent(export);

        // set up click listeners
        setUpClickListeners();
    }

    private void setUpClickListeners() {

        // draft click listener
        draftOrFinalLayout.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);
        draftOrFinalLayout.addLayoutClickListener((LayoutEvents.LayoutClickListener) event -> {

            // get value
            String s = testIsDraftLabel.getValue();

            if (s.equals("draft")) {

                // update ui and test item
                testItem.setTestIsDraft(false);

                // send to database
                if (testServer.updateTestItemTestIsDraft(testItem)) {

                    // update
                    testIsDraftLabel.setValue("final");
                    testIsDraftLabel.removeStyleName(MyTheme.MAIN_TEXT_WARNING);
                    testIsDraftLabel.addStyleName(MyTheme.MAIN_TEXT_CHARCOAL);

                    // add export button
                    controlsLayout.addComponent(export);
                }
                else {

                    // revert
                    testItem.setTestIsDraft(true);

                    // show notification
                    Notification.show("ERROR",
                            "Could not update", Notification.Type.WARNING_MESSAGE);
                }
            }
            else {

                // update ui and test item
                testItem.setTestIsDraft(true);

                if (testServer.updateTestItemTestIsDraft(testItem)) {

                    // update
                    testIsDraftLabel.setValue("draft");
                    testIsDraftLabel.removeStyleName(MyTheme.MAIN_TEXT_CHARCOAL);
                    testIsDraftLabel.addStyleName(MyTheme.MAIN_TEXT_WARNING);

                    // remove export button
                    controlsLayout.removeComponent(export);
                }
                else {

                    // revert
                    testItem.setTestIsDraft(false);

                    // show notification
                    Notification.show("ERROR",
                            "Could not update", Notification.Type.WARNING_MESSAGE);
                }
            }
        });

        // exam or test listener
        examOrTestLayout.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);
        examOrTestLayout.addLayoutClickListener((LayoutEvents.LayoutClickListener) event -> {

            // get value
            String s = testIsExam.getValue();

            if (s.equals("exam")) {

                // update ui and test item
                testItem.setTestIsExam(false);

                // send to database
                if (testServer.updateTestItemTestIsExam(testItem)) testIsExam.setValue("test");
                else {

                    // revert
                    testItem.setTestIsExam(true);

                    // show notification
                    Notification.show("ERROR",
                            "Could not update", Notification.Type.WARNING_MESSAGE);
                }
            }
            else {

                // update ui and test item
                testItem.setTestIsExam(true);

                if (testServer.updateTestItemTestIsExam(testItem)) testIsExam.setValue("exam");
                else {

                    // revert
                    testItem.setTestIsExam(false);

                    // show notification
                    Notification.show("ERROR",
                            "Could not update", Notification.Type.WARNING_MESSAGE);
                }
            }
        });

        // delete test item
        delete.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);
        delete.addClickListener((MouseEvents.ClickListener) event -> {

            if (testServer.deleteTestItemFromTestTable(testItem)) {

                // remove component
                horizontalLayout.removeComponent(getThisTestItemComponent());

                if (horizontalLayout.getComponentCount() == 0) {
                    // remove row
                    verticalLayout.removeComponent(horizontalLayout);

                    if (verticalLayout.getComponentCount() == 0) {
                        // add empty
                        verticalLayout.setSizeFull();
                        EmptyComponent emptyComponent = new EmptyComponent(basePath, EmptyComponent.CREATE_A_TEST);
                        verticalLayout.addComponent(emptyComponent);
                        verticalLayout.setComponentAlignment(emptyComponent, Alignment.MIDDLE_CENTER);
                    }
                }
            }
            else Notification.show("ERROR",
                    "Could not delete", Notification.Type.WARNING_MESSAGE);
        });

        // edit test
        edit.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);
        edit.addClickListener((MouseEvents.ClickListener) event -> {

            // add cookie
            CookieHandling.addCookie(CookieName.EDIT,
                    Integer.toString(testItem.getTestId()), CookieAge.WEEK);

            // go to editor page
            navigator.navigateTo(NavigationStates.EDITOR);
        });

        // rename test
        rename.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);
        rename.addClickListener((MouseEvents.ClickListener) event -> {

            if (isRename) {

                testItem.setTestDraftName(testDraftNameTextField.getValue());

                if (testServer.updateTestItemTestDraftName(testItem)) {

                    // set rename
                    isRename = false;

                    // we are renaming
                    draftNameLayout.removeComponent(testDraftNameTextField);

                    // add label
                    testDraftNameLabel.setValue(testDraftNameTextField.getValue());
                    draftNameLayout.addComponentAsFirst(testDraftNameLabel);
                }
                else {

                    // revert
                    testItem.setTestDraftName(testDraftNameLabel.getValue());

                    // show notification
                    Notification.show("ERROR",
                            "Could not rename", Notification.Type.WARNING_MESSAGE);
                }
            }
            else {

                isRename = true;

                // we are not renaming
                draftNameLayout.removeComponent(testDraftNameLabel);

                // add rename
                testDraftNameTextField.setValue(testDraftNameLabel.getValue());
                draftNameLayout.addComponentAsFirst(testDraftNameTextField);
            }
        });

        // export test
        export.addStyleName(MyTheme.MAIN_CONTROL_CLICKABLE);
        export.addClickListener((MouseEvents.ClickListener)  event -> {

            // add cookie
            CookieHandling.addCookie(CookieName.EDIT,
                    Integer.toString(testItem.getTestId()), CookieAge.WEEK);

            // go to editor page
            navigator.navigateTo(NavigationStates.EXPORT);
        });

    }

    private TestItemComponent getThisTestItemComponent() {
        return this;
    }

    public void setUpHorizontalLayout(HorizontalLayout horizontalLayout) {
        this.horizontalLayout = horizontalLayout;
    }
}
