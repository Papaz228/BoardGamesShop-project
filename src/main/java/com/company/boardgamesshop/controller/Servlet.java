package com.company.boardgamesshop.controller;

import com.company.boardgamesshop.action.factory.Action;
import com.company.boardgamesshop.action.factory.FactoryAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import org.apache.log4j.Logger;


public class Servlet extends HttpServlet {
    private final Logger LOG = Logger.getLogger(this.getClass().getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        FactoryAction factoryAction = FactoryAction.getInstance();
        Action action = factoryAction.getService(path);
        try {
            action.execute(request, response);
        } catch (ParseException | SQLException e) {
            LOG.error(e);
        }

    }
}
