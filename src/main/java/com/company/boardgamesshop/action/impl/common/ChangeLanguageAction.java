package com.company.boardgamesshop.action.impl.common;

import com.company.boardgamesshop.action.Action;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.util.constants.ConstantPageNamesJSPAndAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class ChangeLanguageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession();
        String local = request.getParameter(Constant.LANGUAGE);
        Long localId = Long.parseLong(request.getParameter(Constant.LOCAL_ID));
        session.setAttribute(Constant.LANGUAGE, local);
        session.setAttribute(Constant.LOCAL_ID, localId);
        response.sendRedirect(ConstantPageNamesJSPAndAction.HOME_SERVICE);
    }
}
