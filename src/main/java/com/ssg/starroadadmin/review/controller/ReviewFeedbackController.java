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
    public ResponseEntity<List<StoreFeedbackResponse>> storeFeedback(Model model,
                                                                     // jwt로 받아온 관리자 ID
                                                                     @PathVariable Long storeId) {
        Long managerId = 5L; // 삭제해야할 부분

        List<StoreFeedbackResponse> feedbackResponse = reviewFeedbackService.getStoreFeedback(managerId, storeId);

            System.out.println("====================");
        System.out.println(feedbackResponse.size());
        for (StoreFeedbackResponse storeFeedbackResponse : feedbackResponse) {
            System.out.println(storeFeedbackResponse.reviewFeedbackSelection());
            System.out.println(storeFeedbackResponse.count());
            System.out.println("====================");
        }
            System.out.println("====================");

        return ResponseEntity.ok(feedbackResponse);
    }
}
