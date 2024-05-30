package com.ssg.starroadadmin.user.dto;


import com.ssg.starroadadmin.user.enums.Authority;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManagerRegisterRequest {
    private String username;
    private String password;
    private String name;
    private String businessNumber;
    private Authority authority;
}
