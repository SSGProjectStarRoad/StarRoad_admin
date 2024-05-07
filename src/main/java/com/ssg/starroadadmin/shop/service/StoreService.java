package com.ssg.starroadadmin.shop.service;

import com.ssg.starroadadmin.shop.dto.SearchStoreRequest;
import com.ssg.starroadadmin.shop.dto.StoreListResponse;
import com.ssg.starroadadmin.shop.dto.StoreRegisterRequest;
import com.ssg.starroadadmin.shop.entity.Store;
import org.springframework.data.domain.Page;

public interface StoreService {

    /**
     * 매장 등록
     * 매장명, 층수, 매장 타입, 매장 관리자 아이디를 입력 받아
     * 쇼핑몰 관리자가 매장을 등록
     *
     * @param mallManagerId
     * @param request
     */
    Long createStore(Long mallManagerId, StoreRegisterRequest request);

    /**
     * 매장 목록 조회
     * 쇼핑몰 관리자 권한이 없는 경우 예외 처리
     * 쇼핑몰 관리자의 아이디를 통해서 쇼핑몰에 속한 매장 목록을 조회
     * 매장 이름(input), 층수(select), 매장 타입(select)을 통해서 검색 가능
     *
     * @param mallManagerId
     * @param request
     * @return
     */
    Page<StoreListResponse> searchStoreList(Long mallManagerId, SearchStoreRequest request);

    /**
     * 매장 상세 조회
     * 매장 상세 정보를 조회
     *
     * @param storeId
     * @return
     */
    Store getStore(long storeId);
}
