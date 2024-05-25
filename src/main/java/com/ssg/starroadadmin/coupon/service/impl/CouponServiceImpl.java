package com.ssg.starroadadmin.coupon.service.impl;

import com.ssg.starroadadmin.coupon.dto.SearchCouponResponse;
import com.ssg.starroadadmin.coupon.repository.CouponRepository;
import com.ssg.starroadadmin.coupon.repository.CouponRepositoryCustom;
import com.ssg.starroadadmin.coupon.service.CouponService;
import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final ManagerRepository managerRepository;
    private final CouponRepository couponRepository;
    private final CouponRepositoryCustom couponRepositoryCustom;

    @Override
    public Page<SearchCouponResponse> getCouponList(Long managerId, Pageable pageable) {
        // 관리자 존재 유무 체크(관리자 권한이 STORE인 경우 예외 처리)
        Manager manager = managerRepository.findByIdAndAuthorityNot(managerId, Authority.STORE)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        if (manager.getAuthority() == Authority.ADMIN) {
            // 관리자 권한이 ADMIN인 경우 모든 쿠폰 목록 조회
            List<SearchCouponResponse> list = couponRepository.findAll(pageable).stream().map(SearchCouponResponse::from).toList();

            return new PageImpl<>(list, pageable, list.size());
        } else if (manager.getAuthority() == Authority.MALL) {
            // 관리자 권한이 MALL인 경우 복합쇼핑몰에서 사용가능한 쿠폰 목록 조회
            return couponRepositoryCustom.findAllByManagerId(managerId, pageable);
        }

        return null;
    }
}
