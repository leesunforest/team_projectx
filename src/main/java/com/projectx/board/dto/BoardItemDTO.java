package com.projectx.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardItemDTO {
    private Long id;
    private String boardTitle;

    @QueryProjection
    public BoardItemDTO(Long id, String title) {
        this.id = id;
        this.boardTitle = title;
    }
}
