package com.cimback.simba.model;

import java.time.LocalDateTime;

public class SummaryDTO {
    private Long id;
    private String url;
    private String summary;
    private LocalDateTime createdAt;

    public SummaryDTO(Summary summary) {
        this.id = summary.getId();
        this.url = summary.getUrl();
        this.summary = summary.getSummary();
        this.createdAt = summary.getCreatedAt();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
