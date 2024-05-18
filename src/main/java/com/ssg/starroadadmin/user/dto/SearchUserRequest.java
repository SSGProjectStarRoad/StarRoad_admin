package com.ssg.starroadadmin.user.dto;

import com.ssg.starroadadmin.user.enums.UserSortType;

import java.time.LocalDate;

public record SearchUserRequest(
        UserSortType sortType,
        LocalDate startDate,
        LocalDate endDate,
        String email,
        String name,
        String nickname
) {
}
