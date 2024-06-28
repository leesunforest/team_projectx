package com.projectx.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardItemDTO {
    private Long id;
    private String title;

    @QueryProjection
    public BoardItemDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
