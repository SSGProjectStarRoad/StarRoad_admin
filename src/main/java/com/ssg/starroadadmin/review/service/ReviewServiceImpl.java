package com.ssg.starroadadmin.review.service;

import com.ssg.starroadadmin.global.dto.BetweenDate;
import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.code.ShopErrorCode;
import com.ssg.starroadadmin.global.error.code.UserErrorCode;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.global.error.exception.ShopException;
import com.ssg.starroadadmin.global.error.exception.UserException;
import com.ssg.starroadadmin.review.dto.ReviewListResponse;
import com.ssg.starroadadmin.review.dto.StoreReviewSearchRequest;
import com.ssg.starroadadmin.review.dto.UserReviewSearchRequest;
import com.ssg.starroadadmin.review.repository.ReviewRepositoryCustom;
import com.ssg.starroadadmin.shop.entity.Store;
import com.ssg.starroadadmin.shop.repository.StoreRepository;
import com.ssg.starroadadmin.user.entity.User;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import com.ssg.starroadadmin.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepositoryCustom reviewRepositoryCustom;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;

    /**
     * 매장별 리뷰 리스트 조회
     *
     * @param reviewSearchRequest
     * @return
     */
    @Override
    public Page<ReviewListResponse> searchReviewList(Long managerId, StoreReviewSearchRequest reviewSearchRequest) {
        managerRepository.findByIdAndAuthorityNot(managerId, Authority.STORE)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        Store store = storeRepository.findById(reviewSearchRequest.storeId())
                .orElseThrow(() -> new ShopException(ShopErrorCode.STORE_NOT_FOUND));

        BetweenDate betweenDate = BetweenDate.builder()
                .startDate(reviewSearchRequest.startDate())
                .endDate(reviewSearchRequest.endDate())
                .build();

        Pageable pageable = reviewSearchRequest.pageable();

        Page<ReviewListResponse> page = reviewRepositoryCustom.findAllByStoreIdAndBetweenDate(store.getId(), betweenDate, reviewSearchRequest.sortType(), pageable);

        return page;
    }

    /**
     * 사용자별 리뷰 리스트 조회
     *
     * @param reviewSearchRequest
     * @return
     */
    @Override
    public Page<ReviewListResponse> searchReviewList(Long managerId, UserReviewSearchRequest reviewSearchRequest) {
        managerRepository.findByIdAndAuthorityNot(managerId, Authority.STORE)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        User user = userRepository.findById(reviewSearchRequest.storeId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        BetweenDate betweenDate = BetweenDate.builder()
                .startDate(reviewSearchRequest.startDate())
                .endDate(reviewSearchRequest.endDate())
                .build();

        Pageable pageable = reviewSearchRequest.pageable();

        Page<ReviewListResponse> page = reviewRepositoryCustom.findAllByUserIdAndBetweenDate(user.getId(), betweenDate, reviewSearchRequest.sortType(), pageable);

        return page;
    }
}
