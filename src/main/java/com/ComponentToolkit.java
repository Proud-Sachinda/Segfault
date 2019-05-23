package com;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;

public class ComponentToolkit {

    public static void setMultipleImage(float size, Sizeable.Unit unit, Image ... images) {
        for (Image i : images) {
            if (i != null) {
                i.setWidth(size, unit);
                i.setHeight(size, unit);
            }
        }
    }

    public static void setMultipleComponentStyleName(String style, Component ... components) {
        for (Component i : components) if (i != null)
            i.addStyleName(style);
    }

    public static void setMultipleComponentAlignment(VerticalLayout verticalLayout, Alignment alignment, Component ... components) {
        for (Component i : components) if (i != null)
            verticalLayout.setComponentAlignment(i, alignment);
    }

    public static void setMultipleComponentAlignment(HorizontalLayout horizontalLayout, Alignment alignment, Component ... components) {
        for (Component i : components) if (i != null)
            horizontalLayout.setComponentAlignment(i, alignment);
    }
}
