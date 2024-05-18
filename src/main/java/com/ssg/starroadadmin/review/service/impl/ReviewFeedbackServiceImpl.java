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

        Store store = null;
        if (manager.getAuthority() == Authority.STORE) {
            store = storeRepository.findByIdAndManagerId(storeId, managerId)
                    .orElseThrow(() -> new ShopException(ShopErrorCode.ACCESS_DENIED));
        } else if (manager.getAuthority() == Authority.MALL) {
            store = storeRepository.findByIdAndComplexShoppingmallManagerId(storeId, managerId)
                    .orElseThrow(() -> new ShopException(ShopErrorCode.ACCESS_DENIED));
        } else { // 총 관리자일 겨웅
            store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new ShopException(ShopErrorCode.STORE_NOT_FOUND));
        }

        return reviewFeedbackRepositoryCustom.getStoreFeedback(store.getId());
    }
}
