# 📅 일정 관리 애플리케이션

이 프로젝트는 **사용자와 일정 데이터를 연동하여 관리**할 수 있는 간단한 CRUD 기반의 백엔드 애플리케이션입니다. 사용자는 자신만의 일정을 작성, 조회, 수정, 삭제할 수 있으며, 각 일정은 특정 사용자에 귀속됩니다.

## ✅ 프로젝트 개요

- 사용자(User)는 이메일, 이름 등의 정보를 기반으로 등록됩니다.
- 일정(Schedule)은 특정 사용자에게 귀속되며, 할 일(Task) 및 내용, 비밀번호 등의 정보를 포함합니다.
- 비밀번호 검증을 통해 수정 및 삭제 기능이 제한됩니다.
- 일정 조회는 사용자 이름, 수정일 조건을 기준으로 필터링 및 페이징 처리할 수 있습니다.

## ⚙️ 기술 스택

- Java 17
- Spring Boot 3.x
- Spring Web / Validation / JDBC
- MySQL
- H2 (로컬 테스트 용도)
- Gradle
- Postman / REST Client

## 📌 주요 기능

- 사용자 등록 / 수정 / 조회
- 일정 등록 / 수정 / 삭제 / 조회
- 사용자 기반 일정 필터링
- 조건부 조회 (수정일, 작성자 이름)
- 페이징 처리
- 유효성 검증 (@Valid, @NotNull, @Size, @Email 등)
- 예외 처리 (@RestControllerAdvice, @ExceptionHandler 활용)

## 🔐 보안 / 데이터 무결성

- 일정 수정 및 삭제 시 비밀번호를 함께 전달하여 검증합니다.
- 잘못된 비밀번호 입력 시 수정 및 삭제가 불가하며, 관련 예외 메시지가 전달됩니다.
- 일정 등록 시 클라이언트에서 전달한 비밀번호는 DB에 저장되지만, 응답에서는 노출되지 않습니다.

## 🧩 테이블 설계

### User Table

| 필드명      | 타입        | 설명               |
|-------------|-------------|--------------------|
| id          | BIGINT (PK) | 사용자 고유 ID     |
| name        | VARCHAR     | 사용자 이름         |
| email       | VARCHAR     | 이메일 (unique)    |
| created_at  | DATETIME    | 생성일             |
| updated_at  | DATETIME    | 수정일             |

### Schedule Table

| 필드명        | 타입        | 설명                   |
|---------------|-------------|------------------------|
| id            | BIGINT (PK) | 일정 고유 ID           |
| taskTitle     | VARCHAR     | 할 일 제목             |
| taskContents  | VARCHAR     | 할 일 상세 내용        |
| userId        | BIGINT (FK) | 작성자 ID (User 참조) |
| password      | VARCHAR     | 일정 비밀번호          |
| created_at    | DATETIME    | 작성일                 |
| updated_at    | DATETIME    | 수정일                 |

## 🔄 삭제 전략

- 현재 프로젝트에서는 **하드 딜리트(Hard Delete)** 방식으로 데이터를 완전히 삭제하고 있습니다.
- 일정 테이블은 User 테이블의 userId를 FK로 참조하므로, 사용자 삭제 시 일정이 남아 있는 경우 삭제가 제한됩니다.
- 향후 **소프트 딜리트(Soft Delete)** 기능을 도입할 경우, `deleted_at` 컬럼을 추가하여 플래그 방식으로 처리할 수 있습니다.

## 🎯 예외 처리 설계

- 존재하지 않는 ID 조회 시 `404 Not Found` 또는 `400 Bad Request`
- 잘못된 비밀번호 입력 시 `403 Forbidden`
- 유효성 검증 실패 시 `400 Bad Request` 및 메시지 출력
- 공통 예외는 `@RestControllerAdvice`로 처리됩니다.

## 📦 실행 방법

```bash
./gradlew bootRun
```

또는

```bash
./gradlew build
java -jar build/libs/schedule-0.0.1-SNAPSHOT.jar
```

## 🧪 테스트

- Postman, curl 등을 통해 RESTful API 테스트가 가능합니다.
- H2 콘솔을 통한 DB 확인 가능 (`http://localhost:8080/h2-console`)

---

본 프로젝트는 **학습용 또는 기능 구현 연습**을 목적으로 설계되었으며, 사용자 인증 및 암호화 같은 보안 요소는 포함되어 있지 않습니다. 실무에서는 OAuth, JWT 등 인증/인가 방식이 반드시 필요합니다.


## 📘 API 목록

