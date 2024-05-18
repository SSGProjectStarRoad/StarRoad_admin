package com.ssg.starroadadmin.user.service.impl;

import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import com.ssg.starroadadmin.user.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepository managerRepository;

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
    public Manager loadUserByUsername(String username) {
        return null;
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
}
