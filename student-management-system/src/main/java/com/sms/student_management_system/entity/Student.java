package com.sms.student_management_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Student Entity representing a registered student in the system.
 * Implements soft delete functionality with 'active' flag.
 * Uses student number with format: KDU-SE-YYYY-XXXX
 * 
 * @author Student Management System
 * @version 1.0
 */
@Entity
@Table(name = "students", uniqueConstraints = {
    @UniqueConstraint(columnNames = "student_number"),
    @UniqueConstraint(columnNames = "email")
})
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Student number cannot be blank")
    @Column(unique = true, nullable = false, length = 20)
    private String studentNumber; // Format: KDU-SE-YYYY-XXXX
    
    @NotBlank(message = "Full name cannot be blank")
    @Column(nullable = false, length = 100)
    private String fullName;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    
    @Column(length = 20)
    private String phone;
    
    @Column(length = 255)
    private String address;
    
    @Column(length = 50)
    private String program;
    
    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;
    
    @Column(nullable = false)
    private Boolean active = true; // Soft delete flag
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "student_courses",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    /**
     * PrePersist lifecycle callback - sets creation timestamp
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * PreUpdate lifecycle callback - updates modification timestamp
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ==================== Constructors ====================
    
    /**
     * Default constructor (required by JPA)
     */
    public Student() {}

    /**
     * Constructor with basic student information
     */
    public Student(String fullName, String email, String phone, String address, 
                   String program, LocalDate dateOfBirth, LocalDate enrollmentDate) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.program = program;
        this.dateOfBirth = dateOfBirth;
        this.enrollmentDate = enrollmentDate;
        this.active = true;
    }

    // ==================== Getters and Setters ====================
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }
    
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public Set<Course> getCourses() { return courses; }
    public void setCourses(Set<Course> courses) { this.courses = courses; }
    
    // ==================== Helper Methods ====================
    
    /**
     * Add a course to student's enrolled courses
     */
    public void addCourse(Course course) {
        courses.add(course);
    }
    
    /**
     * Remove a course from student's enrolled courses
     */
    public void removeCourse(Course course) {
        courses.remove(course);
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentNumber='" + studentNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", program='" + program + '\'' +
                ", active=" + active +
                '}';
    }
}