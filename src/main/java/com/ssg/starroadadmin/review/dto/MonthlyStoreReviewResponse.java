package com.ssg.starroadadmin.review.dto;

public record MonthlyStoreReviewResponse(
        Long reviewCount,
        int reviewYear,
        int reviewMonth,
        Long positiveReviewCount,
        Long neutralReviewCount,
        Long negativeReviewCount
) {
}
