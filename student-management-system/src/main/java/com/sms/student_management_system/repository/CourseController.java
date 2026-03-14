package com.sms.student_management_system.repository;

import com.sms.student_management_system.entity.Course;
import com.sms.student_management_system.entity.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProgramRepository programRepository;

    @GetMapping("/courses")
    public String listCourses(HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        
        long totalCourses = courseRepository.count();
        long activeCount = courses.stream().filter(c -> "Active".equals(c.getStatus())).count();
        long fullCount = courses.stream().filter(c -> "Full".equals(c.getStatus())).count();
        
        model.addAttribute("totalCourses", totalCourses);
        model.addAttribute("activeCourses", activeCount); 
        model.addAttribute("fullCourses", fullCount);
        model.addAttribute("totalEnrolled", 0);
        
        return "course_management";
    }

    @GetMapping("/courses/new")
    public String showCreateForm(HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        Course course = new Course();
        model.addAttribute("course", course);
        model.addAttribute("programs", programRepository.findAll());
        return "add_course";
    }

    @GetMapping("/api/courses/next-code")
    @ResponseBody
    public Map<String, String> getNextCourseCode(@RequestParam String programName) {
        String prefix = getCoursePrefix(programName);
        String nextCode = generateNextCourseCode(prefix);
        Map<String, String> result = new HashMap<>();
        result.put("courseCode", nextCode);
        result.put("prefix", prefix);
        return result;
    }

    @PostMapping("/courses")
    public String saveCourse(@ModelAttribute("course") Course course,
                            @RequestParam(name = "programNames", required = false) List<String> programNames,
                            HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        if (programNames != null && !programNames.isEmpty()) {
            for (String programName : programNames) {
                Course newCourse = new Course();
                newCourse.setCourseName(course.getCourseName());
                newCourse.setCredits(course.getCredits());
                newCourse.setStatus(course.getStatus());
                newCourse.setInstructor(course.getInstructor());
                newCourse.setDepartment(course.getDepartment());
                newCourse.setProgramName(programName);
                String prefix = getCoursePrefix(programName);
                newCourse.setCourseCode(generateNextCourseCode(prefix));
                courseRepository.save(newCourse);
            }
        }
        return "redirect:/courses";
    }

    @GetMapping("/courses/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));
        model.addAttribute("course", course);
        return "edit_course";
    }

    @PostMapping("/courses/update/{id}")
    public String updateCourse(@PathVariable("id") Long id, @ModelAttribute("course") Course course, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        course.setId(id);
        courseRepository.save(course);
        return "redirect:/courses";
    }

    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        courseRepository.deleteById(id);
        return "redirect:/courses";
    }

    private String getCoursePrefix(String programName) {
        if (programName == null) return "GEN";
        if (programName.contains("Data Science")) return "DS";
        if (programName.contains("Cyber Security") && programName.startsWith("MSc")) return "MCY";
        if (programName.contains("Cyber Security")) return "CY";
        if (programName.contains("Software Engineering")) return "SE";
        if (programName.contains("Computer Science") && programName.startsWith("Diploma")) return "DCS";
        if (programName.contains("Computer Science") && programName.startsWith("MSc")) return "MCS";
        if (programName.contains("Computer Science")) return "CS";
        if (programName.contains("Information Technology") && programName.startsWith("Diploma")) return "DIT";
        if (programName.contains("Information Technology") && programName.startsWith("MSc")) return "MIT";
        if (programName.contains("Information Technology")) return "IT";
        return "GEN";
    }

    private String generateNextCourseCode(String prefix) {
        List<String> codes = courseRepository.findCourseCodesByPrefix(prefix);
        int maxNum = 0;
        for (String code : codes) {
            String numPart = code.substring(prefix.length());
            try {
                int num = Integer.parseInt(numPart);
                if (num > maxNum) maxNum = num;
            } catch (NumberFormatException e) {
                // skip non-numeric suffixes
            }
        }
        if (maxNum == 0) {
            return prefix + "1001";
        }
        String format = maxNum < 1000 ? "%03d" : "%04d";
        return prefix + String.format(format, maxNum + 1);
    }
}