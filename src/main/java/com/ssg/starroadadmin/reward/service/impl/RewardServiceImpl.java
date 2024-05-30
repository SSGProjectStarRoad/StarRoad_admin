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
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final RewardRepository rewardRepository;
    private final RewardRepositoryCustom rewardRepositoryCustom;
    private final ManagerRepository managerRepository;
    private final S3Uploader s3Uploader;

    @Override
    @Transactional
    public Long createReward(String email, RewardRegisterRequest request) {
        // 총 관리자인지 확인
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        if (manager.getAuthority() != Authority.ADMIN) {
            throw new ManagerException(ManagerErrorCode.ACCESS_DENIED);
        }

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
    public Page<RewardListResponse> searchRewardList(String email, RewardListRequest request, Pageable pageable) {
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        if (manager.getAuthority() != Authority.ADMIN) {
            throw new ManagerException(ManagerErrorCode.ACCESS_DENIED);
        }

        Page<RewardListResponse> rewardList = rewardRepositoryCustom.findAllByCondition(request, pageable);

        return rewardList;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RewardListResponse> searchUserRewardList(String email, Long userId, RewardListRequest searchRequest, Pageable pageable) {
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        if (manager.getAuthority() != Authority.ADMIN) {
            throw new ManagerException(ManagerErrorCode.ACCESS_DENIED);
        }

        Page<RewardListResponse> rewardList = rewardRepositoryCustom.findAllByUserId(userId, searchRequest, pageable);
        return rewardList;
    }

    @Override
    public RewardDetailResponse searchRewardDetail(String email, Long rewardId, Pageable pageable) {
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        if (manager.getAuthority() != Authority.ADMIN) {
            throw new ManagerException(ManagerErrorCode.ACCESS_DENIED);
        }

        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new RewardException(RewardErrorCode.REWARD_NOT_FOUND));

        Page<RewardDetailUser> page = rewardRepositoryCustom.findAllByRewardId(reward.getId(), pageable);

        return RewardDetailResponse.builder()
                .rewardId(reward.getId())
                .rewardName(reward.getName())
                .rewardImage(reward.getRewardImagePath())
                .rewardCreatedAt(reward.getCreatedAt())
                .userList(page)
                .build();
    }

    @Override
    @Transactional
    public void uploadImage(String email, Long rewardId, MultipartFile image) {
        // 총 관리자인지 확인
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        if (manager.getAuthority() != Authority.ADMIN) {
            throw new ManagerException(ManagerErrorCode.ACCESS_DENIED);
        }

        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new RewardException(RewardErrorCode.REWARD_NOT_FOUND));

        String uploadURL = s3Uploader.upload(image, "ssg/starroad/rewards");

        reward.updateRewardImagePath(uploadURL);
    }
}
