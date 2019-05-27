package com;

import com.Objects.QuestionItem;
import com.Objects.TestItem;
import com.Objects.TrackItem;
import com.Server.QuestionServer;
import com.Server.TestServer;
import com.Server.TrackServer;
import com.itextpdf.kernel.colors.Lab;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.sql.Connection;
import java.util.ArrayList;

@Theme("mytheme")
public class VariancePopupUI extends UI {

    /**
     * this class is used to display variance statistics
     * @param request variable
     */

    private int qId;

    @Override
    protected void init(VaadinRequest request) {

        // set title
        UI.getCurrent().getPage().setTitle("Question Statistics");

        // get parameter
        if (request.getParameter("question_id") != null)
            qId = Integer.parseInt(request.getParameter("question_id"));

        // create and register the views
        Connection connection = ConnectToDatabase.getConnection();

        // set content
        setContent(new VariancePopupView(connection));
    }

    private class VariancePopupView extends VerticalLayout {

        private VariancePopupView(Connection connection) {

            // servers
            TestServer testServer = new TestServer(connection);
            TrackServer trackServer = new TrackServer(connection);
            QuestionServer questionServer = new QuestionServer(connection);

            // question item
            QuestionItem questionItem = questionServer.getQuestionItemById(qId);

            // if it is a variant get original
            if (questionItem.getQuestionIsVariant())
                questionItem = questionServer.getQuestionItemById(questionItem.getQuestionVariance());

            // original question layout
            VerticalLayout originalQuestionVerticalLayout = new VerticalLayout();
            originalQuestionVerticalLayout.setMargin(
                    new MarginInfo(false, false, true, false));

            // set up
            Label originalQuestionLabel = new Label("Original Question");
            originalQuestionLabel.addStyleNames(MyTheme.MAIN_TEXT_SIZE_LARGE, MyTheme.MAIN_TEXT_WEIGHT_900);
            Label originalQuestionBodyLabel = new Label(questionItem.getQuestionBody());
            originalQuestionBodyLabel.addStyleName(MyTheme.MAIN_TEXT_WEIGHT_700);

            // usage statistics
            Label usageStatistics = new Label();
            usageStatistics.addStyleNames(MyTheme.MAIN_TEXT_WEIGHT_800, MyTheme.MAIN_TEXT_SIZE_MEDIUM);
            if (questionItem.getQuestionLastUsed() == null) {
                usageStatistics.addStyleName(MyTheme.MAIN_TEXT_WARNING);
                usageStatistics.setValue("No Usage Statistics");
            }
            else {
                usageStatistics.addStyleName(MyTheme.MAIN_TEXT_GREEN);
                usageStatistics.setValue("Used in the following:");
            }

            // wrap question body
            HorizontalLayout wrap = new HorizontalLayout();
            wrap.setWidth(100f, Unit.PERCENTAGE);
            wrap.addComponentsAndExpand(originalQuestionBodyLabel);

            // cover
            VerticalLayout cover = new VerticalLayout();
            cover.setMargin(false);
            cover.addStyleName(MyTheme.CARD_BORDER);

            cover.addComponents(wrap, usageStatistics);

            // add to original layout
            originalQuestionVerticalLayout.addComponents(originalQuestionLabel, cover);

            // get variants
            ArrayList<TrackItem> tracks = trackServer.getTrackItemsByQuestionId(questionItem.getQuestionId());

            ArrayList<QuestionItem> variants = questionServer.getQuestionItemVariants(questionItem.getQuestionId());

            // add items
            addQuestionLastUsedItems(tracks, questionItem, cover, testServer);

            // add divisor
            originalQuestionVerticalLayout.addStyleName(MyTheme.DASHED_BOTTOM);

            // variant question layout
            VerticalLayout variantQuestionVerticalLayout = new VerticalLayout();
            variantQuestionVerticalLayout
                    .setMargin(new MarginInfo(true, false, false, false));

            // set up
            Label variantQuestionLabel = new Label();
            variantQuestionLabel.addStyleNames(MyTheme.MAIN_TEXT_SIZE_LARGE, MyTheme.MAIN_TEXT_WEIGHT_900);

            if (!variants.isEmpty()) {

                // set value
                if (variants.size() == 1) variantQuestionLabel.setValue("Variant");
                else variantQuestionLabel.setValue("Variants");
                variantQuestionVerticalLayout.addComponent(variantQuestionLabel);

                for (QuestionItem item : variants) {

                    // label
                    Label variantQuestionBodyLabel = new Label(item.getQuestionBody());

                    // new cover
                    cover = new VerticalLayout();
                    cover.setMargin(false);
                    cover.addStyleName(MyTheme.CARD_BORDER);

                    // init wrap again
                    wrap = new HorizontalLayout();
                    wrap.setWidth(100f, Unit.PERCENTAGE);
                    wrap.addComponentsAndExpand(variantQuestionBodyLabel);

                    // usage
                    usageStatistics = new Label();
                    usageStatistics.addStyleNames(MyTheme.MAIN_TEXT_WEIGHT_800, MyTheme.MAIN_TEXT_SIZE_MEDIUM);

                    if (item.getQuestionLastUsed() == null) {
                        usageStatistics.addStyleName(MyTheme.MAIN_TEXT_WARNING);
                        usageStatistics.setValue("No Usage Statistics");
                    }
                    else {
                        usageStatistics.addStyleName(MyTheme.MAIN_TEXT_GREEN);
                        usageStatistics.setValue("Used in the following:");
                    }

                    cover.addComponents(wrap, usageStatistics);

                    // add
                    variantQuestionVerticalLayout.addComponents(cover);

                    // add items
                    tracks = trackServer.getTrackItemsByQuestionId(item.getQuestionId());
                    addQuestionLastUsedItems(tracks, item, cover, testServer);

                }
            }
            else {

                // set value
                variantQuestionLabel.setValue("No Variants");
                variantQuestionVerticalLayout.addComponent(variantQuestionLabel);
            }

            // add layouts
            addComponents(originalQuestionVerticalLayout, variantQuestionVerticalLayout);
        }
    }

    private void addQuestionLastUsedItems(ArrayList<TrackItem> tracks, QuestionItem questionItem, VerticalLayout verticalLayout, TestServer testServer) {

        if (tracks.size() > 0 && questionItem.getQuestionLastUsed() != null) {

            for (TrackItem t : tracks) {

                Label statistic = new Label();
                TestItem testItem = testServer.getTestItemById(t.getTestId());
                String stat = testItem.getTestDraftName() + " in question " + t.getQuestionNumber();
                statistic.setValue(stat);
                verticalLayout.addComponent(statistic);
            }
        }
    }
}
