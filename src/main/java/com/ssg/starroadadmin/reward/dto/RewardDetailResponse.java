package com.ssg.starroadadmin.reward.dto;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Builder
public record RewardDetailResponse(
        Long rewardId,
        String rewardName,
        String rewardImage,
        LocalDateTime rewardCreatedAt,
        Page<RewardDetailUser> userList
) {
}
