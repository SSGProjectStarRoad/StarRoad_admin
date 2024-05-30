package com.ssg.starroadadmin.shop.controller;

import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.shop.dto.*;
import com.ssg.starroadadmin.shop.service.StoreService;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final ManagerService managerService;


    @PostMapping("/create")
    public String create(
            @ModelAttribute("storeCreateRequest") StoreCreateRequest storeCreateRequest,
            @AuthenticationPrincipal Manager manager) {

        String email = manager.getUsername(); // 이메일을 직접 가져옴

        // 1. 받아온 데이터에서 일단 매장 관리자 메소드로 추가하기
        String username = storeCreateRequest.createStoreManagerId();
        String name = storeCreateRequest.createStoreManagerName();
        String businessNumber = storeCreateRequest.createBusinessNumber();
        String password = storeCreateRequest.createStoreManagerPassword();
//        Authority authority = Authority.ADMIN; // 관리자의 권한 설정 (예시)

        Manager newManager = managerService.createManager(username, password, businessNumber, name);

        // 2. 받아온 데이터에서 매장 데이터 매장 추가 메소드로 매장 추가하기
        Long managerId = newManager.getId();
        storeService.createStore(email, storeCreateRequest, managerId);

        // 3. 추후에 시큐리티 적용
        return "redirect:/store/list";
    }

    @GetMapping("/list")
    public String storeList(Model model,
                            @ModelAttribute("searchRequest") SearchStoreRequest searchRequest,
                            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
                            @AuthenticationPrincipal Manager manager
    ) {
        log.info("Manager: {}", manager);
        String email = manager.getUsername(); // 이메일을 직접 가져옴

        Page<StoreListResponse> storeListResponses = storeService.searchStoreList(email, searchRequest, pageable);
        model.addAttribute("storeList", storeListResponses);
        model.addAttribute("pages", storeListResponses);

        return "store/storeList";
    }

    @GetMapping("/detail/{storeId}")
    public String storeDetail(Model model
            , @PathVariable Long storeId, @AuthenticationPrincipal Manager manager) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴

        StoreResponse storeResponse = storeService.getStore(email, storeId);

        model.addAttribute("storeResponse", storeResponse);

        return "store/storeDetail";
    }

    @PostMapping("/logo")
    public String updateStoreLogo(@RequestParam("storeId") Long storeId,
                                  @AuthenticationPrincipal Manager manager,
                                  @RequestParam("logo") MultipartFile file) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴
        if (file.isEmpty()) {
            return "redirect:/store/detail/" + storeId;
        }

        storeService.updateStoreImage(email, storeId, file);

        return "redirect:/store/detail/" + storeId;
    }

    @PostMapping("/{storeId}/modify")
    public String modifyStoreInfo(@PathVariable("storeId") Long storeId,
                                  @AuthenticationPrincipal Manager manager,
                                  StoreModifyRequest storeModifyRequest) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴

        storeService.updateStore(email, storeId, storeModifyRequest);
        return "redirect:/store/detail/" + storeId;
    }

    @ResponseBody
    @GetMapping("/{storeId}/color")
    public ResponseEntity<StoreConfidenceResponse> getStoreConfidenceColor(
            @AuthenticationPrincipal Manager manager,
            @PathVariable Long storeId) {
        String email = manager.getUsername(); // 이메일을 직접 가져옴

        StoreConfidenceResponse storeConfidenceResponse = storeService.getStoreConfidenceColor(email, storeId);

        return ResponseEntity.ok(storeConfidenceResponse);
    }
}
