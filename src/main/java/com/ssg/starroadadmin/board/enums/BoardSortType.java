package com.ssg.starroadadmin.board.enums;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public enum BoardSortType {
    TITLE_ASC("제목 오름차순"),
    TITLE_DESC("제목 내림차순"),
    CREATED_AT_ASC("오래된 순"),
    CREATED_AT_DESC("새로운 순");

    private final String discription;

    BoardSortType(String discription) {
        this.discription = discription;
    }
}
