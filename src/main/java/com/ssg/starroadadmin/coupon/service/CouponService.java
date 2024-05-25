package com.ssg.starroadadmin.coupon.service;

import com.ssg.starroadadmin.coupon.dto.SearchCouponResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CouponService {
    Page<SearchCouponResponse> getCouponList(Long managerId, Pageable pageable);
}
