package com.codegnan.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codegnan.dto.UserDto;
import com.codegnan.model.User;

@Mapper(componentModel="spring")
public interface UserMapper {
	@Mapping(target="fname", source="firstName")
	@Mapping(target="lname", source="lastName")
	@Mapping(target="emailAddress", source="email")
	public UserDto toDto(User user);
	
	@Mapping(source="fname", target="firstName")
	@Mapping(source="lname", target="lastName")
	@Mapping(source="emailAddress", target="email")
	public User toEntity(UserDto userDto);
}
