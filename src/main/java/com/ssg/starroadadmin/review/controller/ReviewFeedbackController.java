package com.ssg.starroadadmin.review.controller;

import com.ssg.starroadadmin.review.service.ReviewFeedbackService;
import com.ssg.starroadadmin.shop.dto.StoreFeedbackResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class ReviewFeedbackController {
    private final ReviewFeedbackService reviewFeedbackService;


    @ResponseBody
    @GetMapping("/{storeId}")
    public ResponseEntity<List<StoreFeedbackResponse>> storeFeedback(
                                                                     // jwt로 받아온 관리자 ID
                                                                     @PathVariable Long storeId) {
        Long managerId = 5L; // 삭제해야할 부분

        List<StoreFeedbackResponse> feedbackResponse = reviewFeedbackService.getStoreFeedback(managerId, storeId);

        return ResponseEntity.ok(feedbackResponse);
    }

    @ResponseBody
    @GetMapping("/required/{storeId}")
    public ResponseEntity<List<StoreFeedbackResponse>> requiredFeedback(
                                   // jwt로 받아온 관리자 ID
                                   @PathVariable Long storeId) {
        Long managerId = 5L; // 삭제해야할 부분

        List<StoreFeedbackResponse> feedbackResponse = reviewFeedbackService.getStoreRequiredFeedback(managerId, storeId);

        return ResponseEntity.ok(feedbackResponse);
    }

    @ResponseBody
    @GetMapping("/optional/{storeId}")
    public ResponseEntity<List<StoreFeedbackResponse>> optionalFeedback(
                                   // jwt로 받아온 관리자 ID
                                   @PathVariable Long storeId) {
        Long managerId = 5L; // 삭제해야할 부분

        List<StoreFeedbackResponse> feedbackResponse = reviewFeedbackService.getStoreOptionalFeedback(managerId, storeId);

        return ResponseEntity.ok(feedbackResponse);
    }
}
