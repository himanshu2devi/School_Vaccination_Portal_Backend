package com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.Controller;


import com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.Entity.VaccinationDrive;
import com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.Service.DriveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drives")
@CrossOrigin(origins = "http://localhost:3000")
public class DriveController {

    private final DriveService driveService;

    public DriveController(DriveService driveService) {
        this.driveService = driveService;
    }

    @PostMapping
    public ResponseEntity<?> createDrive(@RequestBody VaccinationDrive drive) {
        try {
            VaccinationDrive saved = driveService.createDrive(drive);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<VaccinationDrive> updateDrive(@PathVariable Long id, @RequestBody VaccinationDrive drive) {
        try {
            // Try updating the drive
            VaccinationDrive updatedDrive = driveService.updateDrive(id, drive);

            // If successful, return the updated drive with an OK status and a meaningful message
            System.out.println("Drive with ID " + id + " updated successfully.");
            return ResponseEntity.ok(updatedDrive);

        } catch (RuntimeException e) {
            // Handle any exceptions, like if the drive is not found or if the date is in the past
            System.out.println("Error updating drive with ID " + id + ": " + e.getMessage());
            return (ResponseEntity<VaccinationDrive>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<VaccinationDrive>> getUpcomingDrives() {
        List<VaccinationDrive> upcomingDrives = driveService.getUpcomingDrives();
        return ResponseEntity.ok(upcomingDrives);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrive(@PathVariable Long id) {
        driveService.deleteDrive(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<VaccinationDrive> getAllDrives() {
        return driveService.getAllDrives();
    }
}
