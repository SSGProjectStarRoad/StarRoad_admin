package com.ssg.starroadadmin.review.repository;

import com.ssg.starroadadmin.global.dto.BetweenDate;
import com.ssg.starroadadmin.review.dto.ReviewListResponse;
import com.ssg.starroadadmin.review.enums.ReviewSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Page<ReviewListResponse> findAllByStoreIdAndBetweenDate(Long storeId, BetweenDate betweenDate, ReviewSortType reviewSortType, Pageable pageable);

    Page<ReviewListResponse> findAllByUserIdAndBetweenDate(Long userId, BetweenDate betweenDate, ReviewSortType reviewSortType, Pageable pageable);
}
