package com.ssg.starroadadmin.review.controller;

import com.ssg.starroadadmin.global.entity.CustomUserDetails;
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
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        String email = userDetails.getEmail(); // 이메일을 직접 가져옴

        List<MallReviewCountResponse> reviewChartResponses = chartService.gerMallReviewCount(email);

        return ResponseEntity.ok(reviewChartResponses);
    }

    @GetMapping("/store/review-count")
    public ResponseEntity<List<StoreReviewCountResponse>> getStoreReviewCountChart(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        String email = userDetails.getEmail(); // 이메일을 직접 가져옴

        List<StoreReviewCountResponse> reviewChartResponses = chartService.gerStoreReviewCount(email);

        return ResponseEntity.ok(reviewChartResponses);
    }

    @ResponseBody
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<MonthlyStoreReviewResponse>> getMonthlyStoreReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long storeId
    ) {
        String email = userDetails.getEmail(); // 이메일을 직접 가져옴
        log.debug("Get monthly store review count, storeId: {}", storeId);

        List<MonthlyStoreReviewResponse> reviewChartResponses = chartService.gerMonthlyStoreReview(email, storeId);

        return ResponseEntity.ok(reviewChartResponses);
    }
}
