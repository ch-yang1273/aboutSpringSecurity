# Spring Security 학습 내용 정리

Spring Security 관련해서 학습한 내용을 정리합니다.

---

## 1. Deprecated된 WebSecurityConfigurerAdapter을 상속
Config : [SecurityConfigV1_Deprecated.java](study/src/main/java/study/security/config/SecurityConfigV1_Deprecated.java)

> Spring Security 5.0 부터 Deprecated 된 `WebSecurityConfigurerAdapter`를 사용하여 보안 설정을 진행했습니다.

Spring Security 5.0 이전 버전에서는 보안 설정을 위해 WebSecurityConfigurerAdapter 클래스를 상속받아 사용하며, 

configure(HttpSecurity http) 메서드를 오버라이드하여 원하는 설정을 진행하고 빈으로 등록합니다.

---

## 2. 컴포넌트 기반의 보안 설정으로 전환

Config : [SecurityConfigV2.java](study/src/main/java/study/security/config/SecurityConfigV2_Without_WebSecurityConfigurerAdapter.java)

> 권장 방식에 따라 `WebSecurityConfigurerAdapter`을 상속하지 않는 방식으로 보안 설정을 진행 했습니다.

- `SecurityFilterChain`을 빈으로 등록하여 `HttpSecurity`를 설정
- `AuthenticationManagerBuilder`를 사용하여 Custom 한 인증 설정 추가 (`AuthenticationProvider`, `UserDetailsService`)

### 참고
- 공식 블로그 : [Spring Security without the WebSecurityConfigurerAdapter](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)
- baeldung 블로그 : [Spring Security: Upgrading the Deprecated WebSecurityConfigurerAdapter](https://www.baeldung.com/spring-deprecated-websecurityconfigureradapter)

---

## 3. Form Login 인증

Config : [SecurityConfigV3_FormLogin.java](study/src/main/java/study/security/config/SecurityConfigV3_FormLogin.java)

> 전반적인 Form Login 관련 보안 설정을 진행하고, `Thymeleaf`를 사용한 로그인 예시를 작성했습니다.

### 기본적인 설정 방법
보안 정책을 설정하려면, 먼저 관련 Configurer를 반환하는 메서드드를 호출해야 합니다.
- 예) formLogin(), logout(), rememberMe(), sessionManagement() 등

이러한 메서드들을 호출한 후 각 보안 기능에 대한 세부 설정을 할 수 있습니다.

해당 범위의 보안 설정이 끝나면 `;`로 마무리하거나 `and()` 메서드를 호출 할 수 있습니다.
`and()` 메서드를 호출하면 HttpSecurity 객체가 다시 반환되어, 해당 범위의 설정을 끝내고 새로운 보안 설정을 시작할 수 있습니다.

- Form Login 방식의 인증을 하도록 설정
- Spring Security가 제공하는 기본 Login Form 대신 Thymeleaf를 사용하여 재작성

### Thymeleaf
- Spring Security 관련한 Thymeleaf 의존성을 추가 (현재 최신 버전은 springsecurity6)

  ```build.gradle
  implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
  implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity5'
  ```

- [thymeleaf-extras-springsecurity5 사용 예시](study/src/main/resources/templates/layout/top.html)

### 로그인 정책
  
  loginProcessingUrl("/login-proc")
  - login action 처리 경로. 이 경로로 Request하면 `UsernamePasswordAuthenticationFilter`가 요청을 처리합니다.
  - "/login-proc"에 매핑된 컨트롤러는 따로 만들지 않습니다.

  usernameParameter("username")
  - form에서 사용할 username 쿼리 파라미터의 이름을 설정 (input 태그의 name)
  
  passwordParameter("password")
  - form에서 사용할 password 쿼리 파라미터의 이름을 설정 (input 태그의 name)

  `JSESSIONID` 쿠키
  - 로그인 상태를 유지하기 위한 세션 id
  - 로그인 후에도 `세션 고정 보호`를 위해 요청할 때마다 id가 변경됩니다. (이 내용은 이후에 정리)
  - 로그인을 하지 않아도 `JSESSIONID` 쿠키가 있을 수 있는데, 이건 Anonymous id 입니다.

