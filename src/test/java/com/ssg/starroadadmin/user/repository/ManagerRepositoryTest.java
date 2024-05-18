package com.ssg.starroadadmin.user.repository;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("관리자 서비스 테스트")
@SpringBootTest
@Slf4j
class ManagerRepositoryTest {

    @Autowired
    private ManagerRepository managerRepository;
//  private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(ManagerRepository.class);


    @Test
    public void findtest() {
        System.out.println("@@@@@@@@@@");
        System.out.println(managerRepository.findByUsername("store1").toString());
        System.out.println("@@@@@@@@@@");
}

@Test
    public void findByBusinessNumber() {
    System.out.println(managerRepository.findByBusinessNumber("1111111111"));
}

    @Test
    public void testCreateStoreManager() {

    }
}