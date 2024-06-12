## 📺 개발환경
- <img src="https://img.shields.io/badge/IDE-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/Intellij-000000?style=for-the-badge&logo=Intellij&logoColor=white"><img src="https://img.shields.io/badge/Ultimate-515151?style=for-the-badge">
- <img src="https://img.shields.io/badge/Framework-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/3.3.0-515151?style=for-the-badge">
- <img src="https://img.shields.io/badge/Build-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"><img src="https://img.shields.io/badge/7.1.1-515151?style=for-the-badge">
- <img src="https://img.shields.io/badge/Language-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/java-%23ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"><img src="https://img.shields.io/badge/17-515151?style=for-the-badge">
- <img src="https://img.shields.io/badge/Database-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/MySQL-5D5D5D?style=for-the-badge">
- <img src="https://img.shields.io/badge/Project Encoding-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/UTF 8-EA2328?style=for-the-badge">

# 게시판 주요기능 
1. 글쓰기(/board/save)
2. 글목록(/board/)
3. 글조회(/board/{id})
4. 글수정(/board/update/{id})
    - 상세화면에서 수정 버튼 클릭 
    - 서버에서 해당 게시글의 정보를 가지고 수정 화면 출력 
    - 제목, 내용 수정 입력 받아서 서버로 요청 
    - 수정 처리 
5. 글삭제(/board/delete/{id})
6. 페이징처리(/board/paging)
    - /board/paging?page=2
    - /board/paging/2
    - 게시글 14
      - 한페이지에 5개씩 => 3개
      - 한페이지에 3개씩 => 5개
7. 파일(이미지)첨부하기 
   - 단일 파일 첨부
   - 다중 파일 첨부
   - 파일 첨부와 관련하여 추가될 부분들  
     - save.html  
     - BoardDTO  
     - BoardService.save()  
     - BoardEntity
     - BoardFileEntity, BoardFileRepository 추가
     - detail.html

    - board_table(부모) - board_file_table(자식)
```
create table board_table
(
id             bigint auto_increment primary key,
created_time   datetime     null,
updated_time   datetime     null,
board_contents varchar(500) null,
board_hits     int          null,
board_pass     varchar(255) null,
board_title    varchar(255) null,
board_writer   varchar(20)  not null,
file_attached  int          null
);

create table board_file_table
(
id                 bigint auto_increment primary key,
created_time       datetime     null,
updated_time       datetime     null,
original_file_name varchar(255) null,
stored_file_name   varchar(255) null,
board_id           bigint       null,
constraint FKcfxqly70ddd02xbou0jxgh4o3
    foreign key (board_id) references board_table (id) on delete cascade
);
```










## mysql DataBase 계정 생성 및 권한 부여 
```
create database team;
create user user_team@localhost identified by '1234';
grant all privileges on team.* to team@localhost;
```
