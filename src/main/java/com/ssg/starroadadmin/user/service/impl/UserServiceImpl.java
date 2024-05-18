package com.ssg.starroadadmin.user.service.impl;

import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.code.UserErrorCode;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.global.error.exception.UsersException;
import com.ssg.starroadadmin.review.repository.ReviewRepository;
import com.ssg.starroadadmin.user.dto.SearchUserRequest;
import com.ssg.starroadadmin.user.dto.UserListResponse;
import com.ssg.starroadadmin.user.dto.UserResponse;
import com.ssg.starroadadmin.user.entity.User;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import com.ssg.starroadadmin.user.repository.UserRepository;
import com.ssg.starroadadmin.user.repository.UserRepositoryCustom;
import com.ssg.starroadadmin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ManagerRepository managerRepository;
    private final UserRepositoryCustom userRepositoryCustom;
    private final UserRepository userRepository;

    @Override
    public Page<UserListResponse> searchUserList(Long adminManagerId, SearchUserRequest request, Pageable pageable) {
        // 관리자 권한 확인
        managerRepository.findByIdAndAuthority(adminManagerId, Authority.ADMIN)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        Page<UserListResponse> users = userRepositoryCustom.findAllByStartDateAndEndDateAndEmailAndNameAndNickNameAndSortType(request.startDate(), request.endDate(), request.sortType(), request.email(), request.name(), request.nickname(), pageable);

        return users;
    }

    @Override
    public UserResponse getUser(Long adminManagerId, Long userId) {
        // 관리자 권한 확인
        managerRepository.findByIdAndAuthority(adminManagerId, Authority.ADMIN)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsersException(UserErrorCode.USER_NOT_FOUND));


        UserResponse response = userRepositoryCustom.findById(userId)
                .orElseThrow(() -> new UsersException(UserErrorCode.USER_NOT_FOUND));

        return response;
    }
}
