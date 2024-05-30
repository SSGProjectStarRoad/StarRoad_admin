 package com.ssg.starroadadmin.reward.controller;

 import com.ssg.starroadadmin.user.entity.Manager;
 import com.ssg.starroadadmin.reward.dto.RewardDetailResponse;
 import com.ssg.starroadadmin.reward.dto.RewardListRequest;
 import com.ssg.starroadadmin.reward.dto.RewardListResponse;
 import com.ssg.starroadadmin.reward.dto.RewardRegisterRequest;
 import com.ssg.starroadadmin.reward.service.RewardService;
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
 import org.springframework.web.multipart.MultipartFile;

 @Slf4j
@Controller
@RequestMapping("/rewards")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    @GetMapping("/list")
    public String rewardList(
            Model model,
            @AuthenticationPrincipal Manager manager,
            @ModelAttribute("searchRequest") RewardListRequest searchRequest,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        String email = manager.getUsername(); // 이메일을 직접 가져옴

        Page<RewardListResponse> rewardListResponses = rewardService.searchRewardList(email, searchRequest, pageable);

        model.addAttribute("rewardList", rewardListResponses);
        model.addAttribute("pages", rewardListResponses);

        return "reward/rewardList";
    }

    @PostMapping("/create")
    public String createReward(
            @AuthenticationPrincipal Manager manager,
            @ModelAttribute("storeCreateRequest") RewardRegisterRequest request
    ) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴

        rewardService.createReward(email, request);

        return "redirect:/rewards/list";
    }

    @GetMapping("/list/user/{userId}")
    public String rewardListByUser(
            Model model,
            @PathVariable Long userId,
            @AuthenticationPrincipal Manager manager,
            @ModelAttribute("searchRequest") RewardListRequest searchRequest,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        String email = manager.getUsername(); // 이메일을 직접 가져옴

        Page<RewardListResponse> rewardListResponses = rewardService.searchUserRewardList(email, userId, searchRequest, pageable);

        model.addAttribute("rewardList", rewardListResponses);
        model.addAttribute("pages", rewardListResponses);

        return "reward/userRewardHistoryList";
    }

    @GetMapping("/detail/{rewardId}")
    public String rewardDetail(
            Model model,
            @AuthenticationPrincipal Manager manager,
            @PathVariable Long rewardId,
            @PageableDefault Pageable pageable
    ) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴

        // 리워드 상세 조회
        RewardDetailResponse rewardDetail = rewardService.searchRewardDetail(email, rewardId, pageable);

        model.addAttribute("rewardDetail", rewardDetail);
        model.addAttribute("pages", rewardDetail.userList());

        return "reward/rewardDetail";
    }

    @PostMapping("/image")
     public String uploadImage(
             @RequestParam("image") MultipartFile image,
             @RequestParam("rewardId") Long rewardId,
             @AuthenticationPrincipal Manager manager
     ) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴

         rewardService.uploadImage(email, rewardId, image);

         return "redirect:/rewards/detail/" + rewardId;
     }
}
