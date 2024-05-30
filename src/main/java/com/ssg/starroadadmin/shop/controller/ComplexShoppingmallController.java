package com.ssg.starroadadmin.shop.controller;

import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.shop.service.ComplexShoppingmallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/complexmall")
@RequiredArgsConstructor
public class ComplexShoppingmallController {
    private final ComplexShoppingmallService complexShoppingmallService;

    @GetMapping("/name")
    @ResponseBody
    public String getMallName(
            @AuthenticationPrincipal Manager manager
    ) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴

        String mallName = complexShoppingmallService.getComplexShoppingmallName(email);

        return mallName;
    }

    @GetMapping("/info")
    public String getMallInfo(
            @AuthenticationPrincipal Manager manager
    ) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴

//        String mallInfo = complexShoppingmallService.getComplexShoppingmallInfo(managerId);

        return "/store/mallInfo";
    }

    @GetMapping("/info2")
    public String getMallInfo2(
            @AuthenticationPrincipal Manager manager
    ) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴

//        String mallInfo = complexShoppingmallService.getComplexShoppingmallInfo(managerId);

        return "/store/mallInfo2";
    }
}
