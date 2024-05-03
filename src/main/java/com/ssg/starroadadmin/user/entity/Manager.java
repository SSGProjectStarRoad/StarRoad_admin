package com.ssg.starroadadmin.user.entity;

import com.ssg.starroadadmin.common.entity.BaseTimeEntity;
import com.ssg.starroadadmin.user.enums.Authority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Manager extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password; // bcrypt
    private String name;
    @Column(unique = true)
    private String businessNumber;
    private Authority authority;
}
