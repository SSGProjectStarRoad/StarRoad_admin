package com.ssg.starroadadmin.shop.controller;

import com.ssg.starroadadmin.shop.service.ComplexShoppingmallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            // jwt로 받아온 관리자 ID
    ) {
        Long managerId = 5L; // 삭제해야할 부분

        System.out.println("managerId = " + managerId);
        String mallName = complexShoppingmallService.getComplexShoppingmallName(managerId);

        System.out.println("mallName = "    + mallName);
        return mallName;
    }
}
