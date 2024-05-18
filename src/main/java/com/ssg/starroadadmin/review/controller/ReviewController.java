package com.ssg.starroadadmin.review.controller;

import com.ssg.starroadadmin.review.dto.ReviewListResponse;
import com.ssg.starroadadmin.review.dto.ReviewListWithDasyAgoResponse;
import com.ssg.starroadadmin.review.dto.StoreReviewSearchRequest;
import com.ssg.starroadadmin.review.dto.UserReviewSearchRequest;
import com.ssg.starroadadmin.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/store/{storeId}")
    public String storeReviewList(Model model,
                                  // jwt로 받아온 관리자 ID
                                  @PathVariable Long storeId,
                                  @ModelAttribute("searchRequest") StoreReviewSearchRequest searchRequest,
                                  @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Long mallManagerId = 5L; // 삭제해야할 부분

        Page<ReviewListWithDasyAgoResponse> reviewListResponses = reviewService.searchReviewList(mallManagerId, searchRequest, pageable);

        for (ReviewListWithDasyAgoResponse reviewListResponse : reviewListResponses) {
            System.out.println(reviewListResponse.reviewId() + " : " + reviewListResponse.userName() + " : " + reviewListResponse.reviewImagePaths().size());
        }
        model.addAttribute("reviewList", reviewListResponses);
        model.addAttribute("pages", reviewListResponses);

        return "store/storeReviewList";
    }

    @GetMapping("/user/{userId}")
    public String userReviewList(Model model,
                                  // jwt로 받아온 관리자 ID
                                  @PathVariable Long userId,
                                  @ModelAttribute("searchRequest") UserReviewSearchRequest searchRequest,
                                  @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Long mallManagerId = 5L; // 삭제해야할 부분

        Page<ReviewListWithDasyAgoResponse> reviewListResponses = reviewService.searchReviewList(mallManagerId, searchRequest, pageable);

        for (ReviewListWithDasyAgoResponse reviewListResponse : reviewListResponses) {
            System.out.println(reviewListResponse.reviewId() + " : " + reviewListResponse.userName() + " : " + reviewListResponse.reviewImagePaths().size());
        }
        model.addAttribute("reviewList", reviewListResponses);
        model.addAttribute("pages", reviewListResponses);

        return "user/userReviewList";
    }
}
