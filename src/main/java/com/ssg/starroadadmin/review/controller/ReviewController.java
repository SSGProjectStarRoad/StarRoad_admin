package com.ssg.starroadadmin.review.controller;

import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.review.dto.*;
import com.ssg.starroadadmin.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/store/{storeId}")
    public String storeReviewList(Model model,
                                  @AuthenticationPrincipal Manager manager,
                                  @PathVariable Long storeId,
                                  @ModelAttribute("searchRequest") StoreReviewSearchRequest searchRequest,
                                  @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴

        Page<ReviewListWithDasyAgoResponse> reviewListResponses = reviewService.searchReviewList(email, searchRequest, pageable);

        model.addAttribute("reviewList", reviewListResponses);
        model.addAttribute("pages", reviewListResponses);

        return "store/storeReviewList";
    }

    @GetMapping("/user/{userId}")
    public String userReviewList(Model model,
                                 @AuthenticationPrincipal Manager manager,
                                  @PathVariable Long userId,
                                  @ModelAttribute("searchRequest") UserReviewSearchRequest searchRequest,
                                  @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴

        Page<ReviewListWithDasyAgoResponse> reviewListResponses = reviewService.searchReviewList(email, searchRequest, pageable);

        model.addAttribute("reviewList", reviewListResponses);
        model.addAttribute("pages", reviewListResponses);

        return "user/userReviewList";
    }

    @GetMapping("/detail/{reviewId}")
    public String reviewDetail(Model model,
                               @AuthenticationPrincipal Manager manager,
                                 @PathVariable Long reviewId
    ) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴

        log.debug("reviewId : {}", reviewId);
        ReviewDetailResponse reviewDetailResponse = reviewService.getReview(email, reviewId);

        model.addAttribute("review", reviewDetailResponse);

        return "review/reviewDetail";
    }

    @ResponseBody
    @GetMapping("/user/recent")
    public ResponseEntity<List<RecentReviewResponse>> recentReviewList() {
        List<RecentReviewResponse> recentReviewResponses = reviewService.getRecentReviewList();
        log.debug("recentReviewResponses : {}", recentReviewResponses);

        return ResponseEntity.ok(recentReviewResponses);
    }

    @ResponseBody
    @GetMapping("/store/popular")
    public ResponseEntity<List<PopularStoreResponse>> popularStoreList() {
        List<PopularStore> popularStore = reviewService.getPopularStoreList();

        List<PopularStoreResponse> popularStoreResponse = popularStore.stream()
                .map(store -> new PopularStoreResponse(
                        store.storeId(),
                        store.storeName(),
                        store.storeImage(),
                        store.mallId(),
                        store.mallName(),
                        store.floor().getFloor(),
                        store.reviewCount(),
                        store.positiveReviewCount(),
                        store.neutralReviewCount(),
                        store.negativeReviewCount()
                ))
                .toList();
        log.debug("popularStore : {}", popularStore);

        return ResponseEntity.ok(popularStoreResponse);
    }
}
