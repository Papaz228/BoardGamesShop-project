package com.company.boardgamesshop.validator;
import com.company.boardgamesshop.entity.User;
public class Validator {
    private static final String MAIL_REGEX = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private static final String PASSWORD_REGEX = "[a-zA-Z0-9~!@#$%^&*]{6,}";
    public static boolean checkAccess(User user){
        boolean isAccess = false;
        boolean isActivity = user.isBanned();
        if(!isActivity){
            isAccess =true;
        }
        return isAccess;
    }
        public static boolean validateMailWithRegex(String login) {
            return login.matches(MAIL_REGEX);
        }
        public static boolean validatePasswordWithRegex(String password) {
            return password.matches(PASSWORD_REGEX);
        }
}
