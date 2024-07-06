package com.projectx.board.service;

import com.projectx.board.dto.CommentDTO;
import com.projectx.board.entity.BoardEntity;
import com.projectx.board.entity.CommentEntity;
import com.projectx.board.repository.BoardRepository;
import com.projectx.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO, String userId) {

        /* 부모엔티티(BoardEntity) 조회 */
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity, userId);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }

    public List<CommentDTO> findAll(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        /* EntityList -> DTOList */
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    public List<CommentDTO> findUserComment(String userId){
        List<CommentEntity> userCommentList = commentRepository.findByCommentWriter(userId);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: userCommentList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, commentEntity.getBoardEntity().getId());
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}