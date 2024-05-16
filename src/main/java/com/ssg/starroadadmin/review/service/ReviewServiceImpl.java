package com.ssg.starroadadmin.review.service;

import com.ssg.starroadadmin.global.dto.BetweenDate;
import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.code.ShopErrorCode;
import com.ssg.starroadadmin.global.error.code.UserErrorCode;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.global.error.exception.ShopException;
import com.ssg.starroadadmin.global.error.exception.UserException;
import com.ssg.starroadadmin.review.dto.ReviewListResponse;
import com.ssg.starroadadmin.review.dto.ReviewListWithDasyAgoResponse;
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

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public Page<ReviewListWithDasyAgoResponse> searchReviewList(Long managerId, StoreReviewSearchRequest reviewSearchRequest, Pageable pageable) {
        managerRepository.findByIdAndAuthorityNot(managerId, Authority.STORE)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        Store store = storeRepository.findById(reviewSearchRequest.storeId())
                .orElseThrow(() -> new ShopException(ShopErrorCode.STORE_NOT_FOUND));

        BetweenDate betweenDate = BetweenDate.builder()
                .startDate(reviewSearchRequest.startDate())
                .endDate(reviewSearchRequest.endDate())
                .build();

        Page<ReviewListResponse> contents = reviewRepositoryCustom.findAllByStoreIdAndBetweenDate(store.getId(), betweenDate, reviewSearchRequest.sortType(), pageable);

        Page<ReviewListWithDasyAgoResponse> page = contents.map(reviewListResponse -> new ReviewListWithDasyAgoResponse(
                    reviewListResponse.mallId(),
                    reviewListResponse.mallName(),
                    reviewListResponse.storeId(),
                    reviewListResponse.storeName(),
                    reviewListResponse.floor(),
                    reviewListResponse.storeImagePath(),
                    reviewListResponse.reviewId(),
                    reviewListResponse.visible(),
                    reviewListResponse.likeCount(),
                    reviewListResponse.contents(),
                    reviewListResponse.confidence(),
                    reviewListResponse.createdAt(),
                    reviewListResponse.modifiedAt(),
                    getDaysAgo(reviewListResponse.modifiedAt()),
                    reviewListResponse.reviewImagePaths(),
                    reviewListResponse.reviewFeedbackSelection(),
                    reviewListResponse.userId(),
                    reviewListResponse.userName(),
                    reviewListResponse.nickname(),
                    reviewListResponse.userImagePath()
                )
            );

        return page;
    }

    /**
     * 사용자별 리뷰 리스트 조회
     *
     * @param reviewSearchRequest
     * @return
     */
    @Override
    public Page<ReviewListWithDasyAgoResponse> searchReviewList(Long managerId, UserReviewSearchRequest reviewSearchRequest, Pageable pageable) {
        managerRepository.findByIdAndAuthorityNot(managerId, Authority.STORE)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));

        User user = userRepository.findById(reviewSearchRequest.storeId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        BetweenDate betweenDate = BetweenDate.builder()
                .startDate(reviewSearchRequest.startDate())
                .endDate(reviewSearchRequest.endDate())
                .build();

        Page<ReviewListResponse> contents = reviewRepositoryCustom.findAllByUserIdAndBetweenDate(user.getId(), betweenDate, reviewSearchRequest.sortType(), pageable);

        Page<ReviewListWithDasyAgoResponse> page = contents.map(reviewListResponse -> new ReviewListWithDasyAgoResponse(
                        reviewListResponse.mallId(),
                        reviewListResponse.mallName(),
                        reviewListResponse.storeId(),
                        reviewListResponse.storeName(),
                        reviewListResponse.floor(),
                        reviewListResponse.storeImagePath(),
                        reviewListResponse.reviewId(),
                        reviewListResponse.visible(),
                        reviewListResponse.likeCount(),
                        reviewListResponse.contents(),
                        reviewListResponse.confidence(),
                        reviewListResponse.createdAt(),
                        reviewListResponse.modifiedAt(),
                        getDaysAgo(reviewListResponse.modifiedAt()),
                        reviewListResponse.reviewImagePaths(),
                        reviewListResponse.reviewFeedbackSelection(),
                        reviewListResponse.userId(),
                        reviewListResponse.userName(),
                        reviewListResponse.nickname(),
                        reviewListResponse.userImagePath()
                )
        );

        return page;
    }

    private int getDaysAgo(LocalDateTime createdAt) {
        return LocalDate.now().compareTo(createdAt.toLocalDate());
    }
}
