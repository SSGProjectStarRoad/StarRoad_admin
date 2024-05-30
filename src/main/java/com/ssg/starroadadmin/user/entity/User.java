package com.ssg.starroadadmin.user.entity;

import com.ssg.starroadadmin.global.entity.BaseTimeEntity;
import com.ssg.starroadadmin.user.enums.ActiveStatus;
import com.ssg.starroadadmin.user.enums.Gender;
import com.ssg.starroadadmin.user.enums.ProviderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;

    private String password;

    private String name;

    @Column(unique = true)
    private String nickname;

    private String imagePath; // profile image

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private LocalDate birth;

    private String phone;

    @Column(unique = true)
    private String email;

    private int reviewExp;
    private int point;

    @Enumerated(EnumType.STRING)
    private ActiveStatus activeStatus;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType; // 네이버, 카카오, 구글
    private String providerId; // (네이버, 카카오, 구글)에서 받아온 아이디

}
