# 안내사항(06.28VER)
0. applicatin.yml 을 수정했습니다. 
   - 실행시 DB의 테이블이 생성되지 않는 문제가 있어 yml 변경한 상태

1. **resources** 의 파일 **구조를 변경**한 상태(**map branch 파일을 가져왔습니다.**)
   - resources/static/templates 
   - static>css, images, js 
   - templates>각종 html 을 모아둘 예정 (현재 : map.html) 

2. Favorite Entity 수정(테이블 구조 변경됨) → Favorite 테이블 
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
      CONSTRAINT `FKa0d7ydro0ug9nkxycl4igg529` FOREIGN KEY (`user_no`) REFERENCES `user`(`user_no`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
   ```

3. 데이터구조에 따라 임의의 User Entity 생성(테이블 구조 변경됨)
   ```
   CREATE TABLE `user` (
      `user_no` bigint NOT NULL AUTO_INCREMENT,
      `user_email` varchar(50) NOT NULL,
      `user_id` varchar(15) NOT NULL,
      `user_join` datetime(6) NOT NULL,
      `user_pw` varchar(20) NOT NULL,
      PRIMARY KEY (`user_no`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
   ```


---
# 좋아요 주요기능
1. 가게 저장
   - 가게의 정보를 JSON 으로 넘겨받아 DB에 저장
2. 가게 목록 조회
   - 가게 목록에서 원하는 가게를 누르면 가게 상세 정보가 보이도록 하기
3. 좋아요한 가게 상세보기
   - 가게 목록을 누르면 네이버 등의 사이트에 등록된 가게 정보로 바로 넘어가게 할 것인가에 대해 논의하기
4. 가게 삭제

---
## 현재 좋아요 Table
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
      CONSTRAINT `FKa0d7ydro0ug9nkxycl4igg529` FOREIGN KEY (`user_no`) REFERENCES `user`(`user_no`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
   ```

## 현재 Favorite 구현되어야 하는 사항
- Service, Controller 구현 
- map.html 에서 저장 버튼 찾기 또는 구현 
- 가게 정보를 JSON 값으로 받아와 DB에 저장