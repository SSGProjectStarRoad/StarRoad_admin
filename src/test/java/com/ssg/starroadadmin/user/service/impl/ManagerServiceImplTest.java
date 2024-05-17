package com.ssg.starroadadmin.user.service.impl;

import com.ssg.starroadadmin.user.repository.ManagerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class ManagerServiceImplTest {

    @Autowired
    private ManagerRepository managerRepository;

    @Test
    public void test() {
        log.info("test start");
        log.info( managerRepository.findByUsername("store1").toString());
        log.info("test finish");
    }


}