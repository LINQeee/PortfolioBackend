package com.main.LocalServer.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import com.main.LocalServer.Entities.User;
import com.main.LocalServer.Repositories.UserRepository;
import com.main.LocalServer.Services.ValidationService;
import com.main.LocalServer.Services.UserService;
import com.main.LocalServer.Exceptions.UsedEmailException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    ValidationService validationService;

    @InjectMocks
    UserService userService;

    User testUser;

    @BeforeEach
    public void setup() {
        testUser = new User();
        testUser.setEmail("test@email.com");
    }

    @Test
    public void testCreateUser() {
        when(userRepository.findUserByEmail(any(String.class))).thenReturn(Optional.empty());
        userService.createUser(testUser);
        verify(validationService, times(1)).validateAllFields(testUser);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    public void testCreateUserThrowsUsedEmailException() {
        when(userRepository.findUserByEmail(any(String.class))).thenReturn(Optional.of(testUser));
        assertThrows(UsedEmailException.class, () -> userService.createUser(testUser));
    }

    @Test
    public void testGetUserByEmail() {
        when(userRepository.findUserByEmail(any(String.class))).thenReturn(Optional.of(testUser));
        User returnedUser = userService.getUserByEmail("test@email.com");
        assertEquals(testUser, returnedUser, "Returned user should be the test user");
    }
}
