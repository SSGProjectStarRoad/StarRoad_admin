package com.ssg.starroadadmin.review.service;

import com.ssg.starroadadmin.review.dto.ReviewListResponse;
import com.ssg.starroadadmin.review.dto.StoreReviewSearchRequest;
import org.springframework.data.domain.Page;

public interface ReviewService {

    /**
     * 매장별 리뷰 리스트 조회
     *
     * @param reviewSearchRequest
     * @return
     */
    Page<ReviewListResponse> searchReviewList(Long managerId, StoreReviewSearchRequest reviewSearchRequest);
}
