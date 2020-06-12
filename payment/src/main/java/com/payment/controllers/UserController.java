package com.payment.controllers;

import com.payment.services.RestService;
import com.payment.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 12/06/2020 - 1:54 PM
 * @created_by thuynt
 * @since 12/06/2020
 */
@RestController
@RequestMapping(value = "/api")
public class UserController {
    @Autowired
    private RestService restService;

    @Autowired
    private JWTUtil jwtTokenUtil;

    @Value("${api.authentication}")
    private String urlAuthentication;

    @GetMapping(value = "user/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUserByUsername(HttpServletRequest request, @PathVariable("username") String username) throws Exception {
        String authHeader = request.getHeader("Authorization");
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authHeader);
        String userInfo = restService.execute(new StringBuilder(urlAuthentication)
                        .append("/user/").append(username).toString(),
                HttpMethod.GET, header, null, new ParameterizedTypeReference<String>() {
                }, null).getBody();
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping(value = "role/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRoleByUsername(HttpServletRequest request, @PathVariable("username") String username) throws Exception {
        String authHeader = request.getHeader("Authorization");
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authHeader);
        String userInfo = restService.execute(new StringBuilder(urlAuthentication)
                        .append("/role/").append(username).toString(),
                HttpMethod.GET, header, null, new ParameterizedTypeReference<String>() {
                }, null).getBody();
        return ResponseEntity.ok(userInfo);
    }



}
