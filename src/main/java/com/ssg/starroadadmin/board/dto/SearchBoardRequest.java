package com.ssg.starroadadmin.board.dto;

import com.ssg.starroadadmin.board.enums.BoardCategory;
import com.ssg.starroadadmin.board.enums.BoardSortType;

public record SearchBoardRequest(
        String boardName,
        BoardCategory boardCategory,
        BoardSortType sortType
) {
}
