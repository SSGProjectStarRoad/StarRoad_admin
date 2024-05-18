package com.ssg.starroadadmin.user.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.starroadadmin.user.dto.UserListResponse;
import com.ssg.starroadadmin.user.dto.UserResponse;
import com.ssg.starroadadmin.user.entity.Follow;
import com.ssg.starroadadmin.user.entity.User;
import com.ssg.starroadadmin.user.enums.UserSortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.ExpressionUtils.count;
import static com.querydsl.core.types.Projections.constructor;
import static com.ssg.starroadadmin.review.entity.QReview.review;
import static com.ssg.starroadadmin.user.entity.QFollow.follow;
import static com.ssg.starroadadmin.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<UserListResponse> findAllByStartDateAndEndDateAndEmailAndNameAndNickNameAndSortType(LocalDate startDate, LocalDate endDate,
                                                                                                    UserSortType sortType, String email,
                                                                                                    String name, String nickname,
                                                                                                    Pageable pageable) {
        JPAQuery<UserListResponse> query = queryFactory
                .select(constructor(UserListResponse.class,
                        user.id,
                        user.name,
                        user.nickname,
                        user.email,
                        user.imagePath,
                        user.birth,
                        user.createdAt,
                        user.activeStatus,
                        review.id.count().as("reviewCount"),
                        follow.id.count().as("followerCount")
                ))
                .from(user)
                .leftJoin(review).on(user.id.eq(review.user.id))
                .leftJoin(follow).on(user.id.eq(follow.toUser.id))
                .where(
                        startDateEq(startDate),
                        endDateEq(endDate),
                        emailEq(email),
                        nameEq(name),
                        nicknameEq(nickname)
                )
                .groupBy(user.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier(sortType));

        List<UserListResponse> fetch = query.fetch();

        JPAQuery<User> countQuery = queryFactory
                .selectFrom(user)
                .where(
                        startDateEq(startDate),
                        endDateEq(endDate)
                );

        return PageableExecutionUtils.getPage(fetch, pageable, countQuery::fetchCount);
    }

    @Override
    public Optional<UserResponse> findById(Long userId) {

        UserResponse userResponse = queryFactory
                .select(constructor(UserResponse.class,
                        user.id,
                        user.name,
                        user.email,
                        user.nickname,
                        user.phone,
                        user.birth,
                        user.imagePath,
                        user.reviewExp,
                        ExpressionUtils.as(
                                JPAExpressions.
                                        select(count(review.id))
                                        .from(review)
                                        .where(
                                                review.user.id.eq(user.id)
                                        ), "reviewCount"),
                        ExpressionUtils.as(
                                JPAExpressions.
                                        select(count(follow.id))
                                        .from(follow)
                                        .where(
                                                follow.fromUser.id.eq(user.id)
                                        ), "followerCount"),
                        ExpressionUtils.as(
                                JPAExpressions.
                                        select(count(follow.id))
                                        .from(follow)
                                        .where(
                                                follow.toUser.id.eq(user.id)
                                        ), "followingCount"),
                        user.activeStatus,
                        user.createdAt,
                        user.providerType
                ))
                .from(user)
                .where(user.id.eq(userId))
                .fetchOne();

        return Optional.of(userResponse);
    }

    private BooleanExpression startDateEq(LocalDate startDate) {
        return startDate != null ? user.createdAt.goe(startDate.atStartOfDay()) : null;
    }

    private BooleanExpression endDateEq(LocalDate endDate) {
        return endDate != null ? user.createdAt.loe(endDate.atTime(23, 59, 59)) : null;
    }

    private BooleanExpression emailEq(String email) {
        return email != null ? user.email.contains(email) : null;
    }

    private BooleanExpression nameEq(String name) {
        return name != null ? user.name.contains(name) : null;
    }

    private BooleanExpression nicknameEq(String nickname) {
        return nickname != null ? user.nickname.contains(nickname) : null;
    }

    private OrderSpecifier<?> orderSpecifier(UserSortType sortType) {
        if (sortType == null) {
            return user.createdAt.asc(); // Default sorting
        }
        return switch (sortType) {
            case NAME_ASC -> user.name.asc();
            case NAME_DESC -> user.name.desc();
            case REVIEW_COUNT_ASC -> review.id.count().asc();
            case REVIEW_COUNT_DESC -> review.id.count().desc();
            case FOLLOWER_COUNT_ASC -> follow.id.count().asc();
            case FOLLOWER_COUNT_DESC -> follow.id.count().desc();
            case CREATED_AT_ASC -> user.createdAt.asc();
            case CREATED_AT_DESC -> user.createdAt.desc();
        };
    }

    private BooleanExpression followerIdEq(Long userId) {
        return userId != null ? follow.fromUser.id.eq(userId) : null;
    }

    private BooleanExpression followingIdEq(Long userId) {
        return userId != null ? follow.toUser.id.eq(userId) : null;
    }
}
