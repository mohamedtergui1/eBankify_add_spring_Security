package org.example.ebankify.service.user;

import org.example.ebankify.entity.User;
import org.example.ebankify.enums.UserRole;
import org.example.ebankify.exception.DeleteUpdateException;
import org.example.ebankify.exception.NotFoundException;
import org.example.ebankify.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .password("password123")
                .age(30)
                .monthlyIncome(5000.0)
                .creditScore(750)
                .role(UserRole.USER)
                .accounts(new ArrayList<>())
                .invoices(new ArrayList<>())
                .loans(new ArrayList<>())
                .build();
    }

    @Test
    void getUserById_WhenUserExists_ReturnsUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getName(), result.getName());
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(testUser.getAge(), result.getAge());
        assertEquals(testUser.getMonthlyIncome(), result.getMonthlyIncome());
        assertEquals(testUser.getCreditScore(), result.getCreditScore());
        assertEquals(testUser.getRole(), result.getRole());
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ThrowsNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void saveUser_HashesPasswordAndSavesUser() {
        User userToSave = User.builder()
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .password("newPassword")
                .age(25)
                .monthlyIncome(4500.0)
                .creditScore(720)
                .role(UserRole.USER)
                .accounts(new ArrayList<>())
                .invoices(new ArrayList<>())
                .loans(new ArrayList<>())
                .build();

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            assertTrue(BCrypt.checkpw("newPassword", savedUser.getPassword()));
            return savedUser;
        });

        User savedUser = userService.saveUser(userToSave);

        assertNotNull(savedUser);
        assertTrue(BCrypt.checkpw("newPassword", savedUser.getPassword()));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void deleteUser_WhenUserExists_DeletesUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_WhenUserDoesNotExist_ThrowsDeleteUpdateException() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(DeleteUpdateException.class, () -> userService.deleteUser(1L));
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void updateUser_WhenUserExists_UpdatesUser() {
        User existingUser = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .password("hashedPassword")
                .age(30)
                .monthlyIncome(5000.0)
                .creditScore(750)
                .role(UserRole.USER)
                .build();

        User updatedUser = User.builder()
                .id(1L)
                .name("John Doe Updated")
                .email("john.updated@example.com")
                .password("") // Empty password should keep the existing one
                .age(31)
                .monthlyIncome(5500.0)
                .creditScore(760)
                .role(UserRole.USER)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(updatedUser);

        assertNotNull(result);
        assertEquals("hashedPassword", result.getPassword());
        assertEquals("John Doe Updated", result.getName());
        assertEquals(31, result.getAge());
        assertEquals(5500.0, result.getMonthlyIncome());
        assertEquals(760, result.getCreditScore());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_WhenUserDoesNotExist_ThrowsNotFoundException() {
        User nonExistentUser = User.builder()
                .id(999L)
                .build();

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.updateUser(nonExistentUser));
    }

    @Test
    void getUserByEmail_WhenUserExists_ReturnsUser() {
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(testUser));

        User result = userService.getUserByEmail("john.doe@example.com");

        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(testUser.getName(), result.getName());
        assertEquals(testUser.getAge(), result.getAge());
        assertEquals(testUser.getMonthlyIncome(), result.getMonthlyIncome());
        assertEquals(testUser.getCreditScore(), result.getCreditScore());
    }

    @Test
    void getUserByEmail_WhenUserDoesNotExist_ThrowsNotFoundException() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserByEmail("nonexistent@example.com"));
    }

    @Test
    void getAllUsers_ReturnsPageOfUsers() {
        User secondUser = User.builder()
                .id(2L)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .password("password456")
                .age(25)
                .monthlyIncome(4500.0)
                .creditScore(720)
                .role(UserRole.USER)
                .build();

        Page<User> expectedPage = new PageImpl<>(Arrays.asList(testUser, secondUser));
        Pageable pageable = PageRequest.of(0, 10);

        when(userRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<User> result = userService.getAllUsers(0, 10);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(testUser.getName(), result.getContent().get(0).getName());
        assertEquals(secondUser.getName(), result.getContent().get(1).getName());
    }
}