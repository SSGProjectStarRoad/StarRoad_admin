package com.ssg.starroadadmin.review.dto;

import com.ssg.starroadadmin.review.enums.ReviewSortType;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public record UserReviewSearchRequest(
        Long userId,
        LocalDate startDate,
        LocalDate endDate,
        ReviewSortType sortType
) {
}
