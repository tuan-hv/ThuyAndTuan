package com.servicesecutity.controllers;

import com.servicesecutity.service.UserService;
import dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;


	@GetMapping("user/{username}")
	//@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<UserDTO> findByUsername(@PathVariable("username") String username) {
		UserDTO userDTO = userService.findByUsername(username);
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}
	
}
