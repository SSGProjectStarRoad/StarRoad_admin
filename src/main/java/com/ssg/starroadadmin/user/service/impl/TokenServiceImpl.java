package com.ssg.starroadadmin.user.service.impl;

import com.ssg.starroadadmin.global.config.jwt.TokenProvider;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.service.ManagerService;
import com.ssg.starroadadmin.user.service.RefreshTokenService;
import com.ssg.starroadadmin.user.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final ManagerService managerService;

// RefreshToken 엔티티는 Manager 엔티티와 일대일 관계
// manager 필드를 통해 manager 엔티티를 참조
// managerId를 직접 가져오는 대신, manager 객체를 통해 ID를 가져와야
    @Override
    public String createNewAccessToken(String refreshToken) {

        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)){
            throw new IllegalArgumentException("Unexpected token");
        }

        // RefreshToken 객체를 찾고, 연결된 Manager 객체의 ID를 가져오기
        Manager manager = refreshTokenService.findByRefreshToken(refreshToken).getManager();
        // Manager ID로 manager 객체를 조회하지 않고, 바로 manager 객체를 사용하기
        return tokenProvider.generateToken(manager, Duration.ofHours(2));
    }
}

// 전달받은 리프레시 토큰으로 토큰 유효성 검사를 진행
// 유효한 토큰일 때 리프레스 토큰으로 사용자 id를 찾음
// 토큰 제공자의 generateToken 메서드 호출해 새로운 액세스 토큰 생성