package com.ssg.starroadadmin.reward.service.impl;

import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.global.service.S3Uploader;
import com.ssg.starroadadmin.reward.dto.RewardListRequest;
import com.ssg.starroadadmin.reward.dto.RewardListResponse;
import com.ssg.starroadadmin.reward.dto.RewardRegisterRequest;
import com.ssg.starroadadmin.reward.entity.Reward;
import com.ssg.starroadadmin.reward.repository.RewardRepository;
import com.ssg.starroadadmin.reward.repository.RewardRepositoryCustom;
import com.ssg.starroadadmin.reward.service.RewardService;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final RewardRepository rewardRepository;
    private final RewardRepositoryCustom rewardRepositoryCustom;
    private final ManagerRepository managerRepository;
    private final S3Uploader s3Uploader;

    @Override
    @Transactional
    public Long createReward(Long adminManagerId, RewardRegisterRequest request) {
        // 총 관리자인지 확인
        managerRepository.findByIdAndAuthority(adminManagerId, Authority.ADMIN)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        String uploadURL = s3Uploader.upload(request.createRewardImage(), "ssg/starroad/rewards");

        Reward savedReward = rewardRepository.save(Reward.builder()
                .name(request.createRewardName())
                .rewardImagePath(uploadURL)
                .build()
        );

        return savedReward.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RewardListResponse> searchRewardList(Long adminManagerId, RewardListRequest request, Pageable pageable) {
        managerRepository.findByIdAndAuthority(adminManagerId, Authority.ADMIN)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        Page<RewardListResponse> rewardList = rewardRepositoryCustom.findAllByCondition(request.sortType(), pageable);

        return rewardList;
    }
}
