package com.ssg.starroadadmin.review.service;

import com.ssg.starroadadmin.shop.dto.StoreFeedbackResponse;

import java.util.List;

public interface ReviewFeedbackService {

    /**
     * 매장 피드백 조회
     * 매장 피드백을 조회
     *
     * @param email
     * @param storeId
     * @return
     */
    List<StoreFeedbackResponse> getStoreFeedback(String email, Long storeId);

    /**
     * 매장 필수 피드백 조회
     * 매장 필수 피드백을 조회
     *
     * @param email
     * @param storeId
     * @return
     */
    List<StoreFeedbackResponse> getStoreRequiredFeedback(String email, Long storeId);

    /**
     * 매장 선택 피드백 조회
     * 매장 선택 피드백을 조회
     *
     * @param email
     * @param storeId
     * @return
     */
    List<StoreFeedbackResponse> getStoreOptionalFeedback(String email, Long storeId);
}
