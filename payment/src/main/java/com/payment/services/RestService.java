package com.payment.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RestService {

    private static final Logger logger = LoggerFactory.getLogger(RestService.class);

    @Autowired
    private RestTemplate restTemplate;

    public <T> ResponseEntity<T> execute(String url, HttpMethod method, HttpHeaders headers,
                                         Object body, ParameterizedTypeReference<T> type, Map<String, Object> values) throws Exception{
        try {
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            ResponseEntity<T> response = restTemplate.exchange(
                    url,
                    method,
                    entity,
                    type);
            if(response.getStatusCodeValue() >= HttpStatus.OK.value()
                    && response.getStatusCodeValue() < HttpStatus.MULTIPLE_CHOICES.value()) {
                return response;
            }
            logger.info("Can't get data from API - {}");
        } catch (Exception e) {
            logger.info("Some error has occur when call API - {}", e.getMessage());
            throw new Exception(e.getMessage(), e);
        }
        return ResponseEntity.notFound().build();
    }
}
