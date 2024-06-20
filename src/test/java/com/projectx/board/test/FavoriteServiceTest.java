package com.projectx.board.test;

import com.projectx.board.entity.Favorite;
import com.projectx.board.entity.User;
import com.projectx.board.repository.FavoriteRepository;
import com.projectx.board.repository.UserRepository;
import com.projectx.board.service.FavoriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FavoriteServiceTest {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setup() {
        // 테스트에서 사용할 필드 초기화
        testUser = User.builder()
                .email("test@test.com")
                .name("Test User")
                .password("1234")
                .favorite(new ArrayList<>())
                .build();
        userRepository.save(testUser);
    }

    @Test
    public void testSaveFavorite() {
        Favorite favorite = favoriteService.saveFavorite(testUser.getId(), "store1");
        assertNotNull(favorite);
        assertEquals(testUser.getId(), favorite.getUser().getId());
        assertEquals("store1", favorite.getStoreId());
    }

    @Test
    public void testGetFavoritesByUser() {
        favoriteService.saveFavorite(testUser.getId(), "store1");
        List<Favorite> favorites = favoriteService.getFavoritesByUser(testUser.getId());
        assertFalse(favorites.isEmpty());
    }

    @Test
    public void testDeleteFavorite() {
        Favorite favorite = favoriteService.saveFavorite(testUser.getId(), "store1");
        favoriteService.deleteFavorite(favorite.getFavoriteId());
        Optional<Favorite> deleteFavorite = favoriteRepository.findById(favorite.getFavoriteId());
        assertTrue(deleteFavorite.isEmpty());
    }
}
