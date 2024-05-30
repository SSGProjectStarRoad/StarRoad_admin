package com.ssg.starroadadmin.user.service.impl;

import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.code.UserErrorCode;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.global.error.exception.UsersException;
import com.ssg.starroadadmin.user.dto.SearchUserRequest;
import com.ssg.starroadadmin.user.dto.UserListResponse;
import com.ssg.starroadadmin.user.dto.UserResponse;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.entity.User;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import com.ssg.starroadadmin.user.repository.UserRepository;
import com.ssg.starroadadmin.user.repository.UserRepositoryCustom;
import com.ssg.starroadadmin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ManagerRepository managerRepository;
    private final UserRepositoryCustom userRepositoryCustom;
    private final UserRepository userRepository;

    @Override
    public Page<UserListResponse> searchUserList(String email, SearchUserRequest request, Pageable pageable) {
        // 관리자 권한 확인
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        if (manager.getAuthority() != Authority.ADMIN) {
            throw new ManagerException(ManagerErrorCode.ACCESS_DENIED);
        }

        Page<UserListResponse> users = userRepositoryCustom.findAllByStartDateAndEndDateAndEmailAndNameAndNickNameAndSortType(request.startDate(), request.endDate(), request.sortType(), request.email(), request.name(), request.nickname(), pageable);

        return users;
    }

    @Override
    public UserResponse getUser(String email, Long userId) {
        // 관리자 권한 확인
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        if (manager.getAuthority() != Authority.ADMIN) {
            throw new ManagerException(ManagerErrorCode.ACCESS_DENIED);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsersException(UserErrorCode.USER_NOT_FOUND));


        UserResponse response = userRepositoryCustom.findById(userId)
                .orElseThrow(() -> new UsersException(UserErrorCode.USER_NOT_FOUND));

        return response;
    }
}
