package com.company.boardgamesshop.validator;

import com.company.boardgamesshop.entity.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Validator {
    private static final String MAIL_REGEX = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9~!@#$%^&*]{6,}$";
    private static final String NAME_REGEX = "^[A-ZА-Я]{1}[A-zА-я]{2,29}$";
    private static final String PHONE_REGEX = "^[0-9]{11}$";
    private static final String CARD_NUMBER_REGEX = "^[0-9]{16}$";
    private static final String CVV_REGEX = "^[0-9]{3}$";
    private static final String DIGITS_REGEX = "^[0-9]{1,11}$";
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static boolean validateBirthdayWithRegex(String birthday) {
        try {
            FORMAT.parse(birthday);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    public static boolean checkAccess(User user) {
        boolean isAccess = false;
        boolean isActivity = user.isBanned();
        if (!isActivity) {
            isAccess = true;
        }
        return isAccess;
    }

    public static boolean validateMailWithRegex(String login) {
        return !login.matches(MAIL_REGEX);
    }

    public static boolean validatePasswordWithRegex(String password) {
        return !password.matches(PASSWORD_REGEX);
    }

    public static boolean validateNameWithRegex(String name) {
        return !name.matches(NAME_REGEX);
    }

    public static boolean validatePhoneWithRegex(String phoneNumber) {
        return !phoneNumber.matches(PHONE_REGEX);
    }

    public static boolean validateCardNumberWithRegex(String cardNumber) {
        return !cardNumber.matches(CARD_NUMBER_REGEX);
    }

    public static boolean validateCVVWithRegex(String cvv) {
        return !cvv.matches(CVV_REGEX);
    }

    public static boolean validateDigitWithRegex(String price) {
        return !price.matches(DIGITS_REGEX);
    }
}
