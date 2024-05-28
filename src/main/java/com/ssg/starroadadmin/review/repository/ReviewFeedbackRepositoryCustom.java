package com.ssg.starroadadmin.review.repository;

import com.ssg.starroadadmin.shop.dto.StoreFeedbackResponse;

import java.util.List;

public interface ReviewFeedbackRepositoryCustom {

    /**
     * 매장 피드백 조회
     * 매장 피드백을 조회
     *
     * @param storeId
     * @return
     */
    List<StoreFeedbackResponse> getStoreFeedback(Long storeId);

    /**
     * 매장 필수 피드백 조회
     * 매장 필수 피드백을 조회
     *
     * @param storeId
     * @return
     */
    List<StoreFeedbackResponse> getStoreRequiredFeedback(Long storeId);

    /**
     * 매장 선택 피드백 조회
     * 매장 선택 피드백을 조회
     *
     * @param storeId
     * @return
     */
    List<StoreFeedbackResponse> getStoreOptionalFeedback(Long storeId);
}
