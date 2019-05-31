package com.Server;

import com.Components.TagItemsComponent;
import com.Objects.TagItem;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static org.mockito.Matchers.*;

class TagServerTest {

    TagItem tagItem =  new TagItem();

    TagItemsComponent tic;

    ArrayList<TagItemsComponent.TagItemComponent> items;

    @Mock
    Connection connection;
    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    TagServer ts;
   // @Mock
    ArrayList<TagItemsComponent.TagItemComponent> testitems = new ArrayList<>();

    @Mock
    TagItemsComponent component1;

    TagItemsComponent component;
    @Mock
    TagItemsComponent.TagItemComponent comp;


    ArrayList<TagItemsComponent.TagItemComponent> tagitems;

    @BeforeEach
    void setUp() throws  Exception{
        MockitoAnnotations.initMocks(this);

        //component.setQuestionId(1);
       // component.setId("1");
        //comp.setName("name");
        //comp.setTagId(2);
        //comp.setTagName("just tag");
        testitems.add(comp);

        tagItem.setTagName("hash");
        tagItem.setQuestionId(2);
        tagItem.setTagId(1);

        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        //Mockito.when(component.getTagItems()).thenReturn(tagitems);

    }

    @Test
    void getTags() throws Exception{
        resultSet.insertRow();
        ts = new TagServer(connection);
        ArrayList<TagItem> tagItems = ts.getTags(1);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Mockito.verify(statement, Mockito.times(1)).executeQuery(anyString());
        Mockito.verify(resultSet, Mockito.times(2)).getInt(anyString());
        Mockito.verify(resultSet, Mockito.times(1)).getString(anyString());
        Assert.assertNotNull(tagItems);

    }

    @Test
    void postToTagTable() throws Exception{
       // component.setQuestionId(1);
       // component.setId("1");
       // ArrayList<TagItemsComponent.TagItemComponent> newList = new ArrayList<>(anyCollection());
        Mockito.when(component1.getTagItems()).thenReturn(testitems);
       // Mockito.when(testitems.size()).thenReturn(1);
        ts = new TagServer(connection);
        ts.postToTagTable(component1);
        Mockito.verify(connection, Mockito.times(1)).createStatement();
        Mockito.verify(statement, Mockito.times(1)).executeQuery(anyString());
        //Mockito.verify(resultSet, Mockito.times(1)).next();
        Mockito.verify(statement, Mockito.times(1)).executeUpdate(anyString());
//        Mockito.when(component.getTagItems()).thenReturn(items);

    }
}