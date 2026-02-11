package com.codegnan.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.codegnan.dto.UserDto;
import com.codegnan.exceptions.RecordAlreadyExistsException;
import com.codegnan.exceptions.UserNotFoundException;
import com.codegnan.mapper.UserMapper;
import com.codegnan.model.User;
import com.codegnan.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	
	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
		super();
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	@Override
	public UserDto saveUser(UserDto userDto) {
		log.info("Saving user with email : ",userDto.getEmailAddress());
			Optional<User> existingUser = userRepository.findByEmail(userDto.getEmailAddress());
			if(existingUser.isPresent()) {
				throw new RecordAlreadyExistsException("User with email already exists: "+userDto);
			}
			User user = userMapper.toEntity(userDto);
			User savedUser = userRepository.save(user);
		return userMapper.toDto(savedUser);
	}

	@Override
	public UserDto findByUserId(Integer userId) {
		log.info("Fetching user By Id{} ",userId);
		User user = userRepository.findById(userId)
				.orElseThrow(()-> new UserNotFoundException("User Not Found In DB "));
		return userMapper.toDto(user);
	}

	@Override
	public List<UserDto> findAllUsers() {
		log.info("Fetching all users");
		return userRepository.findAll().stream()
				.map(userMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public UserDto updateUser(Integer userId, UserDto userDto) {
		log.info("Updating User Id{} ",userId);
		User existingUser = userRepository.findById(userId)
				.orElseThrow(()-> new UserNotFoundException("User Not Found In DB : "+userId));
		existingUser.setFirstName(userDto.getFname());
		existingUser.setLastName(userDto.getLname());
		existingUser.setEmail(userDto.getEmailAddress());
		existingUser.setPhone(userDto.getPhone());
		User updatedUser = userRepository.save(existingUser);
		return userMapper.toDto(updatedUser);
	}

	@Override
	public void deleteByUserId(Integer userId) {
		log.info("Deleting the user id{} ", userId);
		User existingUser = userRepository.findById(userId)
				.orElseThrow(()-> new UserNotFoundException("User Not Found In DB : "+userId));
		userRepository.deleteById(userId);
	}

	@Override
	public List<UserDto> saveAllUsers(List<UserDto> userDtos) {
		log.info("Saving{} users in bulk{} ", userDtos.size());
		// duplicate email checks for each incoming user.
		for(UserDto dto : userDtos) {
			Optional<User> existingUser = userRepository.findByEmail(dto.getEmailAddress());
			if(existingUser.isPresent()) {
				throw new RecordAlreadyExistsException("User with email already exists: "+dto.getEmailAddress());
			}
		}
		
		//map to dto
		List<User>users=userDtos.stream()
				.map(userMapper::toEntity).collect(Collectors.toList());
		
		//save all
		List<User>savedUsers = userRepository.saveAll(users);
		// map entity to dto
		
		return savedUsers.stream()
				.map(userMapper::toDto).collect(Collectors.toList());
	}

}
