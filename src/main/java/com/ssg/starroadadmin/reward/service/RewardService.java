package com.ssg.starroadadmin.reward.service;

import com.ssg.starroadadmin.reward.dto.RewardListRequest;
import com.ssg.starroadadmin.reward.dto.RewardListResponse;
import com.ssg.starroadadmin.reward.dto.RewardRegisterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RewardService {

    Long createReward(Long adminManagerId, RewardRegisterRequest request);

    Page<RewardListResponse> searchRewardList(Long mallManagerId, RewardListRequest request, Pageable pageable);
}
