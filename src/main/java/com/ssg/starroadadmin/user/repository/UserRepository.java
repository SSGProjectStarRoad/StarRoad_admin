package com.ssg.starroadadmin.user.repository;

import com.ssg.starroadadmin.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

}
