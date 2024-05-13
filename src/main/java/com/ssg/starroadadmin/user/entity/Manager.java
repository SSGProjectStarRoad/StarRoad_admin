package com.ssg.starroadadmin.user.entity;

import com.ssg.starroadadmin.global.entity.BaseTimeEntity;
import com.ssg.starroadadmin.user.enums.Authority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Manager extends BaseTimeEntity implements UserDetails {
    // UserDetails를 상속받아 인증객체로 사용

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password; // bcrypt
    private String name;
    @Column(unique = true)
    private String businessNumber;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
       //만료되었는지 확인하는 로직
        return false; // true => 만료되지 않음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return false; // true => 잠금되지 않았음
    }

    //패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        //패스워드가 만료되었는지 확인하는 로직
        return false; // true => 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return false; // true => 사용 가능
    }
}
