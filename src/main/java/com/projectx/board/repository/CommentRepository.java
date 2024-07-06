package com.projectx.board.repository;

import com.projectx.board.entity.BoardEntity;
import com.projectx.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // select * from comment_table where board_id=? order by id desc;
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);

    List<CommentEntity> findByCommentWriter(String commentWriter);
}