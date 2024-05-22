package com.ssg.starroadadmin.shop.controller;

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
            // jwt로 받아온 관리자 ID
            @ModelAttribute("storeCreateRequest") StoreCreateRequest storeCreateRequest) {
            Long id = 5L; // 삭제할 부분
            //1.받아온 데이터에서 일단 매장 관리자 메소드 로 추가 하기
            String username = storeCreateRequest.createStoreManagerId();
            String name = storeCreateRequest.createStoreManagerName();
            String businessNumber = storeCreateRequest.createBusinessNumber();
            String password = storeCreateRequest.createStoreManagerPassword();
        Manager manager = managerService.createManager(username, name, businessNumber, password);





        //2.받아온 데이터에서 매장 데이터 매장 추가 메소드로 매장 추가하기
            Long managerId = manager.getId();
        storeService.createStore(5L,storeCreateRequest,managerId);

            //3. 추후에 시큐리티 적용



        return "redirect:/store/list";
    }

    @GetMapping("/list")
    public String storeList(Model model,
                            // jwt로 받아온 관리자 ID
                            @ModelAttribute("searchRequest") SearchStoreRequest searchRequest,
                            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Long mallManagerId = 5L; // 삭제해야할 부분

        Page<StoreListResponse> storeListResponses = storeService.searchStoreList(mallManagerId, searchRequest, pageable);
        model.addAttribute("storeList", storeListResponses);
        model.addAttribute("pages", storeListResponses);

        return "store/storeList";
    }

    @GetMapping("/detail/{storeId}")
    public String storeDetail(Model model
            // jwt로 받아온 관리자 ID
            , @PathVariable Long storeId) {
        Long managerId = 5L; // 삭제해야할 부분

        StoreResponse storeResponse = storeService.getStore(managerId, storeId);

        model.addAttribute("storeResponse", storeResponse);

        return "store/storeDetail";
    }

    @PostMapping("/logo")
    public String updateStoreLogo(@RequestParam("storeId") Long storeId,
                                  // jwt로 받아온 관리자 ID
                                  @RequestParam("logo") MultipartFile file) {
            Long managerId = 5L; // 삭제해야할 부분
        if (file.isEmpty()) {
            return "redirect:/store/detail/" + storeId;
        }

        storeService.updateStoreImage(managerId, storeId, file);

        return "redirect:/store/detail/" + storeId;
    }

    @PostMapping("/{storeId}/modify")
    public String modifyStoreInfo(@PathVariable("storeId") Long storeId,
                                    // jwt로 받아온 관리자 ID
                                  StoreModifyRequest storeModifyRequest) {
        Long managerId = 9L; // 삭제해야할 부분

        storeService.updateStore(managerId, storeId, storeModifyRequest);
        return "redirect:/store/detail/" + storeId;
    }
}
