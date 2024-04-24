package com.ssg.starroad.board.entity;

import com.ssg.starroad.board.enums.BoardCategory;
import com.ssg.starroad.common.entity.BaseTimeEntity;
import com.ssg.starroad.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board; // QnA 게시글
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager; // 쇼핑몰 관리자

    private String content; // 답변 내용
}
