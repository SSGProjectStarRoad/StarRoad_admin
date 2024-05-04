package com.ssg.starroadadmin.user.entity;

import com.ssg.starroadadmin.global.entity.BaseTimeEntity;
import com.ssg.starroadadmin.user.enums.ActiveStatus;
import com.ssg.starroadadmin.user.enums.Gender;
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

    @Column(unique = true)
    private String username;
    private String password; // bcrypt
    private String name;
    @Column(unique = true)
    private String nickname;
    private String imagePath; // profile image
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column
    private LocalDate birth;
    private String phone; // xxx-xxxx-xxxx
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String provider; // provider + providerId => unique

    private int reviewExp;
    private int point;

    @Enumerated(EnumType.STRING)
    private ActiveStatus activeStatus;
}
