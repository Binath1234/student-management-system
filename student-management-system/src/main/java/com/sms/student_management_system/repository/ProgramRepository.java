package com.sms.student_management_system.repository;

import com.sms.student_management_system.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    
    
    List<Program> findByProgramNameContainingIgnoreCase(String programName);

    // 1. මුළු ශිෂ්‍ය සංඛ්‍යාව ලබා ගැනීමට (Reports සඳහා)
    // Coalesce පාවිච්චි කළේ දත්ත කිසිවක් නැති විට null වෙනුවට 0 ලබා ගැනීමටයි
    @Query("SELECT COALESCE(SUM(p.enrolled), 0) FROM Program p")
    Integer getTotalEnrolledStudents();

    // 2. Status එක අනුව ප්ලෝග්‍රෑම් ගණන ලබා ගැනීමට (උදා: Active ප්ලෝග්‍රෑම් ගණන)
    long countByStatusIgnoreCase(String status);
    
    // 3. Accreditation (පිළිගැනීම) අනුව ගණනය කිරීමට
    long countByAccreditationIgnoreCase(String accreditation);
}