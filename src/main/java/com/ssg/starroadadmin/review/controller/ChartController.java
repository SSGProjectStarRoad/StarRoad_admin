package com.ssg.starroadadmin.review.controller;

import com.ssg.starroadadmin.review.dto.MallReviewCountResponse;
import com.ssg.starroadadmin.review.dto.StoreReviewCountResponse;
import com.ssg.starroadadmin.review.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
