package com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.repository;


import com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.DTO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
