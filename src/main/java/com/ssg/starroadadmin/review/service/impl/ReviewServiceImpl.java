package com.ssg.starroadadmin.review.service.impl;

import com.ssg.starroadadmin.global.dto.BetweenDate;
import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.code.ReviewErrorCode;
import com.ssg.starroadadmin.global.error.code.ShopErrorCode;
import com.ssg.starroadadmin.global.error.code.UserErrorCode;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.global.error.exception.ReviewException;
import com.ssg.starroadadmin.global.error.exception.ShopException;
import com.ssg.starroadadmin.global.error.exception.UsersException;
import com.ssg.starroadadmin.review.dto.*;
import com.ssg.starroadadmin.review.entity.Review;
import com.ssg.starroadadmin.review.repository.*;
import com.ssg.starroadadmin.review.service.ReviewService;
import com.ssg.starroadadmin.shop.entity.Store;
import com.ssg.starroadadmin.shop.repository.StoreRepository;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.entity.User;
import com.ssg.starroadadmin.user.repository.FollowRepository;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import com.ssg.starroadadmin.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepositoryCustom reviewRepositoryCustom;
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewFeedbackRepository reviewFeedbackRepository;
    private final ReviewSentimentRepository reviewSentimentRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;
    private final FollowRepository followRepository;

    /**
     * 매장별 리뷰 리스트 조회
     *
     * @param email
     * @param reviewSearchRequest
     * @return
     */
    @Override
    public Page<ReviewListWithDasyAgoResponse> searchReviewList(String email, StoreReviewSearchRequest reviewSearchRequest, Pageable pageable) {
        managerRepository.findByUsername(email)
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
     * @param email
     * @param reviewSearchRequest
     * @return
     */
    @Override
    public Page<ReviewListWithDasyAgoResponse> searchReviewList(String email, UserReviewSearchRequest reviewSearchRequest, Pageable pageable) {
        managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.ACCESS_DENIED));



        User user = userRepository.findById(reviewSearchRequest.userId())
                .orElseThrow(() -> new UsersException(UserErrorCode.USER_NOT_FOUND));

        System.out.println("user.getId() = " + user.getId());

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

    /**
     * 리뷰 상세 조회
     *
     * @param email
     * @param reviewId
     * @return
     */
    @Override
    public ReviewDetailResponse getReview(String email, Long reviewId) {
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));
        log.debug("manager.getAuthority() : {}", manager.getAuthority());

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        ReviewDetailWithOutList reviewDetailWithOutList = reviewRepositoryCustom.findAllByManager(manager, reviewId);

        // 리뷰 이미지들
        // 리뷰 선택지들
        // 리뷰 sentiment
        ExcludedReviewList excludedList = ExcludedReviewList.builder()
                .reviewImages(reviewImageRepository.findAllByReviewId(reviewId)
                        .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_IMAGE_NOT_FOUND)))
                .reviewFeedbacks(reviewFeedbackRepository.findAllByReviewId(reviewId)
                        .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_FEEDBACK_NOT_FOUND)))
                .reviewSentimentResponses(reviewSentimentRepository.findAllByReviewId(reviewId)
                        .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_SENTIMENT_NOT_FOUND)))
                .build();

        return ReviewDetailResponse.from(reviewDetailWithOutList, excludedList);
    }

    /**
     * 최근 리뷰 5개 조회
     *
     * @return
     */
    @Override
    public List<RecentReviewResponse> getRecentReviewList() {
        List<Review> recentReviewList = reviewRepository.findTop5ByOrderByCreatedAtDesc()
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        return recentReviewList.stream()
                .map(review -> {
                    User user = userRepository.findById(review.getUser().getId())
                            .orElseThrow(() -> new UsersException(UserErrorCode.USER_NOT_FOUND));

                    Long followerCount = followRepository.countByToUser_Id(user.getId());
                    Long followingCount = followRepository.countByFromUser_Id(user.getId());

                    return RecentReviewResponse.builder()
                            .reviewId(review.getId())
                            .userName(user.getName())
                            .userImagePath(user.getImagePath())
                            .userImagePath(user.getImagePath())
                            .followerCount(followerCount)
                            .followingCount(followingCount)
                            .contents(review.getContents())
                            .build();
                })
                .toList();
    }

    /**
     * 인기 매장 5개 조회
     * 리뷰가 가장 많이 달린 매장 조회
     *
     * @return
     */
    @Override
    public List<PopularStore> getPopularStoreList() {
        List<PopularStore> popularStore = reviewRepositoryCustom.findTop5PopularStore()
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));
        log.debug("popularStore : {}", popularStore);
        return popularStore;
    }
}
