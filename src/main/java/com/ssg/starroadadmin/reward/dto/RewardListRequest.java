package com.ssg.starroadadmin.reward.dto;

import com.ssg.starroadadmin.reward.enums.RewardSortType;
import lombok.Builder;

@Builder
public record RewardListRequest(
        Long userId,
        String rewardName,
        RewardSortType sortType
) {
}
