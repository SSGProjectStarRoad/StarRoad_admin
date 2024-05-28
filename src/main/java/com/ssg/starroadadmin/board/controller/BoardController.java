package com.ssg.starroadadmin.board.controller;

import com.ssg.starroadadmin.board.dto.BoardCreateRequest;
import com.ssg.starroadadmin.board.dto.BoardListResponse;
import com.ssg.starroadadmin.board.dto.SearchBoardRequest;
import com.ssg.starroadadmin.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public String list(Model model,
                       // jwt로 받아온 관리자 ID,
                       @ModelAttribute("searchRequest") SearchBoardRequest searchRequest,
                       @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Long mallManagerId = 5L; // 삭제해야할 부분

        Page<BoardListResponse> boardListResponses = boardService.searchBoardList(mallManagerId, searchRequest, pageable);

        model.addAttribute("boardList", boardListResponses);
        model.addAttribute("pages", boardListResponses);
        return "board/boardList";
    }

    @PostMapping("/create")
    public String create(
            // jwt로 받아온 관리자 ID
            @ModelAttribute("boardCreateRequest") BoardCreateRequest request) {
        Long mallManagerId = 5L; // 삭제해야할 부분

        boardService.createBoard(mallManagerId, request);
        return "redirect:/board/list";
    }
}
