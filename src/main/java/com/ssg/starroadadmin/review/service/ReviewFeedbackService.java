package com.ssg.starroadadmin.review.service;

import com.ssg.starroadadmin.shop.dto.StoreFeedbackResponse;

import java.util.List;

public interface ReviewFeedbackService {

    /**
     * 매장 피드백 조회
     * 매장 피드백을 조회
     *
     * @param managerId
     * @param storeId
     * @return
     */
    List<StoreFeedbackResponse> getStoreFeedback(Long managerId, Long storeId);
}
