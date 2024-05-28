package com.ssg.starroadadmin.coupon.repository;

import com.ssg.starroadadmin.coupon.dto.UserCouponRequest;
import com.ssg.starroadadmin.coupon.dto.UserCouponResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponHistoryRepositoryCustom {
    Page<UserCouponResponse> findAllByCondition(UserCouponRequest request, Pageable pageable);
}
