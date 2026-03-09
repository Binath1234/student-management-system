package com.sms.student_management_system.controller;

import com.sms.student_management_system.entity.Student;
import com.sms.student_management_system.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // 1. මුල් පිටුව (Home Page)
    @GetMapping("/")
    public String welcomePage() {
        return "welcome";
}

// 2. ශිෂ්‍ය ලැයිස්තුව (ලොග් වී ඇත්නම් පමණි)
   

    // 3. ලියාපදිංචි කිරීමේ පිටුව (ලොග් වී ඇත්නම් පමණි)
    @GetMapping("/register")
    public String registrationPage(HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        Student student = new Student();
        model.addAttribute("student", student);
        return "registration";
    }

    // 4. අලුත් ශිෂ්‍යයෙක් Save කිරීම
    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentRepository.save(student);
        return "redirect:/students";
    }

    // ශිෂ්‍ය ලැයිස්තුව පෙන්වීම සහ සෙවීම (Search) යන දෙකම මේ Method එකෙන් සිද්ධ වෙනවා
@GetMapping("/students")
public String listStudents(@RequestParam(name = "keyword", required = false) String keyword, HttpSession session, Model model) {
    
    // Admin ලොග් වෙලා නැත්නම් Login එකට හරවා යවන්න
    if (session.getAttribute("loggedInAdmin") == null) {
        return "redirect:/login";
    }

    if (keyword != null && !keyword.isEmpty()) {
        // සෙවුම් පදයක් (Keyword) තිබේ නම් ඒ අනුව සොයන්න
        model.addAttribute("students", studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(keyword, keyword));
    } else {
        // සෙවුම් පදයක් නැත්නම් සියලුම ශිෂ්‍යයන් පෙන්වන්න
        model.addAttribute("students", studentRepository.findAll());
    }
    
    return "students";
}
    // 5. ශිෂ්‍ය තොරතුරු සංස්කරණය කරන Form එක (Edit)
    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        model.addAttribute("student", studentRepository.findById(id).get());
        return "edit_student";
    }

    // 6. සංස්කරණය කළ දත්ත Update කිරීම
    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student) {
        Student existingStudent = studentRepository.findById(id).get();
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());
        studentRepository.save(existingStudent);
        return "redirect:/students";
    }

    // 7. ශිෂ්‍යයෙකු ඉවත් කිරීම (Delete)
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        studentRepository.deleteById(id);
        return "redirect:/students";
    }
}