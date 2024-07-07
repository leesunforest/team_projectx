package com.projectx.board.repository;

import com.projectx.board.entity.Favorite;
import com.projectx.board.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    // 특정 사용자(userNo)의 저장 목록을 조회하는 메서드
    List<Favorite> findByUser (UserEntity user);
}