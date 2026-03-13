package com.sms.student_management_system.aspect;

import com.sms.student_management_system.entity.AuditLog;
import com.sms.student_management_system.entity.Course;
import com.sms.student_management_system.entity.Student;
import com.sms.student_management_system.repository.AuditLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Audit Aspect - Implements AOP-based audit logging for administrative actions.
 * 
 * Design Pattern: Observer Pattern
 * This aspect observes all administrative operations (ADD, UPDATE, DELETE, SEARCH)
 * on Student and Course entities and automatically logs them to the audit_log table.
 * 
 * How it works:
 * 1. Intercepts method calls at service layer boundaries
 * 2. Captures method parameters and return values
 * 3. Serializes old and new values for comparison
 * 4. Creates audit log entries with timestamp and actor information
 * 5. Persists to database for audit trail
 * 
 * @author Student Management System
 * @version 1.0
 */
@Aspect
@Component
public class AuditAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    @Autowired
    private AuditLogRepository auditLogRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private static final String CURRENT_ACTOR = "admin"; // Hardcoded for now, can be made dynamic

    /**
     * Audit logging for student CRUD operations.
     * Triggered before and after methods in StudentService that modify data.
     */
    @Around("execution(* com.sms.student_management_system.service.StudentService.saveStudent(..))")
    public Object auditSaveStudent(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        Student student = (Student) joinPoint.getArgs()[0];
        boolean isNew = student.getId() == null;
        
        String action = isNew ? "ADD" : "UPDATE";
        String oldValue = !isNew ? serializeObject(fetchExistingStudent(student.getId())) : null;
        
        Object result = joinPoint.proceed();
        
        Student savedStudent = (Student) result;
        String newValue = serializeObject(savedStudent);
        
        createAuditLog(action, "STUDENT", savedStudent.getId(), oldValue, newValue);
        logger.debug("Student {} operation completed for ID: {}", action, savedStudent.getId());
        
        return result;
    }

    /**
     * Audit logging for course CRUD operations.
     */
    @Around("execution(* com.sms.student_management_system.service.CourseService.saveCourse(..))")
    public Object auditSaveCourse(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        Course course = (Course) joinPoint.getArgs()[0];
        boolean isNew = course.getId() == null;
        
        String action = isNew ? "ADD" : "UPDATE";
        String oldValue = !isNew ? serializeObject(fetchExistingCourse(course.getId())) : null;
        
        Object result = joinPoint.proceed();
        
        Course savedCourse = (Course) result;
        String newValue = serializeObject(savedCourse);
        
        createAuditLog(action, "COURSE", savedCourse.getId(), oldValue, newValue);
        logger.debug("Course {} operation completed for ID: {}", action, savedCourse.getId());
        
        return result;
    }

    /**
     * Audit logging for student deletion (soft delete).
     */
    @Around("execution(* com.sms.student_management_system.service.StudentService.deleteStudent(..))")
    public Object auditDeleteStudent(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        Long studentId = (Long) joinPoint.getArgs()[0];
        
        Object result = joinPoint.proceed();
        
        createAuditLog("DELETE", "STUDENT", studentId, null, "Student marked as inactive");
        logger.info("Student DELETE operation completed for ID: {}", studentId);
        
        return result;
    }

    /**
     * Audit logging for course deletion.
     */
    @Around("execution(* com.sms.student_management_system.service.CourseService.deleteCourse(..))")
    public Object auditDeleteCourse(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        Long courseId = (Long) joinPoint.getArgs()[0];
        
        Object result = joinPoint.proceed();
        
        createAuditLog("DELETE", "COURSE", courseId, null, "Course deleted");
        logger.info("Course DELETE operation completed for ID: {}", courseId);
        
        return result;
    }

    /**
     * Audit logging for search operations on students.
     */
    @AfterReturning(pointcut = "execution(* com.sms.student_management_system.service.StudentService.searchStudents(..))", 
                    returning = "result")
    public void auditSearchStudents(JoinPoint joinPoint, Object result) {
        String keyword = (String) joinPoint.getArgs()[0];
        createAuditLog("SEARCH", "STUDENT", null, null, "Searched with keyword: " + keyword);
        logger.debug("Student SEARCH operation completed with keyword: {}", keyword);
    }

    /**
     * Audit logging for retrieving student details.
     */
    @AfterReturning(pointcut = "execution(* com.sms.student_management_system.service.StudentService.getStudentById(..))", 
                    returning = "result")
    public void auditGetStudent(JoinPoint joinPoint, Object result) {
        Long studentId = (Long) joinPoint.getArgs()[0];
        createAuditLog("VIEW", "STUDENT", studentId, null, "Student details viewed");
        logger.debug("Student VIEW operation completed for ID: {}", studentId);
    }

    /**
     * Create and persist an audit log entry.
     * 
     * @param action The action performed (ADD, UPDATE, DELETE, SEARCH, VIEW)
     * @param entityType The type of entity (STUDENT or COURSE)
     * @param entityId The ID of the entity
     * @param oldValue The old value (for updates)
     * @param newValue The new value
     */
    private void createAuditLog(String action, String entityType, Long entityId, 
                               String oldValue, String newValue) {
        try {
            AuditLog auditLog = new AuditLog();
            auditLog.setTimestamp(LocalDateTime.now());
            auditLog.setAction(action);
            auditLog.setActor(CURRENT_ACTOR);
            auditLog.setEntityType(entityType);
            auditLog.setEntityId(entityId);
            auditLog.setOldValue(oldValue);
            auditLog.setNewValue(newValue);
            
            auditLogRepository.save(auditLog);
            logger.debug("Audit log created: {} - {} - {}", action, entityType, entityId);
        } catch (Exception e) {
            logger.error("Error creating audit log", e);
            // Don't rethrow - we don't want audit failures to affect business operations
        }
    }

    /**
     * Serialize an object to JSON string for audit trail.
     */
    private String serializeObject(Object obj) {
        try {
            if (obj == null) return null;
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.warn("Failed to serialize object", e);
            return obj.toString();
        }
    }

    /**
     * Fetch existing student (this would be implemented in StudentService).
     * For now, returns null as placeholder.
     */
    private Student fetchExistingStudent(Long id) {
        // This would be implemented via StudentRepository in actual service
        return null;
    }

    /**
     * Fetch existing course (this would be implemented in CourseService).
     * For now, returns null as placeholder.
     */
    private Course fetchExistingCourse(Long id) {
        // This would be implemented via CourseRepository in actual service
        return null;
    }
}
