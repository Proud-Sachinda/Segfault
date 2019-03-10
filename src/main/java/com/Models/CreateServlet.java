package com.Models;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/CreateServlet", name = "CreateServlet")
public class CreateServlet extends HttpServlet {

    /**
     * This function will be used to get drop down list of
     * courses and other things that are from the database
     * and relevant to the Create Question Form Page
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
     * This function will be used to insert data from
     * Create Question Form page and redirect to Question
     * View http://localhost:8000/qbank_war_exploded/#!question
     *
     * @param request request variable
     * @param response response variable
     * @throws ServletException in case of servlet errors
     * @throws IOException in case of IO errors
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