| 기능           | 메서드 | URL                                      | Request 필드            | Response 필드              | 상태    |
|----------------|--------|------------------------------------------|--------------------------|-----------------------------|---------|
| 일정 목록 조회   | GET    | /schedules?page_no=???&page_size=???     | 없음                     | 일정 목록 + 작성자 이름 포함 | 200 OK |
| 일정 단건 조회   | GET    | /schedules/{taskId}                      | 없음                     | 일정 정보                   | 200 OK |
| 일정 작성자별 조회 | GET    | /schedules?user_id=???                   | 없음                     | 일정 목록                   | 200 OK |
| 일정 등록       | POST   | /schedules                                | task, user_id, password  | id, task, user_id, createdAt | 200 OK |
| 일정 수정       | PATCH  | /schedules/{taskId}                       | task, user_id, password  | result: success             | 200 OK |
| 일정 삭제       | DELETE | /schedules/{taskId}                       | password                 | result: deleted             | 200 OK |
| 작성자 조회     | GET    | /users/{userId}                           | 없음                     | user_name, e-mail           | 200 OK |
| 작성자 등록     | POST   | /users                                    | user_name, e-mail        | id, user_name, e-mail       | 200 OK |
| 작성자 수정     | PATCH  | /users/{userId}                           | user_name, e-mail        | user_name, e-mail           | 200 OK |


<details>
<summary><b>📌 일정 등록 - Request/Response 예시 보기</b></summary>

<br/>

**📥 Request**

```json
{
  "task": "스터디 준비",
  "user_id": 1,
  "password": "1234"
}
```

**📤 Response**

```json
{
  "id": 1,
  "task": "스터디 준비",
  "user_id": 1,
  "createdAt": "2025-05-11T12:00:00"
}
```

</details>

<details>
<summary><b>📌 일정 목록 조회 - Request/Response 예시 보기</b></summary>

<br/>

**📥 Request**

없음 (쿼리 파라미터: `?page_no=0&page_size=10`)

**📤 Response**

```json
{
  "content": [
    {
      "id": 1,
      "task": "스터디 준비",
      "user_name": "홍길동",
      "createdAt": "2025-05-11T12:00:00"
    }
  ],
  "page_no": 0,
  "page_size": 10,
  "total_elements": 1,
  "total_pages": 1
}
```

</details>

<details>
<summary><b>📌 일정 단건 조회 - Request/Response 예시 보기</b></summary>

<br/>

**📥 Request**

없음

**📤 Response**

```json
{
  "id": 1,
  "task": "스터디 준비",
  "user_id": 1,
  "user_name": "홍길동",
  "createdAt": "2025-05-11T12:00:00"
}
```

</details>

<details>
<summary><b>📌 일정 작성자별 조회 - Request/Response 예시 보기</b></summary>

<br/>

**📥 Request**

없음 (쿼리 파라미터: `?user_id=1`)

**📤 Response**

```json
[
  {
    "id": 1,
    "task": "스터디 준비",
    "user_name": "홍길동",
    "createdAt": "2025-05-11T12:00:00"
  }
]
```

</details>

<details>
<summary><b>📌 일정 수정 - Request/Response 예시 보기</b></summary>

<br/>

**📥 Request**

```json
{
  "task": "스터디 발표 준비",
  "user_id": 1,
  "password": "1234"
}
```

**📤 Response**

```json
{
  "result": "success"
}
```

</details>

<details>
<summary><b>📌 일정 삭제 - Request/Response 예시 보기</b></summary>

<br/>

**📥 Request**

```json
{
  "password": "1234"
}
```

**📤 Response**

```json
{
  "result": "deleted"
}
```

</details>

<details>
<summary><b>📌 작성자 등록 - Request/Response 예시 보기</b></summary>

<br/>

**📥 Request**

```json
{
  "user_name": "홍길동",
  "e-mail": "hong@example.com"
}
```

**📤 Response**

```json
{
  "id": 1,
  "user_name": "홍길동",
  "e-mail": "hong@example.com"
}
```

</details>

<details>
<summary><b>📌 작성자 수정 - Request/Response 예시 보기</b></summary>

<br/>

**📥 Request**

```json
{
  "user_name": "홍길동",
  "e-mail": "new_email@example.com"
}
```

**📤 Response**

```json
{
  "user_name": "홍길동",
  "e-mail": "new_email@example.com"
}
```

</details>

<details>
<summary><b>📌 작성자 조회 - Request/Response 예시 보기</b></summary>

<br/>

**📥 Request**

없음

**📤 Response**

```json
{
  "id": 1,
  "user_name": "홍길동",
  "e-mail": "hong@example.com"
}
```

</details>


![Schedule.png](/Schedule.png)
