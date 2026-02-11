package com.codegnan.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codegnan.dto.UserDto;
import com.codegnan.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

// create user
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create a new User")
	public UserDto createUser(@Valid @RequestBody UserDto userDto) {
		log.info("Creating user with email{} ", userDto.getEmailAddress());
		return userService.saveUser(userDto);
	}

// get user by id
	@GetMapping("/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Fetching User by Id")
	public UserDto getUser(@PathVariable Integer userId) {
		log.info("Fetching User with Id{} ", userId);
		return userService.findByUserId(userId);
	}

// get all users
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Fetching all Users")
	public List<UserDto> getAllUsers() {
		log.info("Fetching all Users");
		return userService.findAllUsers();
	}

// update the user
	@PutMapping("/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Update User By Id")
	public UserDto updateUser(@PathVariable Integer userId, @Valid @RequestBody UserDto userDto) {
		log.info("Deleting User by Id{} ", userId);
		return userService.updateUser(userId, userDto);
	}

// deleting the user
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Deleting user By Id")
	public void deleteUser(@PathVariable Integer userId) {
		log.info("Deleting User by Id{} ", userId);
		userService.deleteByUserId(userId);
	}

//Bulk Create Users
	@PostMapping("/bulk")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "creating multiple users")
	public List<UserDto> createUsers(@RequestBody List<@Valid UserDto> userDtos) {
		log.info("Creating{} users in buld {} ", userDtos.size());
		return userService.saveAllUsers(userDtos);
	}
}
