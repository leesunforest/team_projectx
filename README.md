# 안내사항(0707_VER)
1. favorite 기능이 잘 작동 됩니다. 
    - /map 에서 그룹참여를 클릭 -> 사용자 위치 입력 -> save 버튼을 누르면 DB에 저장한 가게 목록이 들어옵니다.
    - /map 에서 그룹참여를 클릭 -> 사용자 위치 입력 -> 이미 save 한 버튼은 Unsaved 이라고 바뀌어지며, Unsaved 를 한번 더 클릭하면 save 버튼으로 바뀌고 저장되어 있던 DB는 삭제 됩니다.
    - localhost:8082 접속 -> 로그인 -> 마이페이지 클릭 -> 저장한 가게 클릭을 하면 저장한 가게 목록이 나옵니다.
    - localhost:8082 접속 -> 로그인 -> 마이페이지 클릭 -> 저장한 가게 클릭 -> 저장한 가게 목록의 삭제 버튼을 클릭하면 DB에서 해당 가게가 삭제되고, 찜 목록도 바로 업데이트 됩니다.
2. FavoriteService 와 FavoriteController 에서 사용하지 않던 메소드를 삭제했습니다. 
3. map.html 에서 주석 처리 되어있던 마이페이지 버튼을 삭제했습니다.

---
# 좋아요 테이블 구조
```
CREATE TABLE `favorite` (
  `favorite_id` bigint NOT NULL AUTO_INCREMENT,
  `favorite_at` datetime(6) NOT NULL,
  `store_address` varchar(255) NOT NULL,
  `store_name` varchar(255) NOT NULL,
  `store_number` varchar(255) DEFAULT NULL,
  `user_id` varchar(15) NOT NULL,
  `user_no` bigint NOT NULL,
  PRIMARY KEY (`favorite_id`),
  KEY `FKa0d7ydro0ug9nkxycl4igg529` (`user_no`),
  CONSTRAINT `FKa0d7ydro0ug9nkxycl4igg529` FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```