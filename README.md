
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
