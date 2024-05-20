package com.ssg.starroadadmin.review.service.impl;

import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.review.dto.MallReviewCountResponse;
import com.ssg.starroadadmin.review.dto.StoreReviewCountResponse;
import com.ssg.starroadadmin.review.repository.ChartRepositoryCustom;
import com.ssg.starroadadmin.review.service.ChartService;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChartServiceImpl implements ChartService {
    private final ChartRepositoryCustom charRepository;
    private final ManagerRepository managerRepository;


    /**
     * 월별 리뷰 수 조회하여 차트 데이터로 반환
     *
     * @param mallManagerId
     */
    public List<MallReviewCountResponse> gerMallReviewCount(Long mallManagerId) {
        Manager manager = managerRepository.findById(mallManagerId)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        List<MallReviewCountResponse> responseList = charRepository.findMallReviewsByManager(manager);

        return responseList;
    }

    @Override
    public List<StoreReviewCountResponse> gerStoreReviewCount(Long mallManagerId) {
        Manager manager = managerRepository.findById(mallManagerId)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        List<StoreReviewCountResponse> responseList = charRepository.findStoreReviewsByManager(manager);

        log.debug("responseList: {}", responseList);
        return responseList;
    }
}
