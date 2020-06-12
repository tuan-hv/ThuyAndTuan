package com.payment.controllers;

import com.payment.modals.LoginRequest;
import com.payment.services.RestService;
import com.payment.services.UserDetailServiceImpl;
import com.payment.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(value = "/api")
public class PaymentController {

    @Autowired
    private RestService restService;

    @Autowired
    private JWTUtil jwtTokenUtil;

    @Value("${api.authentication}")
    private String urlAuthentication;

    @Value("${api.order}")
    private String urlOrder;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @GetMapping(value = "/payments/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> payment(HttpServletRequest request, @PathVariable("username") String username) throws Exception {


        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String paymentInfo = restService.execute(new StringBuilder(urlOrder)
                            .append("/orders/").append("username/").append(username).toString(),
                    HttpMethod.GET, null, null, new ParameterizedTypeReference<String>() {
                    }, null).getBody();

            return ResponseEntity.ok(paymentInfo);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws Exception {

        String authToken = restService.execute(new StringBuilder(urlAuthentication).append("/auth/signin").toString(),
                HttpMethod.POST, null, loginRequest, new ParameterizedTypeReference<String>() {
                }, null).getBody();

//        String authToken = jwtTokenUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth("Bearer " + authToken);

        if (authToken != null) {

            return ResponseEntity.ok("Bearer " + authToken);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/authenticate")
    public Authentication authenticate(@RequestParam(name = "username") String username,
                                       @RequestParam(name = "password") String password)
            throws Exception {
        UserDetails userDetail = userDetailsService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

}
