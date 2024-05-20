package com.ssg.starroadadmin.review.dto;

import com.ssg.starroadadmin.review.entity.ReviewSentiment;
import com.ssg.starroadadmin.review.enums.ConfidenceType;

public record ReviewSentimentResponse(
        Long id,
        Long reviewId,
        ConfidenceType confidence,
        int length,
        int offset,
        int highlightLength,
        int highlightOffset
) {
    public static ReviewSentimentResponse from(ReviewSentiment reviewSentiment) {
        return new ReviewSentimentResponse(
                reviewSentiment.getId(),
                reviewSentiment.getReview().getId(),
                reviewSentiment.getConfidence(),
                reviewSentiment.getTotalLength(),
                reviewSentiment.getTotalOffset(),
                reviewSentiment.getHighlightLength(),
                reviewSentiment.getHighlightOffset()
        );
    }
}
