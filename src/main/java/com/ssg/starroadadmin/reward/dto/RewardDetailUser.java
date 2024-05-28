package com.ssg.starroadadmin.reward.dto;

import lombok.Builder;

@Builder
public record RewardDetailUser(
        Long userId,
        String userNickname,
        String userImage,
        Long rewardCount
) {
}
