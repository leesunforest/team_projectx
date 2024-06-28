package com.projectx.board.repository;

import com.projectx.board.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    // 특정 사용자(userNo)의 저장 목록을 조회하는 메서드
    List<Favorite> findByUserUserNo(Long userNo);
}
