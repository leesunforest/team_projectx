package com.projectx.board.repository;

import com.projectx.board.entity.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {
    void deleteByBoardEntityId(Long boardEntityId);
}