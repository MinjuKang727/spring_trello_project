# 🏃내일lo
> 내일lo는 프로젝트 관리를 위한 협업 툴로, 직관적인 **칸반 보드(Kanban Board)** 방식으로 작업을 관리할 수 있는 도구입니다.

<br><br>

## 팀원 소개
**강민주**	`팀장`	🍀`INTJ`	🗣️`공부해서 남주나? 다 내끼다! 화이팅하겠습니다!`  [블로그 링크](https://ajtwltsk.tistory.com/)	[깃허브 링크](https://github.com/MinjuKang727)	 <br>

**이봉원**	`팀원`	🍀`ISFJ`	🗣️`열심히 하겠습니다!`  [블로그 링크](https://movieid94.tistory.com/)	[깃허브 링크](https://github.com/LeeBongwon94)	 <br>	
**신승재**	`팀원`	🍀`INFP`	🗣️`끝까지 열심히 합시다!` [블로그 링크](https://durururuk.tistory.com/)	[깃허브 링크](https://github.com/durururuk)	 <br>	
**이지택**	`팀원`	🍀`ISTJ`  🗣️`___________________`     	[블로그 링크](https://diary-3.tistory.com/)	[깃허브 링크](https://github.com/jitaeklee)	 <br>	
**전우성**	`팀원`	🍀`INFP/ENFP`	🗣️`아직많이 부족한데 열심히 하겠습니다.` [블로그 링크](https://blog.naver.com/zeno9302)	[깃허브 링크](https://github.com/zenoWS/zenoWs)	 <br>


<Br><Br>
---
## 개발 Scope 및 담당 팀원
- **필수 기능 구현**
    - 회원가입/로그인 : `이봉원`
        - Spring Security
        - OAuth(카카오 로그인)
        - Redis
        - 로그아웃
    - 멤버 및 역할 관리 : `이지택`
    - 워크스페이스(Workspace) : `이지택`
    - 보드(Board) : `전우성`
    - 덱(Deck) : `강민주`
    - 카드(Card) : `신승재`
    - 댓글(Comment) : `전우성`
    - 첨부파일(Attachment) : `신승재`
    - 알림(Notification) : `강민주`
      - 워크스페이스별 private 채널 생성
      - 워크스페이스 멤버 초대
      - 워크스페이스 변경 사항 알람 메세지 전송
    - 검색
        - 카드 단/다건 조회 : `신승재`
        - 보드 단/다건 조회 : `전우성`
        - 워크스페이스 단건 조회 : `이지택`
- **도전 기능**
    - 최적화(Indexing)
    - 캐싱(Caching) : `신승재`
<br><br>
---
## 와이어프레임
![내일lo 와이어프레임](https://github.com/user-attachments/assets/8324e7c8-63ef-499f-b391-511b342565c0)

<br><Br>
---
## ERD
![내일lo ERD](https://github.com/user-attachments/assets/72622a84-8c72-471b-9ea8-c957c2ea2676)

<br><Br>
---
## API 명세서
![내일lo API 명세서](https://github.com/user-attachments/assets/f71c2f0a-6569-4a80-9fd8-b1196ac5e0f6)

주요기능 및 로직의 흐름

![image](https://github.com/user-attachments/assets/d13fb6b6-79dc-476c-9aac-dabd094a6a1e)


주요기능 : 첨부파일 추가기능

로직의 흐름 : 요청한 유저가 해당 카드의 매니저인지 확인 -> 들어온 멀티파트 파일 검증(확장자, 파일크기) -> 멀티파트 파일 > 파일로 변환 (UUID로 파일명에 고유값 부여) -> 서버의 Upload폴더의 저장 -> 저장된 파일주소 포함해서 응답 DTO반환

![image](https://github.com/user-attachments/assets/067ea58f-236a-489a-9dd7-8c3ba24156ee)


트러블 슈팅 : CI/CD의 필요성 

![image](https://github.com/user-attachments/assets/859c2ec1-47e3-4f19-b240-0ce7641a5137)

![image](https://github.com/user-attachments/assets/c4cf30c4-4b89-47cf-b9c2-e9728dd7bf07)

![image](https://github.com/user-attachments/assets/e81e5a70-15f7-405d-9265-11b7abf11eea)

![image](https://github.com/user-attachments/assets/5fb421dc-b733-4b52-bc5e-1df94cc86c5f)

![image](https://github.com/user-attachments/assets/5b6f727d-1e98-43c2-b0c5-96e1854c5734)

![image](https://github.com/user-attachments/assets/bd64a171-f10c-4254-b2f9-c3152845f950)

![image](https://github.com/user-attachments/assets/5b02b5ec-10c1-4b19-9508-4d98cf279c93)

![image](https://github.com/user-attachments/assets/d1bee6ef-5dd4-438e-aeb0-1397f6d9ffe1)
