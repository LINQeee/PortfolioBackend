package com.main.LocalServer.Controllers;

import com.main.LocalServer.Entities.Player;
import com.main.LocalServer.Exceptions.MissingUserException;
import com.main.LocalServer.Exceptions.UsedEmailException;
import com.main.LocalServer.Exceptions.ValidationException;
import com.main.LocalServer.Exceptions.WrongInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.main.LocalServer.Entities.User;
import com.main.LocalServer.Services.UserService;
import com.main.LocalServer.Entities.Error;

@RestController
@RequestMapping(path = "/api/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping("/new-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return ResponseEntity.ok("Your account has been created successfully!");
        } catch (UsedEmailException e) {
            return ResponseEntity.internalServerError().body(new Error(e.getMessage(), Error.ErrorType.ALREADY_EXISTS.toString()));
        } catch (ValidationException e) {
            return ResponseEntity.internalServerError().body(new Error(e.getMessage(), Error.ErrorType.VALIDATION_ERROR.toString()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new Error(e.getMessage(), Error.ErrorType.UNEXPECTED.toString()));
        }

    }

    @PostMapping("/check-user-password")
    public ResponseEntity<?> checkUserPassword(@RequestBody User user) {
        try {
            userService.checkUserPassword(user.getEmail(), user.getPassword());
            return ResponseEntity.ok("Successfully logged in!");
        } catch (MissingUserException e) {
            return ResponseEntity.internalServerError().body(new Error(e.getMessage(), Error.ErrorType.NOT_FOUND_USER.toString()));
        } catch (WrongInputException e) {
            return ResponseEntity.internalServerError().body(new Error(e.getMessage(), Error.ErrorType.WRONG_INPUT.toString()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new Error(e.getMessage(), Error.ErrorType.UNEXPECTED.toString()));
        }
    }

    @PostMapping("/check-user-code")
    public ResponseEntity<?> checkUserCode(@RequestBody User user) {
        try {
            userService.checkUserCode(user.getEmail(), user.getCode());
            return ResponseEntity.ok("Code correct!");
        } catch (WrongInputException e) {
            return ResponseEntity.internalServerError().body(new Error(e.getMessage(), Error.ErrorType.WRONG_INPUT.toString()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new Error(e.getMessage(), Error.ErrorType.UNEXPECTED.toString()));
        }
    }

    @PostMapping("/change-psw")
    public ResponseEntity<?> changePassword(@RequestBody User user) {
        try {
            userService.updatePassword(user.getEmail(), user.getPassword());
            return ResponseEntity.ok("Password changed successfully!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new Error(e.getMessage(), Error.ErrorType.UNEXPECTED.toString()));
        }
    }

    @PostMapping("/get-user")
    public ResponseEntity<?> getUser(@RequestBody String email) {
        try {
            return ResponseEntity.ok(userService.getUserByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new Error(e.getMessage(), Error.ErrorType.UNEXPECTED.toString()));
        }
    }

    @GetMapping("/test")
    public Player test() {
        return new Player(242, "Danya", 58);
    }

}
