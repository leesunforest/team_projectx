package com.projectx.board.dto;

import com.projectx.board.entity.BoardEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BoardItemDTO {
    private Long id;
    private String boardTitle;
    private String boardWriter;
    private Integer boardHits;

    @QueryProjection
    public BoardItemDTO(Long id, String boardTitle, String boardWriter, Integer boardHits) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.boardHits = boardHits;
    }

    public static BoardItemDTO toBoardItemDTO(BoardEntity boardEntity){
        BoardItemDTO boardItemDTO = new BoardItemDTO();
        boardItemDTO.setId(boardEntity.getId());
        boardItemDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardItemDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardItemDTO.setBoardHits(boardEntity.getBoardHits());

        return boardItemDTO;
    }
}
