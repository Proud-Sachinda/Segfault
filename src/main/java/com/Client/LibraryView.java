package com.Client;

import com.AttributeHandling;
import com.Components.CourseComboBox;
import com.Components.CreateCourseComponent;
import com.Components.EmptyComponent;
import com.Components.TestItemComponent;
import com.Dashboard;
import com.MyTheme;
import com.NavigationStates;
import com.Objects.CourseItem;
import com.Objects.LecturerItem;
import com.Objects.TestItem;
import com.Server.CourseServer;
import com.Server.TestServer;
import com.vaadin.data.HasValue;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Connection;
import java.util.ArrayList;

public class LibraryView extends HorizontalLayout implements View {

    // navigator used to redirect to another page
    private Navigator navigator;

    // attributeHandling
    private AttributeHandling attributeHandling;

    // items
    private CourseItem courseItem;
    private LecturerItem lecturerItem;

    // server
    private TestServer testServer;
    private CourseServer courseServer;

    // course items
    private ArrayList<CourseItem> courseItems = new ArrayList<>();

    // base path
    private String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    // components
    private CourseComboBox courseComboBox;
    private final Label headerTitle = new Label();
    private final Panel libraryPanel = new Panel();
    private CreateCourseComponent createCourseComponent;
    private final Button addTest = new Button("+");
    private final Button addCourse = new Button("+");
    private final VerticalLayout library = new VerticalLayout();
    private final HorizontalLayout header = new HorizontalLayout();
    private final HorizontalLayout footer = new HorizontalLayout();
    private final HorizontalLayout addCourseHorizontalLayout = new HorizontalLayout();
    private final EmptyComponent emptyComponent = new EmptyComponent(basePath);

    // navigation and content area
    private final VerticalLayout content = new VerticalLayout();

    // dashboard
    private Dashboard dashboard;

    public LibraryView(Navigator navigator, Connection connection, AttributeHandling attributeHandling) {

        // we get the Apps Navigator object
        this.navigator = navigator;

        // attributeHandling
        this.attributeHandling = attributeHandling;

        // course server
        this.testServer = new TestServer(connection);
        this.courseServer = new CourseServer(connection);

        // set to fill browser screen
        setSizeFull();

        // set up dashboard
        dashboard = new Dashboard(navigator, connection);
        addComponent(dashboard);

        // set content area
        content.setSizeFull();
        content.addStyleName("paper-border");
        addComponentsAndExpand(content);

        // create course component
        createCourseComponent = new CreateCourseComponent();

        // set up library explorer
        setUpLibraryExplorer();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        // set page title
        UI.getCurrent().getPage().setTitle("Dashboard - Library");

        // set active item
        dashboard.setActiveLink("library");

        // if not signed in kick out
        lecturerItem =  attributeHandling.getLecturerItem();


        if (lecturerItem == null) {

            // set message
            attributeHandling.setMessage("Please Sign In");

            // navigate
            navigator.navigateTo("");
        }

        // set up page
        setUpLibrary();
    }

    private void setUpLibrary() {

        // check cookies
        courseItem = attributeHandling.getCourseItem();

        if (courseItem == null) {

            // set course item
            courseItem = courseItems.get(0);
            attributeHandling.setCourseItem(courseItem);

            // set combo box item
            courseComboBox.setValue(courseItem);

            // change view
            changeCurrentLibraryView(courseItem);
        }
        else {

            // int of cookie
            int current = attributeHandling.getCourseItem().getCourseId();

            // find course item
            for (CourseItem i : courseItems) {
                if (current == i.getCourseId()) {
                    courseItem = i;
                    attributeHandling.setCourseItem(i);
                    break;
                }
            }

            // set item
            courseComboBox.setValue(courseItem);

            // change view
            changeCurrentLibraryView(courseItem);
        }
    }

    private void setUpLibraryItems() {

        // remove current items
        library.removeAllComponents();

        // set test items
        ArrayList<TestItem> testItems = testServer.getTestItemsByCourseId(
                courseItem.getCourseId(), lecturerItem.getLecturerId());

        if (testItems.isEmpty()) {

            // remove all components of explorer
            library.removeAllComponents();
            library.setSizeFull();

            // add empty component
            emptyComponent.setType(EmptyComponent.CREATE_A_TEST);
            library.addComponent(emptyComponent);
            library.setComponentAlignment(emptyComponent, Alignment.MIDDLE_CENTER);
        }
        else {

            // remove all components of explorer
            library.removeAllComponents();
            library.setHeightUndefined();

            //library.addComponent(item);
            int size = testItems.size();

            // horizontal layout for adding
            HorizontalLayout addHorizontal = null;

            // add test items to library
            for (int i = 0; i < size; i++) {

                // create test item component
                TestItemComponent item = new TestItemComponent(testItems.get(i), basePath, testServer,
                        library, navigator, attributeHandling);

                if (i % 4 == 0) {

                    // add row
                    addHorizontal = new HorizontalLayout();
                    addHorizontal.setWidth(100f, Unit.PERCENTAGE);
                    item.setUpHorizontalLayout(addHorizontal);
                    library.addComponentsAndExpand(addHorizontal);
                    library.setHeightUndefined();
                    library.setComponentAlignment(addHorizontal, Alignment.MIDDLE_CENTER);
                    addHorizontal.addComponent(item);
                }
                else {
                    item.setUpHorizontalLayout(addHorizontal);
                    addHorizontal.addComponent(item);
                }

            }
        }
    }

