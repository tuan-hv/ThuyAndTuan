package com.payment.controllers;

import com.payment.services.RestService;
import com.payment.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/payment")
    public String payment() throws Exception {
        String authToken = jwtTokenUtil.getJwtTokenFromSecurityContext();
        /*String authToken2 = restService.execute(new StringBuilder(urlAuthentication).append("/auth/signin").toString(),
                HttpMethod.GET, null, null, new ParameterizedTypeReference<String>() {}).getBody();*/
        HttpHeaders header = new HttpHeaders();
        header.setBearerAuth(authToken);

        /*String ordersDTO = restService.execute(new StringBuilder(urlOrder).append("/orders").toString(),
                HttpMethod.GET, header, null, new ParameterizedTypeReference<String>() {}).getBody();*/

        return "";
    }
}
