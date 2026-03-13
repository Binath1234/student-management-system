package com.sms.student_management_system.dto;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for Course.
 * Used for transferring course data between layers.
 * 
 * @author Student Management System
 * @version 1.0
 */
public class CourseDTO {
    
    private Long id;
    
    @NotBlank(message = "Course code is required")
    @Size(min = 3, max = 20, message = "Course code must be between 3 and 20 characters")
    @Pattern(regexp = "^[A-Z]{2,3}[0-9]{3}$", message = "Course code format should be like CS101")
    private String courseCode;
    
    @NotBlank(message = "Course name is required")
    @Size(min = 5, max = 100, message = "Course name must be between 5 and 100 characters")
    private String courseName;
    
    @NotNull(message = "Credits are required")
    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 6, message = "Credits cannot exceed 6")
    private Integer credits;
    
    @NotBlank(message = "Instructor name is required")
    @Size(min = 3, max = 100, message = "Instructor name must be between 3 and 100 characters")
    private String instructor;
    
    @NotBlank(message = "Department is required")
    private String department;
    
    @NotBlank(message = "Semester is required")
    private String semester; // e.g., FALL-2024, SPRING-2024
    
    private String schedule;
    
    private String status = "Active";

    // ==================== Constructors ====================
    
    public CourseDTO() {}

    public CourseDTO(String courseCode, String courseName, Integer credits, 
                    String instructor, String department, String semester) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.instructor = instructor;
        this.department = department;
        this.semester = semester;
        this.status = "Active";
    }

    // ==================== Getters and Setters ====================
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
    
    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    
    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
