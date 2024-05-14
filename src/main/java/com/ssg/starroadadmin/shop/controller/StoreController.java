package com.ssg.starroadadmin.shop.controller;

import com.ssg.starroadadmin.shop.dto.SearchStoreRequest;
import com.ssg.starroadadmin.shop.dto.StoreListResponse;
import com.ssg.starroadadmin.shop.dto.StoreResponse;
import com.ssg.starroadadmin.shop.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/list")
    public String storeList(Model model,
                            // jwt로 받아온 관리자 ID
                            @ModelAttribute("searchRequest") SearchStoreRequest searchRequest,
                            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Long mallManagerId = 4L; // 삭제해야할 부분

        Page<StoreListResponse> storeListResponses = storeService.searchStoreList(mallManagerId, searchRequest, pageable);

        model.addAttribute("storeList", storeListResponses);
        model.addAttribute("pages", storeListResponses);

        return "store/storeList";
    }

    @GetMapping("/{storeId}")
    public String storeDetail(Model model
            // jwt로 받아온 관리자 ID
            , @PathVariable Long storeId) {
        Long managerId = 4L; // 삭제해야할 부분

        StoreResponse storeResponse = storeService.getStore(managerId, storeId);

        model.addAttribute("storeResponse", storeResponse);

        return "store/storeDetail";
    }
}
