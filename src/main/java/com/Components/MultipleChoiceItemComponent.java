package com.Components;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class MultipleChoiceItemComponent extends HorizontalLayout {

    // components
    private Label number = new Label();
    private TextField field = new TextField();
    private Button add = new Button("+");
    private Button remove = new Button("-");

    public MultipleChoiceItemComponent(int choice) {

        // set number
        char alpha = 'a';
        alpha += (choice - 1);
        number.setValue("(" + alpha + ")");
        number.addStyleName(ValoTheme.LABEL_HUGE);
        addComponent(number);

        // set field
        addComponentsAndExpand(field);

        // add button
        addComponent(add);

        // add remove
        if (choice > 2) {
            addComponent(remove);

            // set id
            remove.setId(Character.toString(alpha));
        }

        // align
        setComponentAlignment(number, Alignment.MIDDLE_CENTER);
    }

    public boolean isEmpty() {
        return field.getValue().trim().isEmpty();
    }

    public Button getAddButton() {
        return this.add;
    }

    public Button getRemoveButton() {
        return this.remove;
    }

    public TextField getTextField() {
        return this.field;
    }

    public String getNumberValue() {
        return Character.toString(this.number.getValue().charAt(1));
    }

    public String getItemValue() {
        if (field.getValue().trim().isEmpty()) return this.number.getValue();
        else return this.number.getValue() + " - " + field.getValue();
    }

    public void setNumberValue(String alpha) {
        this.number.setValue("(" + alpha + ")");
    }

    public void setRemoveButtonId(String alpha) {
        remove.setId(alpha);
    }
}
