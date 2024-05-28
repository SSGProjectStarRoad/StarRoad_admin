package com.ssg.starroadadmin.board.enums;

import lombok.Getter;

@Getter
public enum BoardCategory {
    NOTICE("공지사항"),
    FAQ("FAQ"),
    QNA("Q&A"),
    EVENT("이벤트");

    private String discription;

    BoardCategory(String discription) {
        this.discription = discription;
    }
}
