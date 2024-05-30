package com.ssg.starroadadmin.review.service;

import com.ssg.starroadadmin.review.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

    /**
     * 매장별 리뷰 리스트 조회
     *
     * @param email
     * @param reviewSearchRequest
     * @return Page<ReviewListWithDasyAgoResponse>
     */
    Page<ReviewListWithDasyAgoResponse> searchReviewList(String email, StoreReviewSearchRequest reviewSearchRequest, Pageable pageable);

    /**
     * 사용자별 리뷰 리스트 조회
     *
     * @param email
     * @param reviewSearchRequest
     * @return Page<ReviewListWithDasyAgoResponse>
     */
    Page<ReviewListWithDasyAgoResponse> searchReviewList(String email, UserReviewSearchRequest reviewSearchRequest, Pageable pageable);

    /**
     * 리뷰 상세 조회
     *
     * @param email
     * @param reviewId
     * @return ReviewDetailResponse
     */
    ReviewDetailResponse getReview(String email, Long reviewId);

    /**
     * 최근 리뷰 5개 조회
     *
     * @return List<RecentReviewResponse>
     */
    List<RecentReviewResponse> getRecentReviewList();

    /**
     * 인기 매장 조회
     *
     * @return List<PopularStoreResponse>
     */
    List<PopularStore> getPopularStoreList();
}
