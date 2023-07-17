package com.task.HRPORTAL.security;

import com.task.HRPORTAL.entity.SecurityUser;
import com.task.HRPORTAL.repo.SecurityUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class SecurityDetails implements UserDetailsService {
    @Autowired
    SecurityUserRepo securityUserRepo;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<SecurityUser> securityUser= securityUserRepo.findByName(name);
//        return user.map(com.task.HRPORTAL.security.UserDetails::new).orElseThrow();
        return securityUser.map(com.task.HRPORTAL.security.UserDetails::new).orElseThrow();
    }
}
