package com.servicesecutity.controllers;

import com.servicesecutity.service.RoleService;
import dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private RoleService roleService;
    
    @GetMapping("role/{username}")
    public ResponseEntity<List<RoleDTO>> findRoleByUsername(
    		@PathVariable("username") String username) {
        List<RoleDTO> roleList = roleService.getAllRoleByUserName(username);
        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }
    
}
