package com.main.LocalServer.Services;

import com.main.LocalServer.Entities.User;
import com.main.LocalServer.Exceptions.ValidationException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidationService {
    private static final String EMAIL_EMPTY_ERROR = "Email cannot be empty";
    private static final String EMAIL_PATTERN_ERROR = "Invalid email format";
    public static final Pattern VALID_EMAIL_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public void validateEmail(String email) {
        if (email == null || email.isEmpty()) throw new ValidationException(EMAIL_EMPTY_ERROR);
        if (!VALID_EMAIL_REGEX.matcher(email).matches()) throw new ValidationException(EMAIL_PATTERN_ERROR);
    }
    public void validatePassword(String password) {
        if (password == null || password.isEmpty()) throw new ValidationException("Password cannot be empty");
    }
    public void validateUsername(String username) {
        if (username == null || username.isEmpty()) throw new ValidationException("Username cannot be empty");
    }

    public void validateAllFields (User user) {
        validateUsername(user.getUsername());
        validatePassword(user.getPassword());
        validateEmail(user.getEmail());
    }
}
