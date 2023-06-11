# Spring Security 실습

Spring Security에 대해 전반적인 실습을 진행했습니다.

### 1. Deprecated된 WebSecurityConfigurerAdapter을 상속
[SecurityConfigV1_Deprecated.java](security/src/main/java/study/security/config/SecurityConfigV1_Deprecated.java)

Spring Security 5.4 버전에서는 보안 설정을 위해 WebSecurityConfigurerAdapter 클래스를 상속받아 사용하며, 

configure(HttpSecurity http) 메서드를 오버라이드하여 원하는 설정을 진행하고 빈으로 등록한다.

### 2. 컴포넌트 기반의 보안 설정으로 전환

[SecurityConfigV2.java](security/src/main/java/study/security/config/SecurityConfigV2.java)

Spring Security 5.7 부터는 SecurityFilterChain Bean을 등록하는 방식을 권장한다.

### 3. Form Login 인증

[SecurityConfigV3_FormLogin.java](security/src/main/java/study/security/config/SecurityConfigV3_FormLogin.java)

- Form Login 방식의 인증을 하도록 설정
- Spring Security가 제공하는 기본 Login Form 대신 Thymeleaf를 사용하여 재작성
- Spring Security 관련한 Thymeleaf 의존성을 추가 (현재 최신 버전은 springsecurity6)
    ```build.gradle
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity5'
    ```
- [thymeleaf-extras-springsecurity5 사용 예시](security/src/main/resources/templates/layout/top.html)
- 애플리케이션 실행 시 생성되는 security password와 user 아이디를 사용해서 로그인 가능