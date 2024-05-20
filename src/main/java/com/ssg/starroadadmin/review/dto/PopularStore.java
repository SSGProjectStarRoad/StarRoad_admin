package com.ssg.starroadadmin.review.dto;

import com.ssg.starroadadmin.shop.enums.Floor;

public record PopularStore(
        Long storeId,
        String storeName,
        String storeImage,
        Long mallId,
        String mallName,
        Floor floor,
        Long reviewCount,
        Long positiveReviewCount,
        Long neutralReviewCount,
        Long negativeReviewCount
) {
}
