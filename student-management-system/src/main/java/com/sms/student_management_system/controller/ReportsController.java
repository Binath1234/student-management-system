package com.sms.student_management_system.controller;

import com.sms.student_management_system.entity.Course;
import com.sms.student_management_system.entity.Student;
import com.sms.student_management_system.repository.CourseRepository;
import com.sms.student_management_system.repository.ProgramRepository;
import com.sms.student_management_system.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ReportsController {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/reports")
    public String showReports(HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }

        // Summary stats
        long totalProgramsCount = programRepository.count();
        List<Student> activeStudents = studentRepository.findAllActive();

        long activeCourses = courseRepository.findAll().stream()
                .filter(c -> "Active".equals(c.getStatus())).count();

        model.addAttribute("totalStudents", activeStudents.size());
        model.addAttribute("totalPrograms", totalProgramsCount);
        model.addAttribute("activeCourses", activeCourses);

        // --- Pie Chart: Students by Program (count actual enrolled students) ---
        Map<String, Long> studentsByProgram = activeStudents.stream()
                .filter(s -> s.getProgram() != null && !s.getProgram().isBlank())
                .collect(Collectors.groupingBy(Student::getProgram, Collectors.counting()));

        // Include all programs (even those with 0 students)
        var allPrograms = programRepository.findAll();
        List<String> pieLabels = new ArrayList<>();
        List<Long> pieData = new ArrayList<>();

        for (var p : allPrograms) {
            pieLabels.add(p.getProgramName());
            pieData.add(studentsByProgram.getOrDefault(p.getProgramName(), 0L));
        }

        model.addAttribute("pieLabels", pieLabels);
        model.addAttribute("pieData", pieData);

        // --- Line Chart: Enrollment Trend (last 12 months) ---
        LocalDate now = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM yyyy");
        List<String> trendLabels = new ArrayList<>();
        List<Long> trendData = new ArrayList<>();

        for (int i = 11; i >= 0; i--) {
            LocalDate month = now.minusMonths(i);
            trendLabels.add(month.format(fmt));
            long count = activeStudents.stream()
                    .filter(s -> s.getEnrollmentDate() != null
                            && s.getEnrollmentDate().getYear() == month.getYear()
                            && s.getEnrollmentDate().getMonthValue() == month.getMonthValue())
                    .count();
            trendData.add(count);
        }

        model.addAttribute("trendLabels", trendLabels);
        model.addAttribute("trendData", trendData);

        return "reports";
    }
}