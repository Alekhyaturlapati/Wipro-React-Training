package com.apigateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    public String getContactInfo(String userId) {
        // This is load-balanced because of @LoadBalanced RestTemplate
        String url = "http://contact-service/contact/" + userId;
        return restTemplate.getForObject(url, String.class);
    }
}
