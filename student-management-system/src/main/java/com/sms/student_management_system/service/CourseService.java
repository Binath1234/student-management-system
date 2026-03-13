package com.sms.student_management_system.service;

import com.sms.student_management_system.dto.CourseDTO;
import com.sms.student_management_system.entity.Course;
import com.sms.student_management_system.entity.Student;
import com.sms.student_management_system.exception.ResourceNotFoundException;
import com.sms.student_management_system.repository.CourseRepository;
import com.sms.student_management_system.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Course Service - Business logic layer for course operations.
 * 
 * Design Patterns:
 * 1. Singleton Decorator (@Service) - Spring manages single instance
 * 2. Repository Pattern - Data access abstraction
 * 
 * Service Responsibilities:
 * - Course creation and management
 * - CRUD operations for courses
 * - Student course enrollment
 * - Course search and filtering
 * - Hard delete functionality
 * 
 * @author Student Management System
 * @version 1.0
 */
@Service
@Transactional
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    /**
     * Create a new course.
     * 
     * @param courseDTO The course data transfer object
     * @return Saved course entity
     * @throws IllegalArgumentException if course data is invalid
     */
    public Course createCourse(CourseDTO courseDTO) {
        logger.info("Creating new course: {}", courseDTO.getCourseCode());
        
        // Validate course data
        validateCourseData(courseDTO);
        
        // Check if course code already exists
        if (courseRepository.findByCourseCode(courseDTO.getCourseCode()).isPresent()) {
            throw new IllegalArgumentException("Course code already exists: " + courseDTO.getCourseCode());
        }

        Course course = new Course(
            courseDTO.getCourseCode(),
            courseDTO.getCourseName(),
            courseDTO.getCredits(),
            courseDTO.getSemester(),
            courseDTO.getDepartment(),
            courseDTO.getInstructor()
        );
        
        course.setSchedule(courseDTO.getSchedule());
        course.setStatus(courseDTO.getStatus() != null ? courseDTO.getStatus() : "Active");

        Course savedCourse = courseRepository.save(course);
        logger.info("Course created successfully with ID: {}", savedCourse.getId());
        
        return savedCourse;
    }

    /**
     * Save or update a course.
     * 
     * @param courseDTO The course data to save
     * @return Saved course entity
     */
    public Course saveCourse(CourseDTO courseDTO) {
        logger.info("Saving course: {}", courseDTO.getCourseCode());
        
        Course course;
        if (courseDTO.getId() != null) {
            // Update existing course
            course = courseRepository.findById(courseDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + courseDTO.getId()));
            
            // Update fields
            course.setCourseName(courseDTO.getCourseName());
            course.setCredits(courseDTO.getCredits());
            course.setInstructor(courseDTO.getInstructor());
            course.setDepartment(courseDTO.getDepartment());
            course.setSemester(courseDTO.getSemester());
            course.setSchedule(courseDTO.getSchedule());
            course.setStatus(courseDTO.getStatus());
            
            logger.debug("Updated course with ID: {}", course.getId());
        } else {
            // New course
            return createCourse(courseDTO);
        }

        return courseRepository.save(course);
    }

    /**
     * Get all active courses.
     * 
     * @return List of all active courses
     */
    public List<Course> getAllCourses() {
        logger.debug("Fetching all active courses");
        return courseRepository.findAllActive();
    }

    /**
     * Get a course by ID.
     * 
     * @param id The course ID
     * @return Course entity
     * @throws ResourceNotFoundException if course not found
     */
    public Course getCourseById(Long id) {
        logger.debug("Fetching course with ID: {}", id);
        return courseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));
    }

    /**
     * Get a course by course code.
     * 
     * @param courseCode The unique course code
     * @return Course entity
     * @throws ResourceNotFoundException if course not found
     */
    public Course getCourseByCourseCode(String courseCode) {
        logger.debug("Fetching course with code: {}", courseCode);
        return courseRepository.findByCourseCode(courseCode)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found with code: " + courseCode));
    }

    /**
     * Search courses by keyword (code or name).
     * 
     * @param keyword The search keyword
     * @return List of matching courses
     */
    public List<Course> searchCourses(String keyword) {
        logger.info("Searching courses with keyword: {}", keyword);
        return courseRepository.searchCourses(keyword);
    }

    /**
     * Get all courses in a specific semester.
     * 
     * @param semester The semester (e.g., FALL-2024)
     * @return List of courses in the semester
     */
    public List<Course> getCoursesBySemester(String semester) {
        logger.debug("Fetching courses for semester: {}", semester);
        return courseRepository.findBySemester(semester);
    }

    /**
     * Get all courses in a specific department.
     * 
     * @param department The department name
     * @return List of courses in the department
     */
    public List<Course> getCoursesByDepartment(String department) {
        logger.debug("Fetching courses for department: {}", department);
        return courseRepository.findByDepartment(department);
    }

    /**
     * Get all courses taught by a specific instructor.
     * 
     * @param instructor The instructor name
     * @return List of courses taught by the instructor
     */
    public List<Course> getCoursesByInstructor(String instructor) {
        logger.debug("Fetching courses for instructor: {}", instructor);
        return courseRepository.findByInstructor(instructor);
    }

    /**
     * Enroll a student in a course.
     * 
     * @param studentId The student ID
     * @param courseId The course ID
     * @throws ResourceNotFoundException if student or course not found
     */
    public void enrollStudentInCourse(Long studentId, Long courseId) {
        logger.info("Enrolling student {} in course {}", studentId, courseId);
        
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));
        
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + courseId));

        if (student.getCourses().contains(course)) {
            logger.warn("Student {} already enrolled in course {}", studentId, courseId);
            throw new IllegalStateException("Student already enrolled in this course");
        }

        student.addCourse(course);
        studentRepository.save(student);
        logger.info("Student {} successfully enrolled in course {}", studentId, courseId);
    }

    /**
     * Unenroll a student from a course.
     * 
     * @param studentId The student ID
     * @param courseId The course ID
     */
    public void unenrollStudentFromCourse(Long studentId, Long courseId) {
        logger.info("Unenrolling student {} from course {}", studentId, courseId);
        
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));
        
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + courseId));

        if (!student.getCourses().contains(course)) {
            logger.warn("Student {} not enrolled in course {}", studentId, courseId);
            throw new IllegalStateException("Student not enrolled in this course");
        }

        student.removeCourse(course);
        studentRepository.save(student);
        logger.info("Student {} successfully unenrolled from course {}", studentId, courseId);
    }

    /**
     * Hard delete a course.
     * 
     * @param id The course ID to delete
     * @throws ResourceNotFoundException if course not found
     */
    public void deleteCourse(Long id) {
        logger.info("Deleting course with ID: {}", id);
        
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));

        courseRepository.delete(course);
        logger.info("Course with ID: {} deleted successfully", id);
    }

    /**
     * Convert entity to DTO.
     * 
     * @param course The course entity
     * @return CourseDTO
     */
    public CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setCourseCode(course.getCourseCode());
        dto.setCourseName(course.getCourseName());
        dto.setCredits(course.getCredits());
        dto.setInstructor(course.getInstructor());
        dto.setDepartment(course.getDepartment());
        dto.setSemester(course.getSemester());
        dto.setSchedule(course.getSchedule());
        dto.setStatus(course.getStatus());
        return dto;
    }

    /**
     * Convert DTO to entity.
     * 
     * @param courseDTO The course DTO
     * @return Course entity
     */
    public Course convertToEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setId(courseDTO.getId());
        course.setCourseCode(courseDTO.getCourseCode());
        course.setCourseName(courseDTO.getCourseName());
        course.setCredits(courseDTO.getCredits());
        course.setInstructor(courseDTO.getInstructor());
        course.setDepartment(courseDTO.getDepartment());
        course.setSemester(courseDTO.getSemester());
        course.setSchedule(courseDTO.getSchedule());
        course.setStatus(courseDTO.getStatus());
        return course;
    }

    /**
     * Validate course data.
     * 
     * @param courseDTO The course DTO to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateCourseData(CourseDTO courseDTO) {
        if (courseDTO.getCourseCode() == null || courseDTO.getCourseCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be empty");
        }
        if (courseDTO.getCourseName() == null || courseDTO.getCourseName().trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }
        if (courseDTO.getCredits() == null || courseDTO.getCredits() < 1) {
            throw new IllegalArgumentException("Credits must be at least 1");
        }
        if (courseDTO.getDepartment() == null || courseDTO.getDepartment().trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty");
        }
    }
}
