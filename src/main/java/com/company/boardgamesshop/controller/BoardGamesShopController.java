package com.company.boardgamesshop.controller;

import com.company.boardgamesshop.action.Action;
import com.company.boardgamesshop.action.impl.factory.FactoryAction;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class BoardGamesShopController extends HttpServlet {
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getServletPath();
        FactoryAction factoryAction = FactoryAction.getInstance();
        try {
            Action action = factoryAction.getService(path);
            action.execute(request, response);
        } catch (ParseException | SQLException | ServletException | IOException e) {
            LOGGER.error(e);
        }
    }
}
