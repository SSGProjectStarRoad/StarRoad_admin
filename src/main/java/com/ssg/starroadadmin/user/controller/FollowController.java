package com.ssg.starroadadmin.user.controller;

import com.ssg.starroadadmin.user.dto.PopularUserResponse;
import com.ssg.starroadadmin.user.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @ResponseBody
    @GetMapping("/user/popular")
    public ResponseEntity<List<PopularUserResponse>> popularUserList() {
        List<PopularUserResponse> popularUserList = followService.getPopularUserList();

        return ResponseEntity.ok().body(popularUserList);
    }
}
