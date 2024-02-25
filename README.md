![notice1.png](img/notice1.png)

<h1><a href="https://webduck.info" target="_blank"> 웹툰 덕후들을 위한 사이트, WebDuck</a></h1>


[https://webduck.info](https://webduck.info)


---
## 소개

웹덕은 웹툰을 좋아하는 사람들의 웹툰 리뷰들을 모아놓은 커뮤니티 사이트입니다

웹툰 리뷰평을 보고 보고싶은 웹툰을 찾아봐요


## 기술 스택

---
|                                                                                                                                                                                                                                                                          **Backend**                                                                                                                                                                                                                                                                           |         
|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:| 
|![Spring boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=SpringBoot&logoColor=white) ![RestDocs](https://img.shields.io/badge/RestDocs-ff?style=flat-square&logo=SpringBoot&logoColor=white) ![Spring Security](https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat-square&logo=SpringSecurity&logoColor=white) ![Gradle](https://img.shields.io/badge/Gradle-white?style=flat-square&logo=gradle&color=02303A) ![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=flat-the-badge&logo=mariadb&logoColor=white) 
|                                                                                                                                                                                                                                                                          **Frontend**   
|![Vue](https://img.shields.io/badge/Vue.js-35495E?style=flat-the-badge&logo=vuedotjs&logoColor=4FC08D)
|                                                                                                                                                                                                                                                                           **Infra**                                                                                                                                                                                                                                                                            |
|![AWS](https://img.shields.io/badge/AWS-232F3E?style=flat-square&logo=AmazonAWS&logoColor=white)![Git Action](https://img.shields.io/badge/GitAction-2088FF?style=flat-square&logo=GithubActions&logoColor=white) ![S3](https://img.shields.io/badge/S3-569A31?style=flat-square&logo=AmazonS3&logoColor=white)                                                                                                                         

## 주요 기능

---
- 장르별/플랫폼별/요일별 웹툰 조회
- 웹툰 리뷰 작성
- 리뷰순/평점순으로 웹툰을 정렬해 인기 웹툰 조회

## 아키텍쳐

---
![img.png](img/architecture.png)

## ERD

---
작성중...


## 기술

---
### 보안
- [OAuth 인증 구현](#oauth-인증-구현)
- [Session 기반 사용자 인가](#session-기반-사용자-인가)

### 성능
- [N+1](#n+1)
- [더미 데이터를 위한 Bulk insert](#bulk-insert)


### API 문서 관리
- [RestDocs 이용한 API 문서화](#restdocs-이용한-api-문서화)

### QueryDsl
- [QueryDsl 동적 쿼리](#querydsl)



---

## OAuth 인증 구현

**1. OAuth를 도입한 이유**
- 간편 로그인을 통한 사용자 경험 향상
- 사용자의 세부적인 개인정보(비밀번호) 관리를 줄여주고 내 사이트에서 개인정보를 관리하는 것보다 보안적임 

---
## Session 기반 사용자 인가
**1. JWT Token 대신 Session을 선택한 이유**
- 비용성
  - JWT : RefreshToken 저장을 위한 Redis 서버를 생성해야되는 추가적인 비용 발생 
  - Session : 단일 서버 환경에서는 추가 비용 없이 구현 가능
    >  그러나 로드 밸런서를 통한 여러 서버로 확장 시, 세션을 공유하기 위해 별도의 세션 저장소(예:Redis)가 필요하므로, 초기 단계에서 비용이 절감되나, 확장 단계에서는 추가 비용 발생

- 확장성
  - Session 기반으로 구성했을 때 Session 을 사용하면 확장성이 좋지 않은가? X
    > Refresh Token 을 Redis 에 저장하는것처럼 Session 또한 서버 메모리대신 외부 메모리에 저장하여 세션 클러스터링이나 세션 Storage를 통해 확장 가능
    
- 안정성
  - JWT : 생각보다 큰 JWT Token 사이즈
  - JWT : 로그아웃과 같은 사용자 상태 관리 불가능 **(가능하긴 하지만 완전한 stateless 불가능)**
  - Session : 로그아웃 시 서버 메모리단에서 사용자 세션정보 삭제
  - Session : 동시 접속수가 많을 시 서버단에서 세션을 관리하기 때문에 서버 부하 발생

- 보안성
  - JWT : 토큰 도난 시 능동적인 토큰 만료 불가능 **(가능하긴 하지만 완전한 stateless 불가능)**
  - JWT : 토큰 안전하게 구현하지 않을 시 XSS/CSRF 취약점 발생 가능(짧은 토큰 만료시간,HttpOnly,CSRF,HTTPS 등으로 해결 가능하긴 함)
  - JWT : 사용자의 정보를 토큰에 담아서 보관 
  - Session : 세션 기반 인증은 사용자 상태를 서버에서 관리하므로 세션을 관리 즉 무효화 가능
  - Session :  사용자 쿠키에 저장된 세션 식별자는 민감한 정보를 포함하지 않음


결과적으로 초기 비용 절감,사용자 상태 관리의 용이성,아직은 여유로운 트래픽,보안성을 고민한 결과 Session 방식을 선택하게 되었습니다.

물론, Session 방식이 JWT 방식보다 더 낫다는것이 아닙니다.

서버측에서 사용자의 세션 정보를 보관하므로 동시 접속중인 사용자가 많아지면 서버에 부하가 생길 수 있고 세션 정보가 여러 서버에 분산될 경우 데이터 일관성에 대한 문제가 발생 할 수 있습니다.(추후 고민거리)

또한 Session 하이재킹같은 보안적인 문제가 발생할수도 있습니다. 

---
## N+1 

---
## Bulk insert

---
## RestDocs 이용한 API 문서화
**1. 신뢰할 수 있는 API 문서**
- 정확성 : 실제 테스트 케이스에서 생성된 요청과 응답을 기반으로 문서를 생성
- 유연성 : 코드변경 시 테스트가 실패하기 때문에 개발자가 알 수 밖에 없음


---

## QueryDsl
**1.QueryDsl를 도입한 이유** 
- 무한 페이징,장르별 웹툰 조회 등 조건에 따라 변하는 동적 쿼리 발생
- 이러한 동적 쿼리를 JPQL로 작성하게 된다면 문자열로 작성해야 되는데 가독성이 좋지 않음
- 타입 안전성 : 컴파일 시점에서 문법 오류를 잡아내어 런타임 시 발생할 수 있는 에러 최소화
- [QueryDsl 적용기](https://velog.io/@minu1117/QueryDsl-%EA%B8%B0%EB%B3%B8-%EC%82%AC%EC%9A%A9%EB%B2%95-1)