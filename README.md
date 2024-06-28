## 📺 개발환경
- <img src="https://img.shields.io/badge/IDE-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/Intellij-000000?style=for-the-badge&logo=Intellij&logoColor=white"><img src="https://img.shields.io/badge/Ultimate-515151?style=for-the-badge">
- <img src="https://img.shields.io/badge/Framework-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/3.3.0-515151?style=for-the-badge">
- <img src="https://img.shields.io/badge/Build-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"><img src="https://img.shields.io/badge/7.1.1-515151?style=for-the-badge">
- <img src="https://img.shields.io/badge/Language-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/java-%23ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"><img src="https://img.shields.io/badge/17-515151?style=for-the-badge">
- <img src="https://img.shields.io/badge/Database-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/MySQL-FFFFFF?style=for-the-badge">
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
   - github에 올려놓은 코드를 보시고 어떤 부분이 바뀌는지 잘 살펴봐주세요. 

    - board_table(부모) - board_file_table(자식)

# 진행 상황
User 엔티티랑 레포지토리 임시로 생성한 채로 개발 중입니다!

(진행된 것)  
게시판쪽 화면 구현  
게시판에 게시글 나타내기 구현  
게시판 -> 글 작성 클릭시 상세페이지 나타내기 구현  
상세페이지에서 목록으로 이동 및 댓글 게시 기능 구현  
검색 기능 구현  

(해야할 것)  
게시글 수정 기능  
웹 메인 페이지 제작  
마이페이지 제작  
마이페이지에서 내가 쓴 게시글 확인  

제대로 된 데이터가 없는 상태로 개발 중이라 테스트가 어려운 상황입니다ㅠㅠ 나중에 코드 통합되면 제 부분에서 오류가 좀 생길 것 같은데 통합 후에 수정하도록 하겠습니다!