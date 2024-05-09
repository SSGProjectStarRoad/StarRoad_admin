package com.ssg.starroadadmin.reward.service;

import com.ssg.starroadadmin.reward.dto.RewardListRequest;
import com.ssg.starroadadmin.reward.dto.RewardListResponse;
import com.ssg.starroadadmin.reward.enums.RewardSortType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

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
                .page(0)
                .size(10)
                .sortType(RewardSortType.NAME_ASC)
                .build();

        // when
        Page<RewardListResponse> result = rewardService.searchRewardList(mallManagerId, request);

        // then
        assertNotNull(result);
        assertEquals(request.size(), result.getSize());
        assertEquals(request.page(), result.getNumber());
        List<RewardListResponse> content = result.getContent();
        assertThat(content).extracting(RewardListResponse::name).isSorted();
    }

    @Test
    @DisplayName("리워드 리스트 조회 - 이름순 내림차순 정렬")
    public void givenRewardListRequestSortByNameDESC_whenSearchRewardList_thenSuccess() {
        // given
        Long mallManagerId = 8L;
        RewardListRequest request = RewardListRequest.builder()
                .page(0)
                .size(10)
                .sortType(RewardSortType.NAME_DESC)
                .build();

        // when
        Page<RewardListResponse> result = rewardService.searchRewardList(mallManagerId, request);

        // then
        assertNotNull(result);
        assertEquals(request.size(), result.getSize());
        assertEquals(request.page(), result.getNumber());
        List<RewardListResponse> content = result.getContent();
        assertThat(content).extracting(RewardListResponse::name).isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    @DisplayName("리워드 리스트 조회 - 생성일시순 오른차순 정렬")
    public void givenRewardListRequestSortByCreatedAtASC_whenSearchRewardList_thenSuccess() {
        // given
        Long mallManagerId = 8L;
        RewardListRequest request = RewardListRequest.builder()
                .page(0)
                .size(10)
                .sortType(RewardSortType.CREATED_AT_ASC)
                .build();

        // when
        Page<RewardListResponse> result = rewardService.searchRewardList(mallManagerId, request);

        // then
        assertNotNull(result);
        assertEquals(request.size(), result.getSize());
        assertEquals(request.page(), result.getNumber());
        List<RewardListResponse> content = result.getContent();
        assertThat(content).extracting(RewardListResponse::createdAt).isSorted();
    }

    @Test
    @DisplayName("리워드 리스트 조회 - 생성일시순 내림차순 정렬")
    public void givenRewardListRequestSortByCreatedAtDESC_whenSearchRewardList_thenSuccess() {
        // given
        Long mallManagerId = 8L;
        RewardListRequest request = RewardListRequest.builder()
                .page(0)
                .size(10)
                .sortType(RewardSortType.CREATED_AT_DESC)
                .build();

        // when
        Page<RewardListResponse> result = rewardService.searchRewardList(mallManagerId, request);

        // then
        assertNotNull(result);
        assertEquals(request.size(), result.getSize());
        assertEquals(request.page(), result.getNumber());
        List<RewardListResponse> content = result.getContent();
        assertThat(content).extracting(RewardListResponse::createdAt).isSortedAccordingTo(Comparator.reverseOrder());
    }
}