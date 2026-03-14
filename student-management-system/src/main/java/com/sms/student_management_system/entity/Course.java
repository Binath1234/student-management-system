package com.sms.student_management_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Course Entity representing a course offered in a program.
 * Supports enrollment of multiple students across different semesters.
 * 
 * @author Student Management System
 * @version 1.0
 */
@Entity
@Table(name = "courses", uniqueConstraints = {
    @UniqueConstraint(columnNames = "course_code")
})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_code", nullable = false, unique = true, length = 20)
    private String courseCode; // Auto-generated: PREFIX + sequence

    @NotBlank(message = "Course name cannot be blank")
    @Column(name = "course_name", nullable = false, length = 100)
    private String courseName;

    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 6, message = "Credits cannot exceed 6")
    @Column(nullable = false)
    private Integer credits; // Default: 3

    @Column(length = 100)
    private String instructor;

    @Column(length = 50)
    private String department;

    @Column(length = 20)
    private String semester; // Format: FALL-2024, SPRING-2024, etc.

    @Column(length = 50)
    private String schedule;

    @Column(length = 20)
    private String status; // Active, Inactive, Full, Cancelled

    @Column(name = "program_name", length = 100)
    private String programName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();

    /**
     * PrePersist lifecycle callback - sets creation timestamp
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = "Active";
        }
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
    public Course() {
        this.credits = 3;
        this.status = "Active";
    }

    /**
     * Constructor with essential course information
     */
    public Course(String courseCode, String courseName, Integer credits, 
                 String semester, String department, String instructor) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.semester = semester;
        this.department = department;
        this.instructor = instructor;
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

    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Set<Student> getStudents() { return students; }
    public void setStudents(Set<Student> students) { this.students = students; }

    // ==================== Helper Methods ====================
    
    /**
     * Add a student to the course
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Remove a student from the course
     */
    public void removeStudent(Student student) {
        students.remove(student);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credits=" + credits +
                ", semester='" + semester + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}