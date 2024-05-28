package com.ssg.starroadadmin.reward.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

@Repository
@RequiredArgsConstructor
public class RewardRepositoryCustomImpl implements RewardRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<RewardListResponse> findAllByCondition(RewardSortType sortType, Pageable pageable) {
        List<RewardListResponse> result = queryFactory
                .select(Projections.constructor(RewardListResponse.class,
                        reward.id,
                        reward.name,
                        reward.rewardImagePath,
                        reward.createdAt,
                        reward.modifiedAt
                ))
                .from(reward)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier(sortType))
                .fetch();

        JPAQuery<Reward> count = queryFactory
                .selectFrom(reward);

        return PageableExecutionUtils.getPage(result, pageable, count::fetchCount);
    }

    private OrderSpecifier orderSpecifier(RewardSortType sortType) {
        return switch (sortType == null ? RewardSortType.NAME_ASC : sortType) {
            case NAME_ASC -> reward.name.asc();
            case NAME_DESC -> reward.name.desc();
            case CREATED_AT_ASC -> reward.createdAt.asc();
            case CREATED_AT_DESC -> reward.createdAt.desc();
        };
    }
}
