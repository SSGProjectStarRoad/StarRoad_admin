//package com.ssg.starroadadmin.user.service;
//
//import com.ssg.starroadadmin.user.entity.Manager;
//import com.ssg.starroadadmin.user.enums.Authority;
//import com.ssg.starroadadmin.user.repository.ManagerRepository;
//import com.ssg.starroadadmin.user.service.impl.ManagerServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.annotation.Rollback;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Import(ManagerServiceImpl.class)
//public class ManagerServiceTest {
//
//    @Autowired
//    private ManagerService managerService;
//    @Autowired
//    private ManagerRepository managerRepository;
//
//    @Test
//    @Rollback(false)
//    public void testCreateManager() {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        // Given
//        String uniqueIdentifier = String.valueOf(System.currentTimeMillis());
//        String username = "testuser" + uniqueIdentifier + "@gmail.com";
//        String rawPassword = "password";
//        String businessNumber = "99999999";
//        String name = "Test UserStore";
//        Authority authority = Authority.STORE; // 권한 설정
//
//        // When
//        Manager manager = managerService.createManager(username, rawPassword, businessNumber, name, authority);
//
//        // Then
//        assertThat(manager).isNotNull();
//        assertThat(manager.getId()).isNotNull(); // ID가 생성되었는지 확인
//        assertThat(manager.getUsername()).isEqualTo(username);
//        assertThat(passwordEncoder.matches(rawPassword, manager.getPassword())).isTrue(); // 비밀번호 확인
//        assertThat(manager.getBusinessNumber()).isEqualTo(businessNumber);
//        assertThat(manager.getName()).isEqualTo(name);
//        assertThat(manager.getAuthority()).isEqualTo(authority);
//
//        // Optional: 저장된 데이터를 다시 읽어서 확인
//        Manager savedManager = managerRepository.findById(manager.getId()).orElse(null);
//        assertThat(savedManager).isNotNull();
//        assertThat(savedManager.getUsername()).isEqualTo(username);
//        assertThat(passwordEncoder.matches(rawPassword, savedManager.getPassword())).isTrue(); // 비밀번호 확인
//    }
//}