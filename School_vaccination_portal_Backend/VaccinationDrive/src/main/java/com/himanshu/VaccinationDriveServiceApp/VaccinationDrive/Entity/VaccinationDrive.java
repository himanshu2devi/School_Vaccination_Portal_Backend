package com.himanshu.VaccinationDriveServiceApp.VaccinationDrive.Entity;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class VaccinationDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vaccineName;
    private String className;
    private LocalDate date;
    private int dosesRequired;

    @Enumerated(EnumType.STRING)
    private DriveStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getDosesRequired() {
        return dosesRequired;
    }

    public void setDosesRequired(int dosesRequired) {
        this.dosesRequired = dosesRequired;
    }

    public DriveStatus getStatus() {
        return status;
    }

    public void setStatus(DriveStatus status) {
        this.status = status;
    }

    public VaccinationDrive(){}

    public VaccinationDrive(Long id, String vaccineName, String className, LocalDate date, int dosesRequired,DriveStatus status) {
        this.id = id;
        this.vaccineName = vaccineName;
        this.className = className;
        this.date = date;
        this.dosesRequired = dosesRequired;
        this.status=status;
    }

    @Override
    public String toString() {
        return "VaccinationDrive{" +
                "id=" + id +
                ", vaccineName='" + vaccineName + '\'' +
                ", className='" + className + '\'' +
                ", date=" + date +
                ", dosesRequired=" + dosesRequired +
                '}';
    }
}
