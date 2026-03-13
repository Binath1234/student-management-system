package com.sms.student_management_system.controller;

import com.sms.student_management_system.repository.StudentRepository;
import com.sms.student_management_system.repository.ProgramRepository;
import com.sms.student_management_system.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/")
    public String dashboard(@RequestParam(name = "keyword", required = false) String keyword,
                            HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }

        model.addAttribute("totalStudents", studentRepository.findAllActive().size());
        model.addAttribute("totalCourses", courseRepository.count());
        model.addAttribute("totalPrograms", programRepository.count());

        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("students", studentRepository.searchStudents(keyword.trim()));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("students", studentRepository.findAllActive());
        }

        return "dashboard";
    }
}