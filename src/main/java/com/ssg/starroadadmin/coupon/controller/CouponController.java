package com.ssg.starroadadmin.coupon.controller;

import com.ssg.starroadadmin.coupon.dto.*;
import com.ssg.starroadadmin.coupon.service.CouponService;
import com.ssg.starroadadmin.user.entity.Manager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    /**
     * 쿠폰 목록 조회
     * @param model
     * @param pageable
     * @return
     */
    @GetMapping("/list")
    public String getCouponList(
            Model model,
            @AuthenticationPrincipal Manager manager,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<SearchCouponResponse> couponList = couponService.getCouponList(manager.getUsername(), pageable);

        model.addAttribute("couponList", couponList);
        model.addAttribute("pages", couponList);
        return "/coupon/couponList";
    }

    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<CreateCouponResponse> createCoupon(
            @AuthenticationPrincipal Manager manager,
            @RequestBody CreateCouponRequest request) {

        try {
            CreateCouponResponse savedCoupon = couponService.createCoupon(request, manager.getUsername());
            return new ResponseEntity<>(savedCoupon, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/history")
    public String getUserCouponList(
            Model model,
            @AuthenticationPrincipal Manager manager,
            @ModelAttribute("userCouponRequest") UserCouponRequest request,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<UserCouponResponse> userCouponList = couponService.getUserCouponList(manager.getUsername(), request, pageable);

        model.addAttribute("userCouponList", userCouponList);
        model.addAttribute("pages", userCouponList);

        return "couponHistoryList";
    }
}
