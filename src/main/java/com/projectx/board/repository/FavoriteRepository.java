package com.projectx.board.repository;

import com.projectx.board.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    // 사용자 ID를 이용하여 좋아요 목록 찾기
    List<Favorite> findByUserId(Long userId);
}
