package com.ssg.starroadadmin.user.controller;

import com.ssg.starroadadmin.user.repository.ManagerRepository;
import com.ssg.starroadadmin.user.service.impl.ManagerDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpClient;


@Controller
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerRepository managerRepository;
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