package com.Models;

import com.vaadin.annotations.Theme;
//import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.BeanValidationBinder;
//import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.*;
import com.vaadin.data.provider.*;
import com.vaadin.server.VaadinRequest;
//import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
//import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Theme("valo")
public class myExampleUI extends UI {


    private personService service;

    private person person;

    private Grid grid = new Grid();
    private TextField name = new TextField("Name");
    private TextField website = new TextField("Website");
    private Button save = new Button("Save", e -> saveCompany());

    @Override
    protected void init(VaadinRequest request) {
        updateGrid();
        grid.addSelectionListener(e -> updateForm());

        VerticalLayout layout = new VerticalLayout(grid, name, website, save);
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
    }


    private void updateGrid() {
        List<person> person = service.findAll();

       //grid.setContainerDataSource(new BeanItemContainer<>(person.class, companies));
        grid.setDataProvider(new ListDataProvider<>(person) );
        setFormVisible(false);
    }

    private void updateForm() {
       setFormVisible(!grid.getSelectedItems().isEmpty());

        if (!grid.getSelectedItems().isEmpty()) {
           // person = (person) grid.getSelectedRow();
        person = (person) grid.getSelectedItems();
        DataProvider.ofItems(person);
            //DataProvider.bindFieldsUnbuffered(person, this);
        }
    }

    private void setFormVisible(boolean visible) {
        name.setVisible(visible);
        website.setVisible(visible);
        save.setVisible(visible);
    }

    private void saveCompany() {
        service.update(person);
        updateGrid();
    }
}