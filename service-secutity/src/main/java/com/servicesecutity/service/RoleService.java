package com.servicesecutity.service;

import com.servicesecutity.entities.Role;
import com.servicesecutity.repositories.RoleRepository;
import dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;


	public List<RoleDTO> getAllRoleByUserName(String username) {
		List<Role> roles = roleRepository.findByUser_UserName(username);
		List<RoleDTO> roleDTOs = new ArrayList<>();
		roles.forEach(p -> {
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setId(p.getId());
			roleDTO.setName(p.getName());
			roleDTOs.add(roleDTO);
		});
		return roleDTOs;
	}

}
