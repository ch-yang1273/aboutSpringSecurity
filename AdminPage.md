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

form 로그인 방식으로 구현했고, 권한이 필요한 페이지 접근 시에는 로그인 페이지로 이동됩니다.

로그인 후에 권한이 부족한 페이지 접근 시에는 403 Forbidden 페이지로 이동하도록 구성했습니다.

![회원가입 페이지]()
![로그인 페이지]()

### 관리자 페이지

`jQuery`를 사용하여 각 설정 테이블이 SPA로 동작합니다.

![관리자 페이지 버튼]()

### 사용자 관리

회원으로 등록된 사용자의 정보를 보여줍니다.

![사용자 관리 테이블]()

### 권한 관리

사용자 권한(Role) 정보를 동적으로 관리합니다.

`RoleHierarchy`로 각 권한에 계층 관계를 설정하여, 상위 권한을 갖는 사용자가 하위 권한으로 설정 된 리소스에 접근이 가능합니다.

![권한 관리 테이블]()

- 권한 계층 설정

    ![권한 계층 설정]()

- 권한 추가

    ![권한 추가]()

### 리소스 관리

각 URL 경로에 접근할 수 있는 권한 레벨을 설정합니다.

![리소스 테이블]()

- 리소스 추가

![리소스 추가]()

### 인가 테스트

리소스에 설정한 인가 권한을 테스트할 수 있는 링크 버튼입니다.