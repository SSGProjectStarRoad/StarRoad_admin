package com.ssg.starroadadmin.user.service;

import com.ssg.starroadadmin.user.entity.Manager;

public interface ManagerService {

    Manager loadUserByUsername(String username);
}
