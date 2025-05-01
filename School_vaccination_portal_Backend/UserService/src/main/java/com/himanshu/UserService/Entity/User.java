package com.himanshu.UserService.Entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

    @Entity
    @Table(name = "users")
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true, length = 100)
        private String username;

        @Column(nullable = false, length = 255)
        private String password;

        @Column(name = "full_name", nullable = false, length = 150)
        private String fullName;

        @Column(nullable = false, length = 50)
        private String role;

        @Column(nullable = false, unique = true, length = 150)
        private String email;

        @Column(name = "created_at", updatable = false)
        private LocalDateTime createdAt = LocalDateTime.now();

        @Column(name = "last_login_at")
        private LocalDateTime lastLoginAt;

        // Constructors
        public User() {}

        public User(String username, String password, String fullName, String role, String email) {
            this.username = username;
            this.password = password;
            this.fullName = fullName;
            this.role = role;
            this.email = email;
        }

        // Getters and Setters

        public Long getId() { return id; }

        public String getUsername() { return username; }

        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }

        public void setPassword(String password) { this.password = password; }

        public String getFullName() { return fullName; }

        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getRole() { return role; }

        public void setRole(String role) { this.role = role; }

        public String getEmail() { return email; }

        public void setEmail(String email) { this.email = email; }

        public LocalDateTime getCreatedAt() { return createdAt; }

        public LocalDateTime getLastLoginAt() { return lastLoginAt; }

        public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }
    }

