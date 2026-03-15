package com.sms.student_management_system.util;

import com.sms.student_management_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.Year;
import java.util.Map;

/**
 * Student ID Generator using Factory Pattern.
 * 
 * Design Pattern: Factory Pattern
 * This class acts as a factory for generating unique student numbers
 * in the format: KDU-{programCode}-YYYY-XXXX
 * 
 * Program-specific prefixes:
 * - BSc Computer Science       → KDU-BCS-YYYY-XXXX
 * - BSc Software Engineering   → KDU-BSE-YYYY-XXXX
 * - BSc Information Technology  → KDU-BIT-YYYY-XXXX
 * - BSc Data Science            → KDU-BDS-YYYY-XXXX
 * - BSc Information Systems     → KDU-BIS-YYYY-XXXX
 * - MSc Computer Science        → KDU-MCS-YYYY-XXXX
 * - MSc Information Technology  → KDU-MIT-YYYY-XXXX
 * 
 * @author Student Management System
 * @version 2.0
 */
@Component
public class StudentIdGenerator {

    @Autowired
    private StudentRepository studentRepository;

    private static final String PREFIX = "KDU";

    private static final Map<String, String> PROGRAM_CODE_MAP = Map.of(
        "BSc (Hons) in Computer Science", "BCS",
        "BSc (Hons) in Software Engineering", "BSE",
        "BSc (Hons) in Information Technology", "BIT",
        "BSc (Hons) in Data Science", "BDS",
        "BSc (Hons) in Information Systems", "BIS",
        "MSc in Computer Science", "MCS",
        "MSc in Information Technology", "MIT"
    );

    /**
     * Generate a unique student number based on the program.
     * Format: KDU-{programCode}-YYYY-XXXX
     * 
     * @param programName The full program name (e.g. "BSc (Hons) in Software Engineering")
     * @return Generated student number
     */
    public String generateStudentNumber(String programName) {
        String programCode = getProgramCode(programName);
        String currentYear = String.valueOf(Year.now().getValue());
        int nextSequence = getNextSequenceNumber(programCode, currentYear);

        return String.format("%s-%s-%s-%04d", PREFIX, programCode, currentYear, nextSequence);
    }

    /**
     * Resolve the short program code from the full program name.
     */
    private String getProgramCode(String programName) {
        if (programName == null) {
            return "GEN";
        }
        return PROGRAM_CODE_MAP.getOrDefault(programName.trim(), "GEN");
    }

    /**
     * Get the next sequence number for the given program code and year.
     * Queries ALL students (including soft-deleted) to avoid unique constraint violations.
     */
    private int getNextSequenceNumber(String programCode, String year) {
        try {
            String pattern = PREFIX + "-" + programCode + "-" + year + "-";

            int maxSequence = studentRepository.findAll()
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
            return 1;
        }
    }

    /**
     * Validate if a student number follows the expected format.
     */
    public boolean isValidFormat(String studentNumber) {
        return studentNumber != null &&
               studentNumber.matches(PREFIX + "-[A-Z]{2,3}-\\d{4}-\\d{4}");
    }
}
