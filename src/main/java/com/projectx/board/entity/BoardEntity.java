package com.projectx.board.entity;

import com.projectx.board.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private UserEntity user;

    @Column(length = 20, nullable = false)
    private String boardWriter;

    @Column
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private Integer boardHits;

    @Column
    private Integer fileAttached = 0; // 기본 값을 0으로 설정

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    public static BoardEntity toSaveEntity(BoardDTO boardDTO, UserEntity user) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(user.getUserId());
        boardEntity.setUser(user);
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(0);
        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardDTO boardDTO, UserEntity user) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        boardEntity.setCreatedTime(boardDTO.getBoardCreatedTime());
        boardEntity.setUpdatedTime(LocalDateTime.now());
        boardEntity.setFileAttached(boardDTO.getFileAttached());
        boardEntity.setUser(user);
        boardEntity.setBoardWriter(user.getUserId());
        return boardEntity;
    }

    public static BoardEntity toSaveFileEntity(BoardDTO boardDTO, UserEntity user) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(user.getUserId());
        boardEntity.setUser((user));
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(1); // 파일 있음.
        return boardEntity;
    }

    public static BoardEntity toUpdateFileEntity(BoardDTO boardDTO, UserEntity user) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        boardEntity.setCreatedTime(boardDTO.getBoardCreatedTime());
        boardEntity.setUpdatedTime(LocalDateTime.now());
        boardEntity.setFileAttached(boardDTO.getFileAttached());
        boardEntity.setUser(user);
        return boardEntity;
    }
}