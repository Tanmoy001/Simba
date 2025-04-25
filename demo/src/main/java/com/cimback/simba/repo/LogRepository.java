package com.cimback.simba.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimback.simba.model.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findAllByUserIdOrderByCreatedAtDesc(Long user_Id);
}