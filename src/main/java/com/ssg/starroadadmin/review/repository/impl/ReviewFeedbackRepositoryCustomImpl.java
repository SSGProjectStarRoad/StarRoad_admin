package com.ssg.starroadadmin.review.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.starroadadmin.review.repository.ReviewFeedbackRepositoryCustom;
import com.ssg.starroadadmin.shop.dto.StoreFeedbackResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssg.starroadadmin.review.entity.QReview.review;
import static com.ssg.starroadadmin.review.entity.QReviewFeedback.reviewFeedback;

@Repository
@RequiredArgsConstructor
public class ReviewFeedbackRepositoryCustomImpl implements ReviewFeedbackRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<StoreFeedbackResponse> getStoreFeedback(Long storeId) {

        return queryFactory.select(
                        Projections.constructor(StoreFeedbackResponse.class,
                                reviewFeedback.reviewFeedbackSelection,
                                reviewFeedback.reviewFeedbackSelection.count()))
                .from(reviewFeedback)
                .join(review).on(reviewFeedback.review.id.eq(review.id))
                .where(review.store.id.eq(storeId))
                .groupBy(reviewFeedback.reviewFeedbackSelection)
                .fetch();

    }
}
