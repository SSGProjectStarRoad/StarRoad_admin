package com.ssg.starroadadmin.shop.service;

import com.ssg.starroadadmin.shop.dto.*;
import com.ssg.starroadadmin.shop.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

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
    Page<StoreListResponse> searchStoreList(Long mallManagerId, SearchStoreRequest request, Pageable pageable);

    /**
     * 매장 상세 조회
     * 매장 상세 정보를 조회
     *
     * @param storeId
     * @return
     */
    StoreResponse getStore(Long managerId, Long storeId);

    /**
     * 매장 정보 수정
     * 매장 정보를 수정
     * 매장 설명, 운영시간, 연락처 수정
     *
     * @param managerId
     * @param storeId
     * @param request
     */
    void updateStore(Long managerId, Long storeId, StoreModifyRequest request);

    /**
     * 매장 이미지 수정
     * 매장 이미지를 수정
     *
     * @param managerId
     * @param storeId
     * @param file
     */
    void updateStoreImage(Long managerId, Long storeId, MultipartFile file);

    /**
     * 매장 삭제
     * 매장을 삭제
     *
     * @param managerId
     * @param storeId
     */
    void deleteStore(Long managerId, Long storeId);

}
