package com.ssg.starroadadmin.reward.dto;

import com.ssg.starroadadmin.reward.enums.RewardSortType;
import lombok.Builder;

@Builder
public record RewardListRequest(
        String rewardName,
        RewardSortType sortType
) {
}
