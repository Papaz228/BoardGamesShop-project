package com.company.boardgamesshop.action.admin;
import com.company.boardgamesshop.action.factory.Action;
import com.company.boardgamesshop.entity.User;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.util.constants.ConstantPageNamesJSPAndAction;
import com.company.boardgamesshop.database.dao.impl.UserDaoImpl;
import com.company.boardgamesshop.database.dao.interfaces.UserDao;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
public class UnbanUserAction implements Action {
    UserDao userDao = new UserDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession();
        User currentUser = (User)session.getAttribute(Constant.USER);
        if(currentUser.isAdmin()) {
            long userId = Long.parseLong(request.getParameter(Constant.USER_ID));
            userDao.bannedUser(userId, false);
            response.sendRedirect(ConstantPageNamesJSPAndAction.ALL_USERS_SERVICE);
        }
        else{
            response.sendRedirect(ConstantPageNamesJSPAndAction.LOGIN_SERVICE);
        }
    }
}
