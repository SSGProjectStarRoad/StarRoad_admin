package com.ssg.starroadadmin.review.dto;

import com.ssg.starroadadmin.review.enums.ConfidenceType;
import com.ssg.starroadadmin.shop.enums.Floor;

import java.time.LocalDateTime;
import java.util.List;

public record ReviewListWithDasyAgoResponse(
        // 쇼핑몰
        Long mallId,
        String mallName,

        // 매장
        Long storeId,
        String storeName,
        Floor floor,
        String storeImagePath,

        // 리뷰
        Long reviewId,
        boolean visible,
        int likeCount,
        String contents,
        ConfidenceType confidence,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        int daysAgo,


        // 리뷰 이미지들
        List<String> reviewImagePaths,

        // 리뷰 선택지들
        List<String> reviewFeedbackSelection,

        // 유저
        Long userId,
        String userName,
        String nickname,
        String userImagePath

        ) {
}
