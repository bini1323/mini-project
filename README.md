# mini-project : Aircraft Parts Management System

### 용어 정리
1. fork - 다른 사람의 repository를 내가 어떤 부분을 수정하거나 추가 기능을 넣고 싶을 때 해당 repository를 내 github repository로 복제하는 기능
2. upstream - upstream 저장소 (fork 당한 저장소)에 대해 push 및 fetch 수행을 위한 설정
3. add&commit - 내 로컬 저장소에 작업한 내용을 기록 (더 자세한 공부 필요)
4. push - 원격 저장소에 add&commit 내역을 반영
5. Pull Request - fork 했던 저장소에 원격 저장소의 push 했던 코드를 반영해달라고 요청
6. fetch - 원격 저장소와 내 로컬 저장소의 commit 이력을 비교하여 변경 내용을 로컬 저장소에 반영

<br>

### 작업방식
1. 메인 저장소를 fork 해 온다.
2. fork 한 Repository를 clone 한다.
3. git remote add upstream <메인 저장소 주소>를 통해 upstream 설정을 한다.
4. git fetch를 통해 최신 코드를 받아온다.
5. 작업한 내용을 origin 저장소에 push한다.
6. push한 코드를 upstream 저장소에 PR한다.
7. 코드 확인 후 Merge한다.

<br>

### 패키지 구조
src<br>
&nbsp;&nbsp;|<br>
main<br>
&nbsp;&nbsp;|------|------|------|<br>
vo&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;dao&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;util&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ui

<br>
 
### 기능
#### EMPLOYEE
1. 회원가입&로그인 기능

#### REQUEST
1. 부품 신청 수락/거절 기능<br>- 직급에 따라 수행 또는 에러메시지 출력

#### STOCK
1. 부품 저장 기능
2. 오버홀 기간 업데이트 기능

<br>

### 테이블 정의서
[Link](https://docs.google.com/spreadsheets/d/1ZhWNLXs1adAD-r5b5s-IVcEGUZOZ3MXLC_gLS2dlDJo/edit?hl=ko#gid=0)
