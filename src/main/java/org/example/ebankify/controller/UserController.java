package org.example.ebankify.controller;

import jakarta.validation.Valid;
import org.example.ebankify.dto.user.request.UpdateUserRequest;
import org.example.ebankify.dto.user.respense.UserDtoResponse;
import org.example.ebankify.entity.User;
import org.example.ebankify.dto.user.request.CreateUserRequest;
import org.example.ebankify.mappers.UserMapper;
import org.example.ebankify.service.user.UserService;
import org.example.ebankify.util.ResponseForme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public ResponseForme<User> getUser(@PathVariable Long id) {
        return new ResponseForme<>(userService.getUserById(id));
    }

    @PostMapping
    public ResponseForme<User> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        return new ResponseForme<>(userService.saveUser(userMapper.toRegisterRequest(createUserRequest)), "user created successfully");
    }

    @PutMapping
    public ResponseForme<User> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        return new ResponseForme<>(userService.updateUser(userMapper.toRegisterRequest(updateUserRequest)), "user updated successfully");
    }

    @DeleteMapping
    public ResponseForme<?> deleteUser(@RequestBody User user) {
        userService.deleteUser(user.getId());
        return new ResponseForme<>("User deleted successfully");
    }

    @GetMapping
    public ResponseEntity<Page<UserDtoResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(userService.getAllUsers(page, size)
                .map(userMapper::toDto), HttpStatus.OK);
    }

}