package com.ssg.starroadadmin.review.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.starroadadmin.global.dto.BetweenDate;
import com.ssg.starroadadmin.review.dto.ReviewDetails;
import com.ssg.starroadadmin.review.dto.ReviewListResponse;
import com.ssg.starroadadmin.review.dto.ReviewListWithOutImagesAndFeedbacksResponse;
import com.ssg.starroadadmin.review.enums.ReviewSortType;
import com.ssg.starroadadmin.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ssg.starroadadmin.review.entity.QReview.review;
import static com.ssg.starroadadmin.review.entity.QReviewFeedback.reviewFeedback;
import static com.ssg.starroadadmin.review.entity.QReviewImage.reviewImage;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReviewListResponse> findAllByStoreIdAndBetweenDate(Long storeId, BetweenDate betweenDate, ReviewSortType reviewSortType, Pageable pageable) {
        // 리뷰 세부 정보 가져오기 (이미지 와 피드백은 가져오지 않음)
        List<ReviewListWithOutImagesAndFeedbacksResponse> reviews = queryFactory
                .select(Projections.constructor(ReviewListWithOutImagesAndFeedbacksResponse.class,
                        // 쇼핑몰
                        review.store.complexShoppingmall.id.as("mallId"),
                        review.store.complexShoppingmall.name.as("mallName"),
                        // 매장
                        review.store.id.as("storeId"),
                        review.store.name.as("storeName"),
                        review.store.floor.as("floor"),
                        review.store.imagePath.as("storeImagePath"),
                        // 리뷰
                        review.id.as("reviewId"),
                        review.visible.as("visible"),
                        review.likeCount.as("likeCount"),
                        review.contents.as("contents"),
                        review.confidence.as("confidence"),
                        review.createdAt.as("createdAt"),
                        review.modifiedAt.as("modifiedAt"),
                        // 유저
                        review.user.id.as("userId"),
                        review.user.name.as("userName"),
                        review.user.nickname.as("nickname"),
                        review.user.imagePath.as("userImagePath")
                ))
                .from(review)
                .where(
                        storeIdEq(storeId),
                        createdAtBetween(betweenDate.startDate(), betweenDate.endDate())
                )
                .orderBy(orderSpecifier(reviewSortType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 리뷰별 이미지와 피드백을 추적하기 위한 Map
        Map<Long, ReviewDetails> reviewImagesMap = new HashMap<>();

        // 리뷰 이미지와 피드백 선택을 별도로 가져오기
        reviews.forEach(r -> {
            List<String> images = queryFactory
                    .select(reviewImage.imagePath)
                    .from(reviewImage)
                    .where(reviewImage.review.id.eq(r.reviewId()))
                    .fetch();

            List<String> feedbacks = queryFactory
                    .select(reviewFeedback.reviewFeedbackSelection)
                    .from(reviewFeedback)
                    .where(reviewFeedback.review.id.eq(r.reviewId()))
                    .fetch();

            // 리뷰별 이미지와 피드백을 Map에 저장
            reviewImagesMap.put(r.reviewId(), new ReviewDetails(images, feedbacks));
        });

        // 이미지와 피드백을 ReviewListResponse에 넣기
        List<ReviewListResponse> completeReviews = reviews.stream()
                .map(r -> new ReviewListResponse(
                        r.mallId(), r.mallName(), r.storeId(), r.storeName(), r.floor(), r.storeImagePath(),
                        r.reviewId(), r.visible(), r.likeCount(), r.contents(), r.confidence(),
                        r.createdAt(), r.modifiedAt(),
                        reviewImagesMap.get(r.reviewId()).images(),
                        reviewImagesMap.get(r.reviewId()).feedbacks(),
                        r.userId(), r.userName(), r.nickname(), r.userImagePath()
                ))
                .collect(Collectors.toList());

        JPAQuery<Review> count = queryFactory
                .selectFrom(review)
                .where(
                        storeIdEq(storeId),
                        createdAtBetween(betweenDate.startDate(), betweenDate.endDate())
                );

        return PageableExecutionUtils.getPage(completeReviews, pageable, count::fetchCount);
    }

    @Override
    public Page<ReviewListResponse> findAllByUserIdAndBetweenDate(Long userId, BetweenDate betweenDate, ReviewSortType reviewSortType, Pageable pageable) {
        // 리뷰 세부 정보 가져오기 (이미지 와 피드백은 가져오지 않음)
        List<ReviewListWithOutImagesAndFeedbacksResponse> reviews = queryFactory
                .select(Projections.constructor(ReviewListWithOutImagesAndFeedbacksResponse.class,
                        // 쇼핑몰
                        review.store.complexShoppingmall.id.as("mallId"),
                        review.store.complexShoppingmall.name.as("mallName"),
                        // 매장
                        review.store.id.as("storeId"),
                        review.store.name.as("storeName"),
                        review.store.floor.as("floor"),
                        review.store.imagePath.as("storeImagePath"),
                        // 리뷰
                        review.id.as("reviewId"),
                        review.visible.as("visible"),
                        review.likeCount.as("likeCount"),
                        review.contents.as("contents"),
                        review.confidence.as("confidence"),
                        review.createdAt.as("createdAt"),
                        review.modifiedAt.as("modifiedAt"),
                        // 유저
                        review.user.id.as("userId"),
                        review.user.name.as("userName"),
                        review.user.nickname.as("nickname"),
                        review.user.imagePath.as("userImagePath")
                ))
                .from(review)
                .where(
                        userIdEq(userId),
                        createdAtBetween(betweenDate.startDate(), betweenDate.endDate())
                )
                .orderBy(orderSpecifier(reviewSortType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 리뷰별 이미지와 피드백을 추적하기 위한 Map
        Map<Long, ReviewDetails> reviewImagesMap = new HashMap<>();

        // 리뷰 이미지와 피드백 선택을 별도로 가져오기
        reviews.forEach(r -> {
            List<String> images = queryFactory
                    .select(reviewImage.imagePath)
                    .from(reviewImage)
                    .where(reviewImage.review.id.eq(r.reviewId()))
                    .fetch();

            List<String> feedbacks = queryFactory
                    .select(reviewFeedback.reviewFeedbackSelection)
                    .from(reviewFeedback)
                    .where(reviewFeedback.review.id.eq(r.reviewId()))
                    .fetch();

            // 리뷰별 이미지와 피드백을 Map에 저장
            reviewImagesMap.put(r.reviewId(), new ReviewDetails(images, feedbacks));
        });

        // 이미지와 피드백을 ReviewListResponse에 넣기
        List<ReviewListResponse> completeReviews = reviews.stream()
                .map(r -> new ReviewListResponse(
                        r.mallId(), r.mallName(), r.storeId(), r.storeName(), r.floor(), r.storeImagePath(),
                        r.reviewId(), r.visible(), r.likeCount(), r.contents(), r.confidence(),
                        r.createdAt(), r.modifiedAt(),
                        reviewImagesMap.get(r.reviewId()).images(),
                        reviewImagesMap.get(r.reviewId()).feedbacks(),
                        r.userId(), r.userName(), r.nickname(), r.userImagePath()
                ))
                .collect(Collectors.toList());

        JPAQuery<Review> count = queryFactory
                .selectFrom(review)
                .where(
                        userIdEq(userId),
                        createdAtBetween(betweenDate.startDate(), betweenDate.endDate())
                );

        return PageableExecutionUtils.getPage(completeReviews, pageable, count::fetchCount);
    }

    private BooleanExpression storeIdEq(Long storeId) {
        return storeId != null ? review.store.id.eq(storeId) : null;
    }
    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? review.store.id.eq(userId) : null;
    }

    private BooleanExpression createdAtBetween(LocalDate start, LocalDate end) {
        return start != null && end != null ? review.createdAt.between(start.atStartOfDay(), end.atTime(LocalTime.MAX)) : null;
    }

    private OrderSpecifier orderSpecifier(ReviewSortType sortType) {
        return switch (sortType) {
            case CREATED_AT_ASC -> review.createdAt.asc();
            case CREATED_AT_DESC -> review.createdAt.desc();
            case LIKE_COUNT_ASC -> review.likeCount.asc();
            case LIKE_COUNT_DESC -> review.likeCount.desc();
        };
    }
}
