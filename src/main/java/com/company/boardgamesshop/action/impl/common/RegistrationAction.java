package com.company.boardgamesshop.action.impl.common;
import com.company.boardgamesshop.action.Action;
import com.company.boardgamesshop.entity.User;
import com.company.boardgamesshop.util.constants.Constant;
import com.company.boardgamesshop.util.constants.ConstantPageNamesJSPAndAction;
import com.company.boardgamesshop.database.dao.impl.UserDaoImpl;
import com.company.boardgamesshop.database.dao.interfaces.UserDao;
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
public class RegistrationAction implements Action {
    private final UserDao USER_DAO = new UserDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher;
        String email = request.getParameter(Constant.EMAIL);
        if (email == null) {
            dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.REGISTRATION_JSP);
            dispatcher.forward(request, response);
        }
        else if (USER_DAO.isEmailExist(email)) {
                request.setAttribute(Constant.ERROR, Constant.ERROR_EMAIL_EXIST);
                dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.REGISTRATION_JSP);
                dispatcher.forward(request, response);
            }
        else if (!validateMailWithRegex(email)) {
                request.setAttribute(Constant.ERROR, Constant.ERROR_EMAIL_FORMAT);
                dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.REGISTRATION_JSP);
                dispatcher.forward(request, response);
            }
        else if (!validatePasswordWithRegex(request.getParameter(Constant.PASSWORD))) {
                request.setAttribute(Constant.ERROR, Constant.ERROR_PASSWORD_FORMAT);
                dispatcher = request.getRequestDispatcher(ConstantPageNamesJSPAndAction.REGISTRATION_JSP);
                dispatcher.forward(request, response);
            }
        else {
                User newUser = new User();
                newUser.setFirstName(request.getParameter("first_name"));
                newUser.setLastName(request.getParameter("last_name"));
                newUser.setBirthday(request.getParameter("birthday"));
                newUser.setPhoneNumber(request.getParameter("phone_number"));
                newUser.setEmail(request.getParameter("email"));
                String password = request.getParameter(Constant.PASSWORD);
                String securedPassword = DigestUtils.md5Hex(password);
                newUser.setPassword(securedPassword);
                newUser.setIsAdmin(false);
                newUser.setBanned(false);
                USER_DAO.addUser(newUser);
                newUser.setId(USER_DAO.getUserByLoginPassword(newUser.getEmail(), newUser.getPassword()).getId());
                session.setAttribute(Constant.USER, newUser);
                session.setAttribute(Constant.ADMIN, newUser.isAdmin());
                response.sendRedirect(ConstantPageNamesJSPAndAction.HOME_SERVICE);
            }
    }



}
