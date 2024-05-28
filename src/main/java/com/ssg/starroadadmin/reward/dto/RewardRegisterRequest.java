package com.ssg.starroadadmin.reward.dto;

import org.springframework.web.multipart.MultipartFile;

public record RewardRegisterRequest(
        String createRewardName,
        MultipartFile createRewardImage
) {
}
