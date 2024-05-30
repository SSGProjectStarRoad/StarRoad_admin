package com.ssg.starroadadmin.coupon.service;

import com.ssg.starroadadmin.coupon.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CouponService {
    /**
     * 쿠폰 목록 조회
     * 로그인한 아이디를 기준으로 쿠폰 목록을 조회한다.
     *
     * @param email
     * @param pageable
     * @return
     */
    Page<SearchCouponResponse> getCouponList(String email, Pageable pageable);

    /**
     * 쿠폰 생성
     * 쿠폰을 생성한다.
     *
     * @param request
     * @param email
     * @return
     */
    CreateCouponResponse createCoupon(CreateCouponRequest request, String email);

    /**
     * 쿠폰 사용 내역 조회
     *
     * @param email
     * @param request
     * @param pageable
     * @return
     */
    Page<UserCouponResponse> getUserCouponList(String email, UserCouponRequest request, Pageable pageable);
}
