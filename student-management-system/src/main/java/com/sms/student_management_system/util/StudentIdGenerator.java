package com.sms.student_management_system.util;

import com.sms.student_management_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.Year;

/**
 * Student ID Generator using Factory Pattern.
 * 
 * Design Pattern: Factory Pattern
 * This class acts as a factory for generating unique student numbers
 * in the format: KDU-SE-YYYY-XXXX (e.g., KDU-SE-2024-0001)
 * 
 * Factory Pattern Benefits:
 * - Encapsulates the logic of ID generation
 * - Makes it easy to change generation strategy without affecting other parts
 * - Provides a single point for managing ID generation rules
 * 
 * @author Student Management System
 * @version 1.0
 */
@Component
public class StudentIdGenerator {

    @Autowired
    private StudentRepository studentRepository;
    
    @Value("${student.id.format:KDU-SE-{year}-{sequence}}")
    private String idFormat;
    
    @Value("${student.id.prefix:KDU}")
    private String prefix;
    
    @Value("${student.id.program:SE}")
    private String program;

    /**
     * Generate a unique student number with the format: KDU-SE-YYYY-XXXX
     * Sequential numbering is maintained per year.
     * 
     * @return Generated student number
     */
    public String generateStudentNumber() {
        String currentYear = String.valueOf(Year.now().getValue());
        int nextSequence = getNextSequenceNumber(currentYear);
        
        // Format: KDU-SE-YYYY-XXXX
        return String.format("%s-%s-%s-%04d", prefix, program, currentYear, nextSequence);
    }

    /**
     * Get the next sequence number for the current year.
     * Queries the database to find the maximum sequence number for the current year,
     * then returns the next available number.
     * 
     * @param year The year for which to get the next sequence
     * @return Next sequence number
     */
    private int getNextSequenceNumber(String year) {
        try {
            String pattern = prefix + "-" + program + "-" + year + "-";
            
            // For this demonstration, we'll use a simple counter
            // In production, this could use database sequences or atomic increments
            int maxSequence = studentRepository.findAllActive()
                .stream()
                .filter(s -> s.getStudentNumber() != null && s.getStudentNumber().startsWith(pattern))
                .mapToInt(s -> {
                    try {
                        String[] parts = s.getStudentNumber().split("-");
                        return Integer.parseInt(parts[3]);
                    } catch (Exception e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0);
            
            return maxSequence + 1;
        } catch (Exception e) {
            // If error occurs, start from 1
            return 1;
        }
    }

    /**
     * Validate if a student number follows the expected format.
     * 
     * @param studentNumber The student number to validate
     * @return true if valid format, false otherwise
     */
    public boolean isValidFormat(String studentNumber) {
        return studentNumber != null && 
               studentNumber.matches(prefix + "-" + program + "-\\d{4}-\\d{4}");
    }
}
