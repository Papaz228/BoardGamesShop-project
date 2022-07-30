package com.company.boardgamesshop.action.impl.user;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.util.constants.ConstantPageNamesJSPAndAction;
import com.company.boardgamesshop.database.dao.impl.UserDaoImpl;
import com.company.boardgamesshop.database.dao.interfaces.UserDao;
import com.company.boardgamesshop.entity.User;
import com.company.boardgamesshop.action.Action;
import org.apache.commons.codec.digest.DigestUtils;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import static com.company.boardgamesshop.validator.Validator.*;
public class CheckMyProfileAndChangePasswordAction implements Action {
    UserDao userDao=new UserDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher;
        User currentUser = (User)session.getAttribute(Constant.USER);
        String password = request.getParameter(Constant.PASSWORD);
        if(currentUser!=null) {
            if (password != null) {
                if (validatePasswordWithRegex(request.getParameter(Constant.PASSWORD))) {
                    String newPassword = request.getParameter(Constant.PASSWORD);
                    String securedPassword = DigestUtils.md5Hex(newPassword);
                    userDao.changePassword(currentUser.getId(), securedPassword);
                   response.sendRedirect("home");
                } else {
                    request.setAttribute(Constant.ERROR, Constant.ERROR_PASSWORD_FORMAT);
                    request.setAttribute("user",currentUser);
                    dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.MY_PROFILE_JSP);
                    dispatcher.forward(request,response);
                }
            }
            else{
                dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.MY_PROFILE_JSP);
                dispatcher.forward(request, response);
            }
        }
        else {
            response.sendRedirect(ConstantPageNamesJSPAndAction.LOGIN_SERVICE);
        }
    }
}
