package com.ssg.starroadadmin.reward.repository;

import com.ssg.starroadadmin.reward.dto.RewardListRequest;
import com.ssg.starroadadmin.reward.dto.RewardListResponse;
import com.ssg.starroadadmin.reward.enums.RewardSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RewardRepositoryCustom {
    Page<RewardListResponse> findAllByCondition(RewardListRequest searchRequest, Pageable pageable);

    Page<RewardListResponse> findAllByUserId(Long userId, RewardListRequest searchRequest, Pageable pageable);
}
