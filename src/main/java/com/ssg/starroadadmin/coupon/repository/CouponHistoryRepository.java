package com.ssg.starroadadmin.coupon.repository;

import com.ssg.starroadadmin.coupon.entity.CouponHistory;
import com.ssg.starroadadmin.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponHistoryRepository extends JpaRepository<CouponHistory, Long> {
}
