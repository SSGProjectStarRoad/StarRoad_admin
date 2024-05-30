package com.ssg.starroadadmin.review.service.impl;

import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.code.ShopErrorCode;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.global.error.exception.ShopException;
import com.ssg.starroadadmin.review.dto.MallReviewCountResponse;
import com.ssg.starroadadmin.review.dto.MonthlyStoreReviewResponse;
import com.ssg.starroadadmin.review.dto.StoreReviewCountResponse;
import com.ssg.starroadadmin.review.repository.ChartRepositoryCustom;
import com.ssg.starroadadmin.review.service.ChartService;
import com.ssg.starroadadmin.shop.entity.Store;
import com.ssg.starroadadmin.shop.repository.StoreRepository;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChartServiceImpl implements ChartService {
    private final ChartRepositoryCustom charRepository;
    private final ManagerRepository managerRepository;
    private final StoreRepository storeRepository;


    /**
     * 월별 리뷰 수 조회하여 차트 데이터로 반환
     *
     * @param email
     */
    public List<MallReviewCountResponse> gerMallReviewCount(String email) {
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        List<MallReviewCountResponse> responseList = charRepository.findMallReviewsByManager(manager);

        return responseList;
    }

    /**
     * 최근 3개월 매장별 리뷰 수 조회하여 차트 데이터로 반환
     *
     * @param email
     */
    @Override
    public List<StoreReviewCountResponse> gerStoreReviewCount(String email) {
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        List<StoreReviewCountResponse> responseList = charRepository.findStoreReviewsByManager(manager);

        log.debug("responseList: {}", responseList);
        return responseList;
    }

    /**
     * 매장별 월별 리뷰 수 조회하여 차트 데이터로 반환
     *
     * @param email
     * @param storeId
     */
    @Override
    public List<MonthlyStoreReviewResponse> gerMonthlyStoreReview(String email, Long storeId) {
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ShopException(ShopErrorCode.STORE_NOT_FOUND));

        // 다른 쇼핑몰 관리자가 접근할 경우
        if (store.getComplexShoppingmall().getManager().getId() != manager.getId() && manager.getAuthority() == Authority.MALL) {
            throw new ShopException(ShopErrorCode.ACCESS_DENIED);
        }

        return charRepository.findMonthlyStoreReview(storeId);
    }
}
