package com.ssg.starroadadmin.user.controller;

import com.ssg.starroadadmin.user.repository.ManagerRepository;
import com.ssg.starroadadmin.user.service.ManagerService;
import com.ssg.starroadadmin.user.service.impl.ManagerDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/checkDuplicateEmail")
    @ResponseBody
    public Map<String, Boolean> checkDuplicateEmail(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        boolean isDuplicate = managerService.isEmailDuplicate(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        return response;
    }

//     사업자 번호 중복 확인 엔드포인트
    @PostMapping("/checkDuplicateBusinessNumber")
    @ResponseBody
    public Map<String, Boolean> checkDuplicateBusinessNumber(@RequestBody Map<String, String> requestBody) {
        String businessNumber = requestBody.get("businessNumber");
        boolean isDuplicate = managerService.isBusinessNumberDuplicate(businessNumber);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        return response;
    }

    // 로그인 페이지로 이동하는 요청을 처리하는 메서드
    @GetMapping("/login")
    public String login() {
        return "signin"; // signin.html의 경로를 반환합니다.
    }

    // 회원가입 페이지로 이동하는 요청을 처리하는 메서드
    @GetMapping("/signup")
    public String signup() {
        System.out.println("goto signup@@@@");
        return "signup"; // signup.html의 경로를 반환합니다.
    }
}