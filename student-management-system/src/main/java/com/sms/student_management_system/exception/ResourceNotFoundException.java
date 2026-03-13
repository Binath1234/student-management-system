package com.sms.student_management_system.exception;

/**
 * Custom exception for resource not found scenarios.
 * 
 * @author Student Management System
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
