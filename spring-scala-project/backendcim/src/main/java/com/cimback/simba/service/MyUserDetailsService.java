package com.cimback.simba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cimback.simba.model.User;
import com.cimback.simba.model.UserPrinciple;
import com.cimback.simba.repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
    
    @Autowired
    private  UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return new UserPrinciple(user);

    }
}
