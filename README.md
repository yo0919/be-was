# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## 깃의 사실과 오해
+ Q1. git init을 수행하면 현재디렉토리가 로컬 저장소가된다?   
A1. NO, .git이 저장소가된다   

+ Q2. git의 객체에는 무엇이 있을까?   
A2. Blob tree commit Tag   
commit 안에는 blob과 tree가 들어있다.   

+ Q3. 저장은 어느시점부터될까? 파일 만드는시점? Git add? commit? + 각 명령어의 기능   
A3. Git add    
stage는 무엇인가? - 커밋을 만들기위한 준비공간   
commit이 뭔데? 일종의 세이브 포인트? 우리의 작업내용의 스냅샷   

+ Q4. 커밋을 하고나면 스테이지는 깨끗하게 비워진다?   
A4. 안비워진다.(작업디렉토리와 스테이지와 저장소가 모두 같을때 비어있다고 표현)    
마지막 커밋에는 트리가 있고, 그 트리안에는 블롭들이 들어있다.   

+ Q5. 스테이지의 변경사항을 취소하려면 스테이지를 직접 조작하는 명령인 'git rm --cached <파일이름>' 을 사용해서 변경사항을 스테이지에서 제거하면 된다.   
A5. YES   

+ Q6. Git status는 [...]들을 비교해서 보여주는 명령어이다   
A6. stage HEAD commit   

+ Q7. 파일의 내용을 수정하고 커밋하면 GIT은 공간 효율을 위해 변경사항만 저장한다.   
A7. NO (전체 파일이 저장된다.)   
깃은 변경사항이 아니라 전체를 저장하기때문에 순식간에 switch가 가능하다. 성능을 위해 전체저장!    

+ Q8. a.txt를 새로 만들고 새로운 디렉토리에 복사한 후 add, commit 한 후 새로운 브랜치를 만들어 변경했다. 이때 blob이 새로 생성되는가?    
A8. NO (깃에서 똑같은 파일을 여러개만들어도 더이상의 blob이 생기지않고 하나만 올라간다.)    
## HTTP

* DNS란?   
  - 사람이 볼수있는 주소를 서버가 인식할수있도록 IP로 변환하는것   
  - 그럼 항상 접근할때마다 DNS로 변환하나? NO
   
* 로드밸런서(Load Balancer)?   
  - 내 코드가 JVM을 통해 시작될때 DNS를 이용하여 다른 곳에서 정보를 얻어올때 필요하다?   
  - 예를들어 외부 API로 부터 정보를 받아올때   
  - 로드밸런서가 고유한 주소를 가지고있고, 우리는 요청을 그 주소로 보낸다.    
  - 주로 사용되는것은 L4, L7. 이들은 주기적으로 인스턴스의 헬스체크를 한다.   
  - HTTP가 가지고있는 특성, 즉 어떠한 서버군에 요청하든 항상 같은 값을 반환해야하기 때문에 로드밸런서는 거의 필수적이다.
* JVM DNS캐싱
  - DNS캐싱으로 인해 계속해서 잘못된 접근을 할수도있다.
  - 하지만 항상 DNS요청을 하기엔 부하가 걸리기에 사용하는것이다.
  - 그럼 오류가 생길땐 어떻게하나? 주기적으로 dns서버에 요청하여 캐쉬를 전체업데이트!
* OSI 7계층
  - 7계층(application layer) : 사용자가 이해할수 있는 인터페이스   
    -대표 프로토콜 : HTTP
  - 4계층(transport layer)    
    -대표 프로토콜 : TCP,UDP,~~IP~~(네트워크레이어)
    TCP와 UDP의 가장 큰 차이? 보안의 정도
* HTTP
  - 특징 : stateless, coneectionless, self-descriptive, uniformed(protocol)
  - 크게 request와 response로 구분가능, RESTful(Representational State Transfer)
    + Request : method, URL/path, HTTP version
    * method : GET, POST, PATCH, PUT, DELETE(결국 CRUD)
      PUT,PATCH는 둘다 수정을 위한것이지만, PUT은 전체수정, PATCH는 부분수정
## CIDR

* CIDR?
  - 용도 : Resource를 얼마나 사용할 수 있는지 사이즈 계산     
    + 예를들어 10.0.0.0/32 IP를 리소스로 보고 얼마까지 쓸 수 있는지 알려주는것     
  - 32란 무엇인가? 32개를 마스크를 씌워서 고정한다는 말      
    + 10.0.0.0에는 8*4로 32개를 고정      
  그렇다면 10.0.0.0/16 이면 상위 16개 고정하고, 2^16개가 사용가능
