package com.Models;

import com.Controllers.QuestionController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/QuestionServlet", name = "QuestionServlet")
public class QuestionServlet extends HttpServlet {

    // question controller must be populated
    private final QuestionController questionController = new QuestionController();

    /**
     * This function will be used to get questions in the database
     * and show them in the Question View, it will use the Question
     * Controller ass well the Question Controllers's list must be
     * populated with the variables from the database and redirect
     * back to http://localhost:8000/qbank_war_exploded/#!question
     *
     * @param request request variable
     * @param response response variable
     * @throws ServletException in case of servlet errors
     * @throws IOException in case of IO errors
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * This function will be used when editing a question it will
     * update the entry in the database and redirect back to
     * http://localhost:8000/qbank_war_exploded/#!question
     *
     * @param request request variable
     * @param response response variable
     * @throws ServletException in case of servlet errors
     * @throws IOException in case of IO errors
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * This function will be used when deleting a question it will
     * remove the question in the database and redirect back to
     * http://localhost:8000/qbank_war_exploded/#!question
     *
     * @param request request variable
     * @param response response variable
     * @throws ServletException in case of servlet errors
     * @throws IOException in case of IO errors
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
