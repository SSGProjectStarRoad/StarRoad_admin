package com.ssg.starroadadmin.review.dto;

public record StoreReviewCountResponse(
        String storeName,
        Long totalReviewCount,
        Long positiveReviewCount,
        Long neutralReviewCount,
        Long negativeReviewCount
) {
}
