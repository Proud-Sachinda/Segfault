package com.Components;

import com.MyTheme;
import com.Objects.TagItem;
import com.Server.QuestionViewServer;
import com.vaadin.data.HasValue;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.FileResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.util.ArrayList;

public class TagItemsComponent extends HorizontalLayout {

    // attribute
    private int question_id;

    // question server
    private QuestionViewServer questionViewServer;

    // base path
    private String basePath;

    // component root
    private final HorizontalLayout root = new HorizontalLayout();

    // array
    private final ArrayList<TagItemComponent> tagItemArrayList = new ArrayList<>();

    public TagItemsComponent(QuestionViewServer questionViewServer, String basePath) {

        // set attribute
        this.basePath = basePath;
        this.questionViewServer = questionViewServer;

        // tags label
        // components
        Label tags = new Label("Tags ");
        tags.addStyleNames(MyTheme.MAIN_TEXT_SIZE_MEDIUM, MyTheme.MAIN_TEXT_WEIGHT_800);

        // set add image button
        FileResource addResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/tag-add.svg"));
        Image add = new Image(null, addResource);
        add.setWidth(24.0f, Unit.PIXELS);
        add.setHeight(24.0f, Unit.PIXELS);

        // add items
        root.addComponents(tags, add);
        root.setComponentAlignment(add, Alignment.MIDDLE_CENTER);

        // set button listener
        add.addClickListener((MouseEvents.ClickListener) event -> {

            if (tagItemArrayList.size() < 4) {
                // get size of horizontal layout
                int index = root.getComponentCount() - 1;

                // create tag item
                TagItemComponent item = new TagItemComponent();
                item.setFocused();

                // add to array
                tagItemArrayList.add(item);

                // add tag item at index
                root.addComponent(item, index);
            }
            else Notification.show("ERROR", "Maximum tags reached", Notification.Type.WARNING_MESSAGE);
        });

        // add root
        addComponent(root);
        setComponentAlignment(root, Alignment.MIDDLE_LEFT);
    }

    TagItemsComponent (QuestionViewServer questionViewServer, String basePath, int question_id) {

        // set attribute
        this.basePath = basePath;
        this.questionViewServer = questionViewServer;

        // populate array
        ArrayList<TagItem> tagItems = questionViewServer.getTags(question_id);

        // check for nullness
        if (tagItems != null) {
            if (tagItems.size() > 0) {

                for (TagItem i : tagItems) {

                    // create tag item component
                    TagItemComponent tag = new TagItemComponent(i);

                    // add to array
                    tagItemArrayList.add(tag);

                    // add to view
                    root.addComponent(tag);
                    root.setComponentAlignment(tag, Alignment.MIDDLE_LEFT);
                }
            }
        }

        // add root
        addComponent(root);
        setComponentAlignment(root, Alignment.MIDDLE_LEFT);
    }

    public ArrayList<TagItemComponent> getTagItems() {

        // iterate
        for (TagItemComponent item : tagItemArrayList) {

            // remove empty tags
            if (item.getTagName().trim().isEmpty()) {

                // remove
                tagItemArrayList.remove(item);
                root.removeComponent(item);
            }
        }

        return tagItemArrayList;
    }

    boolean isEmpty() {
        return tagItemArrayList.size() == 0;
    }

    public int getQuestionId() {
        return question_id;
    }

    public void setQuestionId(int question_id) {
        this.question_id = question_id;
    }

    public class TagItemComponent extends HorizontalLayout {

        // attributes
        private TagItem tag;

        // components
        private Label name;
        private TextField edit;

        /**
         * used for new tags
         */
        private TagItemComponent() {

            // set style
            addStyleName(MyTheme.MAIN_FLAT_TAG_EDIT_LAYOUT);

            // set add image button
            FileResource addResource = new FileResource(new File(basePath + "/WEB-INF/img/icons/tag-delete.svg"));
            Image delete = new Image(null, addResource);
            delete.setWidth(12.0f, Unit.PIXELS);
            delete.setHeight(12.0f, Unit.PIXELS);

            // declare variables
            edit = new TextField();
            edit.setMaxLength(10);
            edit.addStyleNames(MyTheme.MAIN_TEXT_SIZE_SMALL, MyTheme.MAIN_FLAT_TAG_TEXT_FIELD);
            edit.setWidth(64.0f, Unit.PIXELS);

            // set styles
            edit.addStyleNames(ValoTheme.TEXTFIELD_BORDERLESS, MyTheme.MAIN_CHARCOAL);

            // add tag item
            addComponents(edit, delete);
            setComponentAlignment(delete, Alignment.MIDDLE_CENTER);

            // set edit listener
            edit.addValueChangeListener((HasValue.ValueChangeListener<String>)
                    event -> tag.setTagName(event.getValue()));

            // set delete listener
            delete.addClickListener((MouseEvents.ClickListener) event -> {

                // delete from array and remove from view
                root.removeComponent(getThisTagItem());
                tagItemArrayList.remove(getThisTagItem());
            });
        }

        private TagItemComponent(TagItem tag) {

            // set attributes
            this.tag = tag;
            this.name = new Label(tag.getTagName().toLowerCase());
            this.setDescription(tag.getTagName());

            // set style
            this.name.addStyleNames(MyTheme.MAIN_TEXT_SIZE_EXTRA_SMALL,
                    MyTheme.MAIN_FLAT_TAG_LABEL);

            // add label name
            addComponent(name);
        }

        private void setFocused() {

            // set edit focused
            edit.focus();
        }

        private TagItemComponent getThisTagItem() {
            return this;
        }

        public int getTagId() {
            return tag.getTagId();
        }

        public void setTagId(int tagId) {
            this.tag.setTagId(tagId);
        }

        public String getTagName() {
            return this.tag.getTagName();
        }

        public void setTagName(String tagName) {
            this.tag.setTagName(tagName);
        }

        public void setName(String name) {
            this.name.setValue(name);
        }
    }
}
