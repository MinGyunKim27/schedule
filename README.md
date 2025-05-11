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
