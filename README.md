# Student Management API

## 1. 프로젝트 소개

Spring Boot를 활용하여 학생 정보를 등록, 조회, 수정, 삭제할 수 있는 REST API를 구현한 프로젝트입니다.

## 2. 개발 환경

- Java 21
- Spring Boot 4.1.1
- Gradle
- IntelliJ IDEA
- Spring Web MVC

## 3. API 목록

| HTTP Method | Endpoint | 기능 |
|---|---|---|
| GET | /students | 전체 학생 조회 |
| GET | /students/{id} | 특정 학생 조회 |
| POST | /students | 학생 등록 |
| POST | /students/sample | 예시 학생 등록 |
| PUT | /students/{id} | 학생 정보 전체 수정 |
| PUT | /students/{id}/name | 학생 이름 수정 |
| DELETE | /students/{id} | 특정 학생 삭제 |
| DELETE | /students | 전체 학생 삭제 |

## 4. 주요 구현 기능

### REST API
GET, POST, PUT, DELETE 메소드를 각각 2개씩 구현하여 총 8개의 API를 제공합니다.

### 미들웨어
RequestLoggingFilter를 구현하여 HTTP 요청 메소드, 요청 URL 및 응답 상태 코드를 콘솔에 출력합니다.

### HTTP 응답 코드
다음 HTTP 응답 코드를 사용합니다.

- 200 OK
- 201 Created
- 400 Bad Request
- 404 Not Found
- 500 Internal Server Error
- 503 Service Unavailable

500과 503 응답은 과제 테스트를 위해 `/students?error=500`, `/students?error=503`으로 재현할 수 있습니다.

### JSON 응답 형식 통일
모든 주요 API 응답을 status와 data 구조로 반환하며, GlobalExceptionHandler를 통해 잘못된 JSON 요청과 예기치 않은 서버 예외도 처리합니다.

## 5. 실행 방법

1. IntelliJ IDEA에서 프로젝트를 엽니다.
2. Java 21 환경을 설정합니다.
3. Spring Boot 애플리케이션을 실행합니다.
4. `http://localhost:8080/students`에 접속합니다.
5. `requests.http` 파일을 사용하여 API를 테스트합니다.

## 6. 데이터 저장 방식

학생 데이터는 ArrayList를 이용하여 메모리에 저장합니다. 별도의 데이터베이스를 사용하지 않으며, 서버를 재시작하면 초기 학생 데이터로 돌아갑니다.

## 7. 테스트

IntelliJ HTTP Client를 이용하여 API 동작 및 HTTP 응답 코드를 확인하였습니다. 실행 결과는 별도의 스크린샷으로 기록하였습니다.
