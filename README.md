# Spring Security 실습

Spring Security에 대해 전반적인 실습을 진행했습니다.

## 1. Deprecated된 WebSecurityConfigurerAdapter을 상속
Config : [SecurityConfigV1_Deprecated.java](src/main/java/study/security/config/SecurityConfigV1_Deprecated.java)

Spring Security 5.4 버전에서는 보안 설정을 위해 WebSecurityConfigurerAdapter 클래스를 상속받아 사용하며, 

configure(HttpSecurity http) 메서드를 오버라이드하여 원하는 설정을 진행하고 빈으로 등록합니다.

## 2. 컴포넌트 기반의 보안 설정으로 전환

Config : [SecurityConfigV2.java](src/main/java/study/security/config/SecurityConfigV2.java)

Spring Security 5.7 부터는 SecurityFilterChain Bean을 등록하는 방식을 권장합니다.
- 애플리케이션 실행 시 생성되는 security password와 `user` 아이디를 사용해서 로그인 테스트 가능

### 설정 기본 개념
보안 정책을 설정하려면, 먼저 관련 Configurer를 반환하는 메서드드를 호출해야 합니다.
- 예) formLogin(), logout(), rememberMe(), sessionManagement() 등

이러한 메서드들을 호출한 후 각 보안 기능에 대한 세부 설정을 할 수 있습니다.

해당 범위의 보안 설정이 끝나면 ```;```로 마무리하거나 ```and()``` 메서드를 호출 할 수 있습니다.
```and()``` 메서드를 호출하면 HttpSecurity 객체가 다시 반환되어, 해당 범위의 설정을 끝내고 새로운 보안 설정을 시작할 수 있습니다.

## 3. Form Login 인증

Config : [SecurityConfigV3_FormLogin.java](src/main/java/study/security/config/SecurityConfigV3_FormLogin.java)

- Form Login 방식의 인증을 하도록 설정
- Spring Security가 제공하는 기본 Login Form 대신 Thymeleaf를 사용하여 재작성

### Thymeleaf
- Spring Security 관련한 Thymeleaf 의존성을 추가 (현재 최신 버전은 springsecurity6)
  ```build.gradle
  implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
  implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity5'
  ```
- [thymeleaf-extras-springsecurity5 사용 예시](src/main/resources/templates/layout/top.html)

### Memorable

[로그인 정책]
  
  loginProcessingUrl("/login-proc")
  - login action 처리 경로. 이 경로로 Request하면 ```UsernamePasswordAuthenticationFilter```가 요청을 처리합니다.
  - "/login-proc"에 매핑된 컨트롤러는 따로 만들지 않습니다.

  usernameParameter("username")
  - form에서 사용할 username 쿼리 파라미터의 이름을 설정 (input 태그의 name)
  
  passwordParameter("password")
  - form에서 사용할 password 쿼리 파라미터의 이름을 설정 (input 태그의 name)

  ```JSESSIONID``` 쿠키
  - 로그인 상태를 유지하기 위한 세션 id
  - 로그인 후에도 ```세션 고정 보호```를 위해 요청할 때마다 id가 변경됩니다. (이 내용은 이후에 정리)
  - 로그인을 하지 않아도 ```JSESSIONID``` 쿠키가 있을 수 있는데, 이건 Anonymous id 입니다.

[로그아웃 정책]

  logoutRequestMatcher(new AntPathRequestMatcher("/logout-page", "GET")
  - logout action 처리 경로. 이 경로로 Request하면 ```LogoutFilter```가 요청을 처리합니다.
  - "/logout-page"에 매핑된 컨트롤러는 따로 만들지 않습니다.

  deleteCookies("JSESSIONID", "remember-me")
  - 로그아웃 성공 시, 해당 쿠키를 삭제
  - "JSESSIONID"을 삭제할 필요가 있는지 모르겠지만, 일단 삭제했습니다.
  - Remember-me 기능을 위한 "remember-me" 쿠키도 삭제

[Remember-me]

  userDetailsService(userDetailsService)
  - 꼭 설정 해야한다는데 용도를 잘 모르겠다. 나중에 정리해야겠다.

[Session Management]

  maximumSessions(1)
  - 최대로 유지할 세션 수 설정

  maxSessionsPreventsLogin(true)
  - 세션 수가 최대일 때 정책 설정
  - true : 새로운 로그인 요청을 차단
  - false : 기존 세션을 만료

## 4. 선언적 방식의 URL 인가(Authorization) 권한 설정

Config : [SecurityConfigV4_Authorization.java](src/main/java/study/security/config/SecurityConfigV4_Authorization.java)

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

인증 예외 조건 (AuthenticationException 발생 조건)
1. 사용자가 아직 인증되지 않았음
2. 인증에 실패함 (올바르지 않은 아이디나 패스워드)

인가 예외 조건 (AccessDeniedException 발생 조건)
1. 권한이 없는 페이지 접근

authenticationEntryPoint : 인증 실패 시 처리
- Redirection은 굳이 이 핸들러를 설정하지 않아도 됩니다.
- 특별히 처리 할 내용 없으면 설정 X

accessDeniedHandler : 인가 실패 시 처리
- 인가 실패는 처리하지 않으면 403 에러가 반환됩니다.

accessDeniedPage : 인가 실패 시 리디렉션 할 페이지
- 특별히 처리할 일 없으면 accessDeniedHandler말고 이 메서드 사용