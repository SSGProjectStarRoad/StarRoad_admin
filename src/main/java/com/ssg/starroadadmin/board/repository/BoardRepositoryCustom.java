package com.ssg.starroadadmin.board.repository;

import com.ssg.starroadadmin.board.dto.BoardListResponse;
import com.ssg.starroadadmin.board.dto.SearchBoardRequest;
import com.ssg.starroadadmin.user.entity.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    /**
     * 쇼핑몰 관리자 별 게시판 리스트 조회
     *
     * @param manager
     * @param searchRequest
     * @param pageable
     * @return
     */
    Page<BoardListResponse> findAllByMallManager(Long MallManagerId, SearchBoardRequest searchRequest, Pageable pageable);

    /**
     * 프로젝트 관리자가 보는 게시판 리스트 조회
     *
     * @param manager
     * @param searchRequest
     * @param pageable
     * @return
     */
    Page<BoardListResponse> findAllCondition(Long AdminId, SearchBoardRequest searchRequest, Pageable pageable);
}
