package com.cimback.simba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.cimback.simba.model.Summary;
import com.cimback.simba.model.User;
import com.cimback.simba.repo.SummaryRepo;
import com.cimback.simba.repo.UserRepo;

@Service
public class SummaryService {
    @Autowired
    private SummaryRepo summaryRepo;

    @Autowired
    private UserRepo userRepo;

    private static final String FASTAPI_SERVICE_URL = "http://localhost:8000/process";

    public String generateSummary(String url, String username) {
        User user = userRepo.findByUsername(username).orElseThrow();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> request = new HttpEntity<>(url, headers);

        try {
            String summaryText = restTemplate.postForObject(FASTAPI_SERVICE_URL, request, String.class);

            Summary summary = new Summary();
            summary.setUrl(url);
            summary.setSummary(summaryText);
            summary.setUser(user);
            summaryRepo.save(summary);
            return summaryText;
        } catch (RestClientException e) {
            // Handle exception
            throw new RuntimeException("Failed to connect to summary service: " + e.getMessage());
        }
    }
}