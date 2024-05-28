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
     * @param managerId
     * @param pageable
     * @return
     */
    Page<SearchCouponResponse> getCouponList(Long managerId, Pageable pageable);

    /**
     * 쿠폰 생성
     * 쿠폰을 생성한다.
     *
     * @param request
     * @param managerId
     * @return
     */
    CreateCouponResponse createCoupon(CreateCouponRequest request, Long managerId);

    /**
     * 쿠폰 사용 내역 조회
     *
     * @param managerId
     * @param request
     * @param pageable
     * @return
     */
    Page<UserCouponResponse> getUserCouponList(Long managerId, UserCouponRequest request, Pageable pageable);
}
