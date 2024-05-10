package com.ssg.starroadadmin.reward.repository;

import com.ssg.starroadadmin.reward.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RewardRepository extends JpaRepository<Reward, Long> {
    Optional<Reward> findByName(String name);
}
