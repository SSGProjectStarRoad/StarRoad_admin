package com.ssg.starroadadmin.user.repository;

import com.ssg.starroadadmin.user.dto.PopularUserResponse;
import com.ssg.starroadadmin.user.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowRepositoryCustom {

    /**
     * 사용자별 팔로워 수, 팔로잉 수, 리뷰 수를 조회하여 인기 유저 리스트를 반환한다.
     *
     * @return 인기 유저 리스트
     */
    Optional<List<PopularUserResponse>> findTop5ByUser();
}
