package com.ssg.starroadadmin.coupon.controller;

import com.ssg.starroadadmin.coupon.dto.SearchCouponResponse;
import com.ssg.starroadadmin.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
     private final CouponService couponService;

    @GetMapping("/list")
    public String getCouponList(
            Model model,
            // jwt로 받아온 관리자 ID
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Long managerId = 5L; // 삭제해야할 부분

        Page<SearchCouponResponse> couponList = couponService.getCouponList(managerId, pageable);

        model.addAttribute("couponList", couponList);
        model.addAttribute("pages", couponList);
        return "/coupon/couponList";
    }
}
