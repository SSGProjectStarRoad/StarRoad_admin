package com.ssg.starroadadmin.coupon.service.impl;

import com.ssg.starroadadmin.coupon.dto.*;
import com.ssg.starroadadmin.coupon.entity.Coupon;
import com.ssg.starroadadmin.coupon.repository.CouponHistoryRepository;
import com.ssg.starroadadmin.coupon.repository.CouponHistoryRepositoryCustom;
import com.ssg.starroadadmin.coupon.repository.CouponRepository;
import com.ssg.starroadadmin.coupon.repository.CouponRepositoryCustom;
import com.ssg.starroadadmin.coupon.service.CouponService;
import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.code.ShopErrorCode;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.global.error.exception.ShopException;
import com.ssg.starroadadmin.shop.entity.ComplexShoppingmall;
import com.ssg.starroadadmin.shop.repository.ComplexShoppingmallRepository;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import com.ssg.starroadadmin.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final UserRepository userRepository;
    private final ComplexShoppingmallRepository complexShoppingmallRepository;
    private final ManagerRepository managerRepository;
    private final CouponRepository couponRepository;
    private final CouponHistoryRepository couponHistoryRepository;
    private final CouponHistoryRepositoryCustom couponHistoryRepositoryCustom;
    private final CouponRepositoryCustom couponRepositoryCustom;

    /**
     * 쿠폰 목록 조회
     *
     * @param email    관리자 정보
     * @param pageable 페이지 정보
     * @return 쿠폰 목록
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SearchCouponResponse> getCouponList(String email, Pageable pageable) {
        // 관리자 존재 유무 체크(관리자 권한이 STORE인 경우 예외 처리)
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        if (manager.getAuthority() == Authority.ADMIN) {
            // 관리자 권한이 ADMIN인 경우 모든 쿠폰 목록 조회
            List<SearchCouponResponse> list = couponRepository.findAll(pageable).stream().map(SearchCouponResponse::from).toList();

            return new PageImpl<>(list, pageable, list.size());
        } else if (manager.getAuthority() == Authority.MALL) {
            // 관리자 권한이 MALL인 경우 복합쇼핑몰에서 사용가능한 쿠폰 목록 조회
            return couponRepositoryCustom.findAllByManagerId(manager.getId(), pageable);
        } else {
            throw new ManagerException(ManagerErrorCode.ACCESS_DENIED);
        }
    }

    /**
     * 쿠폰 생성
     *
     * @param request 쿠폰 생성 요청
     * @param email   관리자 정보
     * @return 생성된 쿠폰 정보
     */
    @Override
    @Transactional
    public CreateCouponResponse createCoupon(CreateCouponRequest request, String email) {
        // 관리자 존재 유무 체크(관리자 권한이 STORE인 경우 예외 처리)
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        // 복합쇼핑몰 존재 유무 체크 및 복합쇼핑몰 조회
        ComplexShoppingmall complexShoppingmall = complexShoppingmallRepository.findByManagerId(manager.getId())
                .orElseThrow(() -> new ShopException(ShopErrorCode.SHOPPINGMALL_NOT_FOUND));

        Coupon save = couponRepository.save(request.toEntity(complexShoppingmall.getName()));

        return CreateCouponResponse.from(save);
    }

    /**
     * 쿠폰 사용 내역 조회
     *
     * @param email    관리자 정보
     * @param request  사용 내역 조회 요청
     * @param pageable 페이지 정보
     * @return 사용 내역 목록
     */
    @Override
    public Page<UserCouponResponse> getUserCouponList(String email, UserCouponRequest request, Pageable pageable) {
        // 관리자 존재 유무 체크(관리자 권한이 ADMIN인 경우)
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        if (manager.getAuthority() == Authority.ADMIN) {
            return couponHistoryRepositoryCustom.findAllByCondition(request, pageable);
        } else {
            throw new ManagerException(ManagerErrorCode.ACCESS_DENIED);
        }
    }
}
