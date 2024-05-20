package com.ssg.starroadadmin.review.dto;

public record ReviewAnswerResponse(
        // 리뷰 댓글
        Long reviewAnswerId,
        String contents,

        // 유저
        Long userId,
        String nickname,

        // 리뷰
        Long reviewId
) {
}
