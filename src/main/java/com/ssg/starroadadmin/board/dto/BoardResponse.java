package com.ssg.starroadadmin.board.dto;

import com.ssg.starroadadmin.board.enums.BoardCategory;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BoardResponse(
        Long id,
        String title,
        String writer,
        String contents,
        BoardCategory category,
        List<String> images,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}
