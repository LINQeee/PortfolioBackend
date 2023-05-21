package com.main.LocalServer.Services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.main.LocalServer.Exceptions.MissingUserException;
import com.main.LocalServer.Exceptions.UsedEmailException;
import com.main.LocalServer.Exceptions.WrongInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.main.LocalServer.Entities.User;
import com.main.LocalServer.Repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ValidationService validationService;
    public void createUser(User user) {
        validationService.validateAllFields(user);
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new UsedEmailException("User with email: " + user.getEmail() + " already exists");
        }
        userRepository.save(user);

    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).get();
    }

    public void checkUserPassword(String email, String password) {
		Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isEmpty()) {
            throw new MissingUserException("User with email: " + email + " does not exist");
        }
        else if(!userOptional.get().getPassword().equals(password)) {
            throw new WrongInputException("Password does not match");
        }
    }

    public void checkUserCode(String email, String code) {
        if(!code.equals(userRepository.findUserByEmail(email).get().getCode())) {
            throw new WrongInputException("Code does not match");
        }
    }

    public void updateCode(String email, String code) {
        User user = userRepository.findUserByEmail(email).get();
        user.setCode(code);
        userRepository.save(user);
    }

    public void updatePassword(String email, String password) {
        User user = userRepository.findUserByEmail(email).get();
        user.setPassword(password);
        userRepository.save(user);
    }
}
