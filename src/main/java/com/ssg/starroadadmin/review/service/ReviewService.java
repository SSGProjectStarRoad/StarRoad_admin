package com.ssg.starroadadmin.review.service;

import com.ssg.starroadadmin.review.dto.ReviewListResponse;
import com.ssg.starroadadmin.review.dto.ReviewListWithDasyAgoResponse;
import com.ssg.starroadadmin.review.dto.StoreReviewSearchRequest;
import com.ssg.starroadadmin.review.dto.UserReviewSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    /**
     * 매장별 리뷰 리스트 조회
     *
     * @param reviewSearchRequest
     * @return
     */
    Page<ReviewListWithDasyAgoResponse> searchReviewList(Long managerId, StoreReviewSearchRequest reviewSearchRequest, Pageable pageable);

    /**
     * 사용자별 리뷰 리스트 조회
     *
     * @param reviewSearchRequest
     * @return
     */
    Page<ReviewListWithDasyAgoResponse> searchReviewList(Long managerId, UserReviewSearchRequest reviewSearchRequest, Pageable pageable);
}
