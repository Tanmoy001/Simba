package com.cimback.simba.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cimback.simba.model.User;

public interface UserRepo extends JpaRepository<User,Long>{
    User findByUsername(String username);
}
