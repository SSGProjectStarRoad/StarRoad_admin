package com.ssg.starroadadmin.user.repository;

import com.ssg.starroadadmin.user.dto.PopularUserResponse;
import com.ssg.starroadadmin.user.entity.Follow;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Long countByToUser_Id(Long id);

    Long countByFromUser_Id(Long id);

    @Query("SELECT new com.ssg.starroadadmin.user.dto.PopularUserResponse(" +
            "u.id, " +
            "u.name, " +
            "u.imagePath, " +
            "(SELECT COUNT(f.id) FROM Follow f WHERE f.toUser.id = u.id), " +
            "(SELECT COUNT(f.id) FROM Follow f WHERE f.fromUser.id = u.id), " +
            "(SELECT COUNT(r.id) FROM Review r WHERE r.user.id = u.id)) " +
            "FROM User u " +
            "ORDER BY (SELECT COUNT(f.id) FROM Follow f WHERE f.toUser.id = u.id) DESC")
    List<PopularUserResponse> findTop5ByFollow();


}
