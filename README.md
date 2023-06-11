# Spring Security 실습

Spring Security에 대해 전반적인 실습을 진행했습니다.

## 1. Deprecated된 WebSecurityConfigurerAdapter을 상속
[SecurityConfigV1_Deprecated.java](src/main/java/study/security/config/SecurityConfigV1_Deprecated.java)

Spring Security 5.4 버전에서는 보안 설정을 위해 WebSecurityConfigurerAdapter 클래스를 상속받아 사용하며, 

configure(HttpSecurity http) 메서드를 오버라이드하여 원하는 설정을 진행하고 빈으로 등록한다.

## 2. 컴포넌트 기반의 보안 설정으로 전환

[SecurityConfigV2.java](src/main/java/study/security/config/SecurityConfigV2.java)

Spring Security 5.7 부터는 SecurityFilterChain Bean을 등록하는 방식을 권장한다.

## 3. Form Login 인증

[SecurityConfigV3_FormLogin.java](src/main/java/study/security/config/SecurityConfigV3_FormLogin.java)

- Form Login 방식의 인증을 하도록 설정
- Spring Security가 제공하는 기본 Login Form 대신 Thymeleaf를 사용하여 재작성

### Thymeleaf
- Spring Security 관련한 Thymeleaf 의존성을 추가 (현재 최신 버전은 springsecurity6)
  ```build.gradle
  implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
  implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity5'
  ```
- [thymeleaf-extras-springsecurity5 사용 예시](src/main/resources/templates/layout/top.html)
- 애플리케이션 실행 시 생성되는 security password와 user 아이디를 사용해서 로그인 가능

### Memorable

[로그인 정책]
  
  loginProcessingUrl("/login-proc")
    - login action 처리 경로. 이 경로로 Request하면 ```UsernamePasswordAuthenticationFilter```가 요청을 처리한다.
    - 따로 "/login-proc"에 매핑된 컨트롤러를 만들 필요없다.

  usernameParameter("username")
  - form에서 사용할 username 쿼리 파라미터의 이름을 설정한다. (input 태그의 name)
  
  passwordParameter("password")
  - form에서 사용할 password 쿼리 파라미터의 이름을 설정한다. (input 태그의 name)

  ```JSESSIONID``` 쿠키
  - 로그인 상태를 유지하기 위한 세션 id
  - 로그인 후에도 ```세션 고정 보호```를 위해 요청할 때마다 id가 변경된다. (이 내용은 이후에 정리)
  - 로그인을 하지 않아도 Anonymous id가 할당되어 있다.

[로그아웃 정책]

  logoutRequestMatcher(new AntPathRequestMatcher("/logout-page", "GET")
  - logout action 처리 경로. 이 경로로 Request하면 ```LogoutFilter```가 요청을 처리한다.
  - 따로 "/logout-page"에 매핑된 컨트롤러를 만들 필요없다.

  deleteCookies("JSESSIONID", "remember-me")
  - 로그아웃 성공 시, 해당 쿠키를 삭제한다.
  - "JSESSIONID"을 삭제할 필요가 있는지 모르겠지만, 일단 삭제했다.
  - Remember-me 기능을 위한 "remember-me" 쿠키도 삭제했다.

[Remember-me]

  userDetailsService(userDetailsService)
  - 꼭 설정 해야한다는데 용도를 잘 모르겠다. 나중에 정리해야겠다.