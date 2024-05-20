package com.ssg.starroadadmin.review.dto;

import com.ssg.starroadadmin.review.entity.ReviewFeedback;
import com.ssg.starroadadmin.review.entity.ReviewImage;
import com.ssg.starroadadmin.review.entity.ReviewSentiment;
import lombok.Builder;

import java.util.List;

@Builder
public record ExcludedReviewList(
        List<ReviewImage> reviewImages,
        List<ReviewFeedback> reviewFeedbacks,
        List<ReviewSentiment> reviewSentimentResponses
) {
}
