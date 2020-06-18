package com.payment.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.modals.LoginRequest;
import com.payment.modals.Payment;
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

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JWTUtil jwtTokenUtil;

    @Value("${api.authentication}")
    private String urlAuthentication;

    @Value("${api.order}")
    private String urlOrder;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @GetMapping(value = "/payments/totalmoney/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> paymentTotalMoney(HttpServletRequest request, @PathVariable("username") String username) throws Exception {


        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String paymentInfo = restService.execute(new StringBuilder(urlOrder)
                            .append("/orders/").append("username/").append(username).toString(),
                    HttpMethod.GET, null, null, new ParameterizedTypeReference<String>() {
                    }).getBody();

            return ResponseEntity.ok(paymentInfo);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/payments/{paymentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Payment> payment(HttpServletRequest request, @PathVariable("paymentId") String paymentId) throws Exception {


        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String paymentInfo = restService.execute(new StringBuilder(urlOrder)
                            .append("/orders/").append(paymentId).toString(),
                    HttpMethod.GET, null, null, new ParameterizedTypeReference<String>() {
                    }).getBody();

            JsonNode jsonNode = objectMapper.readTree(paymentInfo);
            Payment payment = new Payment();
            String totalPrice = jsonNode.get("totalPrice").asText();
            payment.setMoney(Double.valueOf(totalPrice));

            return ResponseEntity.ok(payment);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws Exception {

        String authToken = restService.execute(new StringBuilder(urlAuthentication).append("/auth/signin").toString(),
                HttpMethod.POST, null, loginRequest, new ParameterizedTypeReference<String>() {
                }).getBody();

        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth("Bearer " + authToken);

        if (authToken != null) {

            return ResponseEntity.ok("Bearer " + authToken);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/authenticate")
    public Authentication authenticate(@RequestParam(name = "username") String username,
                                       @RequestParam(name = "password") String password) {
        UserDetails userDetail = userDetailsService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

}
