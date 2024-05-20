package com.ssg.starroadadmin.review.repository;

import com.ssg.starroadadmin.global.dto.BetweenDate;
import com.ssg.starroadadmin.review.dto.PopularStore;
import com.ssg.starroadadmin.review.dto.ReviewDetailWithOutList;
import com.ssg.starroadadmin.review.dto.ReviewListResponse;
import com.ssg.starroadadmin.review.enums.ReviewSortType;
import com.ssg.starroadadmin.user.entity.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReviewRepositoryCustom {

    Page<ReviewListResponse> findAllByStoreIdAndBetweenDate(Long storeId, BetweenDate betweenDate, ReviewSortType reviewSortType, Pageable pageable);

    Page<ReviewListResponse> findAllByUserIdAndBetweenDate(Long userId, BetweenDate betweenDate, ReviewSortType reviewSortType, Pageable pageable);

    ReviewDetailWithOutList findAllByManager(Manager manager, Long reviewId);

    Optional<List<PopularStore>> findTop5PopularStore();
}
