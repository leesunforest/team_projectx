package com.projectx.board.entity;

import com.projectx.board.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// DB의 테이블 역할을 하는 클래스
@Entity
@Getter
@Setter
@Table(name = "board")
public class BoardEntity extends BaseEntity {
    @Id // pk 컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private UserEntity user;

    @Column(length = 20, name = "board_writer", nullable = false) // 크기 20, not null
    private String boardWriter;

    @Column(length = 50, name = "board_title")
    private String boardTitle;

    @Column(length = 500, name = "board_contents")
    private String boardContents;

    @Column(name = "board_hits")
    private int boardHits;

    @Column(name = "file_attached")
    private int fileAttached; // 1 or 0

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @Column(length = 20)
    private String city;

    // save.html의 값을 boardDTO로 담음.
    // DTO에 있는 객체를 Entity로 옮겨닮는 작업.
    public static BoardEntity toSaveEntity(BoardDTO boardDTO, UserEntity user) { // static 형식의 메서드로 정의
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(user.getUserId());
        boardEntity.setUser(user);
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(0); // 파일 없음.
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










