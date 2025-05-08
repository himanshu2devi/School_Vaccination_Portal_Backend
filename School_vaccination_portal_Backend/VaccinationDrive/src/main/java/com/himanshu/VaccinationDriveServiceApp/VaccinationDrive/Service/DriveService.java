package com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.Service;

import com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.Entity.DriveStatus;
import com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.Entity.VaccinationDrive;
import com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.repository.DriveRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DriveService {

    private final DriveRepository driveRepository;

    public DriveService(DriveRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    public VaccinationDrive createDrive(VaccinationDrive drive) {
        Optional<VaccinationDrive> existing = driveRepository.findByClassNameAndDate(drive.getClassName(), drive.getDate());
        if (existing.isPresent()) {
            throw new RuntimeException("A drive is already scheduled for this class on the same date!");
        }
        return driveRepository.save(drive);
    }

    public List<VaccinationDrive> getUpcomingDrives() {
        LocalDate now = LocalDate.now();
        LocalDate end = now.plusDays(30);
        return driveRepository.findByDateBetween(now, end);
    }

    public void deleteDrive(Long id) {
        driveRepository.deleteById(id);
    }

    public VaccinationDrive updateDrive(Long id, VaccinationDrive updatedDrive) {
        try {
            VaccinationDrive existing = driveRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Drive not found"));

            // Do not allow editing of past drives
            if (existing.getDate().isBefore(LocalDate.now())) {
                throw new RuntimeException("Cannot edit a past drive.");
            }

            // Update editable fields
            existing.setVaccineName(updatedDrive.getVaccineName());
            existing.setClassName(updatedDrive.getClassName());
            existing.setDate(updatedDrive.getDate());
            existing.setDosesRequired(updatedDrive.getDosesRequired());

            // Update status (if changed), assuming role check is already done in controller
            if (updatedDrive.getStatus() != null && updatedDrive.getStatus() != existing.getStatus()) {
                existing.setStatus(updatedDrive.getStatus());
            }

            return driveRepository.save(existing);

        } catch (RuntimeException e) {
            System.err.println("Error occurred while updating the drive: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to update the drive: " + e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("An unexpected error occurred while updating the drive.", e);
        }
    }

    public List<VaccinationDrive> getAllDrives() {
        return driveRepository.findAll();
    }
}