| 기능       | 메서드 | URL                  | Request                   | Response             | 상태       |
|------------|--------|-------------|--------------------|-----------------------|------------|
| 일정 목록 조회   | GET    | /schedules?page_no=???&&page_size=??? | 없음            | 다건 응답 정보 | 200 OK       |
| 일정 조회   | GET    | /schedules/{taskId} | 없음           | 단건 응답 정보 | 200 OK       |
| 일정 조회   | GET    | /schedules?user_id=??? | 없음           | 다건 응답 정보 | 200 OK       |
| 일정 등록  | POST| /schedules| { "task": "...", "user_id": "...", "password" : "..."} | { "id": 1, "createdAt": "...","task": "...", "user_id": "..." } | 200 OK |
| 일정 수정| PATCH | /schedules/{taskId}|{ "task": "...", "user_id": "...", "password" : "..." } | { "result": "success" }| 200 OK  |
| 일정 삭제   | DELETE | /schedules/{taskId}  | { "password" : "..." }  | "deleted" | 200 OK    |
| 작성자 조회   | GET    | /users/{userId} | 없음           | 단건 응답 정보 | 200 OK       |
| 작성자 등록   | POST    | /users | {"user_name" : "...", "e-mail":"..."  | {"id" = 1,"user_name" : "...", "e-mail":"..."} | 200 OK       |
| 작성자 수정   | PATCH    | /users/{userId} | {"user_name" : "...", "e-mail":"..."  | "user_name" : "...", "e-mail":"..." | 200 OK       |