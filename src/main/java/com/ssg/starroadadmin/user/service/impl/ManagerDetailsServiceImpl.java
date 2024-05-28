package com.ssg.starroadadmin.user.service.impl;

import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ManagerDetailsServiceImpl
//        implements UserDetailsService
        {

    private final ManagerRepository managerRepository;

    public ManagerDetailsServiceImpl(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return managerRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }
}
