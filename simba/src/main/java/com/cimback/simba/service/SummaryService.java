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

    private final String SCALA_SERVICE_URL = "http://localhost:9000/scala/process";

    public Summary generateSummary(String username, String url) {
        User user = userRepo.findByUsername(username).orElseThrow();

        RestTemplate restTemplate = new RestTemplate();
        String transformedUrl = restTemplate.postForObject(SCALA_SERVICE_URL, url, String.class);

        Summary summary = new Summary();
        summary.setUrl(url);
        summary.setSummary(transformedUrl);
        summary.setUser(user);
        return summaryRepo.save(summary);
    }

    public List<Summary> getHistory(String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        return summaryRepo.findAllByUserIdOrderByCreatedDescList(user.getId());
    }

}
