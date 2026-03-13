package com.sms.student_management_system.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * Data Transfer Object for Student.
 * Used for transferring student data between layers.
 * Includes validation constraints for API input validation.
 * 
 * @author Student Management System
 * @version 1.0
 */
public class StudentDTO {
    
    private Long id;
    
    private String studentNumber; // Auto-generated, read-only
    
    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 100, message = "Full name must be between 3 and 100 characters")
    private String fullName;
    
    @NotNull(message = "Date of birth is required")
    @PastOrPresent(message = "Date of birth cannot be in the future")
    private LocalDate dateOfBirth;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", 
             message = "Phone number must be valid")
    private String phone;
    
    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
    private String address;
    
    @NotBlank(message = "Program is required")
    private String program;
    
    @NotNull(message = "Enrollment date is required")
    private LocalDate enrollmentDate;
    
    private Boolean active = true;

    // ==================== Constructors ====================
    
    public StudentDTO() {}

    public StudentDTO(Long id, String studentNumber, String fullName, String email, 
                     String phone, String address, String program) {
        this.id = id;
        this.studentNumber = studentNumber;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.program = program;
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
}
