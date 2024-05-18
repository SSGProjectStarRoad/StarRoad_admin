package com.ssg.starroadadmin.shop.dto;

import com.ssg.starroadadmin.shop.entity.Store;
import com.ssg.starroadadmin.shop.enums.Floor;
import com.ssg.starroadadmin.shop.enums.StoreType;
import com.ssg.starroadadmin.user.entity.Manager;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record StoreCreateRequest(

        String createStoreManagerName,
        String createStoreManagerId,
        String createStoreManagerPassword,
        String createBusinessNumber,
        String createStoreName,
        Floor createStoreFloor,
        String createStoreType,
        MultipartFile createStoreGuideMap){
    public StoreCreateRequest {
        // 기본 생성자에 필드 초기화 추가
    }



}
