package com.main.LocalServer.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CodeService {

    private final EmailService emailService;
    private final UserService userService;

    private final Random random = new Random();

    public String getRandomNumberString() {
        int number = random.nextInt(999999);

        return String.format("%06d", number);
    }

    public void sendCode(String email) {
        userService.updateCode(email, getRandomNumberString());
        emailService.sendEmail(email, "Verification code", "Your verification code is: " + userService.getUserByEmail(email).getCode());
    }

}
