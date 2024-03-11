package com.example.hw3_rest_service.mapper;

import com.example.hw3_rest_service.entity.Claim;
import com.example.hw3_rest_service.entity.User;
import com.example.hw3_rest_service.model.dto.ClaimDto;
import com.example.hw3_rest_service.model.dto.UserDto;

public class MapperUser {
    public UserDto entityToDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public User dtoToEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getEmail());
        return user;
    }
}
