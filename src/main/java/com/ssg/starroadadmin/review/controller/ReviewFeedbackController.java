package com.ssg.starroadadmin.review.controller;

import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.review.service.ReviewFeedbackService;
import com.ssg.starroadadmin.shop.dto.StoreFeedbackResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class ReviewFeedbackController {
    private final ReviewFeedbackService reviewFeedbackService;


    @ResponseBody
    @GetMapping("/{storeId}")
    public ResponseEntity<List<StoreFeedbackResponse>> storeFeedback(
            @AuthenticationPrincipal Manager manager,
            @PathVariable Long storeId) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴

        List<StoreFeedbackResponse> feedbackResponse = reviewFeedbackService.getStoreFeedback(email, storeId);

        return ResponseEntity.ok(feedbackResponse);
    }

    @ResponseBody
    @GetMapping("/required/{storeId}")
    public ResponseEntity<List<StoreFeedbackResponse>> requiredFeedback(
            @AuthenticationPrincipal Manager manager,
            @PathVariable Long storeId) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴

        List<StoreFeedbackResponse> feedbackResponse = reviewFeedbackService.getStoreRequiredFeedback(email, storeId);

        return ResponseEntity.ok(feedbackResponse);
    }

    @ResponseBody
    @GetMapping("/optional/{storeId}")
    public ResponseEntity<List<StoreFeedbackResponse>> optionalFeedback(
            @AuthenticationPrincipal Manager manager,
            @PathVariable Long storeId) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴

        List<StoreFeedbackResponse> feedbackResponse = reviewFeedbackService.getStoreOptionalFeedback(email, storeId);

        return ResponseEntity.ok(feedbackResponse);
    }
}
