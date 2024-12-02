package org.example.ebankify.service.user;

import org.example.ebankify.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
    User getUserById(Long id);

    User saveUser(User user);

    void deleteUser(Long id);

    User updateUser(User user);

    User getUserByEmail(String email);

    Page<User> getAllUsers(int page, int size);

}
