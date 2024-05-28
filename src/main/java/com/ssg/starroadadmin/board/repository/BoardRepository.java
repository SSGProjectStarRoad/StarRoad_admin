package com.ssg.starroadadmin.board.repository;

import com.ssg.starroadadmin.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
