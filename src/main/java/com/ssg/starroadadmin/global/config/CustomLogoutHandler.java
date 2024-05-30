package com.ssg.starroadadmin.global.config;

import com.ssg.starroadadmin.user.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final RefreshTokenService refreshTokenService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String refreshToken = request.getHeader("refreshToken"); // 클라이언트에서 리프레시 토큰을 헤더로 전달해야 함
        if (refreshToken != null) {
            refreshTokenService.invalidateRefreshToken(refreshToken);
        }
    }
}
