package com.ssg.starroadadmin.reward.service.impl;

import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.code.RewardErrorCode;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.global.error.exception.RewardException;
import com.ssg.starroadadmin.global.util.S3Uploader;
import com.ssg.starroadadmin.reward.dto.*;
import com.ssg.starroadadmin.reward.entity.Reward;
import com.ssg.starroadadmin.reward.entity.RewardHistory;
import com.ssg.starroadadmin.reward.repository.RewardHistoryRepository;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final RewardRepository rewardRepository;
    private final RewardRepositoryCustom rewardRepositoryCustom;
    private final ManagerRepository managerRepository;
    private final S3Uploader s3Uploader;
    private final RewardHistoryRepository rewardHistoryRepository;

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

        Page<RewardListResponse> rewardList = rewardRepositoryCustom.findAllByCondition(request, pageable);

        return rewardList;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RewardListResponse> searchUserRewardList(Long adminManagerId, Long userId, RewardListRequest searchRequest, Pageable pageable) {
        managerRepository.findByIdAndAuthority(adminManagerId, Authority.ADMIN)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        Page<RewardListResponse> rewardList = rewardRepositoryCustom.findAllByUserId(userId, searchRequest, pageable);
        return rewardList;
    }

    @Override
    public RewardDetailResponse searchRewardDetail(Long adminManagerId, Long rewardId, Pageable pageable) {
        managerRepository.findByIdAndAuthority(adminManagerId, Authority.ADMIN)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new RewardException(RewardErrorCode.REWARD_NOT_FOUND));

//        Page<RewardHistory> rewardHistories = rewardHistoryRepository.findAllByRewardId(reward.getId(), pageable);

//        Page<RewardDetailUser> page = rewardHistories.map(rewardHistory -> {
//                    List<RewardHistory> histories = rewardHistoryRepository.findAllByUser(rewardHistory.getUser())
//                            .orElseThrow(() -> new RewardException(RewardErrorCode.REWARD_HISTORY_NOT_FOUND));
//
//                    return RewardDetailUser.builder()
//                            .userId(rewardHistory.getUser().getId())
//                            .userNickname(rewardHistory.getUser().getNickname())
//                            .userImage(rewardHistory.getUser().getImagePath())
//                            .rewardCount(histories.size())
//                            .build();
//                }
//        );

        Page<RewardDetailUser> page = rewardRepositoryCustom.findAllByRewardId(reward.getId(), pageable);

        return RewardDetailResponse.builder()
                .rewardId(reward.getId())
                .rewardName(reward.getName())
                .rewardImage(reward.getRewardImagePath())
                .rewardCreatedAt(reward.getCreatedAt())
                .userList(page)
                .build();
    }
}
