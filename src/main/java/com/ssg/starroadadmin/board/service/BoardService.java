package com.ssg.starroadadmin.board.service;

import com.ssg.starroadadmin.board.dto.BoardCreateRequest;
import com.ssg.starroadadmin.board.dto.BoardListResponse;
import com.ssg.starroadadmin.board.dto.BoardResponse;
import com.ssg.starroadadmin.board.dto.SearchBoardRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    /**
     * 권한 및 쇼핑몰 별 게시판 목록 조회
     *
     * @param email
     * @param searchRequest
     * @param pageable
     * @return
     */
    Page<BoardListResponse> searchBoardList(String email, SearchBoardRequest searchRequest, Pageable pageable);

    /**
     * 게시판 생성
     *
     * @param email
     * @param request
     */
    void createBoard(String email, BoardCreateRequest request);

    /**
     * 게시판 상세 조회
     *
     * @param email
     * @param boardId
     * @return
     */
    BoardResponse getBoardDetail(String email, Long boardId);
}
