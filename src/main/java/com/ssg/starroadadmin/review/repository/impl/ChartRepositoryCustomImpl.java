package com.ssg.starroadadmin.review.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.starroadadmin.review.dto.MallReviewCountResponse;
import com.ssg.starroadadmin.review.dto.MonthlyStoreReviewResponse;
import com.ssg.starroadadmin.review.dto.StoreReviewCountResponse;
import com.ssg.starroadadmin.review.enums.ConfidenceType;
import com.ssg.starroadadmin.review.repository.ChartRepositoryCustom;
import com.ssg.starroadadmin.user.entity.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.FALSE;
import static com.ssg.starroadadmin.review.entity.QReview.review;
import static com.ssg.starroadadmin.review.enums.ConfidenceType.*;
import static com.ssg.starroadadmin.shop.entity.QComplexShoppingmall.complexShoppingmall;
import static com.ssg.starroadadmin.shop.entity.QStore.store;

@Repository
@RequiredArgsConstructor
public class ChartRepositoryCustomImpl implements ChartRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<MallReviewCountResponse> findMallReviewsByManager(Manager manager) {
        return queryFactory
                .select(
                        Projections.constructor(MallReviewCountResponse.class,
                                review.count().as("reviewCount"),
                                review.createdAt.year().as("reviewYear"),
                                review.createdAt.month().as("reviewMonth"),
                                review.confidence.nullif(POSITIVE).count().as("positiveReviewCount"),
                                review.confidence.nullif(NEUTRAL).count().as("neutralReviewCount"),
                                review.confidence.nullif(NEGATIVE).count().as("negativeReviewCount")
                        ))
                .from(review)
                .innerJoin(store).on(review.store.id.eq(store.id))
                .innerJoin(complexShoppingmall).on(store.complexShoppingmall.id.eq(complexShoppingmall.id)
                )
                .where(
                        managerEq(manager),
                        // 오늘 날짜 기준 1년 전까지의 데이터만 조회
                        review.createdAt.after(LocalDateTime.now().minusYears(1))
                )
                .groupBy(
                        review.createdAt.year(),
                        review.createdAt.month()
                )
                .fetch();
    }

    @Override
    public List<StoreReviewCountResponse> findStoreReviewsByManager(Manager manager) {
        return queryFactory
                .select(
                        Projections.constructor(StoreReviewCountResponse.class,
                                store.name.as("storeName"),
                                review.count().as("reviewCount"),
                                ConfidenceCount(POSITIVE).as("positiveReviewCount"),
                                ConfidenceCount(NEUTRAL).as("neutralReviewCount"),
                                ConfidenceCount(NEGATIVE).as("negativeReviewCount")
                        ))
                .from(review)
                .innerJoin(store).on(review.store.id.eq(store.id))
                .innerJoin(complexShoppingmall).on(store.complexShoppingmall.id.eq(complexShoppingmall.id)
                )
                .where(
                        managerEq(manager),
                        // 오늘 날짜 기준 3개월 전까지의 데이터만 조회
                        review.createdAt.after(LocalDateTime.now().minusMonths(3))
                )
                .groupBy(
                        store.name
                )
                .fetch();
    }

    @Override
    public List<MonthlyStoreReviewResponse> findMonthlyStoreReview(Long storeId) {
        return queryFactory.select(Projections.constructor(MonthlyStoreReviewResponse.class,
                review.count().as("reviewCount"),
                review.createdAt.year().as("reviewYear"),
                review.createdAt.month().as("reviewMonth"),
                ConfidenceCount(POSITIVE).as("positiveReviewCount"),
                ConfidenceCount(NEUTRAL).as("neutralReviewCount"),
                ConfidenceCount(NEGATIVE).as("negativeReviewCount")
                ))
                .from(review)
                .innerJoin(store).on(review.store.id.eq(store.id))
                .where(
                        store.id.eq(storeId),
                        // 오늘 날짜 기준 12개월 전까지의 데이터만 조회
                        review.createdAt.after(LocalDateTime.now().minusMonths(12))
                )
                .groupBy(
                        review.createdAt.year(),
                        review.createdAt.month()
                )
                .fetch();
    }

    private NumberExpression<Long> ConfidenceCount(ConfidenceType confidenceType) {
        return review.confidence
                .when(confidenceType).then(1L)
                .otherwise(0L).sum();
    }

    private BooleanExpression managerEq(Manager manager) {
        switch (manager.getAuthority()) {
            case MALL:
                return complexShoppingmall.manager.id.eq(manager.getId());
            case STORE: // 매장 관리자에서 매장을 찾고 매창의 복합 쇼핑몰을 찾아서 복합 쇼핑몰의 pk와 쇼핑몰 pk가 같은지 확인
                return complexShoppingmall.id.eq(store.complexShoppingmall.id).and(store.manager.id.eq(manager.getId()));
            default:
                return FALSE;
        }
    }
}
