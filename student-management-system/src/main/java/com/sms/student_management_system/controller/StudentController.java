package com.sms.student_management_system.controller;

import com.sms.student_management_system.entity.Student;
import com.sms.student_management_system.dto.StudentDTO;
import com.sms.student_management_system.service.StudentService;
import com.sms.student_management_system.repository.StudentRepository;
import com.sms.student_management_system.repository.ProgramRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private ProgramRepository programRepository;

    // ERROR විසඳීමට: "/" වෙනුවට "/welcome" භාවිතා කරන්න
    @GetMapping("/welcome")
    public String welcomePage() {
        return "welcome";
    }

    // 2. ලියාපදිංචි කිරීමේ පිටුව
    @GetMapping("/register")
    public String registrationPage(HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        model.addAttribute("student", new Student());
        model.addAttribute("programs", programRepository.findAll());
        return "registration";
    }

    // 3. අලුත් ශිෂ්‍යයෙක් Save කිරීම
    @PostMapping("/students/save")
    public String saveStudent(@ModelAttribute("student") Student student, 
                             HttpSession session, Model model) {
        // Check authentication
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        
        try {
            // Convert Student entity to StudentDTO
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setFullName(student.getFullName());
            studentDTO.setEmail(student.getEmail());
            studentDTO.setPhone(student.getPhone());
            studentDTO.setAddress(student.getAddress());
            studentDTO.setDateOfBirth(student.getDateOfBirth());
            studentDTO.setProgram(student.getProgram());
            studentDTO.setEnrollmentDate(java.time.LocalDate.now());
            
            // Register student using service (handles ID generation, validation)
            studentService.registerStudent(studentDTO);
            
            return "redirect:/students";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to register student: " + e.getMessage());
            model.addAttribute("student", student);
            model.addAttribute("programs", programRepository.findAll());
            return "registration";
        }
    }

    // 4. ශිෂ්‍ය ලැයිස්තුව පෙන්වීම
    @GetMapping("/students")
    public String listStudents(@RequestParam(name = "keyword", required = false) String keyword, 
                               HttpSession session, Model model) {
        
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }

        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("students", studentRepository.searchStudents(keyword));
        } else {
            model.addAttribute("students", studentRepository.findAllActive());
        }
        
        return "students";
    }

    // 5. ශිෂ්‍ය තොරතුරු සංස්කරණය (Edit)
    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        // .get() වෙනුවට .orElse(null) භාවිතා කිරීම වඩාත් ආරක්ෂිතයි
        model.addAttribute("student", studentRepository.findById(id).orElse(null));
        model.addAttribute("programs", programRepository.findAll());
        return "edit_student";
    }

    // 6. දත්ත Update කිරීම
    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student, 
                               HttpSession session, Model model) {
        // Check authentication
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        
        try {
            studentRepository.findById(id).ifPresent(existingStudent -> {
                existingStudent.setFullName(student.getFullName());
                existingStudent.setEmail(student.getEmail());
                existingStudent.setPhone(student.getPhone());
                existingStudent.setAddress(student.getAddress());
                existingStudent.setProgram(student.getProgram());
                existingStudent.setDateOfBirth(student.getDateOfBirth());
                studentRepository.save(existingStudent);
            });
            return "redirect:/students";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update student: " + e.getMessage());
            model.addAttribute("student", studentRepository.findById(id).orElse(null));
            model.addAttribute("programs", programRepository.findAll());
            return "edit_student";
        }
    }

    // 7. ශිෂ්‍යයෙකු ඉවත් කිරීම (Soft Delete)
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        studentRepository.findById(id).ifPresent(student -> {
            student.setActive(false);
            studentRepository.save(student);
        });
        return "redirect:/students";
    }
}