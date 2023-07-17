package com.task.HRPORTAL.repo;

import com.task.HRPORTAL.entity.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecurityUserRepo extends JpaRepository<SecurityUser,Integer> {
    Optional< SecurityUser> findByName(String name);
}
