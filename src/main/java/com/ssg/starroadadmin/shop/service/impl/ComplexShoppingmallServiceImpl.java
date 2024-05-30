package com.ssg.starroadadmin.shop.service.impl;

import com.ssg.starroadadmin.global.error.code.ComplexmallErrorCode;
import com.ssg.starroadadmin.global.error.code.ManagerErrorCode;
import com.ssg.starroadadmin.global.error.exception.ComplexmallException;
import com.ssg.starroadadmin.global.error.exception.ManagerException;
import com.ssg.starroadadmin.shop.entity.ComplexShoppingmall;
import com.ssg.starroadadmin.shop.repository.ComplexShoppingmallRepository;
import com.ssg.starroadadmin.shop.service.ComplexShoppingmallService;
import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComplexShoppingmallServiceImpl implements ComplexShoppingmallService {
    private final ManagerRepository managerRepository;
    private final ComplexShoppingmallRepository complexShoppingmallRepository;

    /**
     * 복합몰 이름 가져오기
     *
     * @param email
     * @return
     */
    @Override
    public String getComplexShoppingmallName(String email) {
        Manager mallManager = managerRepository.findByUsername(email)
                .orElseThrow(() -> new ManagerException(ManagerErrorCode.MANAGER_NOT_FOUND));

        ComplexShoppingmall mall = complexShoppingmallRepository.findByManagerId(mallManager.getId())
                .orElseThrow(() -> new ComplexmallException(ComplexmallErrorCode.COMPLEXMALL_NOT_FOUND));

        return mall.getName();
    }
}
