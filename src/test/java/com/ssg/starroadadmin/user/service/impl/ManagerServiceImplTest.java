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

    @Autowired
    private ManagerServiceImpl managerServiceImpl;

    @Test
    public void test() {
        log.info("test start");
        log.info( managerRepository.findByUsername("store1").toString());
        log.info("test finish");
    }

    @Test
    public void test2() {
        System.out.println(managerServiceImpl.isEmailDuplicate("store1"));
    }

    @Test
    public void test3() {

        managerServiceImpl.createManager("choijh9023@naver.com","9023","123-45-67491","최문석");

    }


}