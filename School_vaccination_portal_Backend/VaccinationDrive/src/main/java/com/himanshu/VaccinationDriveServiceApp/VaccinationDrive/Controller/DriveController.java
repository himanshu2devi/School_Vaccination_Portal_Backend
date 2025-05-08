package com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.Controller;

import com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.DTO.User;
import com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.Entity.DriveStatus;
import com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.Entity.VaccinationDrive;
import com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.Service.DriveService;
import com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drives")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = {"Content-Type", "Authorization", "username"})
public class DriveController {

    private final DriveService driveService;

    @Autowired
    private UserRepository userRepository;

    public DriveController(DriveService driveService) {
        this.driveService = driveService;
    }

    @PostMapping
    public ResponseEntity<?> createDrive(@RequestBody VaccinationDrive drive) {
        try {
            drive.setStatus(DriveStatus.PENDING); // Set default status
            VaccinationDrive saved = driveService.createDrive(drive);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDrive(
            @PathVariable Long id,
            @RequestBody VaccinationDrive drive,
            @RequestHeader("username") String username) {

        try {
            // Validate user
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Only admins can approve drives
            if (drive.getStatus() == DriveStatus.APPROVED &&
                    !"ADMIN".equalsIgnoreCase(user.getRole())) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Only admins can approve drives.");
            }

            VaccinationDrive updatedDrive = driveService.updateDrive(id, drive);
            return ResponseEntity.ok(updatedDrive);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<VaccinationDrive>> getUpcomingDrives() {
        List<VaccinationDrive> upcomingDrives = driveService.getUpcomingDrives();
        return ResponseEntity.ok(upcomingDrives);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDrive(@PathVariable Long id) {
        try {
            driveService.deleteDrive(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<VaccinationDrive>> getAllDrives() {
        return ResponseEntity.ok(driveService.getAllDrives());
    }
}