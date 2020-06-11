package com.servicesecutity.service;

import com.servicesecutity.entities.User;
import com.servicesecutity.repositories.RoleRepository;
import com.servicesecutity.repositories.UserRepository;
import dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public UserDTO findByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent()) {
			UserDTO userDTO = new UserDTO();
			userDTO.setUserId(user.get().getId());
			userDTO.setUserName(user.get().getUsername());
			userDTO.setEmail(user.get().getEmail());
			userDTO.setPassword(user.get().getPassword());
			userDTO.setFullName(user.get().getName());
			return userDTO;
		} else {
			return new UserDTO();
		}
	}

}
