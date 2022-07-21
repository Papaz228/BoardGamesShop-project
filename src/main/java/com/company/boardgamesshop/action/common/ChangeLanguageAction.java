package com.company.boardgamesshop.action.common;
import com.company.boardgamesshop.action.factory.Action;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.util.constants.ConstantPageNamesJSPAndAction;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
public class ChangeLanguageAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {

        HttpSession session = request.getSession();

        String local = request.getParameter(Constant.LANGUAGE);
        Long localId=Long.parseLong(request.getParameter("localId"));
        session.setAttribute(Constant.LANGUAGE, local);
        session.setAttribute("localId",localId);

        response.sendRedirect(ConstantPageNamesJSPAndAction.HOME_SERVICE);
    }
}
