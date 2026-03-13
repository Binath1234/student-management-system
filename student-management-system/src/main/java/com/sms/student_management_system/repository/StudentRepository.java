package com.sms.student_management_system.repository;

import com.sms.student_management_system.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Student entity.
 * Provides CRUD operations and custom queries for student management.
 * 
 * @author Student Management System
 * @version 1.0
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Find student by unique student number
     */
    Optional<Student> findByStudentNumber(String studentNumber);

    /**
     * Find students by full name (case-insensitive, partial match)
     */
    @Query("SELECT s FROM Student s WHERE LOWER(s.fullName) LIKE LOWER(CONCAT('%', :name, '%')) AND s.active = true")
    List<Student> findByFullNameContaining(@Param("name") String name);

    /**
     * Find students by email
     */
    Optional<Student> findByEmail(String email);

    /**
     * Find students by program
     */
    @Query("SELECT s FROM Student s WHERE s.program = :program AND s.active = true")
    List<Student> findByProgram(@Param("program") String program);

    /**
     * Find all active students
     */
    @Query("SELECT s FROM Student s WHERE s.active = true")
    List<Student> findAllActive();

    /**
     * Find all deleted (inactive) students
     */
    @Query("SELECT s FROM Student s WHERE s.active = false")
    List<Student> findAllDeleted();

    /**
     * Search students by name or student number
     */
    @Query("SELECT s FROM Student s WHERE (LOWER(s.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR s.studentNumber = :keyword) AND s.active = true")
    List<Student> searchStudents(@Param("keyword") String keyword);

    /**
     * Find students with active course enrollments
     */
    @Query("SELECT DISTINCT s FROM Student s JOIN s.courses c WHERE s.id = :studentId AND s.active = true")
    Optional<Student> findByIdWithCourses(@Param("studentId") Long studentId);
}
