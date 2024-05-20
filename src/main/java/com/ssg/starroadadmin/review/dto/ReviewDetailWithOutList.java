package com.ssg.starroadadmin.review.dto;

import com.ssg.starroadadmin.review.enums.ConfidenceType;
import com.ssg.starroadadmin.shop.enums.Floor;

import java.time.LocalDateTime;

public record ReviewDetailWithOutList(
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
        LocalDateTime modifiedAt
) {
}
