package com.projectx.board.repository;

import com.projectx.board.dto.BoardItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Page<BoardItemDTO> getBoardItemPage(Pageable pageable);
}
