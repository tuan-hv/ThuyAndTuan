package com.payment.controllers;

import com.payment.services.RestService;
import com.payment.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class PaymentController {

    @Autowired
    private RestService restService;

    @Autowired
    private JWTUtil jwtTokenUtil;

    @Value("${api.authentication}")
    private String urlAuthentication;

    @Value("${api.api.order}")
    private String urlOrder;

    @GetMapping("/payments")
    public String payment() throws Exception {
        String authToken = jwtTokenUtil.getJwtTokenFromSecurityContext();
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);

        String ordersDTO = restService.execute(new StringBuilder(urlOrder).append("/orders").toString(),
                HttpMethod.GET, header, null, new ParameterizedTypeReference<String>() {}).getBody();

        return "";
    }

}
