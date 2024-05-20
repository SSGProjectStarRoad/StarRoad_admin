package com.ssg.starroadadmin.user.dto;

public record PopularUserResponse(
    Long userId,
    String userName,
    String imagePath,
    Long followerCount,
    Long followingCount,
    Long reviewCount
) {
}
