package com.ssg.starroadadmin.user.service;

import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ManagerService extends UserDetailsService {
    Manager createManager(String username, String password,String businessNumber,String name);
    boolean isEmailDuplicate(String username);
    boolean isBusinessNumberDuplicate(String businessNumber);
    Manager findById(Long managerId);
}
