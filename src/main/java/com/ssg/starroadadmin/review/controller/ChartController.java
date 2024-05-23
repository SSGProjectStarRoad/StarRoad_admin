package com.ssg.starroadadmin.review.controller;

import com.ssg.starroadadmin.review.dto.MallReviewCountResponse;
import com.ssg.starroadadmin.review.dto.MonthlyStoreReviewResponse;
import com.ssg.starroadadmin.review.dto.StoreReviewCountResponse;
import com.ssg.starroadadmin.review.service.ChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/chart")
@RequiredArgsConstructor
public class ChartController {
    private final ChartService chartService;



    @GetMapping("/mall/review-count")
    public ResponseEntity<List<MallReviewCountResponse>> getMallReviewCountChart(
            // jwt로 받아온 관리자 ID
    ) {
        Long mallManagerId = 5L; // 삭제해야할 부분

        List<MallReviewCountResponse> reviewChartResponses = chartService.gerMallReviewCount(mallManagerId);

        return ResponseEntity.ok(reviewChartResponses);
    }

    @GetMapping("/store/review-count")
    public ResponseEntity<List<StoreReviewCountResponse>> getStoreReviewCountChart(
            // jwt로 받아온 관리자 ID
    ) {
        Long mallManagerId = 5L; // 삭제해야할 부분

        List<StoreReviewCountResponse> reviewChartResponses = chartService.gerStoreReviewCount(mallManagerId);

        return ResponseEntity.ok(reviewChartResponses);
    }

    @ResponseBody
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<MonthlyStoreReviewResponse>> getMonthlyStoreReview(
            // jwt로 받아온 관리자 ID
            @PathVariable Long storeId
    ) {
        Long mallManagerId = 5L; // 삭제해야할 부분
        log.debug("Get monthly store review count, storeId: {}", storeId);

        List<MonthlyStoreReviewResponse> reviewChartResponses = chartService.gerMonthlyStoreReview(mallManagerId, storeId);

        return ResponseEntity.ok(reviewChartResponses);
    }
}
