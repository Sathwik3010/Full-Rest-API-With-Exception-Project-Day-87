package com.codegnan.service;

import java.util.List;

import com.codegnan.dto.UserDto;

public interface UserService {
public UserDto saveUser(UserDto userDto);

public UserDto findByUserId(Integer userId);

public List<UserDto> findAllUsers();

public UserDto updateUser(Integer userId, UserDto userDto);

public void deleteByUserId(Integer userId);

List<UserDto> saveAllUsers(List<UserDto> userDtos);
}
