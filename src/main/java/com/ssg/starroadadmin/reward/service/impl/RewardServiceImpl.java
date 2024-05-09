package com.ssg.starroadadmin.reward.service.impl;

import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.reward.dto.RewardListRequest;
import com.ssg.starroadadmin.reward.dto.RewardListResponse;
import com.ssg.starroadadmin.reward.dto.RewardRegisterRequest;
import com.ssg.starroadadmin.reward.repository.RewardRepository;
import com.ssg.starroadadmin.reward.repository.RewardRepositoryCustom;
import com.ssg.starroadadmin.reward.service.RewardService;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final RewardRepository rewardRepository;
    private final RewardRepositoryCustom rewardRepositoryCustom;
    private final ManagerRepository managerRepository;

    @Override
    public Long createReward(Long mallManagerId, RewardRegisterRequest request) {
        return null;
    }

    @Override
    public Page<RewardListResponse> searchRewardList(Long mallManagerId, RewardListRequest request) {
        // 쇼핑몰 관리자인지 확인
        managerRepository.findByIdAndAuthority(mallManagerId, Authority.ADMIN)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        Pageable pageable = PageRequest.of(request.page(), request.size());

        Page<RewardListResponse> rewardList = rewardRepositoryCustom.findAllByCondition(request.sortType(), pageable);

        return rewardList;
    }
}
