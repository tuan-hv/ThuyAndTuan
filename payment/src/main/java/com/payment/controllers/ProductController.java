package com.payment.controllers;

import com.payment.modals.ProductDTO;
import com.payment.services.RestService;
import com.payment.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 12/06/2020 - 11:08 AM
 * @created_by thuynt
 * @since 12/06/2020
 */

@RestController
@RequestMapping(value = "/api")
public class ProductController {
    @Autowired
    private RestService restService;

    @Autowired
    private JWTUtil jwtTokenUtil;

    @Value("${api.authentication}")
    private String urlAuthentication;

    @Value("${api.product}")
    private String urlProduct;

    @PostMapping(value = "/products/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProduct(HttpServletRequest request, @RequestBody ProductDTO productDTO) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String productInfor = restService.execute(new StringBuilder(urlProduct)
                            .append("/products").toString(),
                    HttpMethod.POST, null, productDTO, new ParameterizedTypeReference<String>() {
                    }).getBody();

            return ResponseEntity.ok(productInfor);
        }
        return ResponseEntity.noContent().build();

    }

    @PutMapping(value = "/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editProduct(HttpServletRequest request, @PathVariable("productId") Integer productId,@RequestBody ProductDTO productDTO) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String productInfor = restService.execute(new StringBuilder(urlProduct)
                            .append("/products/").append(productId).toString(),
                    HttpMethod.PUT, null, productDTO, new ParameterizedTypeReference<String>() {
                    }).getBody();
            return ResponseEntity.ok(productInfor);
        }
        return ResponseEntity.noContent().build();
    }





}
