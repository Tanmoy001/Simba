package com.cimback.simba.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import com.cimback.simba.model.User;
import com.cimback.simba.repo.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JWTservice jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public User register(User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return user;
    }
 
    public String login(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
       if (authentication.isAuthenticated()) {
        return jwtService.generateToken(user.getUsername());
     } else {
        return "fail";
    }
    }
}

