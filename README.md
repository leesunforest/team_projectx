# 안내사항(0703_VER)
0. 디렉토리 구조가 변경되어 있습니다. 병합할 때 문제가 생길 수 있을 것 같아 꼭 확인해 주세요! 
1. application.yml 을 변경했습니다. 
2. DB 단에서 save 버튼을 누르면 데이터가 넘어가고, 한번 더 누르면 DB가 삭제되는 것 까지 확인이 되었습니다.
3. 하지만, FavoriteList.html 과 mapping 하는 방법을 해결하지 못한 상태입니다
4. 그 외 FavoriteController 의 상세보기, 목록 보기 기능은 아직 구현되지 않았습니다. 
5. Map 관련 js 파일 중 recommend.js 파일에 현재 임의로 userId 값을 넣어둔 상태입니다. 하지만 추후에는 userId : userId 로 변경해야 하는데 userId 값을 어떻게 받아올지 아직 해결하지 못했습니다.

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