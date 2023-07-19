package com.task.HRPORTAL.controller;

import com.task.HRPORTAL.entity.SecurityUser;
import com.task.HRPORTAL.exception.SecurityExeception;
import com.task.HRPORTAL.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityUserController {

    @Autowired
    private SecurityUserService securityUserService;

    @PostMapping("/addSecurityUsers")
    public ResponseEntity<String> addSecurity(@RequestBody SecurityUser securityUser) throws SecurityExeception {
        return ResponseEntity.ok(securityUserService.addNewUser(securityUser));
    }
}
