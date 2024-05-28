package com.ssg.starroadadmin.board.dto;

import com.ssg.starroadadmin.board.enums.BoardCategory;
import org.springframework.web.multipart.MultipartFile;

public record BoardCreateRequest(
        String title,
        String content,
        BoardCategory category,
        MultipartFile[] images
) {
}
