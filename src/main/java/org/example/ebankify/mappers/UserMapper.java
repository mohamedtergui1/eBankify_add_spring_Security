package org.example.ebankify.mappers;

import org.example.ebankify.dto.user.request.CreateUserRequest;
import org.example.ebankify.dto.user.request.LoginRequest;
import org.example.ebankify.dto.user.request.RegisterRequest;
import org.example.ebankify.dto.user.request.UpdateUserRequest;
import org.example.ebankify.dto.user.respense.UserDtoResponse;
import org.example.ebankify.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserRequest createUserRequest);

    User toEntity(LoginRequest loginRequest);

    User toEntity(RegisterRequest registerRequest);

    User toEntity(UpdateUserRequest updateUserRequest);

    UserDtoResponse toDto(User user);

}