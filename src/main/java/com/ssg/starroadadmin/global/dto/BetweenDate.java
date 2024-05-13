package com.ssg.starroadadmin.global.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BetweenDate(
        LocalDate startDate,
        LocalDate endDate
) {
}
