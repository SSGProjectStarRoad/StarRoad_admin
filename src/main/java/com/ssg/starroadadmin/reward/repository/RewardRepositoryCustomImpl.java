package com.ssg.starroadadmin.reward.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.starroadadmin.reward.dto.RewardDetailUser;
import com.ssg.starroadadmin.reward.dto.RewardListRequest;
import com.ssg.starroadadmin.reward.dto.RewardListResponse;
import com.ssg.starroadadmin.reward.entity.Reward;
import com.ssg.starroadadmin.reward.enums.RewardSortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssg.starroadadmin.reward.entity.QReward.reward;
import static com.ssg.starroadadmin.reward.entity.QRewardHistory.rewardHistory;

@Repository
@RequiredArgsConstructor
public class RewardRepositoryCustomImpl implements RewardRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<RewardListResponse> findAllByCondition(RewardListRequest request, Pageable pageable) {
        List<RewardListResponse> result = queryFactory
                .select(Projections.constructor(RewardListResponse.class,
                        reward.id,
                        reward.name,
                        reward.rewardImagePath,
                        reward.createdAt,
                        reward.modifiedAt
                ))
                .from(reward)
                .where(rewardNameContains(request.rewardName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier(request.sortType()))
                .fetch();

        JPAQuery<Reward> count = queryFactory
                .selectFrom(reward);

        return PageableExecutionUtils.getPage(result, pageable, count::fetchCount);
    }

    @Override
    public Page<RewardListResponse> findAllByUserId(Long userId, RewardListRequest searchRequest, Pageable pageable) {
        List<RewardListResponse> result = queryFactory
                .select(Projections.constructor(RewardListResponse.class,
                        reward.id,
                        reward.name,
                        reward.rewardImagePath,
                        rewardHistory.createdAt,
                        rewardHistory.modifiedAt
                ))
                .from(rewardHistory)
                .join(reward).on(reward.id.eq(rewardHistory.rewardId))
                .where(
                        rewardHistory.user.id.eq(userId),
                        rewardNameContains(searchRequest.rewardName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier(searchRequest.sortType()))
                .fetch();

        JPAQuery<RewardListResponse> count = queryFactory
                .select(Projections.constructor(RewardListResponse.class,
                        reward.id,
                        reward.name,
                        reward.rewardImagePath,
                        rewardHistory.createdAt,
                        rewardHistory.modifiedAt
                ))
                .from(rewardHistory)
                .join(reward).on(reward.id.eq(rewardHistory.rewardId))
                .where(
                        rewardHistory.user.id.eq(userId),
                        rewardNameContains(searchRequest.rewardName())
                );

        return PageableExecutionUtils.getPage(result, pageable, count::fetchCount);
    }

    @Override
    public Page<RewardDetailUser> findAllByRewardId(Long rewardId, Pageable pageable) {
        List<RewardDetailUser> fetch = queryFactory
                .select(Projections.constructor(RewardDetailUser.class,
                        rewardHistory.user.id.as("userId"),
                        rewardHistory.user.nickname.as("userNickname"),
                        rewardHistory.user.imagePath.as("userImage"),
                        rewardHistory.count().as("rewardCount")
                ))
                .from(rewardHistory)
                .where(rewardHistory.rewardId.eq(rewardId))
                .groupBy(rewardHistory.user.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderHistorySpecifier(RewardSortType.CREATED_AT_DESC))
                .fetch();

        JPAQuery<RewardDetailUser> count = queryFactory
                .select(Projections.constructor(RewardDetailUser.class,
                        rewardHistory.user.id,
                        rewardHistory.user.nickname,
                        rewardHistory.user.imagePath,
                        rewardHistory.count()
                ))
                .from(rewardHistory)
                .where(rewardHistory.rewardId.eq(rewardId))
                .groupBy(rewardHistory.user.id);

        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchCount);
    }

    private BooleanExpression rewardNameContains(String rewardName) {
        return rewardName == null || rewardName.isEmpty() ? null : reward.name.contains(rewardName);
    }

    private OrderSpecifier orderSpecifier(RewardSortType sortType) {
        return switch (sortType == null ? RewardSortType.CREATED_AT_DESC : sortType) {
            case NAME_ASC -> reward.name.asc();
            case NAME_DESC -> reward.name.desc();
            case CREATED_AT_ASC -> reward.createdAt.asc();
            case CREATED_AT_DESC -> reward.createdAt.desc();
        };
    }

    private OrderSpecifier orderHistorySpecifier(RewardSortType sortType) {
        return switch (sortType == null ? RewardSortType.CREATED_AT_DESC : sortType) {
            case NAME_ASC -> reward.name.asc();
            case NAME_DESC -> reward.name.desc();
            case CREATED_AT_ASC -> rewardHistory.createdAt.asc();
            case CREATED_AT_DESC -> rewardHistory.createdAt.desc();
        };
    }
}
