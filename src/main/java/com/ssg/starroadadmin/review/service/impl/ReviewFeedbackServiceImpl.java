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
    @Override
    public List<StoreFeedbackResponse> getStoreFeedback(Long managerId, Long storeId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        Store store = checkStoreAccessLevel(storeId, manager);

        return reviewFeedbackRepositoryCustom.getStoreFeedback(store.getId());
    }

    @Override
    public List<StoreFeedbackResponse> getStoreRequiredFeedback(Long managerId, Long storeId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        Store store = checkStoreAccessLevel(storeId, manager);

        return reviewFeedbackRepositoryCustom.getStoreRequiredFeedback(store.getId());
    }

    @Override
    public List<StoreFeedbackResponse> getStoreOptionalFeedback(Long managerId, Long storeId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        Store store = checkStoreAccessLevel(storeId, manager);

        return reviewFeedbackRepositoryCustom.getStoreOptionalFeedback(store.getId());
    }

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
