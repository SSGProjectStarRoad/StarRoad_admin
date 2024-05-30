package com.ssg.starroadadmin.board.controller;

import com.ssg.starroadadmin.board.dto.BoardCreateRequest;
import com.ssg.starroadadmin.board.dto.BoardListResponse;
import com.ssg.starroadadmin.board.dto.BoardResponse;
import com.ssg.starroadadmin.board.dto.SearchBoardRequest;
import com.ssg.starroadadmin.board.service.BoardService;
import com.ssg.starroadadmin.user.entity.Manager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public String list(Model model,
                       @AuthenticationPrincipal Manager manager,
                       @ModelAttribute("searchRequest") SearchBoardRequest searchRequest,
                       @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<BoardListResponse> boardListResponses = boardService.searchBoardList(manager.getUsername(), searchRequest, pageable);

        model.addAttribute("boardList", boardListResponses);
        model.addAttribute("pages", boardListResponses);
        return "board/boardList";
    }

    @PostMapping("/create")
    public String create(
            @AuthenticationPrincipal Manager manager,
            @ModelAttribute("boardCreateRequest") BoardCreateRequest request) {

        boardService.createBoard(manager.getUsername(), request);
        return "redirect:/board/list";
    }

    @GetMapping("/detail/{boardId}")
    public String detail(Model model,
                         @AuthenticationPrincipal Manager manager,
                         @PathVariable Long boardId) {

        BoardResponse response = boardService.getBoardDetail(manager.getUsername(), boardId);
        model.addAttribute("boardResponse", response);
        return "board/boardDetail";
    }
}
