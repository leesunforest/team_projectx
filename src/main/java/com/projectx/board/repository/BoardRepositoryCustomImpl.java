package com.projectx.board.repository;

import com.projectx.board.dto.BoardItemDTO;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public BoardRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression boardCityOrTitleLike(String searchQuery) {
        if (StringUtils.isEmpty(searchQuery)) {
            return null;
        }

        BooleanExpression cityContains = QBoard.board.city.like("%" + searchQuery + "%");
        BooleanExpression titleContains = Qboard.board.boardTitle.like("%" + searchQuery + "%");

        return cityContains.or(titleContains);
    }
    @Override
    public Page<BoardItemDTO> getBoardItemPage(String searchQuery, Pageable pageable) {
        QBoard board = QBoard.board;

        QueryResults<BoardItemDTO> results = queryFactory
                .select(
                        new QBoardItemDTO(
                                board.id,
                                board.boardTitle)
                        )
                .where(boardCityOrTitleLike(searchQuery))
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<BoardItemDTO> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
