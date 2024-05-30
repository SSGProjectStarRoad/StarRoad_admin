package com.ssg.starroadadmin.coupon.repository.impl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.starroadadmin.coupon.dto.UserCouponRequest;
import com.ssg.starroadadmin.coupon.dto.UserCouponResponse;
import com.ssg.starroadadmin.coupon.enums.CouponSortType;
import com.ssg.starroadadmin.coupon.repository.CouponHistoryRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.ssg.starroadadmin.coupon.entity.QCoupon.coupon;
import static com.ssg.starroadadmin.coupon.entity.QCouponHistory.couponHistory;
import static com.ssg.starroadadmin.user.entity.QUser.user;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CouponHistoryRepositoryCustomImpl implements CouponHistoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<UserCouponResponse> findAllByCondition(UserCouponRequest request, Pageable pageable) {
        List<UserCouponResponse> fetch = queryFactory.select(Projections.constructor(UserCouponResponse.class,
                        couponHistory.id.as("couponHistoryId"),
                        user.id.as("userId"),
                        user.nickname.as("userNickname"),
                        coupon.id.as("couponId"),
                        coupon.name.as("couponName"),
                        coupon.shopType.as("couponShopType"),
                        couponHistory.usageStatus.as("useStatus"),
                        couponHistory.discountRate.as("discountRate"),
                        couponHistory.createdAt.as("createdAt"),
                        couponHistory.modifiedAt.as("modifiedAt"),
                        couponHistory.expiredAt.as("expiredAt")
                ))
                .from(couponHistory)
                .join(coupon).on(couponHistory.couponId.eq(coupon.id))
                .join(user).on(couponHistory.user.id.eq(user.id))
                .where(
                        userNicknameEq(request.userNickname()),
                        couponNameEq(request.couponName()),
                        usageStatusEq(request.usageStatus()),
                        startDateGoe(request.startDate()),
                        endDateLoe(request.endDate())
                )
                .orderBy(orderSpecifier(request.sortType()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<UserCouponResponse> count = queryFactory.select(Projections.constructor(UserCouponResponse.class,
                        couponHistory.id.as("couponHistoryId"),
                        user.id.as("userId"),
                        user.nickname.as("userNickname"),
                        coupon.id.as("couponId"),
                        coupon.name.as("couponName"),
                        coupon.shopType.as("couponShopType"),
                        couponHistory.usageStatus.as("useStatus"),
                        couponHistory.discountRate.as("discountRate"),
                        couponHistory.createdAt.as("createdAt"),
                        couponHistory.modifiedAt.as("modifiedAt"),
                        couponHistory.expiredAt.as("expiredAt")
                ))
                .from(couponHistory)
                .join(coupon).on(couponHistory.couponId.eq(coupon.id))
                .join(user).on(couponHistory.user.id.eq(user.id))
                .where(
                        userNicknameEq(request.userNickname()),
                        couponNameEq(request.couponName()),
                        usageStatusEq(request.usageStatus()),
                        startDateGoe(request.startDate()),
                        endDateLoe(request.endDate())
                );

        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchCount);
    }

    private BooleanExpression userNicknameEq(String nickname) {
        return nickname != null && !nickname.isEmpty() ? user.nickname.eq(nickname) : null;
    }

    private BooleanExpression usageStatusEq(String usageStatus) {
        if (usageStatus == null || usageStatus.isEmpty()) {
            return null;
        }
        if (usageStatus.equals("Y")) {
            return couponHistory.usageStatus.eq(true);
        } else if (usageStatus.equals("N")) {
            return couponHistory.usageStatus.eq(false);
        }
        return null;
    }

    private BooleanExpression couponNameEq(String couponName) {
        return couponName != null && !couponName.isEmpty() ? coupon.name.eq(couponName) : null;
    }

    private BooleanExpression startDateGoe(LocalDate startDate) {
        return startDate != null ? couponHistory.createdAt.goe(startDate.atStartOfDay()) : null;
    }

    private BooleanExpression endDateLoe(LocalDate endDate) {
        return endDate != null ? couponHistory.createdAt.loe(endDate.plusDays(1).atStartOfDay()) : null;
    }

    // 정렬
    private OrderSpecifier orderSpecifier(CouponSortType sortType) {
        return switch (sortType != null ? sortType : CouponSortType.CREATED_AT_DESC) {
            case NAME_ASC -> coupon.name.asc();
            case NAME_DESC -> coupon.name.desc();
            case USER_NAME_ASC -> user.nickname.asc();
            case USER_NAME_DESC -> user.nickname.desc();
            case CREATED_AT_ASC -> couponHistory.createdAt.asc();
            case CREATED_AT_DESC -> couponHistory.createdAt.desc();
        };
    }
}
