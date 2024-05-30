package com.ssg.starroadadmin.review.controller;

import com.ssg.starroadadmin.global.entity.CustomUserDetails;
import com.ssg.starroadadmin.review.service.ReviewFeedbackService;
import com.ssg.starroadadmin.shop.dto.StoreFeedbackResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long storeId) {
        String email = userDetails.getEmail(); // 이메일을 직접 가져옴

        List<StoreFeedbackResponse> feedbackResponse = reviewFeedbackService.getStoreFeedback(email, storeId);

        return ResponseEntity.ok(feedbackResponse);
    }

    @ResponseBody
    @GetMapping("/required/{storeId}")
    public ResponseEntity<List<StoreFeedbackResponse>> requiredFeedback(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long storeId) {
        String email = userDetails.getEmail(); // 이메일을 직접 가져옴

        List<StoreFeedbackResponse> feedbackResponse = reviewFeedbackService.getStoreRequiredFeedback(email, storeId);

        return ResponseEntity.ok(feedbackResponse);
    }

    @ResponseBody
    @GetMapping("/optional/{storeId}")
    public ResponseEntity<List<StoreFeedbackResponse>> optionalFeedback(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long storeId) {
        String email = userDetails.getEmail(); // 이메일을 직접 가져옴

        List<StoreFeedbackResponse> feedbackResponse = reviewFeedbackService.getStoreOptionalFeedback(email, storeId);

        return ResponseEntity.ok(feedbackResponse);
    }
}
