package com.cimback.simba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    // private final String SCALA_SERVICE_URL = "http://localhost:9000/scala/process";

    public String  generateSummary( String url,String username) {
        System.out.println("User found: ");
        User user = userRepo.findByUsername(username).orElseThrow();
        System.out.println("User found: ");
        // RestTemplate restTemplate = new RestTemplate();
        // String transformedUrl = restTemplate.postForObject(SCALA_SERVICE_URL, url, String.class);
        String predefinedSummary = "This is a test summary for the given URL: " ;
        Summary summary = new Summary();
        summary.setUrl(url);
        summary.setSummary(predefinedSummary);
        summary.setUser(user);
        summaryRepo.save(summary);
        return predefinedSummary;
    }

    // public List<Summary> getHistory(Long Id) {
       
        
    //     return summaryRepo.findAllByUserIdOrderByCreatedAtDesc(Id));
    // }

}
