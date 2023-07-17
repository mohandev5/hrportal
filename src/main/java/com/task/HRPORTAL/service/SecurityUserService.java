package com.task.HRPORTAL.service;

import com.task.HRPORTAL.entity.SecurityUser;
import com.task.HRPORTAL.repo.SecurityUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserService {
    @Autowired
    private SecurityUserRepo securityUserRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String addNewUser(SecurityUser user){
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        securityUserRepo.save(user);
        return "user created successfully";
    }
}
