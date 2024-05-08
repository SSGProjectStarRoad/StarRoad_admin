package com.ssg.starroadadmin.user.entity;

import com.ssg.starroadadmin.global.entity.BaseTimeEntity;
import com.ssg.starroadadmin.user.enums.ActiveStatus;
import com.ssg.starroadadmin.user.enums.Gender;
import com.ssg.starroadadmin.user.enums.ProviderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String password;
    private String name;
    @Column(unique = true)
    private String nickname;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column
    private LocalDate birth;
    private String phone;
    @Column(unique = true)
    private String email;
    private String imagePath;

    private int reviewExp;
    private int point;

    @Enumerated(EnumType.STRING)
    private ActiveStatus activeStatus;

    private ProviderType providerType; // 네이버, 카카오, 구글
    private String providerId; // (네이버, 카카오, 구글)에서 받아온 아이디
}
