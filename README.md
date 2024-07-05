# 안내사항(0704_VER)
0. 디렉토리 구조가 변경되어 있습니다. 병합할 때 문제가 생길 수 있을 것 같아 꼭 확인해 주세요! 
1. application.yml 을 변경했습니다. 
2. 현재 가게 저장, 목록보기, 삭제가 가능합니다. templates/data.sql 을 실행 후, 서버를 실행하여 localhost:8082 에 접속 후, 아무 가게를 저장한 다음 localhost:8082/mypage/favorites/list/1 에 접속을 하면 저장된 목록을 볼 수 있습니다.
3. 만약 실행이 되지 않는다면 js 폴더의 recommend.js 파일의 saveBtn 부분에서 userId : 'test1' 로 변경 후 실행하면 확인할 수 있습니다.
4. 로그인을 하지 않으면 userId 의 세션값을 defaultUser 로 받아오도록 구현했습니다. 그러나 실제로 userId 의 세션이 받아지는지 확인이 불가한 상태입니다. 
5. map.html 의 양식을 thymeleaf 로 변경해둔 상태입니다. 임의의 MainController 또한 변경하여 session 값을 받아오도록 바꾸었습니다.

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