package com.himanshu.StudentService.Controller;

import com.himanshu.StudentService.CSVHelper;
import com.himanshu.StudentService.Entity.Student;
import com.himanshu.StudentService.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.himanshu.StudentService.security.JwtUtil;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {


    private final StudentService studentService;

    private final JwtUtil jwtUtil;






    @PostMapping("/upload")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".csv")) {
            return ResponseEntity.badRequest().body("Invalid file format");
        }

        try {
            List<Student> students = CSVHelper.csvToStudents(file);
            studentService.saveAll(students);
            return ResponseEntity.ok("Uploaded " + students.size() + " students successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.saveStudent(student));
    }

    @GetMapping
    public ResponseEntity<Page<Student>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (size < 1) {
            size = 10;
        }
        return ResponseEntity.ok(studentService.getAllStudents(page, size));
    }
    @GetMapping("/vaccinated/count")
    public ResponseEntity<Long> getVaccinatedStudents() {
        return ResponseEntity.ok(studentService.getVaccinatedStudents());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Student deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalStudents() {
        return ResponseEntity.ok(studentService.getTotalStudents());
    }


    @PutMapping("{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @PutMapping("/vaccinate/{id}")
    public ResponseEntity<String> markVaccinated(
            @PathVariable Long id,
            @RequestParam String vaccineName,
            @RequestParam String date // format: yyyy-MM-dd
    ) {
        studentService.markVaccinated(id, vaccineName, LocalDate.parse(date));
        return ResponseEntity.ok("Student marked as vaccinated");
    }

    @GetMapping("/report")
    public ResponseEntity<Page<Student>> getReport(
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String vaccineName,
            @RequestParam(required = false) Boolean vaccinated,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(studentService.getFilteredReport(className, vaccineName, vaccinated, page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Student>> search(@RequestParam String query) {
        return ResponseEntity.ok(studentService.search(query));
    }
}
