package com.sms.student_management_system.repository;

import com.sms.student_management_system.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Course entity.
 * Provides CRUD operations and custom queries for course management.
 * 
 * @author Student Management System
 * @version 1.0
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Find course by unique course code
     */
    Optional<Course> findByCourseCode(String courseCode);

    /**
     * Find courses by name (case-insensitive, partial match)
     */
    @Query("SELECT c FROM Course c WHERE LOWER(c.courseName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Course> findByCourseName(@Param("name") String name);

    /**
     * Find courses by semester
     */
    @Query("SELECT c FROM Course c WHERE c.semester = :semester AND c.status = 'Active'")
    List<Course> findBySemester(@Param("semester") String semester);

    /**
     * Find courses by department
     */
    @Query("SELECT c FROM Course c WHERE c.department = :department AND c.status = 'Active'")
    List<Course> findByDepartment(@Param("department") String department);

    /**
     * Find all active courses
     */
    @Query("SELECT c FROM Course c WHERE c.status = 'Active'")
    List<Course> findAllActive();

    /**
     * Search courses by code or name
     */
    @Query("SELECT c FROM Course c WHERE (c.courseCode = :keyword OR " +
           "LOWER(c.courseName) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND c.status = 'Active'")
    List<Course> searchCourses(@Param("keyword") String keyword);

    /**
     * Find courses taught by specific instructor
     */
    @Query("SELECT c FROM Course c WHERE c.instructor = :instructor AND c.status = 'Active'")
    List<Course> findByInstructor(@Param("instructor") String instructor);
}
