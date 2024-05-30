package com.ssg.starroadadmin.user.controller;

import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.dto.SearchUserRequest;
import com.ssg.starroadadmin.user.dto.UserListResponse;
import com.ssg.starroadadmin.user.dto.UserResponse;
import com.ssg.starroadadmin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
                           @AuthenticationPrincipal Manager manager,
                           @ModelAttribute("searchRequest") SearchUserRequest searchRequest,
                           Pageable pageable) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴

        Page<UserListResponse> userListResponses = userService.searchUserList(email, searchRequest, pageable);

        model.addAttribute("userList", userListResponses);
        model.addAttribute("pages", userListResponses);

        return "user/userList";
    }

    @GetMapping("/detail/{userId}")
    public String userDetail(Model model,
                             @AuthenticationPrincipal Manager manager,
                             @PathVariable Long userId) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴

        UserResponse userResponse = userService.getUser(email, userId);

        model.addAttribute("userResponse", userResponse);

        return "user/userDetail";
    }
}
