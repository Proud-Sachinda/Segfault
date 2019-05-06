package com.Server;

import com.Components.TagItemsComponent;
import com.Objects.TagItem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TagServer {

    // connection variable
    private Connection connection;

    public TagServer(Connection connection) {

        // initialise connection variable
        this.connection = connection;
    }

    // -------------------------------- GET METHODS (SELECT)
    public ArrayList<TagItem> getTags(int question_id) {

        // question array
        ArrayList<TagItem> tags = new ArrayList<>();

        try {

            // get database variables
            Statement statement = connection.createStatement();

            // query
            String query = "SELECT * FROM public.tag " +
                    "WHERE question_id = " + question_id;

            // execute statement
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {

                // QuestionItem class variable
                TagItem tag = new TagItem();

                // set variables
                tag.setQuestionId(set.getInt("tag_id"));
                tag.setTagName(set.getString("tag_name"));
                tag.setQuestionId(set.getInt("question_id"));

                // add to array list
                tags.add(tag);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tags;
    }


    // -------------------------------- POST METHODS (INSERT)
    public void postToTagTable(TagItemsComponent component) {

        // get array list
        ArrayList<TagItemsComponent.TagItemComponent> items = component.getTagItems();

        if (items != null) {
            if (items.size() != 0) {
                try {

                    // get question id
                    String query = "SELECT question_id FROM public.question ORDER BY question_id DESC LIMIT 1";

                    // get statement
                    Statement statement = connection.createStatement();

                    // execute statement
                    ResultSet resultSet = statement.executeQuery(query);

                    while (resultSet.next()) {
                        component.setQuestionId(resultSet.getInt("question_id"));
                    }

                    // string builder
                    StringBuilder builder = new StringBuilder();
                    query = "INSERT INTO tag(question_id, tag_name) VALUES ";

                    for (TagItemsComponent.TagItemComponent i : items) {

                        // query
                        builder.append("(").append(component.getQuestionId()).append(", '").append(i.getTagName()).append("'), ");
                    }

                    // set query
                    builder.deleteCharAt(builder.length() - 1);
                    builder.deleteCharAt(builder.length() - 1);
                    query += builder.toString();

                    // execute statement
                    statement.executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
