package com.ssg.starroadadmin.shop.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.starroadadmin.shop.dto.StoreListResponse;
import com.ssg.starroadadmin.shop.entity.Store;
import com.ssg.starroadadmin.shop.enums.Floor;
import com.ssg.starroadadmin.shop.enums.StoreSortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssg.starroadadmin.shop.entity.QStore.store;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<StoreListResponse> findByComplexShoppingmallIdAndNameContainingAndFloorAndStoreType(
            Long complexShoppingmallId, String name, Floor floor, String storeType, StoreSortType sortType, Pageable pageable
    ) {
        List<StoreListResponse> fetch = queryFactory
                .select(Projections.constructor(StoreListResponse.class,
                        store.id,
                        store.name,
                        store.storeType,
                        store.imagePath,
                        store.floor,
                        store.contactNumber))
                .from(store)
                .where(
                        complexShoppingmallIdEq(complexShoppingmallId),
                        nameContains(name),
                        floorEq(floor),
                        storeTypeEq(storeType)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier(sortType))
                .fetch();

        JPAQuery<Store> count = queryFactory
                .selectFrom(store)
                .where(
                        complexShoppingmallIdEq(complexShoppingmallId),
                        nameContains(name),
                        floorEq(floor),
                        storeTypeEq(storeType)
                );

        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchCount);
    }

    private BooleanExpression complexShoppingmallIdEq(Long complexShoppingmallId) {
        return complexShoppingmallId != null ? store.complexShoppingmall.id.eq(complexShoppingmallId) : null;
    }
    private BooleanExpression nameContains(String name) {
        return hasText(name) ? store.name.contains(name) : null;
    }

    private BooleanExpression floorEq(Floor floor) {
        return floor != null ? store.floor.eq(floor) : null;
    }

    private BooleanExpression storeTypeEq(String storeType) {
        return hasText(storeType) ? store.storeType.eq(storeType) : null;
    }

    private OrderSpecifier orderSpecifier(StoreSortType sortType) {
        return switch (sortType) {
            case NAME_ASC -> store.name.asc();
            case NAME_DESC -> store.name.desc();
            case FLOOR_ASC -> store.floor.asc();
            case FLOOR_DESC -> store.floor.desc();
            case CREATED_AT_ASC -> store.createdAt.asc();
            case CREATED_AT_DESC -> store.createdAt.desc();
            default -> store.name.asc();
        };
    }
}
