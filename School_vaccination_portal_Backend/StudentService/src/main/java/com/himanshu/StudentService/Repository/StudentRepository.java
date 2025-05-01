package com.himanshu.StudentService.Repository;

import com.himanshu.StudentService.Entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
   // List<Student> findByNameContainingIgnoreCase(String name);
    List<Student> findByClassName(String className);
    Optional<Student> findByStudentId(String studentId);
    List<Student> findByVaccinated(boolean status);

    Long countByVaccinatedTrue();

    @Query("SELECT s FROM Student s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(s.studentId) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(s.className) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Student> searchByKeyword(@Param("query") String query);

    Page<Student> findAll(Pageable pageable);

    @Query("SELECT s FROM Student s WHERE " +
            "(:className IS NULL OR s.className = :className) AND " +
            "(:vaccineName IS NULL OR s.vaccineName = :vaccineName) AND " +
            "(:vaccinated IS NULL OR s.vaccinated = :vaccinated)")
    Page<Student> findFiltered(@Param("className") String className,
                               @Param("vaccineName") String vaccineName,
                               @Param("vaccinated") Boolean vaccinated,
                               Pageable pageable);
}
