package com.ssg.starroadadmin.review.service.impl;

import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.code.ShopErrorCode;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.global.error.exception.ShopException;
import com.ssg.starroadadmin.review.repository.ReviewFeedbackRepositoryCustom;
import com.ssg.starroadadmin.review.service.ReviewFeedbackService;
import com.ssg.starroadadmin.shop.dto.StoreFeedbackResponse;
import com.ssg.starroadadmin.shop.entity.Store;
import com.ssg.starroadadmin.shop.repository.StoreRepository;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewFeedbackServiceImpl implements ReviewFeedbackService {

    private final ManagerRepository managerRepository;
    private final StoreRepository storeRepository;
    private final ReviewFeedbackRepositoryCustom reviewFeedbackRepositoryCustom;

    /**
     * 매장 피드백 조회
     * 매장 피드백을 조회
     *
     * @param email
     * @param storeId
     * @return
     */
    @Override
    public List<StoreFeedbackResponse> getStoreFeedback(String email, Long storeId) {
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        Store store = checkStoreAccessLevel(storeId, manager);

        return reviewFeedbackRepositoryCustom.getStoreFeedback(store.getId());
    }

    /**
     * 매장 필수 피드백 조회
     * 매장 필수 피드백을 조회
     *
     * @param email
     * @param storeId
     * @return
     */
    @Override
    public List<StoreFeedbackResponse> getStoreRequiredFeedback(String email, Long storeId) {
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        Store store = checkStoreAccessLevel(storeId, manager);

        return reviewFeedbackRepositoryCustom.getStoreRequiredFeedback(store.getId());
    }

    /**
     * 매장 선택 피드백 조회
     * 매장 선택 피드백을 조회
     *
     * @param email
     * @param storeId
     * @return
     */
    @Override
    public List<StoreFeedbackResponse> getStoreOptionalFeedback(String email, Long storeId) {
        Manager manager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        Store store = checkStoreAccessLevel(storeId, manager);

        return reviewFeedbackRepositoryCustom.getStoreOptionalFeedback(store.getId());
    }

    /**
     * 매장 접근 권한 확인
     * 매장 접근 권한을 확인
     *
     * @param storeId
     * @param manager
     * @return
     */
    private Store checkStoreAccessLevel(Long storeId, Manager manager) {
        if (manager.getAuthority() == Authority.STORE) {
            return storeRepository.findByIdAndManagerId(storeId, manager.getId())
                    .orElseThrow(() -> new ShopException(ShopErrorCode.ACCESS_DENIED));
        } else if (manager.getAuthority() == Authority.MALL) {
            return storeRepository.findByIdAndComplexShoppingmallManagerId(storeId, manager.getId())
                    .orElseThrow(() -> new ShopException(ShopErrorCode.ACCESS_DENIED));
        } else { // 총 관리자일 겨웅
            return storeRepository.findById(storeId)
                    .orElseThrow(() -> new ShopException(ShopErrorCode.STORE_NOT_FOUND));
        }
    }
}
