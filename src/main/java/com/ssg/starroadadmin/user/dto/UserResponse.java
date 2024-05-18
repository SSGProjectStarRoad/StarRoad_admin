package com.ssg.starroadadmin.user.dto;

import com.ssg.starroadadmin.user.enums.ActiveStatus;
import com.ssg.starroadadmin.user.enums.ProviderType;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record UserResponse(
        Long id,
        String name,
        String email,
        String nickname,
        String phone,
        LocalDate birth,
        String imagePath,
        int reviewExp,
        Long reviewCount,
        Long followerCount,
        Long followingCount,
        ActiveStatus activeStatus,
        LocalDateTime createdAt,
        ProviderType providerType
        ) {
}
