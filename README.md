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

주요기능 : 첨부파일 추가기능

로직의 흐름 : 요청한 유저가 해당 카드의 매니저인지 확인 -> 들어온 멀티파트 파일 검증(확장자, 파일크기) -> 멀티파트 파일 > 파일로 변환 (UUID로 파일명에 고유값 부여) -> 서버의 Upload폴더의 저장 -> 저장된 파일주소 포함해서 응답 DTO반환

트러블 슈팅 : CI/CD의 필요성 
코드리뷰 -> 빌드가 잘되는지 기능이 잘되는지 확실히 알지 못해 해결방법으로 기능을 작성하고 PR을 올리는 팀원이 테스트를 작성해서 기능이 검증을 하고 빌드는 잘 되는지 확인하고 PR을 작성한 뒤에 다른 팀원들이 더 꼼꼼하게 코드리뷰를 하는 방법이 있지만 수동으로 다 검증하는 작업은
휴먼 에러 발생 가능성이 높습니다.

그렇기 때문에 문제가 발생하였고 자동화 할수 있는 부분이라도 자동화해서 안되는 코드가 dev브랜치에 들어오는 일을 최소한으로 줄여야 합니다.

그래서 저희가 구상한 해결방안은 CI를 통한 자동화였습니다. 먼저 PR이 생성되면 CI가 파이프라인이 가동됩니다.

그리고 CI 서버에서 프로젝트 의존성 설치하고 Grradle빌드를 돌려서 빌드는 잘 되는지, 테스트는 통과하는지 확인합니다.

그리고 빌드가 성공하면 테스트 커버리지를 넘었는지 확인합니다. 여기서는 예를 들어 40%라고 가정했습니다.
