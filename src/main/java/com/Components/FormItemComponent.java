package com.Components;

import com.MyTheme;
import com.Objects.CourseItem;
import com.vaadin.data.HasValue;
import com.vaadin.ui.*;
import org.vaadin.ui.NumberField;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class FormItemComponent extends VerticalLayout {

    // attributes
    private String componentType;

    // components
    private Label label;
    private Component component;

    /**
     * Used for forms
     * @param caption required for caption
     * @param component required component
     * @param type required type of component
     */
    public FormItemComponent(@NotNull String caption, @NotNull Component component, @Null String type) {

        // remove margin
        setMargin(false);

        // set variables
        this.componentType = type;
        this.component = component;

        // set up header
        label = new Label(caption);
        label.addStyleNames(MyTheme.MAIN_TEXT_SIZE_MEDIUM, MyTheme.MAIN_TEXT_WEIGHT_500);

        // set up component
        component.setWidth(100.0f, Unit.PERCENTAGE);

        // add to form item component
        addComponent(label);
        addComponent(component);

        // set difficulty slider
        setUpDifficultySlider();
    }

    public void setValueOfComponent(String value) {

        //
        if (this.componentType.matches(ComponentType.TEXT_AREA)) {

            // set temporary variable
            TextArea tmp = (TextArea) this.component;

            // set value
            tmp.setValue(value);
        }
        else if (this.componentType.matches(ComponentType.TEXT_FIELD)) {

            // set temporary variable
            TextField tmp = (TextField) this.component;

            // set string
            tmp.setValue(value);
        }
    }

    public void setValueOfComponent(CourseItem courseItem) {

        if (this.componentType.matches(ComponentType.COURSE_COMBO_BOX)) {

            // set temporary variable
            CourseComboBox tmp = (CourseComboBox) this.component;

            // set string
            tmp.setValue(courseItem);
        }
    }

    public void setValueOfComponent(int d) {

        double value = Double.parseDouble(d + "");

        if (this.componentType.matches(ComponentType.SLIDER)) {

            // set temporary variable
            Slider tmp = (Slider) this.component;

            // set value
            tmp.setValue(value);
        }
        else if (this.componentType.matches(ComponentType.NUMBER_FIELD)) {

            // set temporary variable
            NumberField tmp = (NumberField) this.component;

            // set value
            tmp.setValue(value + "");
        }
    }

    public String getStringValueOfComponent() {

        // return String
        String ret = null;

        if (this.componentType.matches(ComponentType.TEXT_AREA)) {

            // set temporary variable
            TextArea tmp = (TextArea) this.component;

            // set string
            ret = tmp.getValue().trim();
        }
        else if (this.componentType.matches(ComponentType.TEXT_FIELD)) {

            // set temporary variable
            TextField tmp = (TextField) this.component;

            // set string
            ret = tmp.getValue().trim();
        }

        return ret;
    }

    public int getIntValueOfComponent() {

        // return int
        int ret = 0;

        if (this.componentType.matches(ComponentType.SLIDER)) {

            // set temporary variable
            Slider tmp = (Slider) this.component;

            // set string
            ret = tmp.getValue().intValue();
        }
        else if (this.componentType.matches(ComponentType.NUMBER_FIELD)) {

            // set temporary variable
            NumberField tmp = (NumberField) this.component;

            // set string
            ret = Integer.parseInt(tmp.getValue());
        }
        else if (this.componentType.matches(ComponentType.COURSE_COMBO_BOX)) {

            // set temporary variable
            CourseComboBox tmp = (CourseComboBox) this.component;

            // set string
            ret = tmp.getValue().getCourseId();
        }

        return ret;
    }

    public String getComponentType() {
        return this.componentType;
    }

    private void setUpDifficultySlider() {

        if (this.componentType.matches(ComponentType.SLIDER)) {

            // tmp variable
            Slider tmp = (Slider) this.component;

            tmp.addValueChangeListener((HasValue.ValueChangeListener<Double>) event1 -> {
                int x = event1.getValue().intValue();

                // check
                if (x == 1) label.setValue("Difficulty - Beginner");
                else if (x == 2) label.setValue("Difficulty - Basic");
                else if (x == 3) label.setValue("Difficulty - Intermediate");
                else if (x == 4) label.setValue("Difficulty - Advanced");
                else if (x == 5) label.setValue("Difficulty - Expert");
            });
        }
    }

    public void setComponentWidth(float f, Unit unit) {
        this.setWidth(f, unit);
        this.component.setWidth(f, unit);
    }

    public void resetItem() {

        // remove any text
        if (this.componentType.matches(ComponentType.TEXT_AREA)) {
            TextArea textArea = (TextArea) this.component;
            textArea.setValue("");
        }
    }

    public void setWarningLabel() {

        // red text
        label.addStyleName(MyTheme.MAIN_TEXT_WARNING);
    }

    public void unsetWarningLabel() {

        // normal text
        label.removeStyleName(MyTheme.MAIN_TEXT_WARNING);
    }

    public boolean isEmpty() {

        // return variable
        boolean empty = true;

        // get component type
        if (this.componentType.matches(ComponentType.SLIDER)) {

            // set temporary variable
            Slider tmp = (Slider) this.component;

            // set boolean
            empty = !(tmp.getValue() > 0);
        }
        else if (this.componentType.matches(ComponentType.TEXT_AREA)) {

            // set temporary variable
            TextArea tmp = (TextArea) this.component;

            // set boolean
            empty = tmp.getValue().trim().isEmpty();
        }
        else if (this.componentType.matches(ComponentType.TEXT_FIELD)) {

            // set temporary variable
            TextField tmp = (TextField) this.component;

            // set boolean
            empty = tmp.getValue().trim().isEmpty();
        }
        else if (this.componentType.matches(ComponentType.NUMBER_FIELD)) {

            // set temporary variable
            NumberField tmp = (NumberField) this.component;

            // set boolean
            empty = tmp.getValue().trim().isEmpty();
        }
        else if (this.componentType.matches(ComponentType.COURSE_COMBO_BOX)) {

            // set temporary variable
            CourseComboBox tmp = (CourseComboBox) this.component;

            // set boolean
            empty = tmp.getValue() == null;
        }

        return empty;
    }

    public static class ComponentType {

        // Slider
        public static final String SLIDER = "SLIDER";

        // Text Field
        static final String TEXT_FIELD = "TEXT_FIELD";

        // Text Area
        public static final String TEXT_AREA = "TEXT_AREA";

        // Number Field
        public static final String NUMBER_FIELD = "NUMBER_FIELD";

        // Course Combo Box
        public static final String COURSE_COMBO_BOX = "COURSE_COMBO_BOX";
    }
}
