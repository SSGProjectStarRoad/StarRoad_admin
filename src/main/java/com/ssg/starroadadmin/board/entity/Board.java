package com.ssg.starroadadmin.board.entity;

import com.ssg.starroadadmin.board.enums.BoardCategory;
import com.ssg.starroadadmin.global.entity.BaseTimeEntity;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private BoardCategory category;
}
