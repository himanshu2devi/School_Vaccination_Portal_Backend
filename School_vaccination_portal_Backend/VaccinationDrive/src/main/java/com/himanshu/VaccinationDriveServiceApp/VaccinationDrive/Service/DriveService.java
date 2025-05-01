package com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.Service;


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
            // Attempt to find the existing drive by id
            VaccinationDrive existing = driveRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Drive not found"));

            // Check if the drive is in the past
            if (existing.getDate().isBefore(LocalDate.now())) {
                throw new RuntimeException("Cannot edit a past drive.");
            }

            // Update the existing drive with the new details
            existing.setVaccineName(updatedDrive.getVaccineName());
            existing.setClassName(updatedDrive.getClassName());
            existing.setDate(updatedDrive.getDate());
            existing.setDosesRequired(updatedDrive.getDosesRequired());

            // Save and return the updated drive
            return driveRepository.save(existing);

        } catch (RuntimeException e) {
            // Log the exception and rethrow it
            System.err.println("Error occurred while updating the drive: " + e.getMessage());
            e.printStackTrace();  // This will print the full stack trace to the console
            throw new RuntimeException("Failed to update the drive: " + e.getMessage(), e);
        } catch (Exception e) {
            // Catch any unexpected exceptions
            System.err.println("Unexpected error occurred: " + e.getMessage());
            e.printStackTrace();  // This will print the full stack trace to the console
            throw new RuntimeException("An unexpected error occurred while updating the drive.", e);
        }
    }

    public List<VaccinationDrive> getAllDrives() {
        return driveRepository.findAll();
    }
}




