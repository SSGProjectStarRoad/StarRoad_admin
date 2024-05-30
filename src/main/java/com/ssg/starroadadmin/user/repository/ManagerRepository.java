package com.ssg.starroadadmin.user.repository;

import com.ssg.starroadadmin.user.entity.Manager;
import com.ssg.starroadadmin.user.enums.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    /**
     * Optional 은 null인지 아닌지 체크하는 용으로 작성하는 것이다.
     */
    Optional<Manager> findByUsername(String username);

    /**
     *FROM MANAGER WHERE USERNAME = #{USERNAME} 이런 쿼리문이 되는것이다.
      */
    Optional<Manager> findByBusinessNumber(String businessNumber);


    Optional<Manager> findByIdAndAuthorityNot(Long id, Authority authority);

    Optional<Manager> findByIdAndAuthority(Long mallManagerId, Authority authority);
    Optional<Manager> findByUsernameAndAuthority(String username, Authority authority);
}
