package com.ssg.starroadadmin.user.service;

import com.ssg.starroadadmin.user.dto.SearchUserRequest;
import com.ssg.starroadadmin.user.dto.UserListResponse;
import com.ssg.starroadadmin.user.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {


    Page<UserListResponse> searchUserList(String email, SearchUserRequest request, Pageable pageable);

    UserResponse getUser(String email, Long userId);
}