### 로그아웃 정책

  logoutRequestMatcher(new AntPathRequestMatcher("/logout-page", "GET")
  - logout action 처리 경로. 이 경로로 Request하면 `LogoutFilter`가 요청을 처리합니다.
  - "/logout-page"에 매핑된 컨트롤러는 따로 만들지 않습니다.

  deleteCookies("JSESSIONID", "remember-me")
  - 로그아웃 성공 시, 해당 쿠키를 삭제
  - "JSESSIONID"을 삭제할 필요가 있는지 모르겠지만, 일단 삭제했습니다.
  - Remember-me 기능을 위한 "remember-me" 쿠키도 삭제

### Remember-me

```java
.rememberMe()
.rememberMeParameter("remember-me")
.tokenValiditySeconds(3600)
.userDetailsService(userDetailsService)
```

  userDetailsService(userDetailsService)

### Session Management

  maximumSessions(1)
  - 최대로 유지할 세션 수 설정

  maxSessionsPreventsLogin(true)
  - 세션 수가 최대일 때 정책 설정
  - true : 새로운 로그인 요청을 차단
  - false : 기존 세션을 만료

---

## 4. 선언적 방식의 URL 인가(Authorization) 권한 설정

Config : [SecurityConfigV4_Authorization.java](study/src/main/java/study/security/config/SecurityConfigV4_Authorization.java)

> 선언적 방식의 URL 인가 설정 방법을 알아봤습니다.

### URL Authorization 표현식

Spring Security 공식 API 문서 (v5.7.8) : [ExpressionUrlAuthorizationConfigurer.AuthorizedUrl](https://docs.spring.io/spring-security/site/docs/5.7.8/api/org/springframework/security/config/annotation/web/configurers/ExpressionUrlAuthorizationConfigurer.AuthorizedUrl.html)

| Method                                     | Description |
|:-------------------------------------------|:----|
| `permitAll()`                              | 모든 사용자가 허용되는 URL을 설정합니다. |
| `denyAll()`                                | 아무도 허용되지 않는 URL을 설정합니다. |
| `authenticated()`                          | 인증된 사용자가 허용되는 URL을 설정합니다. |
| `anonymous()`                              | 익명 사용자가 허용되는 URL을 설정합니다. |
| `rememberMe()`                             | "remembered"된 사용자가 허용되는 URL을 설정합니다. |
| `fullyAuthenticated()`                     | 인증했지만 "remembered"되지 않은 사용자가 허용되는 URL을 설정합니다. |
| `hasAuthority(String authority)`           | URL이 특정 권한을 요구하도록 설정합니다. |
| `hasRole(String role)`                     | URL이 특정 역할을 요구하도록 설정하는 단축어입니다. |
| `hasAnyAuthority(String... authorities)`   | URL이 여러 권한 중 어느 하나를 요구하도록 설정합니다. |
| `hasAnyRole(String... roles)`              | URL이 여러 역할 중 어느 하나를 요구하도록 설정하는 단축어입니다. |
| `hasIpAddress(String ipaddressExpression)` | URL이 특정 IP 주소 또는 서브넷을 요구하도록 설정합니다. |
| `access(String attribute)`                 | 임의의 표현식에 의해 보안된 URL을 설정합니다. |
| `not()`                                    | 다음 표현식을 부정합니다. |
| `getMatchers()`                            | RequestMatcher의 리스트를 반환합니다. |

### 주의 사항

구체적인 경로를 먼저 설정하고, 그것보다 큰 범위의 경로는 뒤에 설정해야 합니다.

```java
.authorizeRequests()
  .antMatchers("/v4/user").hasRole("USER")
  // (선) 구체적인 경로
  .antMatchers("/v4/sys/admin").hasRole("ADMIN")
  // (후) 보다 넓은 범위의 경로
  .antMatchers("/v4/sys/**").access("hasRole('ADMIN') or hasRole('SYS')")
.anyRequest().permitAll()
```

### 인증/인가 예외 처리

[예외 조건]

인증 예외 조건 (AuthenticationException 발생 조건)
1. 사용자가 아직 인증되지 않았음
2. 인증에 실패함 (올바르지 않은 아이디나 패스워드)

인가 예외 조건 (AccessDeniedException 발생 조건)
1. 권한이 없는 페이지 접근

[인증/인가 처리]

- authenticationEntryPoint : 인증 실패 시 처리 (로그인 자체를 안했을 때)
- accessDeniedHandler : 인가 실패 시 처리 (로그인은 했으나 권한이 부족할 때)
- accessDeniedPage : 인가 실패 시 리디렉션 할 페이지

---

## CSRF와 CSRF 필터

### CSRF

참고 : [CSRF(Cross-Site Request Forgery) attack](https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=aepkoreanet&logNo=221457283624)

### 토큰을 사용한 CSRF 방어

HTTP 메서드 중 GET을 제외한 POST, PATCH, PUT, DELETE 요청에는 CSRF 토큰을 요구합니다.

RESTful API에서 JWT 또는 OAuth 등의 방식으로 인증을 처리한다면, 별도의 CSRF 방어는 사용할 필요가 없습니다.

```java
http.csrf() // (Default) 활성화
http.csrf().disabled() // 비활성화
```

*`Thymeleaf`는 자동으로 `<input>` 태그와 `_csrf` 토큰을 생성합니다.

[csrf.html - Thymeleaf]

- `<form th:action="@{/login-proc}" method="post">` : action 속성에 Thymeleaf 문법을 사용해야, 자동으로 `_csrf` 토큰이 생성됩니다.

```html
<div class="container">
  <h4>SecurityConfigV5_CSRF</h4>
  <div class="row">
    <h4 class="col-12">CSRF Test Form</h4>
    <form th:action="@{/v5/csrf}" class="col-12" method="post">
      <div class="form-group">
        <label for="test">post text</label>
        <input type="text" class="form-control" id="test" name="text" placeholder="text">
      </div>
      <button type="submit" class="btn btn-lg btn-primary btn-block">POST</button>
    </form>
  </div>
</div>
```

[생성 된 HTML의 Form]
- `Thymeleaf`에서 작성하지 않은 태그`<input type="hidden" name="_csrf" value="token">`가 삽입되어 있습니다.
```html
<div class="container">
  <h4>SecurityConfigV5_CSRF</h4>
  <div class="row">
    <h4 class="col-12">CSRF Test Form</h4>
    <form action="/v5/csrf" class="col-12" method="post"><input type="hidden" name="_csrf" value="85d2a4da-65f0-4e78-8ae7-9a5c359503f3"/>
      <div class="form-group">
        <label for="test">Post text</label>
        <input type="text" class="form-control" id="test" name="text" placeholder="text">
      </div>
      <button type="submit" class="btn btn-lg btn-primary btn-block">POST</button>
    </form>
  </div>
</div>
```

---

## Reference

- 정수원 - 스프링 시큐리티