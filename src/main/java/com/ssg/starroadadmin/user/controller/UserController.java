package com.ssg.starroadadmin.user.controller;

import com.ssg.starroadadmin.user.dto.SearchUserRequest;
import com.ssg.starroadadmin.user.dto.UserListResponse;
import com.ssg.starroadadmin.user.dto.UserResponse;
import com.ssg.starroadadmin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/list")
    public String userList(Model model,
                           // jwt로 받아온 관리자 ID
                           @ModelAttribute("searchRequest") SearchUserRequest searchRequest,
                           Pageable pageable) {
        Long adminManagerId = 8L; // 삭제해야할 부분

        Page<UserListResponse> userListResponses = userService.searchUserList(adminManagerId, searchRequest, pageable);

        model.addAttribute("userList", userListResponses);
        model.addAttribute("pages", userListResponses);

        return "user/userList";
    }

    @GetMapping("/{userId}")
    public String userDetail(Model model,
                             // jwt로 받아온 관리자 ID
                             @PathVariable Long userId) {
        Long adminManagerId = 8L; // 삭제해야할 부분

        UserResponse userResponse = userService.getUser(adminManagerId, userId);

        model.addAttribute("userResponse", userResponse);

        return "user/userDetail";
    }
}
