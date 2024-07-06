package com.projectx.board.repository;

import com.projectx.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>, QuerydslPredicateExecutor<BoardEntity>, BoardRepositoryCustom {
    // update board_table set board_hits=board_hits+1 where id=?
    // 해당 게시글에 조회수 +1
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);
}