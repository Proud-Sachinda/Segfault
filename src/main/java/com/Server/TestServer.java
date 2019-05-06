package com.Server;

import com.Objects.TestItem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TestServer {

    // connection variable
    private Connection connection;


    public TestServer(Connection connection) {

        // initialise connection variable
        this.connection = connection;
    }

    // -------------------------------- POST METHODS (INSERT)
    public int postToTestTable(boolean testIsDraft, String testDraftName, int courseId) {

        // return variable
        int testId = 0;

        try {

            // query
            String query = "INSERT INTO public.test" +
                    "(test_is_draft, test_draft_name, course_id) VALUES" +
                    "(" + testIsDraft + ", '" + testDraftName + "', " + courseId + ")";

            // statement
            Statement statement = connection.createStatement();

            // execute statement
            testId = statement.executeUpdate(query);

            // get test id
            query = "SELECT test_id FROM test ORDER BY test_id DESC LIMIT 1";

            // execute statement
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                testId = resultSet.getInt("test_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return testId;
    }

        public ArrayList getTestItems(){
             ArrayList<TestItem> testItems = new ArrayList<TestItem>();

            try {

                // get database variables
                Statement statement = connection.createStatement();

                // query
                String query = "SELECT * FROM public.test";

                // execute statement
                ResultSet set = statement.executeQuery(query);
                while(set.next()) {
                    // CourseItem class variable
                    TestItem item = new TestItem();

                    // set variables
                    //item.setUpTestItem(set);

                    // add to array list
                    testItems.add(item);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return testItems;
        }


    }



