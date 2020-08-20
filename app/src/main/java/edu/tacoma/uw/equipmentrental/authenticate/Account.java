package edu.tacoma.uw.equipmentrental.authenticate;

import android.widget.Toast;

import java.util.regex.Pattern;

public class Account {



    private String mEmail;
    private String mPassword;
    private final static int PASSWORD_LEN = 6;

    SignInActivity mSignInActivity;

    /**
     * Email validation pattern.
     */
    public static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public Account(String email, String pwd) {
        setEmail(email);
        setPassword(pwd);
        mSignInActivity = new SignInActivity();
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String pwd) {
        if (!isValidPassword(pwd)) {
            throw new IllegalArgumentException("Invalid password");
//            if (pwd.length() < 6){
//                throw new IllegalArgumentException("Password should be at least 6 characters!!");
//            } else {
//                throw new IllegalArgumentException("Invalid password");
//            }
        }
        this.mPassword = pwd;
    }

    /**
     * Validates if the given input is a valid email address.
     *
     * @param email        The email to validate.
     * @return {@code true} if the input is a valid email. {@code false} otherwise.
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }


    /**
     * Validates if the given password is valid.
     * Valid password must be at last 6 characters long
     * with at least one digit and one symbol.
     *
     * @param password        The password to validate.
     * @return {@code true} if the input is a valid password.
     * {@code false} otherwise.
     */
    public static boolean isValidPassword(String password) {
        boolean foundDigit = false, foundSymbol = false;
        if (password == null ||
                password.length() < PASSWORD_LEN) {
            return false;
        }
        for (int i=0; i<password.length(); i++) {
            if (Character.isDigit(password.charAt(i)))
                foundDigit = true;
            if (!Character.isLetterOrDigit(password.charAt(i)))
                foundSymbol = true;
        }
        return foundDigit && foundSymbol;
    }
}
