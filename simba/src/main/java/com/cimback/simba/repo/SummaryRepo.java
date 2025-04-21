package com.cimback.simba.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimback.simba.model.Summary;

public interface SummaryRepo extends JpaRepository<Summary,Long>{
    List<Summary> findAllByUserIdOrderByCreatedDescList(Long username);
}
