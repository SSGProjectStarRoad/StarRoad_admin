package com.ssg.starroadadmin.coupon.repository;

import com.ssg.starroadadmin.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long>{
}
