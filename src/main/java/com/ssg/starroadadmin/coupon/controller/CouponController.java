package com.ssg.starroadadmin.coupon.controller;

import com.ssg.starroadadmin.coupon.dto.*;
import com.ssg.starroadadmin.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<CreateCouponResponse> createCoupon(
            // jwt로 받아온 관리자 ID
            @RequestBody CreateCouponRequest request) {
        Long managerId = 5L; // 삭제해야할 부분

        try {
            CreateCouponResponse savedCoupon = couponService.createCoupon(request, managerId);
            return new ResponseEntity<>(savedCoupon, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/history")
    public String getUserCouponList(
            Model model,
            // jwt로 받아온 관리자 ID
            @ModelAttribute("userCouponRequest") UserCouponRequest request,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long managerId = 8L; // 삭제해야할 부분

        Page<UserCouponResponse> userCouponList = couponService.getUserCouponList(managerId, request, pageable);

        model.addAttribute("userCouponList", userCouponList);
        model.addAttribute("pages", userCouponList);

        return "couponHistoryList";
    }
}
