package com.ssg.starroadadmin.board.dto;

import com.ssg.starroadadmin.board.enums.BoardCategory;

import java.time.LocalDateTime;

public record BoardListResponse(
        Long id,
        String title,
        BoardCategory boardCategory,
        String shoppingmallName,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}
