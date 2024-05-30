package com.ssg.starroadadmin.user.service.impl;

import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import com.ssg.starroadadmin.user.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepository managerRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public boolean isEmailDuplicate(String username) {

        Optional<Manager> manager = managerRepository.findByUsername(username);
        return manager.isPresent();
    }

    @Override
    public boolean isBusinessNumberDuplicate(String businessNumber) {
        Optional<Manager> manager = managerRepository.findByBusinessNumber(businessNumber);

        return manager.isPresent();
    }

    @Override
    public Manager findById(Long managerId) {
        return managerRepository.findById(managerId)
                .orElseThrow(() -> new UsernameNotFoundException("Manager not found with id: " + managerId));
    }

    @Override
    public Manager createManager(String username, String password, String businessNumber, String name) {
        Manager manager = Manager.builder()
                .name(name)
                .password(password)
                .businessNumber(businessNumber)
                .username(username)
                .authority(Authority.STORE)
                .build();

        return managerRepository.save(manager);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Manager manager = managerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Manager not found with username: " + username));
        return manager;
    }

}
