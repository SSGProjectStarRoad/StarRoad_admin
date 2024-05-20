package com.ssg.starroadadmin.user.service;

import com.ssg.starroadadmin.user.dto.PopularUserResponse;

import java.util.List;

public interface FollowService {
    List<PopularUserResponse> getPopularUserList();
}
