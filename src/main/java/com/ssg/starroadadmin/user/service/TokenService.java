package com.ssg.starroadadmin.user.service;

public interface TokenService {
    String createNewAccessToken(String refreshToken);
}
