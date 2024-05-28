package com.ssg.starroadadmin.reward.service;

import com.ssg.starroadadmin.reward.dto.RewardDetailResponse;
import com.ssg.starroadadmin.reward.dto.RewardListRequest;
import com.ssg.starroadadmin.reward.dto.RewardListResponse;
import com.ssg.starroadadmin.reward.dto.RewardRegisterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RewardService {

    /**
     * 리워드 등록
     *
     * @param adminManagerId 관리자 ID
     * @param request         리워드 등록 요청
     * @return 리워드 ID
     */
    Long createReward(Long adminManagerId, RewardRegisterRequest request);

    /**
     * 리워드 목록 조회
     *
     * @param mallManagerId 관리자 ID
     * @param request       리워드 목록 조회 요청
     * @param pageable      페이지 정보
     * @return 리워드 목록
     */
    Page<RewardListResponse> searchRewardList(Long mallManagerId, RewardListRequest request, Pageable pageable);

    /**
     * 사용자 리워드 목록 조회
     *
     * @param mallManagerId 관리자 ID
     * @param userId        사용자 ID
     * @param searchRequest 사용자 리워드 목록 조회 요청
     * @param pageable      페이지 정보
     * @return 사용자 리워드 목록
     */
    Page<RewardListResponse> searchUserRewardList(Long mallManagerId, Long userId, RewardListRequest searchRequest, Pageable pageable);

    /**
     * 리워드 상세 조회
     *
     * @param mallManagerId
     * @param rewardId
     * @return
     */
    RewardDetailResponse searchRewardDetail(Long mallManagerId, Long rewardId, Pageable pageable);
}
