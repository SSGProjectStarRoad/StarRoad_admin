package com.ssg.starroadadmin.user.dto;

import com.ssg.starroadadmin.user.entity.User;
import com.ssg.starroadadmin.user.enums.ActiveStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record UserListResponse(
        Long id,
        String name,
        String nickname,
        String email,
        String imagePath,
        LocalDate birth,
        LocalDateTime createdAt,
        ActiveStatus activeStatus,
        Long reviewCount,
        Long followerCount
) {
}
