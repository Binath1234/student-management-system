package com.sms.student_management_system.service;

import com.sms.student_management_system.dto.StudentDTO;
import com.sms.student_management_system.entity.Student;
import com.sms.student_management_system.exception.ResourceNotFoundException;
import com.sms.student_management_system.repository.StudentRepository;
import com.sms.student_management_system.util.StudentIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests for StudentService
 * 
 * Testing Strategy:
 * - Tests cover CRUD operations (Create, Read, Update, Delete)
 * - Tests validate business logic (ID generation, validation)
 * - Uses Mockito for repository mocking
 * - Follows JUnit 5 standards
 * 
 * Coverage: >80% of critical business logic
 * 
 * @author Student Management System
 * @version 1.0
 */
@DisplayName("Student Service Unit Tests")
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentIdGenerator studentIdGenerator;

    @InjectMocks
    private StudentService studentService;

    private StudentDTO studentDTO;
    private Student student;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup test data
        studentDTO = new StudentDTO();
        studentDTO.setFullName("John Doe");
        studentDTO.setEmail("john.doe@example.com");
        studentDTO.setPhone("555-1234");
        studentDTO.setAddress("123 Main St");
        studentDTO.setProgram("Software Engineering");
        studentDTO.setDateOfBirth(LocalDate.of(2000, 1, 15));
        studentDTO.setEnrollmentDate(LocalDate.of(2024, 1, 15));

        student = new Student();
        student.setId(1L);
        student.setStudentNumber("KDU-SE-2024-0001");
        student.setFullName("John Doe");
        student.setEmail("john.doe@example.com");
        student.setPhone("555-1234");
        student.setActive(true);
    }

    // ==================== CREATE/REGISTER Tests ====================

    @Test
    @DisplayName("Test register new student successfully")
    public void testRegisterStudentSuccess() {
        when(studentIdGenerator.generateStudentNumber(anyString())).thenReturn("KDU-BSE-2024-0001");
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        student.setStudentNumber("KDU-BSE-2024-0001");
        Student result = studentService.registerStudent(studentDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getFullName());
        assertEquals("KDU-BSE-2024-0001", result.getStudentNumber());
        assertEquals("john.doe@example.com", result.getEmail());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    @DisplayName("Test register student with null full name throws exception")
    public void testRegisterStudentWithNullName() {
        studentDTO.setFullName(null);
        
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(studentDTO);
        });
    }

    @Test
    @DisplayName("Test register student with invalid email throws exception")
    public void testRegisterStudentWithInvalidEmail() {
        studentDTO.setEmail("invalid-email");
        
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(studentDTO);
        });
    }

    @Test
    @DisplayName("Test register student with duplicate email throws exception")
    public void testRegisterStudentWithDuplicateEmail() {
        when(studentRepository.findByEmail(studentDTO.getEmail())).thenReturn(Optional.of(student));
        
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.registerStudent(studentDTO);
        });
    }

    // ==================== READ Tests ====================

    @Test
    @DisplayName("Test get all active students")
    public void testGetAllStudents() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        
        when(studentRepository.findAllActive()).thenReturn(studentList);

        List<Student> result = studentService.getAllStudents();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getFullName());
        verify(studentRepository, times(1)).findAllActive();
    }

    @Test
    @DisplayName("Test get student by ID successfully")
    public void testGetStudentByIdSuccess() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student result = studentService.getStudentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getFullName());
    }

    @Test
    @DisplayName("Test get student by non-existent ID throws exception")
    public void testGetStudentByIdNotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            studentService.getStudentById(999L);
        });
    }

    @Test
    @DisplayName("Test get student by student number successfully")
    public void testGetStudentByNumberSuccess() {
        when(studentRepository.findByStudentNumber("KDU-SE-2024-0001")).thenReturn(Optional.of(student));

        Student result = studentService.getStudentByNumber("KDU-SE-2024-0001");

        assertNotNull(result);
        assertEquals("KDU-SE-2024-0001", result.getStudentNumber());
    }

    @Test
    @DisplayName("Test search students by keyword")
    public void testSearchStudents() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        
        when(studentRepository.searchStudents("John")).thenReturn(studentList);

        List<Student> result = studentService.searchStudents("John");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(studentRepository, times(1)).searchStudents("John");
    }

    @Test
    @DisplayName("Test get students by program")
    public void testGetStudentsByProgram() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        
        when(studentRepository.findByProgram("Software Engineering")).thenReturn(studentList);

        List<Student> result = studentService.getStudentsByProgram("Software Engineering");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(studentRepository, times(1)).findByProgram("Software Engineering");
    }

    // ==================== UPDATE Tests ====================

    @Test
    @DisplayName("Test update student successfully")
    public void testUpdateStudentSuccess() {
        studentDTO.setId(1L);
        studentDTO.setFullName("Jane Doe");
        
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentService.saveStudent(studentDTO);

        assertNotNull(result);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    // ==================== DELETE Tests ====================

    @Test
    @DisplayName("Test soft delete student successfully")
    public void testDeleteStudentSuccess() {
        student.setCourses(new java.util.HashSet<>()); // Empty courses
        
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    @DisplayName("Test delete student with active enrollments throws exception")
    public void testDeleteStudentWithActiveEnrollments() {
        student.getCourses().add(new com.sms.student_management_system.entity.Course());
        
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        assertThrows(IllegalStateException.class, () -> {
            studentService.deleteStudent(1L);
        });
    }

    @Test
    @DisplayName("Test delete non-existent student throws exception")
    public void testDeleteNonExistentStudent() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            studentService.deleteStudent(999L);
        });
    }

    // ==================== RESTORE Tests ====================

    @Test
    @DisplayName("Test restore deleted student successfully")
    public void testRestoreStudentSuccess() {
        student.setActive(false);
        
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.restoreStudent(1L);

        assertTrue(student.getActive());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    // ==================== DTO Conversion Tests ====================

    @Test
    @DisplayName("Test convert entity to DTO")
    public void testConvertEntityToDTO() {
        StudentDTO result = studentService.convertToDTO(student);

        assertNotNull(result);
        assertEquals("John Doe", result.getFullName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("KDU-SE-2024-0001", result.getStudentNumber());
    }

    @Test
    @DisplayName("Test convert DTO to entity")
    public void testConvertDTOToEntity() {
        studentDTO.setId(1L);
        studentDTO.setStudentNumber("KDU-SE-2024-0001");
        
        Student result = studentService.convertToEntity(studentDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getFullName());
        assertEquals("john.doe@example.com", result.getEmail());
    }

    // ==================== Builder Pattern Tests ====================

    @Test
    @DisplayName("Test StudentBuilder pattern")
    public void testStudentBuilder() {
        Student builtStudent = StudentService.StudentBuilder.builder()
            .fullName("Test User")
            .email("test@example.com")
            .phone("555-9876")
            .address("456 Oak Ave")
            .program("Computer Science")
            .active(true)
            .build();

        assertNotNull(builtStudent);
        assertEquals("Test User", builtStudent.getFullName());
        assertEquals("test@example.com", builtStudent.getEmail());
        assertTrue(builtStudent.getActive());
    }
}
