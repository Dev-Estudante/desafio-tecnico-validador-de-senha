package com.sensedia.sample.password.utils;

import java.util.regex.Pattern;

public class PasswordRegexValidator {

    private static final Pattern UPPERCASE = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern DIGIT = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL = Pattern.compile("[!@#$%^&*()\\-_=+]");


    public static boolean hasUppercase(String password) {
        return UPPERCASE.matcher(password).find();
    }

    public static boolean hasLowercase(String password) {
        return LOWERCASE.matcher(password).find();
    }

    public static boolean hasDigits(String password) {
        return DIGIT.matcher(password).find();
    }

    public static boolean hasSpecialCharacters(String password) {
        return SPECIAL.matcher(password).find();
    }

}