    private void setUpLibraryExplorer() {

        // header title
        headerTitle.addStyleName(MyTheme.MAIN_TEXT_SIZE_EXTRA_LARGE);
        header.addComponent(headerTitle);

        // header course combo box
        courseItems = courseServer.getCourseItems();
        courseComboBox = new CourseComboBox(courseItems);
        courseComboBox.setCaption(null);
        courseComboBox.addValueChangeListener((HasValue.ValueChangeListener<CourseItem>)
                event -> changeCurrentLibraryView(event.getValue()));

        // add course button
        setUpAddCourseButton();
        addCourse.addStyleName(MyTheme.MAIN_FLAT_COURSE_ADD_BUTTON);

        // add and combo box
        addCourseHorizontalLayout.addComponents(courseComboBox, addCourse);
        addCourseHorizontalLayout.setComponentAlignment(addCourse, Alignment.MIDDLE_CENTER);
        addCourseHorizontalLayout.setComponentAlignment(courseComboBox, Alignment.MIDDLE_CENTER);
        header.addComponent(addCourseHorizontalLayout);

        // header
        header.addStyleName(MyTheme.DASHED_BOTTOM);
        header.setWidth(100f, Unit.PERCENTAGE);
        header.setComponentAlignment(addCourseHorizontalLayout, Alignment.MIDDLE_RIGHT);
        content.addComponent(header);

        // explorer
        libraryPanel.setSizeFull();
        libraryPanel.setContent(library);
        libraryPanel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        content.addComponentsAndExpand(libraryPanel);

        // footer
        footer.setWidth(100f, Unit.PERCENTAGE);
        content.addComponent(footer);

        // add test
        setUpAddTestButton();
        addTest.addStyleName(MyTheme.MAIN_FLAT_ROUND_BUTTON);

        // add test
        addTest.addClickListener((Button.ClickListener) event -> {

            // set test item to null
            attributeHandling.setTestItem(null);

            // navigate
            navigator.navigateTo(NavigationStates.EDITOR);
        });

        footer.addComponent(addTest);
        footer.setComponentAlignment(addTest, Alignment.MIDDLE_RIGHT);
    }

    private void setUpAddTestButton() {

        // clear test
        attributeHandling.setTestItem(null);

        // navigate
        navigator.navigateTo(NavigationStates.EDITOR);
    }

    private void setUpAddCourseButton() {

        addCourse.addClickListener((Button.ClickListener) event -> {

            // check if there are more than two components
            if (addCourseHorizontalLayout.getComponentCount() > 2) {

                // course item component
                courseItem = createCourseComponent.getCourseItemFromComponent();

                if (courseItem.isEmpty()) {

                    Notification.show("ERROR",
                            "Fill in all fields", Notification.Type.WARNING_MESSAGE);
                }
                else {

                    // show notification
                    attributeHandling.setCourseItem(courseItem);
                    int courseId = courseServer.postToCourseTable(courseItem);

                    if (courseId > 0) {

                        // empty form
                        createCourseComponent.emptyCreateCourseComponent();

                        // set course id
                        courseItem.setCourseId(courseId);
                        changeCurrentLibraryView(courseItem);

                        // remove create course component
                        addCourseHorizontalLayout.removeComponent(createCourseComponent);

                        // add to array and set items
                        courseItems.add(courseItem);
                        courseComboBox.setItems(courseItems);
                        courseComboBox.setValue(courseItem);

                        // show notification
                        Notification.show("SUCCESS",
                                "Course added", Notification.Type.TRAY_NOTIFICATION);
                    }
                    else Notification.show("ERROR",
                            "Could not add course", Notification.Type.ERROR_MESSAGE);
                }
            }
            else {

                // add to view
                addCourseHorizontalLayout.addComponent(createCourseComponent, 1);
                addCourseHorizontalLayout.setComponentAlignment(createCourseComponent, Alignment.MIDDLE_CENTER);
            }
        });
    }

    private void changeCurrentLibraryView(CourseItem item) {

        // set course item
        courseItem = item;

        // set header title
        CourseItem.shortenCourseNameIfTooLong(courseItem, headerTitle);

        // current library
        attributeHandling.setCourseItem(item);

        // set up library items
        setUpLibraryItems();
    }
}