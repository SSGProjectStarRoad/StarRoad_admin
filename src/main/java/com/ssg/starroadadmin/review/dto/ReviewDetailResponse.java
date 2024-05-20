package com.ssg.starroadadmin.review.dto;

import com.ssg.starroadadmin.review.entity.ReviewFeedback;
import com.ssg.starroadadmin.review.entity.ReviewImage;
import com.ssg.starroadadmin.review.enums.ConfidenceType;
import com.ssg.starroadadmin.shop.enums.Floor;

import java.time.LocalDateTime;
import java.util.List;

public record ReviewDetailResponse(
        // 쇼핑몰
        Long mallId,
        String mallName,

        // 매장
        Long storeId,
        String storeName,
        Floor floor,
        String storeImagePath,

        // 유저
        Long userId,
        String userName,
        String nickname,
        String userImagePath,

        // 리뷰
        Long reviewId,
        boolean visible,
        int likeCount,
        String contents,
        ConfidenceType confidence,
        String summary,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,

        // 리뷰 이미지들
        List<String> reviewImagePaths,

        // 리뷰 선택지들
        List<String> reviewFeedbackSelection,

        // 리뷰 sentiment
        List<ReviewSentimentResponse> reviewSentimentResponses
) {
    public static ReviewDetailResponse from(ReviewDetailWithOutList reviewDetailWithOutList, ExcludedReviewList excludedList) {
        return new ReviewDetailResponse(
                reviewDetailWithOutList.mallId(),
                reviewDetailWithOutList.mallName(),
                reviewDetailWithOutList.storeId(),
                reviewDetailWithOutList.storeName(),
                reviewDetailWithOutList.floor(),
                reviewDetailWithOutList.storeImagePath(),
                reviewDetailWithOutList.userId(),
                reviewDetailWithOutList.userName(),
                reviewDetailWithOutList.nickname(),
                reviewDetailWithOutList.userImagePath(),
                reviewDetailWithOutList.reviewId(),
                reviewDetailWithOutList.visible(),
                reviewDetailWithOutList.likeCount(),
                reviewDetailWithOutList.contents(),
                reviewDetailWithOutList.confidence(),
                reviewDetailWithOutList.summary(),
                reviewDetailWithOutList.createdAt(),
                reviewDetailWithOutList.modifiedAt(),
                excludedList.reviewImages().stream().map(ReviewImage::getImagePath).toList(),
                excludedList.reviewFeedbacks().stream().map(ReviewFeedback::getReviewFeedbackSelection).toList(),
                excludedList.reviewSentimentResponses().stream().map(ReviewSentimentResponse::from).toList()
        );
    }
}
