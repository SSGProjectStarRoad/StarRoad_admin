package com.ssg.starroadadmin.user.service;

import com.ssg.starroadadmin.user.entity.RefreshToken;

public interface RefreshTokenService {

    RefreshToken findByRefreshToken(String refreshToken);

    String createRefreshToken(Long managerId);

    void invalidateRefreshToken(String refreshToken);
}
