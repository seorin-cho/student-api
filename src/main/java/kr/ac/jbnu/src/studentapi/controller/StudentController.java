
package kr.ac.jbnu.src.studentapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    // 학생 데이터를 메모리에 저장
    private final List<Map<String, Object>> students = new ArrayList<>();

    public StudentController() {
        students.add(createStudent(1, "홍길동", "20240001"));
        students.add(createStudent(2, "김철수", "20240002"));
    }

    private Map<String, Object> createStudent(
            int id, String name, String studentNumber) {

        Map<String, Object> student = new LinkedHashMap<>();
        student.put("id", id);
        student.put("name", name);
        student.put("studentNumber", studentNumber);
        return student;
    }

    private Map<String, Object> response(
            String status, Object data) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", status);
        result.put("data", data);
        return result;
    }

    // GET 1: 전체 학생 조회
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllStudents(
            @RequestParam(required = false) String error) {

        // 500 서버 내부 오류 테스트
        if ("500".equals(error)) {
            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR
            ).body(response("error",
                    Map.of("message", "서버 내부 오류가 발생했습니다.")));
        }

        // 503 서비스 이용 불가 테스트
        if ("503".equals(error)) {
            return ResponseEntity.status(
                    HttpStatus.SERVICE_UNAVAILABLE
            ).body(response("error",
                    Map.of("message", "서비스를 일시적으로 이용할 수 없습니다.")));
        }

        // 정상적인 전체 학생 조회
        return ResponseEntity.ok(response("success", students));
    }


    // GET 2: 특정 학생 조회
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getStudentById(
            @PathVariable int id) {

        for (Map<String, Object> student : students) {
            if ((int) student.get("id") == id) {
                return ResponseEntity.ok(response("success", student));
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response("error",
                        Map.of("message", "학생을 찾을 수 없습니다.")));
    }

    // POST 1: 학생 등록
    @PostMapping
    public ResponseEntity<Map<String, Object>> addStudent(
            @RequestBody Map<String, Object> request) {

        String name = request.get("name") instanceof String
                ? ((String) request.get("name")).trim() : "";

        String studentNumber =
                request.get("studentNumber") instanceof String
                        ? ((String) request.get("studentNumber")).trim() : "";

        if (name.isEmpty() || studentNumber.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(response("error",
                            Map.of("message",
                                    "이름과 학번을 입력해야 합니다.")));
        }

        int newId = students.stream()
                .mapToInt(s -> (int) s.get("id"))
                .max().orElse(0) + 1;

        Map<String, Object> newStudent =
                createStudent(newId, name, studentNumber);

        students.add(newStudent);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response("success", newStudent));
    }

    // POST 2: 예시 학생 자동 등록
    @PostMapping("/sample")
    public ResponseEntity<Map<String, Object>> addSampleStudent() {

        int newId = students.stream()
                .mapToInt(s -> (int) s.get("id"))
                .max().orElse(0) + 1;

        Map<String, Object> sampleStudent =
                createStudent(newId, "박민수", "20240004");

        students.add(sampleStudent);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response("success", sampleStudent));
    }


    // PUT 1: 학생 정보 전체 수정
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateStudent(
            @PathVariable int id,
            @RequestBody Map<String, Object> request) {

        String name = request.get("name") instanceof String
                ? ((String) request.get("name")).trim() : "";

        String studentNumber =
                request.get("studentNumber") instanceof String
                        ? ((String) request.get("studentNumber")).trim() : "";

        if (name.isEmpty() || studentNumber.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(response("error",
                            Map.of("message", "이름과 학번을 모두 입력해야 합니다.")));
        }

        for (Map<String, Object> student : students) {
            if ((int) student.get("id") == id) {

                student.put("name", name);
                student.put("studentNumber", studentNumber);

                return ResponseEntity.ok(
                        response("success", student));
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response("error",
                        Map.of("message", "학생을 찾을 수 없습니다.")));
    }


    // PUT 2: 학생 이름만 수정
    @PutMapping("/{id}/name")
    public ResponseEntity<Map<String, Object>> updateStudentName(
            @PathVariable int id,
            @RequestBody Map<String, Object> request) {

        String name = request.get("name") instanceof String
                ? ((String) request.get("name")).trim() : "";

        if (name.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(response("error",
                            Map.of("message", "이름을 입력해야 합니다.")));
        }

        for (Map<String, Object> student : students) {
            if ((int) student.get("id") == id) {

                student.put("name", name);

                return ResponseEntity.ok(
                        response("success", student));
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response("error",
                        Map.of("message", "학생을 찾을 수 없습니다.")));
    }


    // DELETE 1: 특정 학생 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudent(
            @PathVariable int id) {

        for (int i = 0; i < students.size(); i++) {
            Map<String, Object> student = students.get(i);

            if ((int) student.get("id") == id) {

                Map<String, Object> deletedStudent =
                        students.remove(i);

                return ResponseEntity.ok(
                        response("success", deletedStudent));
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response("error",
                        Map.of("message", "학생을 찾을 수 없습니다.")));
    }


    // DELETE 2: 전체 학생 삭제
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteAllStudents() {

        int deletedCount = students.size();

        students.clear();

        return ResponseEntity.ok(
                response("success",
                        Map.of(
                                "message", "모든 학생이 삭제되었습니다.",
                                "deletedCount", deletedCount
                        ))
        );
    }

}
