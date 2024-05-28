package com.ssg.starroadadmin.reward.repository;

import com.ssg.starroadadmin.reward.entity.RewardHistory;
import com.ssg.starroadadmin.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RewardHistoryRepository extends JpaRepository<RewardHistory, Long> {
    Page<RewardHistory> findAllByRewardId(Long id, Pageable pageable);

    Optional<List<RewardHistory>> findAllByUser(User user);
}