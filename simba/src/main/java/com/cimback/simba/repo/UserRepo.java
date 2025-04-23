package com.cimback.simba.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimback.simba.model.User;

public interface UserRepo extends JpaRepository<User,Long>{
    Optional<User> findByUsername(String username);
}

