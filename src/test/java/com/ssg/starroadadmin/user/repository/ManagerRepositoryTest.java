package com.ssg.starroadadmin.user.repository;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("관리자 서비스 테스트")
@SpringBootTest
class ManagerRepositoryTest {

    @Autowired
    private ManagerRepository managerRepository;
}