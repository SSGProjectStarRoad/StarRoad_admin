package com.ssg.starroadadmin.user.service;

import com.ssg.starroadadmin.user.entity.Manager;

public interface ManagerService {
    Manager createManager(String username, String password,String businessNumber,String name);
    Manager loadUserByUsername(String username);
    boolean isEmailDuplicate(String username);
    boolean isBusinessNumberDuplicate(String businessNumber);
}
