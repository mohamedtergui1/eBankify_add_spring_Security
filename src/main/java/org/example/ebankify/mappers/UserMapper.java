package org.example.ebankify.mappers;

import org.example.ebankify.dto.user.request.CreateUserRequest;
import org.example.ebankify.dto.auth.LoginRequest;
import org.example.ebankify.dto.auth.RegisterRequest;
import org.example.ebankify.dto.user.request.UpdateUserRequest;
import org.example.ebankify.dto.user.respense.UserDtoResponse;
import org.example.ebankify.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toRegisterRequest(CreateUserRequest createUserRequest);

    User toRegisterRequest(LoginRequest loginRequest);

    User toRegisterRequest(RegisterRequest registerRequest);

    User toRegisterRequest(UpdateUserRequest updateUserRequest);

    UserDtoResponse toDto(User user);

}