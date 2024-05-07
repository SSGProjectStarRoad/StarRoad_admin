package com.ssg.starroadadmin.shop.repository;

import com.ssg.starroadadmin.shop.entity.ComplexShoppingmall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComplexShoppingmallRepository extends JpaRepository<ComplexShoppingmall, Long> {
    Optional<ComplexShoppingmall> findByManagerId(Long managerId);
}
