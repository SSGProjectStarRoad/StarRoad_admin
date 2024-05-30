package com.ssg.starroadadmin.review.controller;

import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.review.dto.MallReviewCountResponse;
import com.ssg.starroadadmin.review.dto.MonthlyStoreReviewResponse;
import com.ssg.starroadadmin.review.dto.StoreReviewCountResponse;
import com.ssg.starroadadmin.review.service.ChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @AuthenticationPrincipal Manager manager
    ) {
        List<MallReviewCountResponse> reviewChartResponses = chartService.gerMallReviewCount(manager.getUsername());

        return ResponseEntity.ok(reviewChartResponses);
    }

    @GetMapping("/store/review-count")
    public ResponseEntity<List<StoreReviewCountResponse>> getStoreReviewCountChart(
            @AuthenticationPrincipal Manager manager
    ) {
        List<StoreReviewCountResponse> reviewChartResponses = chartService.gerStoreReviewCount(manager.getUsername());

        return ResponseEntity.ok(reviewChartResponses);
    }

    @ResponseBody
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<MonthlyStoreReviewResponse>> getMonthlyStoreReview(
            @AuthenticationPrincipal Manager manager,
            @PathVariable Long storeId
    ) {
        log.debug("Get monthly store review count, storeId: {}", storeId);

        List<MonthlyStoreReviewResponse> reviewChartResponses = chartService.gerMonthlyStoreReview(manager.getUsername(), storeId);

        return ResponseEntity.ok(reviewChartResponses);
    }
}
