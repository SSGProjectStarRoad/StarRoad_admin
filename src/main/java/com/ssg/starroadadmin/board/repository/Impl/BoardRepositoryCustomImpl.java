package com.ssg.starroadadmin.board.repository.Impl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.starroadadmin.board.dto.BoardListResponse;
import com.ssg.starroadadmin.board.dto.SearchBoardRequest;
import com.ssg.starroadadmin.board.entity.Board;
import com.ssg.starroadadmin.board.enums.BoardCategory;
import com.ssg.starroadadmin.board.enums.BoardSortType;
import com.ssg.starroadadmin.board.repository.BoardRepositoryCustom;
import com.ssg.starroadadmin.review.enums.ReviewSortType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssg.starroadadmin.board.entity.QBoard.board;
import static com.ssg.starroadadmin.review.entity.QReview.review;
import static com.ssg.starroadadmin.shop.entity.QComplexShoppingmall.complexShoppingmall;
import static com.ssg.starroadadmin.user.entity.QManager.manager;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BoardListResponse> findAllByMallManager(Long mallManagerId, SearchBoardRequest searchRequest, Pageable pageable) {
        List<BoardListResponse> fetch = queryFactory.select(Projections.constructor(BoardListResponse.class,
                        board.id.as("id"),
                        board.title.as("title"),
                        board.category.as("boardCategory"),
                        complexShoppingmall.name.as("shoppingmallName"),
                        board.createdAt.as("createdAt"),
                        board.modifiedAt.as("modifiedAt")
                        )
                )
                .from(board)
                .innerJoin(manager).on(board.manager.id.eq(manager.id))
                .innerJoin(complexShoppingmall).on(manager.id.eq(complexShoppingmall.manager.id))
                .where(
                        mallManagerEq(mallManagerId),
                        boardTitleLike(searchRequest.boardName()),
                        boardCategoryEq(searchRequest.boardCategory())
                )
                .orderBy(orderSpecifier(searchRequest.sortType()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Board> count = queryFactory.selectFrom(board)
                .innerJoin(manager).on(board.manager.id.eq(manager.id))
                .innerJoin(complexShoppingmall).on(manager.id.eq(complexShoppingmall.manager.id))
                .where(
                        mallManagerEq(mallManagerId),
                        boardTitleLike(searchRequest.boardName()),
                        boardCategoryEq(searchRequest.boardCategory())
                );

        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchCount);
    }

    @Override
    public Page<BoardListResponse> findAllCondition(Long adminId, SearchBoardRequest searchRequest, Pageable pageable) {
        return null;
    }

    private BooleanExpression mallManagerEq(Long mallManagerId) {
        return mallManagerId != null ? complexShoppingmall.manager.id.eq(mallManagerId) : null;
    }

    private BooleanExpression boardTitleLike(String title) {
        return title != null ? board.title.contains(title) : null;
    }

    private BooleanExpression boardCategoryEq(BoardCategory category) {
        return category != null ? board.category.eq(category) : null;
    }

    private OrderSpecifier orderSpecifier(BoardSortType sortType) {
        return switch (sortType != null ? sortType : BoardSortType.CREATED_AT_DESC) {
            case TITLE_ASC -> board.title.asc();
            case TITLE_DESC -> board.title.desc();
            case CREATED_AT_ASC -> board.createdAt.asc();
            case CREATED_AT_DESC -> board.createdAt.desc();
        };
    }

}
