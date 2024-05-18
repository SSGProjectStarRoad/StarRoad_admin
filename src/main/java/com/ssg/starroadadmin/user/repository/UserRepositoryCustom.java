package com.ssg.starroadadmin.user.repository;

import com.ssg.starroadadmin.user.dto.UserListResponse;
import com.ssg.starroadadmin.user.dto.UserResponse;
import com.ssg.starroadadmin.user.entity.User;
import com.ssg.starroadadmin.user.enums.UserSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface UserRepositoryCustom {
    Page<UserListResponse> findAllByStartDateAndEndDateAndEmailAndNameAndNickNameAndSortType(LocalDate startDate, LocalDate endDate, UserSortType sortType, String email, String name, String nickname, Pageable pageable);

    Optional<UserResponse> findById(Long userId);
}
