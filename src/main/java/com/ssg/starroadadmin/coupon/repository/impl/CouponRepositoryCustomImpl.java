package com.ssg.starroadadmin.coupon.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.starroadadmin.coupon.dto.SearchCouponResponse;
import com.ssg.starroadadmin.coupon.entity.Coupon;
import com.ssg.starroadadmin.coupon.repository.CouponRepositoryCustom;
import com.ssg.starroadadmin.review.entity.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssg.starroadadmin.coupon.entity.QCoupon.coupon;
import static com.ssg.starroadadmin.shop.entity.QComplexShoppingmall.complexShoppingmall;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CouponRepositoryCustomImpl implements CouponRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SearchCouponResponse> findAllByManagerId(Long managerId, Pageable pageable) {
        List<SearchCouponResponse> fetch = queryFactory.select(Projections.constructor(SearchCouponResponse.class,
                        coupon.id.as("couponId"),
                        coupon.name.as("couponName"),
                        coupon.shopType.as("couponShopType"),
                        coupon.discountAmount.as("couponDiscountAmount"),
                        coupon.discountRate.as("couponDiscountRate"),
                        coupon.minAmount.as("couponMinAmount"),
                        coupon.maxAmount.as("couponMaxAmount"),
                        coupon.status.as("couponStatus"),
                        complexShoppingmall.name.as("couponComplexShoppingmallName"),
                        coupon.createdAt.as("couponCreatedAt"),
                        coupon.modifiedAt.as("couponModifiedAt")
                ))
                .from(coupon)
                .innerJoin(complexShoppingmall).on(coupon.complexShoppingmall.eq(complexShoppingmall.name))
                .where(complexShoppingmall.manager.id.eq(managerId))
                .fetch();

        JPAQuery<Coupon> count = queryFactory.selectFrom(coupon)
                .innerJoin(complexShoppingmall).on(coupon.complexShoppingmall.eq(complexShoppingmall.name))
                .where(complexShoppingmall.manager.id.eq(managerId));

        //PageableExecutionUtils.getPage(completeReviews, pageable, count::fetchCount);
        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchCount);
    }
}
