# Todo List API 서버 설계
### Todo List API 서버 설계 및 POSTMAN을 통한 테스트
 
1. 운영체제 : Window

2. 통합 개발 환경(IDE) : IntelliJ
 
3. JDK 버전 : JDK 17
 
4. Spring Boot 버전 : 3.2.2
 
5. 데이터 베이스 : H2 Database
 
6. 빌드 툴 : Gradle
 
7. ORM : JPA

 
### 요구사항 정리

-   요구사항
    -   기능 명세
        1.  todo 리스트 목록에 아이템을 추가
        2.  todo 리스트 목록 중 특정 아이템을 조회
        3.  todo 리스트 전체 목록을 조회
        4.  todo 리스트 목록 중 특정 아이템을 수정
        5.  todo 리스트 목록 중 특정 아이템을 삭제
        6.  todo 리스트 전체 목록을 삭제
-   API 스펙

| method | endpoint | 기능 | request | response |
| --- | --- | --- | --- | --- |
| POST | / | todo 아이템 추가 | { "title": "자료구조 공부하기"} | { "id": 17, "title": "자료구조 공부하기", "order": 0, "completed": false, "url": "http://localhost:8080/17"} |
| GET | / | 전체 todo 리스트 조회 |   | \[ { "id": 1, "title": "자바 기초 공부하기", "order": 0, "completed": false, "url": "http://localhost:8080/1" }, { "id": 2, "title": "알고리즘 공부하기", "order": 0, "completed": false, "url": "http://localhost:8080/2" }, ... \] |
| GET | /{:id} | todo 아이템 조회 |   | { "id": 17, "title": "자료구조 공부하기", "order": 0, "completed": false, "url": "http://localhost:8080/17"} |
| PATCH | /{:id} | todo 아이템 수정 | { "title": "반복문 공부하기"} | { "id": 1, "title": "반복문 공부하기", "order": 0, "completed": false, "url": "[http://localhost:8080/1](http://localhost:8080/1)"} |
| DELETE | / | 전체 todo 리스트 삭제 |   | 200 |
| DELETE | /{:id} | todo 아이템 삭제 |   | 200 |
