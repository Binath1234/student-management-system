package com.sms.student_management_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "courses") // Database එකේ හැදෙන Table එකේ නම
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_code", nullable = false)
    private String courseCode; // උදා: CS101

    @Column(name = "course_name", nullable = false)
    private String courseName; // උදා: Introduction to Computer Science

    private int credits; // උදා: 3

    private String instructor; // උදා: Dr. Sarah Mitchell

    private String department; // උදා: Computer Science

    private String enrollment; // උදා: 45/50

    private String schedule; // උදා: Mon/Wed 9:00-10:30 AM

    private String status; // උදා: Active හෝ Full

    // Default Constructor (JPA එකට අනිවාර්යයෙන්ම අවශ්‍යයි)
    public Course() {
    }

    // Getters and Setters (Data එහා මෙහා කරන්න අවශ්‍යයි)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getEnrollment() { return enrollment; }
    public void setEnrollment(String enrollment) { this.enrollment = enrollment; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}