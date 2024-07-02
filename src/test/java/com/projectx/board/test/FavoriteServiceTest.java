package com.projectx.board.test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FavoriteServiceTest {
/*
    @MockBean
    private FavoriteRepository favoriteRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private FavoriteService favoriteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // 목을 초기화합니다.
    }

    @Test
    @DisplayName("saveFavorite : 가게를 저장할 수 있다.")
    public void testSaveFavorite() {
        // Mock User 객체 생성
        User mockUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .name("Test User")
                .password("password")
                .build();

        // Mock Store 객체 생성
        Store mockStore = new Store();
        mockStore.setStoreId("store1");
        mockStore.setStoreName("Test Store");

        // Mock Favorite 객체 생성
        Favorite mockFavorite = Favorite.builder()
                .favoriteId(1L)
                .user(mockUser)
                .storeId("store1")
                .storeName("Test Store")
                .build();

        // Mock 동작 설정
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        when(restTemplate.getForObject(anyString(), any(Class.class))).thenReturn(mockStore);
        when(favoriteRepository.save(any(Favorite.class))).thenReturn(mockFavorite);

        // 실제 테스트 실행
        Favorite savedFavorite = favoriteService.saveFavorite(1L, "store1");
        assertNotNull(savedFavorite);
    }

 */
}
