package com.ssg.starroadadmin.user.service.impl;

import com.ssg.starroadadmin.global.error.code.FollowErrorCode;
import com.ssg.starroadadmin.global.error.exception.FollowException;
import com.ssg.starroadadmin.user.dto.PopularUserResponse;
import com.ssg.starroadadmin.user.repository.FollowRepository;
import com.ssg.starroadadmin.user.repository.FollowRepositoryCustom;
import com.ssg.starroadadmin.user.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
//    private final FollowRepositoryCustom followRepositoryCustom;
    private final FollowRepository followRepository;

    @Override
    public List<PopularUserResponse> getPopularUserList() {
        List<PopularUserResponse> top5ByUserList = followRepository.findTop5ByFollow();
//        List<PopularUserResponse> top5ByUserList = followRepositoryCustom.findTop5ByUser()
//                .orElseThrow(() -> new FollowException(FollowErrorCode.FOLLOW_LIST_NOT_FOUND));
        log.info("top5ByUserList: {}", top5ByUserList);
        return top5ByUserList;
    }
}
