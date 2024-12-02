package org.example.ebankify.service.auth;

import org.example.ebankify.entity.User;
import org.example.ebankify.enums.UserRole;
import org.example.ebankify.exception.NotAuthException;
import org.example.ebankify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;

    }

    @Override
    public User login(User loginRequest) {

        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
                return user;
            } else {
                throw new NotAuthException("Invalid credentials");
            }
        } else {
            throw new NotAuthException("Invalid credentials");
        }

    }

    @Override
    public User register(User user) {

        user.setRole(UserRole.USER);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userRepository.save(user);

    }

}
