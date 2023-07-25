package com.task.HRPORTAL.service;

import com.task.HRPORTAL.entity.SecurityUser;
import com.task.HRPORTAL.exception.SecurityExeception;
import com.task.HRPORTAL.repo.SecurityUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserService {
    @Autowired
    private SecurityUserRepo securityUserRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String addNewUser(SecurityUser user) throws SecurityExeception {
        try {
            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
            securityUserRepo.save(user);
            return "user created successfully";
        }catch(Exception ex){
            throw new SecurityExeception("error occurred while adding an security user"+ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
