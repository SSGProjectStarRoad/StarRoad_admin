package com.ssg.starroadadmin.reward.dto;

import java.time.LocalDateTime;

public record RewardListResponse(
        Long id, // 리워드 ID
        String name, // 리워드 이름
        String rewardImagePath, // 리워드 이미지 URL
        LocalDateTime createdAt, // 생성일
        LocalDateTime modifiedAt // 수정일
) {
}
