package com.sms.student_management_system.service;

import com.sms.student_management_system.dto.StudentDTO;
import com.sms.student_management_system.entity.Student;
import com.sms.student_management_system.exception.ResourceNotFoundException;
import com.sms.student_management_system.repository.StudentRepository;
import com.sms.student_management_system.util.StudentIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Student Service - Business logic layer for student operations.
 * 
 * Design Patterns:
 * 1. Singleton Decorator (@Service) - Spring automatically manages single instance
 * 2. Builder Pattern - StudentBuilder for constructing complex Student objects
 * 3. Data Transfer Object (DTO) - Separates API contract from entity model
 * 4. Repository Pattern - Abstracts data access layer
 * 
 * Service Responsibilities:
 * - Student registration and validation
 * - Automatic student ID generation
 * - CRUD operations for students
 * - Soft delete functionality (mark as inactive)
 * - Search and filter capabilities
 * - Course enrollment management
 * 
 * @author Student Management System
 * @version 1.0
 */
@Service
@Transactional
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentIdGenerator studentIdGenerator;

    /**
     * Register a new student.
     * Automatically generates unique student number and saves to database.
     * 
     * @param studentDTO The student data transfer object containing student information
     * @return Saved student entity with generated student number
     * @throws IllegalArgumentException if student data is invalid
     */
    public Student registerStudent(StudentDTO studentDTO) {
        logger.info("Registering new student: {}", studentDTO.getFullName());
        
        // Validate student data
        validateStudentData(studentDTO);
        
        // Generate unique student number
        String studentNumber = studentIdGenerator.generateStudentNumber();
        logger.debug("Generated student number: {}", studentNumber);
        
        // Build student using StudentBuilder (Builder Pattern)
        Student student = StudentBuilder.builder()
            .studentNumber(studentNumber)
            .fullName(studentDTO.getFullName())
            .email(studentDTO.getEmail())
            .phone(studentDTO.getPhone())
            .address(studentDTO.getAddress())
            .program(studentDTO.getProgram())
            .dateOfBirth(studentDTO.getDateOfBirth())
            .enrollmentDate(studentDTO.getEnrollmentDate())
            .active(true)
            .build();

        Student savedStudent = studentRepository.save(student);
        logger.info("Student registered successfully with ID: {} and Number: {}", 
                   savedStudent.getId(), savedStudent.getStudentNumber());
        
        return savedStudent;
    }

    /**
     * Save or update a student.
     * 
     * @param studentDTO The student data to save
     * @return Saved student entity
     */
    public Student saveStudent(StudentDTO studentDTO) {
        logger.info("Saving student: {}", studentDTO.getFullName());
        
        Student student;
        if (studentDTO.getId() != null) {
            // Update existing student
            student = studentRepository.findById(studentDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentDTO.getId()));
            
            // Update fields
            student.setFullName(studentDTO.getFullName());
            student.setEmail(studentDTO.getEmail());
            student.setPhone(studentDTO.getPhone());
            student.setAddress(studentDTO.getAddress());
            student.setProgram(studentDTO.getProgram());
            student.setDateOfBirth(studentDTO.getDateOfBirth());
            
            logger.debug("Updated student with ID: {}", student.getId());
        } else {
            // New student - use registerStudent instead
            return registerStudent(studentDTO);
        }

        return studentRepository.save(student);
    }

    /**
     * Get all active students.
     * 
     * @return List of all active students
     */
    public List<Student> getAllStudents() {
        logger.debug("Fetching all active students");
        return studentRepository.findAllActive();
    }

    /**
     * Get a student by ID.
     * 
     * @param id The student ID
     * @return Student entity
     * @throws ResourceNotFoundException if student not found
     */
    public Student getStudentById(Long id) {
        logger.debug("Fetching student with ID: {}", id);
        return studentRepository.findById(id)
            .filter(Student::getActive) // Ensure student is not deleted
            .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
    }

    /**
     * Get a student by student number.
     * 
     * @param studentNumber The unique student number
     * @return Student entity
     * @throws ResourceNotFoundException if student not found
     */
    public Student getStudentByNumber(String studentNumber) {
        logger.debug("Fetching student with number: {}", studentNumber);
        return studentRepository.findByStudentNumber(studentNumber)
            .filter(Student::getActive)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found with number: " + studentNumber));
    }

    /**
     * Search students by keyword (name or number).
     * 
     * @param keyword The search keyword
     * @return List of matching students
     */
    public List<Student> searchStudents(String keyword) {
        logger.info("Searching students with keyword: {}", keyword);
        return studentRepository.searchStudents(keyword);
    }

    /**
     * Get all students in a specific program.
     * 
     * @param program The program name
     * @return List of students in the program
     */
    public List<Student> getStudentsByProgram(String program) {
        logger.debug("Fetching students for program: {}", program);
        return studentRepository.findByProgram(program);
    }

    /**
     * Soft delete a student (mark as inactive).
     * Prevents deletion if student has active course enrollments.
     * 
     * @param id The student ID to delete
     * @throws ResourceNotFoundException if student not found
     * @throws IllegalStateException if student has active enrollments
     */
    public void deleteStudent(Long id) {
        logger.info("Deleting student with ID: {}", id);
        
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

        // Check if student has active course enrollments
        if (!student.getCourses().isEmpty()) {
            logger.warn("Cannot delete student {} - has {} active enrollments", id, student.getCourses().size());
            throw new IllegalStateException("Cannot delete student with active course enrollments");
        }

        // Perform soft delete
        student.setActive(false);
        studentRepository.save(student);
        logger.info("Student with ID: {} marked as deleted", id);
    }

    /**
     * Restore a deleted (inactive) student.
     * 
     * @param id The student ID to restore
     * @throws ResourceNotFoundException if student not found
     */
    public void restoreStudent(Long id) {
        logger.info("Restoring student with ID: {}", id);
        
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

        if (student.getActive()) {
            logger.warn("Student {} is already active", id);
            return;
        }

        student.setActive(true);
        studentRepository.save(student);
        logger.info("Student with ID: {} restored", id);
    }

    /**
     * Convert entity to DTO.
     * 
     * @param student The student entity
     * @return StudentDTO
     */
    public StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setStudentNumber(student.getStudentNumber());
        dto.setFullName(student.getFullName());
        dto.setEmail(student.getEmail());
        dto.setPhone(student.getPhone());
        dto.setAddress(student.getAddress());
        dto.setProgram(student.getProgram());
        dto.setDateOfBirth(student.getDateOfBirth());
        dto.setEnrollmentDate(student.getEnrollmentDate());
        dto.setActive(student.getActive());
        return dto;
    }

    /**
     * Convert DTO to entity.
     * 
     * @param studentDTO The student DTO
     * @return Student entity
     */
    public Student convertToEntity(StudentDTO studentDTO) {
        return StudentBuilder.builder()
            .id(studentDTO.getId())
            .studentNumber(studentDTO.getStudentNumber())
            .fullName(studentDTO.getFullName())
            .email(studentDTO.getEmail())
            .phone(studentDTO.getPhone())
            .address(studentDTO.getAddress())
            .program(studentDTO.getProgram())
            .dateOfBirth(studentDTO.getDateOfBirth())
            .enrollmentDate(studentDTO.getEnrollmentDate())
            .active(studentDTO.getActive())
            .build();
    }

    /**
     * Validate student data.
     * 
     * @param studentDTO The student DTO to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateStudentData(StudentDTO studentDTO) {
        if (studentDTO.getFullName() == null || studentDTO.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        if (studentDTO.getEmail() == null || !studentDTO.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (studentDTO.getPhone() == null || studentDTO.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        if (studentDTO.getProgram() == null || studentDTO.getProgram().trim().isEmpty()) {
            throw new IllegalArgumentException("Program must be selected");
        }
        
        // Check for duplicate email
        if (studentDTO.getId() == null) {
            Optional<Student> existingStudent = studentRepository.findByEmail(studentDTO.getEmail());
            if (existingStudent.isPresent()) {
                throw new IllegalArgumentException("Email already registered: " + studentDTO.getEmail());
            }
        }
    }

    /**
     * Inner class implementing Builder Pattern for Student creation.
     * 
     * Builder Pattern Benefits:
     * - Simplifies complex object construction
     * - Improves code readability
     * - Allows partial initialization
     * - Makes it easy to handle optional fields
     */
    public static class StudentBuilder {
        private Long id;
        private String studentNumber;
        private String fullName;
        private LocalDate dateOfBirth;
        private String email;
        private String phone;
        private String address;
        private String program;
        private LocalDate enrollmentDate;
        private Boolean active = true;

        public static StudentBuilder builder() {
            return new StudentBuilder();
        }

        public StudentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public StudentBuilder studentNumber(String studentNumber) {
            this.studentNumber = studentNumber;
            return this;
        }

        public StudentBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public StudentBuilder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public StudentBuilder email(String email) {
            this.email = email;
            return this;
        }

        public StudentBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public StudentBuilder address(String address) {
            this.address = address;
            return this;
        }

        public StudentBuilder program(String program) {
            this.program = program;
            return this;
        }

        public StudentBuilder enrollmentDate(LocalDate enrollmentDate) {
            this.enrollmentDate = enrollmentDate;
            return this;
        }

        public StudentBuilder active(Boolean active) {
            this.active = active;
            return this;
        }

        public Student build() {
            Student student = new Student();
            student.setId(id);
            student.setStudentNumber(studentNumber);
            student.setFullName(fullName);
            student.setDateOfBirth(dateOfBirth);
            student.setEmail(email);
            student.setPhone(phone);
            student.setAddress(address);
            student.setProgram(program);
            student.setEnrollmentDate(enrollmentDate);
            student.setActive(active);
            return student;
        }
    }
}
