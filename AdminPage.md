# 인증/인가 관리자 페이지

동적으로 인증과 인가 정보를 설정하는 관리자 페이지입니다.

`Thymeleaf`와 `Bootstrap`으로 프론트 페이지를 작성하였고, `jQuery`를 사용해서 관리자 설정 페이지는 Single page로 동작하도록 구성했습니다. 

---

## 사용 기술

### Backend

![Spring](https://img.shields.io/badge/Spring-6DB33F.svg?&style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-430098.svg?&style=for-the-badge&logo=spring&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-007080.svg?&style=for-the-badge&logo=hibernate&logoColor=white)

### Frontend

![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F.svg?&style=for-the-badge&logo=thymeleaf&logoColor=white)
![jQuery](https://img.shields.io/badge/jQuery-0769AD.svg?&style=for-the-badge&logo=jquery&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-7952B3.svg?&style=for-the-badge&logo=bootstrap&logoColor=white)

---
## ERD

```mermaid
classDiagram
    direction TB
    class Member {
        -Long id
        -String username
        -String password
        -String email
        -MemberRole role
    }
    class AuthorizationResource {
        -Long id
        -String resourceName
        -String httpMethod
        -int orderNum
        -String resourceType
        -MemberRole memberRole
    }
    class MemberRole {
        -Long id
        -String roleName
        -String description
    }
    class MemberRoleHierarchy {
        -Long id
        -String childName
        -String parentName
    }

    Member --> MemberRole
    AuthorizationResource --> MemberRole
    MemberRoleHierarchy --> MemberRole
```

---

## 기능

### 회원가입/로그인

form 로그인 방식으로 구현했고, 권한이 필요한 페이지 접근 시에는 로그인 페이지로 리디렉트됩니다.

로그인 후에 권한이 부족한 페이지 접근 시에는 403 Forbidden 페이지로 리디렉트됩니다.

![회원가입 페이지](https://user-images.githubusercontent.com/61798028/248839983-c78be08f-cf46-4b83-8983-fba6b7f3ad09.png)
![로그인 페이지](https://user-images.githubusercontent.com/61798028/248839980-0f9e3610-1123-4f1a-8769-2b00e10de286.png)

### 관리자 페이지

`jQuery`를 사용하여 각 설정 테이블이 SPA로 동작하도록 구현했습니다.

![관리자 페이지 버튼](https://user-images.githubusercontent.com/61798028/248839986-94d71c48-f93c-4a5f-ae4b-be0f506268ab.png)

### 사용자 관리

회원으로 등록된 사용자의 정보를 보여줍니다.

![사용자 관리 테이블](https://user-images.githubusercontent.com/61798028/248839969-7c858017-4a85-4edb-b3b7-b95489a50502.png)

### 권한 관리

사용자 권한(Role) 정보를 동적으로 관리합니다.

`RoleHierarchy`로 각 권한에 계층 관계를 설정하여, 상위 권한을 갖는 사용자가 하위 권한으로 설정 된 리소스에 접근이 가능합니다.

![권한 관리 테이블](https://user-images.githubusercontent.com/61798028/248839993-a2d17ac8-d25a-4547-8a73-e3a64449df57.png)

[권한 추가]

![권한 추가](https://user-images.githubusercontent.com/61798028/248839996-88c5a8de-6dfe-443a-a2c2-52ae04900cc6.png)

[권한 계층 설정]

![권한 계층 설정](https://user-images.githubusercontent.com/61798028/248839989-c1fbfa40-bbc7-40d7-9516-cd3d67d4d840.png)

### 리소스 관리

각 URL 경로에 접근할 수 있는 권한 레벨을 설정합니다.

![리소스 테이블](https://user-images.githubusercontent.com/61798028/248840007-b11ac193-981c-428c-8075-015a2b5b1fd8.png)

[리소스 추가]

![리소스 추가](https://user-images.githubusercontent.com/61798028/248840010-82c10258-4d64-4945-9220-7f7e89924375.png)

### 인가 테스트

리소스에 설정한 인가 권한을 테스트할 수 있는 링크 버튼입니다.

![인가 테스트 링크 버튼](https://user-images.githubusercontent.com/61798028/248840001-abcbaf19-4128-4ef4-8563-062d49c73693.png)

---

## Reference

- 정수원 - 스프링 시큐리티