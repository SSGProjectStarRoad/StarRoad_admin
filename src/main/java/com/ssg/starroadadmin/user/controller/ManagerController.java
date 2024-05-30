package com.ssg.starroadadmin.user.controller;

import com.ssg.starroadadmin.global.config.jwt.TokenProvider;
import com.ssg.starroadadmin.user.dto.LoginRequest;
import com.ssg.starroadadmin.user.dto.LoginResponse;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.service.ManagerService;
import com.ssg.starroadadmin.user.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ManagerController {

    private final ManagerService managerService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/checkDuplicateEmail")
    @ResponseBody
    public Map<String, Boolean> checkDuplicateEmail(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        boolean isDuplicate = managerService.isEmailDuplicate(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        return response;
    }

    @PostMapping("/checkDuplicateBusinessNumber")
    @ResponseBody
    public Map<String, Boolean> checkDuplicateBusinessNumber(@RequestBody Map<String, String> requestBody) {
        String businessNumber = requestBody.get("businessNumber");
        boolean isDuplicate = managerService.isBusinessNumberDuplicate(businessNumber);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        return response;
    }
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        log.info("로그인 시도: username={}", loginRequest.getUsername());
        try {
            // 인증 객체 생성
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            log.info("인증 성공: authenticationToken={}", authenticationToken.getPrincipal().toString());

            // 인증 시도
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("authentication.getPrincipal(): {}", authentication.getPrincipal());
            // 인증된 사용자 정보 가져오기
            Manager manager = (Manager) authentication.getPrincipal();
            log.info("사용자 정보: {}", manager.getUsername());

            // 액세스 토큰 및 리프레시 토큰 생성
            String accessToken = tokenProvider.generateAccessToken(manager);
            String refreshToken = tokenProvider.generateRefreshToken(manager);

            // 리프레시 토큰 저장
            refreshTokenService.createRefreshToken(manager.getId());

            // 토큰 정보를 응답
            LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken);

            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            // 예외 발생 시 로그 출력 및 JSON 형식으로 응답
            log.error("로그인 오류", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }


    // 리프레시 토큰 무효화 엔드포인트!!
    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<Void> logout(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        refreshTokenService.invalidateRefreshToken(refreshToken);
        return ResponseEntity.ok().build();
    }
}