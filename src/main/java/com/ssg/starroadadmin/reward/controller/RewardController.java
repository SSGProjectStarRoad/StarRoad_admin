 package com.ssg.starroadadmin.reward.controller;

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
            // jwt로 받아온 관리자 ID
            @ModelAttribute("searchRequest") RewardListRequest searchRequest,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        Long mallManagerId = 8L; // 삭제해야할 부분

        Page<RewardListResponse> rewardListResponses = rewardService.searchRewardList(mallManagerId, searchRequest, pageable);

        model.addAttribute("rewardList", rewardListResponses);
        model.addAttribute("pages", rewardListResponses);

        return "reward/rewardList";
    }

    @PostMapping("/create")
    public String createReward(
            // jwt로 받아온 관리자 ID
            @ModelAttribute("storeCreateRequest") RewardRegisterRequest request
    ) {
        Long adminManagerId = 8L; // 삭제해야할 부분

        rewardService.createReward(adminManagerId, request);

        return "redirect:/rewards/list";
    }

    @GetMapping("/list/user/{userId}")
    public String rewardListByUser(
            Model model,
            @PathVariable Long userId,
            // jwt로 받아온 관리자 ID
            @ModelAttribute("searchRequest") RewardListRequest searchRequest,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        Long mallManagerId = 8L; // 삭제해야할 부분

        Page<RewardListResponse> rewardListResponses = rewardService.searchUserRewardList(mallManagerId, userId, searchRequest, pageable);

        model.addAttribute("rewardList", rewardListResponses);
        model.addAttribute("pages", rewardListResponses);

        return "reward/userRewardHistoryList";
    }

    @GetMapping("/detail/{rewardId}")
    public String rewardDetail(
            Model model,
            // jwt로 받아온 관리자 ID
            @PathVariable Long rewardId,
            @PageableDefault Pageable pageable
    ) {
        // jwt로 받아온 관리자 ID
        Long mallManagerId = 8L; // 삭제해야할 부분

        // 리워드 상세 조회
        RewardDetailResponse rewardDetail = rewardService.searchRewardDetail(mallManagerId, rewardId, pageable);

        model.addAttribute("rewardDetail", rewardDetail);
        model.addAttribute("pages", rewardDetail.userList());

        return "reward/rewardDetail";
    }

    @PostMapping("/image")
     public String uploadImage(
             @RequestParam("image") MultipartFile image,
             @RequestParam("rewardId") Long rewardId
     ) {
         // jwt로 받아온 관리자 ID
         Long mallManagerId = 8L; // 삭제해야할 부분

         rewardService.uploadImage(mallManagerId, rewardId, image);

         return "redirect:/rewards/detail/" + rewardId;
     }
}
