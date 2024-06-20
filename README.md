# 안내사항
0. applicatin.yml 을 수정했습니다. 
   - 실행시 DB의 테이블이 생성되지 않는 문제가 있어 yml 변경한 상태
1. 좋아요 기능 관계를 N 관계로 설정(양방향)한 상태
   - 사용자가 다양한 가게를 저장할 수 있음(1:N -> oneToMany)
   - 가게는 다양한 사용자가 저장할 수 있음(N:1 -> manyToOne)
2. 임의로 User 테이블을 생성 및 외부에서 값을 받아올 Store Class(dto 패키지) 생성한 상태
   - User Table 코드
   ```
   CREATE TABLE `user` (
         `id` bigint NOT NULL AUTO_INCREMENT,
         `email` varchar(255) DEFAULT NULL,
         `name` varchar(255) DEFAULT NULL,
         `password` varchar(255) DEFAULT NULL,
         PRIMARY KEY (`id`)
    )
   ```
   - Store Class 코드
   ```
   @Data
   public class Store {
      // 외부 API로 가져온 가게 정보
      private String storeId; // 가게 아이디
      private String storeName; // 가게 이름
      private String storeAddress; // 가게 주소
   }
   ```
3. 가게 부분 API를 받아 구현해야하는데, 테스트를 못해 해결방안 찾아봐야함(현재는 URL이 올바르지 않아서 실행이 안되는 문제)
4. 회원 부분을 테스트 코드로 생성이 되었지만, 회원이 아닌 상태에서 할 수 있도록 변경을 하려고 함 **(변경 전)**

# 좋아요 주요기능
- favoriteController @requestMapping("/favorites")
1. 가게 저장(/favorites/save)
2. 가게 목록 조회(/favorites/list)
   - 가게 목록에서 원하는 가게를 누르면 가게 상세 정보가 보이도록 하기
3. 좋아요한 가게 상세보기 (/favorite/store/{storeId})
   - 가게 목록을 누르면 네이버 등의 사이트에 등록된 가게 정보로 바로 넘어가게 할 것인가에 대해 논의하기
4. 가게 삭제(/favorites/delete/{id})

## 좋아요 Table
```
   // userId 를 외래키로 받아옴
   CREATE TABLE `favorite` (  
      `favorite_id` bigint NOT NULL AUTO_INCREMENT,
      `created_at` datetime(6) DEFAULT NULL,
      `store_id` varchar(255) DEFAULT NULL,
      `store_name` varchar(255) DEFAULT NULL,
      `user_id` bigint NOT NULL,
      PRIMARY KEY (`favorite_id`),
      KEY `FKh3f2dg11ibnht4fvnmx60jcif` (`user_id`),
      CONSTRAINT `FKh3f2dg11ibnht4fvnmx60jcif` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
   )
```