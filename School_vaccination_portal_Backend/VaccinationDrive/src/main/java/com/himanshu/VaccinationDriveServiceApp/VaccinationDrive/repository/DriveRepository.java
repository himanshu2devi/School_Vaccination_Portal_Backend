package com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.repository;


import com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.Entity.VaccinationDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DriveRepository extends JpaRepository<VaccinationDrive, Long> {
    List<VaccinationDrive> findByDateBetween(LocalDate start, LocalDate end);
    Optional<VaccinationDrive> findByClassNameAndDate(String className, LocalDate date);
}
