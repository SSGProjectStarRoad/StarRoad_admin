package com.ssg.starroadadmin.user.repository;

import com.ssg.starroadadmin.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByManagerId(Long managerId);
    Optional<RefreshToken> findByToken(String refreshToken);
    void deleteByToken(String refreshToken);

    // 테스트 용
    @Transactional
    void deleteByManagerId(Long managerId);
}

// findByManager_Id(Long Id) 메서드를 사용하는 것은
// 일대일 관계를 통해 특정 사용자의 리프레시 토큰을 쉽게 찾기 위함
// JPA에서는 연관된 엔티티의 필드를 조회하는 방법으로
// Manager 엔티티의 id 값을 이용하여 해당 Manager의 RefreshToken 조회 가능