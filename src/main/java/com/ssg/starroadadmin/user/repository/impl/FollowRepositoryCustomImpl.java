package com.ssg.starroadadmin.user.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.starroadadmin.review.entity.QReview;
import com.ssg.starroadadmin.user.dto.PopularUserResponse;
import com.ssg.starroadadmin.user.entity.QFollow;
import com.ssg.starroadadmin.user.entity.User;
import com.ssg.starroadadmin.user.repository.FollowRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.ssg.starroadadmin.user.entity.QFollow.follow;
import static com.ssg.starroadadmin.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryCustomImpl implements FollowRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<PopularUserResponse>> findTop5ByUser() {
        List<User> popularUser = queryFactory
                .selectFrom(user)
                .innerJoin(follow).on(follow.toUser.eq(user))
                .orderBy(
                        follow.toUser.count().desc()
                )
                .fetch();

        return Optional.ofNullable(popularUser.stream().map(
                user -> new PopularUserResponse(
                        user.getId(),
                        user.getName(),
                        user.getImagePath(),
                        queryFactory.selectFrom(follow).where(follow.toUser.eq(user)).fetchCount(),
                        queryFactory.selectFrom(follow).where(follow.fromUser.eq(user)).fetchCount(),
                        JPAExpressions.selectFrom(QReview.review).where(QReview.review.user.eq(user)).fetchCount()
                )
        )
                .toList()
        );
    }
}
