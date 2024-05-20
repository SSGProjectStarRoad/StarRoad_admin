package com.ssg.starroadadmin.review.dto;

import lombok.Builder;

@Builder
public record RecentReviewResponse(
        Long reviewId,
        String userName,
        String userImagePath,
        Long followerCount,
        Long followingCount,
        String contents
) {
}
