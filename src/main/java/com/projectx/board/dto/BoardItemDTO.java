package com.projectx.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
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
}