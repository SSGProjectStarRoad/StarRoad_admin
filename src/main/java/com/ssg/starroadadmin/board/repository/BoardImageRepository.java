package com.ssg.starroadadmin.board.repository;

import com.ssg.starroadadmin.board.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
}
