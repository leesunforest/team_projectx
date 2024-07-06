package com.projectx.board.repository;

import com.projectx.board.dto.BoardItemDTO;
import com.projectx.board.dto.QBoardItemDTO;
import com.projectx.board.entity.QBoardEntity;
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

    @Override
    public Page<BoardItemDTO> getBoardItemPage(Pageable pageable) {
        QBoardEntity board = QBoardEntity.boardEntity;

        QueryResults<BoardItemDTO> results = queryFactory
                .select(
                        new QBoardItemDTO(
                                board.id,
                                board.boardTitle,
                                board.boardWriter,
                                board.boardHits)
                )
                .from(board)
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<BoardItemDTO> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

//    private BooleanExpression boardCityOrTitleLike(String searchQuery) {
//        if (StringUtils.isEmpty(searchQuery) || searchQuery == null) {
//            return null;
//        }
//
//        BooleanExpression cityContains = QBoardEntity.boardEntity.city.like("%" + searchQuery + "%");
//        BooleanExpression titleContains = QBoardEntity.boardEntity.boardTitle.like("%" + searchQuery + "%");
//
//        return cityContains.or(titleContains);
//    }
//
//    @Override
//    public Page<BoardItemDTO> getBoardItemPage(String searchQuery, Pageable pageable) {
//        QBoardEntity board = QBoardEntity.boardEntity;
//
//        QueryResults<BoardItemDTO> results = queryFactory
//                .select(
//                        new QBoardItemDTO(
//                                board.id,
//                                board.boardTitle)
//                )
//                .from(board)
//                .where(boardCityOrTitleLike(searchQuery))
//                .orderBy(board.id.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetchResults();
//
//        List<BoardItemDTO> content = results.getResults();
//        long total = results.getTotal();
//        return new PageImpl<>(content, pageable, total);
//    }
}