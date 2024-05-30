package com.ssg.starroadadmin.reward.service;

import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.reward.dto.RewardListRequest;
import com.ssg.starroadadmin.reward.dto.RewardListResponse;
import com.ssg.starroadadmin.reward.dto.RewardRegisterRequest;
import com.ssg.starroadadmin.reward.enums.RewardSortType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("RewardService 테스트")
class RewardServiceTest {

    @Autowired
    private RewardService rewardService;

    @Test
    @DisplayName("리워드 리스트 조회 - 이름순 오름차순 정렬")
    public void givenRewardListRequestSortByNameASC_whenSearchRewardList_thenSuccess() {
        // given
        Long mallManagerId = 8L;
        RewardListRequest request = RewardListRequest.builder()
                .sortType(RewardSortType.NAME_ASC)
                .build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<RewardListResponse> result = rewardService.searchRewardList(mallManagerId, request, pageable);

        // then
        assertNotNull(result);
        assertEquals(pageable.getPageSize(), result.getSize());
        assertEquals(pageable.getPageNumber(), result.getNumber());
        List<RewardListResponse> content = result.getContent();
        assertThat(content).extracting(RewardListResponse::name).isSorted();
    }

    @Test
    @DisplayName("리워드 리스트 조회 - 이름순 내림차순 정렬")
    public void givenRewardListRequestSortByNameDESC_whenSearchRewardList_thenSuccess() {
        // given
        Long mallManagerId = 8L;
        RewardListRequest request = RewardListRequest.builder()
                .sortType(RewardSortType.NAME_DESC)
                .build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<RewardListResponse> result = rewardService.searchRewardList(mallManagerId, request, pageable);

        // then
        assertNotNull(result);
        assertEquals(pageable.getPageSize(), result.getSize());
        assertEquals(pageable.getPageNumber(), result.getNumber());
        List<RewardListResponse> content = result.getContent();
        assertThat(content).extracting(RewardListResponse::name).isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    @DisplayName("리워드 리스트 조회 - 생성일시순 오른차순 정렬")
    public void givenRewardListRequestSortByCreatedAtASC_whenSearchRewardList_thenSuccess() {
        // given
        Long mallManagerId = 8L;
        RewardListRequest request = RewardListRequest.builder()
                .sortType(RewardSortType.CREATED_AT_ASC)
                .build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<RewardListResponse> result = rewardService.searchRewardList(mallManagerId, request, pageable);

        // then
        assertNotNull(result);
        assertEquals(pageable.getPageSize(), result.getSize());
        assertEquals(pageable.getPageNumber(), result.getNumber());
        List<RewardListResponse> content = result.getContent();
        assertThat(content).extracting(RewardListResponse::createdAt).isSorted();
    }

    @Test
    @DisplayName("리워드 리스트 조회 - 생성일시순 내림차순 정렬")
    public void givenRewardListRequestSortByCreatedAtDESC_whenSearchRewardList_thenSuccess() {
        // given
        Long mallManagerId = 8L;
        RewardListRequest request = RewardListRequest.builder()
                .sortType(RewardSortType.CREATED_AT_DESC)
                .build();

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<RewardListResponse> result = rewardService.searchRewardList(mallManagerId, request, pageable);

        // then
        assertNotNull(result);
        assertEquals(pageable.getPageSize(), result.getSize());
        assertEquals(pageable.getPageNumber(), result.getNumber());
        List<RewardListResponse> content = result.getContent();
        assertThat(content).extracting(RewardListResponse::createdAt).isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    @DisplayName("새로운 리워드 생성")
    public void givenRewardRegisterRequest_whenCreateReward_thenSuccess() {
        // given
        Long adminManagerId = 8L;
        String name = "리워드 이름";
        String rewardImageUrl = "https://example.com/image.jpg";

        // when
        Long rewardId = rewardService.createReward(adminManagerId, new RewardRegisterRequest(name, (MultipartFile) new File(rewardImageUrl)));

        // then
        assertNotNull(rewardId);
    }

    @Test
    @DisplayName("리워드 생성 - 관리자 권한이 아닌 경우")
    public void givenNonAdminManager_whenCreateReward_thenThrowException() {
        // given
        Long nonAdminManagerId = 7L;
        String name = "리워드 이름";
        String rewardImageUrl = "https://example.com/image.jpg";

        // when & then
        assertThrows(ManagerException.class, () -> {
            rewardService.createReward(nonAdminManagerId, new RewardRegisterRequest(name, (MultipartFile) new File(rewardImageUrl)));
        });
    }
}