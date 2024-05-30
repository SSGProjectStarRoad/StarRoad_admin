package com.ssg.starroadadmin.reward.service;

import com.ssg.starroadadmin.reward.dto.RewardDetailResponse;
import com.ssg.starroadadmin.reward.dto.RewardListRequest;
import com.ssg.starroadadmin.reward.dto.RewardListResponse;
import com.ssg.starroadadmin.reward.dto.RewardRegisterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface RewardService {

    /**
     * 리워드 등록
     *
     * @param email
     * @param request         리워드 등록 요청
     * @return 리워드 ID
     */
    Long createReward(String email, RewardRegisterRequest request);

    /**
     * 리워드 목록 조회
     *
     * @param email
     * @param request       리워드 목록 조회 요청
     * @param pageable      페이지 정보
     * @return 리워드 목록
     */
    Page<RewardListResponse> searchRewardList(String email, RewardListRequest request, Pageable pageable);

    /**
     * 사용자 리워드 목록 조회
     *
     * @param email
     * @param userId        사용자 ID
     * @param searchRequest 사용자 리워드 목록 조회 요청
     * @param pageable      페이지 정보
     * @return 사용자 리워드 목록
     */
    Page<RewardListResponse> searchUserRewardList(String email, Long userId, RewardListRequest searchRequest, Pageable pageable);

    /**
     * 리워드 상세 조회
     *
     * @param email
     * @param rewardId
     * @return
     */
    RewardDetailResponse searchRewardDetail(String email, Long rewardId, Pageable pageable);

    /**
     * 리워드 이미지 업로드
     *
     * @param email
     * @param rewardId      리워드 ID
     * @param image         이미지 파일
     */
    void uploadImage(String email, Long rewardId, MultipartFile image);
}
