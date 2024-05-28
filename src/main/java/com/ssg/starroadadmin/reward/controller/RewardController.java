 package com.ssg.starroadadmin.reward.controller;

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
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;

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
}
