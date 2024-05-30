package com.ssg.starroadadmin.review.service;

import com.ssg.starroadadmin.review.dto.MallReviewCountResponse;
import com.ssg.starroadadmin.review.dto.MonthlyStoreReviewResponse;
import com.ssg.starroadadmin.review.dto.StoreReviewCountResponse;

import java.util.List;

public interface ChartService {


    /**
     * 월별 리뷰 수 조회하여 차트 데이터로 반환
     *
     * @param email
     */
    List<MallReviewCountResponse> gerMallReviewCount(String email);

    /**
     * 최근 3개월 매장별 리뷰 수 조회하여 차트 데이터로 반환
     *
     * @param email
     */
    List<StoreReviewCountResponse> gerStoreReviewCount(String email);

    /**
     * 매장별 월별 리뷰 수 조회하여 차트 데이터로 반환
     *
     * @param email
     * @param storeId
     */
    List<MonthlyStoreReviewResponse> gerMonthlyStoreReview(String email, Long storeId);
}
