package com.ssg.starroadadmin.review.repository;

import com.ssg.starroadadmin.review.dto.MallReviewCountResponse;
import com.ssg.starroadadmin.review.dto.StoreReviewCountResponse;
import com.ssg.starroadadmin.user.entity.Manager;

import java.util.List;

public interface ChartRepositoryCustom {
    List<MallReviewCountResponse> findMallReviewsByManager(Manager manager);

    List<StoreReviewCountResponse> findStoreReviewsByManager(Manager manager);
}
