//package com.ssg.starroadadmin.user.controller;
//
//import com.ssg.starroadadmin.global.config.JwtTokenProvider;
//import com.ssg.starroadadmin.user.entity.Manager;
//import com.ssg.starroadadmin.user.service.ManagerService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@Controller
//@RequestMapping("/manager")
//@RequiredArgsConstructor
//public class ManagerController {
//
//    private final ManagerService managerService;
//    private final AuthenticationManager authenticationManager;
//    private final JwtTokenProvider jwtTokenProvider;
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    @PostMapping("/checkDuplicateEmail")
//    @ResponseBody
//    public Map<String, Boolean> checkDuplicateEmail(@RequestBody Map<String, String> requestBody) {
//        String email = requestBody.get("email");
//        boolean isDuplicate = managerService.isEmailDuplicate(email);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("isDuplicate", isDuplicate);
//        return response;
//    }
//
//    @PostMapping("/checkDuplicateBusinessNumber")
//    @ResponseBody
//    public Map<String, Boolean> checkDuplicateBusinessNumber(@RequestBody Map<String, String> requestBody) {
//        String businessNumber = requestBody.get("businessNumber");
//        boolean isDuplicate = managerService.isBusinessNumberDuplicate(businessNumber);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("isDuplicate", isDuplicate);
//        return response;
//    }
//
//    @GetMapping("/login")
//    public String login() {
//        return "login"; // login.html의 경로를 반환합니다.
//    }
//
//    @GetMapping("/signup")
//    public String signup() {
//        System.out.println("goto signup@@@@");
//        return "signup"; // signup.html의 경로를 반환합니다.
//    }
//
//    @PostMapping("/login")
//    @ResponseBody
//    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password));
//
//            String token = jwtTokenProvider.generateToken(authentication);
//
//            Map<String, String> response = new HashMap<>();
//            response.put("token", token);
//
//            return ResponseEntity.ok(response);
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(401).body("Invalid username or password");
//        }
//    }
//
//
//}
