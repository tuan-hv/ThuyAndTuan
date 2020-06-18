package com.payment.services;

import com.payment.modals.LoginRequest;
import com.payment.utils.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	public BCryptPasswordEncoder encoder;
	
	@Autowired
	private RestService restService;
	
	@Autowired
	private HttpServletRequest request;

	@Value("${api.authentication}")
	private String urlAuthentication;
	@Override
	public UserDetails loadUserByUsername(String username) {
		try {
			String password = request.getParameter("password");

			String token = restService.execute(new StringBuilder(urlAuthentication).append("/auth/signin").toString(),
					HttpMethod.POST, null, new LoginRequest(username, password), new ParameterizedTypeReference<String>() {
					}).getBody();
			
			HttpHeaders header = new HttpHeaders();
			header.setBearerAuth(token);

			List<GrantedAuthority> grantList = Arrays.asList(
					new SimpleGrantedAuthority("ROLE_ADMIN"),
					new SimpleGrantedAuthority("ROLE_PM"),
					new SimpleGrantedAuthority("ROLE_USER")
			);

			return new AppUserDetails(username, encoder.encode(password), null, token, grantList);
		} catch (Exception e) {
			throw new UsernameNotFoundException("Username not found!", e);
		}
	}

}
