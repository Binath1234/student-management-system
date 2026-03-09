package com.sms.student_management_system.repository;

import com.sms.student_management_system.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // පළමු නම හෝ දෙවන නම අනුව සර්ච් කිරීමට (Case insensitive)
    List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}