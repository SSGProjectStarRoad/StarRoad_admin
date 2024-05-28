package com.ssg.starroadadmin.reward.repository;

import com.ssg.starroadadmin.reward.dto.RewardListRequest;
import com.ssg.starroadadmin.reward.dto.RewardListResponse;
import com.ssg.starroadadmin.reward.enums.RewardSortType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RewardRepositoryCustom 테스트 - QUERYDSL")
@SpringBootTest
class RewardRepositoryCustomTest {

    @Autowired
    private RewardRepositoryCustom rewardRepositoryCustom;

    @Test
    @DisplayName("리워드 정렬 타입에 따라 조회 성공")
    public void givenRewardSortType_whenFindAllByCondition_thenSuccess() {
        // given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        RewardListRequest request = RewardListRequest.builder()
                .sortType(RewardSortType.NAME_ASC)
                .build();

        // when
        Page<RewardListResponse> result = rewardRepositoryCustom.findAllByCondition(request, pageable);

        // then
        assertNotNull(result);
        assertEquals(size, result.getSize());
        assertEquals(page, result.getNumber());
        List<RewardListResponse> content = result.getContent();
        assertThat(content).extracting(RewardListResponse::name).isSorted();
    }

    @Test
    @DisplayName("리워드 정렬 타입에 따라 조회 - 이름 역순")
    public void givenRewardSortType_whenFindAllByCondition_thenSuccessNameDesc() {
        // given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        RewardListRequest request = RewardListRequest.builder()
                .sortType(RewardSortType.NAME_DESC)
                .build();

        // when
        Page<RewardListResponse> result = rewardRepositoryCustom.findAllByCondition(request, pageable);

        // then
        assertNotNull(result);
        assertEquals(size, result.getSize());
        assertEquals(page, result.getNumber());
        List<RewardListResponse> content = result.getContent();
        assertThat(content).extracting(RewardListResponse::name).isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    @DisplayName("리워드 정렬 타입에 따라 조회 - 생성일시 오름차순")
    public void givenRewardSortType_whenFindAllByCondition_thenSuccessCreatedAtAsc() {
        // given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        RewardListRequest request = RewardListRequest.builder()
                .sortType(RewardSortType.CREATED_AT_ASC)
                .build();

        // when
        Page<RewardListResponse> result = rewardRepositoryCustom.findAllByCondition(request, pageable);

        // then
        assertNotNull(result);
        assertEquals(size, result.getSize());
        assertEquals(page, result.getNumber());
        List<RewardListResponse> content = result.getContent();
        assertThat(content).extracting(RewardListResponse::createdAt).isSorted();
    }

    @Test
    @DisplayName("리워드 정렬 타입에 따라 조회 - 생성일시 내림차순")
    public void givenRewardSortType_whenFindAllByCondition_thenSuccessCreatedAtDesc() {
        // given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        RewardListRequest request = RewardListRequest.builder()
                .sortType(RewardSortType.CREATED_AT_DESC)
                .build();

        // when
        Page<RewardListResponse> result = rewardRepositoryCustom.findAllByCondition(request, pageable);

        // then
        assertNotNull(result);
        assertEquals(size, result.getSize());
        assertEquals(page, result.getNumber());
        List<RewardListResponse> content = result.getContent();
        assertThat(content).extracting(RewardListResponse::createdAt).isSortedAccordingTo(Comparator.reverseOrder());
    }
}
