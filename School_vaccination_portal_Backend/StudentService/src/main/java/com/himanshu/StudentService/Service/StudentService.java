package com.himanshu.StudentService.Service;

import com.himanshu.StudentService.Entity.Student;
import com.himanshu.StudentService.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    @Autowired
    private final StudentRepository studentRepo;



    public Student saveStudent(Student student) {
        return studentRepo.save(student);
    }

    public Page<Student> getAllStudents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentRepo.findAll(pageable);
    }
    public Student updateStudent(Long id, Student updatedStudent) {
        Student student = studentRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        student.setName(updatedStudent.getName());
        student.setClassName(updatedStudent.getClassName());
        return studentRepo.save(student);
    }

    public Page<Student> getFilteredReport(String className, String vaccineName, Boolean vaccinated, int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        if (className == null && vaccineName == null && vaccinated == null) {
            return studentRepo.findAll(pageable);
        }
        return studentRepo.findFiltered(className, vaccineName, vaccinated,pageable);
    }

    public void markVaccinated(Long studentId, String vaccineName, LocalDate date) {
        Student student = studentRepo.findById(studentId).orElseThrow();
        if (student.isVaccinated()) throw new RuntimeException("Already vaccinated");
        student.setVaccinated(true);
        student.setVaccineName(vaccineName);
        student.setVaccinationDate(date);
        studentRepo.save(student);
    }

    public void saveAll(List<Student> students) {
        studentRepo.saveAll(students);
    }

    public long getTotalStudents() {
        return studentRepo.count();
    }

    public long getVaccinatedStudents() {
        return studentRepo.countByVaccinatedTrue();
    }

    public List<Student> search(String query) {
        return studentRepo.searchByKeyword(query);
    }

    public void deleteStudent(Long id) {
        if (!studentRepo.existsById(id)) {
            throw new RuntimeException("Student not found");
        }
        studentRepo.deleteById(id);
    }

}
