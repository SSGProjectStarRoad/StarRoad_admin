package com.ssg.starroadadmin.user.repository;

import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findByIdAndAuthorityNot(Long id, Authority authority);

    Optional<Manager> findByIdAndAuthority(Long mallManagerId, Authority authority);
}
