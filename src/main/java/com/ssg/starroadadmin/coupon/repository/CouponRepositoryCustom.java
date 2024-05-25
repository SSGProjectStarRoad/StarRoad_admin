package com.ssg.starroadadmin.coupon.repository;

import com.ssg.starroadadmin.coupon.dto.SearchCouponResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CouponRepositoryCustom {
    Page<SearchCouponResponse> findAllByManagerId(Long managerId, Pageable pageable);
}
