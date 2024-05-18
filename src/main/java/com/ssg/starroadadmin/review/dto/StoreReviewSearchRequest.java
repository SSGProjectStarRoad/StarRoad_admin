package com.ssg.starroadadmin.review.dto;

import com.ssg.starroadadmin.review.enums.ReviewSortType;

import java.time.LocalDate;

public record StoreReviewSearchRequest(
    Long storeId,
    LocalDate startDate,
    LocalDate endDate,
    ReviewSortType sortType
    ) {
}
