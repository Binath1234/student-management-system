package com.sms.student_management_system.controller;

import com.sms.student_management_system.entity.Student;
import com.sms.student_management_system.entity.Course;
import com.sms.student_management_system.dto.StudentDTO;
import com.sms.student_management_system.service.StudentService;
import com.sms.student_management_system.repository.StudentRepository;
import com.sms.student_management_system.repository.ProgramRepository;
import com.sms.student_management_system.repository.CourseRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private CourseRepository courseRepository;

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

    // API: Get courses by program name (JSON for AJAX)
    @GetMapping("/api/courses/by-program")
    @ResponseBody
    public List<Map<String, Object>> getCoursesByProgram(@RequestParam("program") String program) {
        List<Course> courses = courseRepository.findByProgramName(program);
        List<Map<String, Object>> result = new java.util.ArrayList<>();
        for (Course c : courses) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", c.getId());
            map.put("courseCode", c.getCourseCode());
            map.put("courseName", c.getCourseName());
            map.put("credits", c.getCredits());
            map.put("instructor", c.getInstructor());
            map.put("semester", c.getSemester());
            result.add(map);
        }
        return result;
    }

    // 3. අලුත් ශිෂ්‍යයෙක් Save කිරීම
    @PostMapping("/students/save")
    public String saveStudent(@ModelAttribute("student") Student student,
                             @RequestParam(name = "courseIds", required = false) List<Long> courseIds,
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
            Student savedStudent = studentService.registerStudent(studentDTO);

            // Enroll student in selected courses
            if (courseIds != null && !courseIds.isEmpty()) {
                for (Long courseId : courseIds) {
                    courseRepository.findById(courseId).ifPresent(savedStudent::addCourse);
                }
                studentRepository.save(savedStudent);
            }
            
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

    // 8. Student Course Enrollment Page
    @GetMapping("/students/{id}/courses")
    public String studentCourses(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            return "redirect:/students";
        }

        // Get courses related to the student's degree program
        List<Course> programCourses = courseRepository.findByProgramName(student.getProgram());

        // Mark which courses the student is already enrolled in
        Set<Long> enrolledCourseIds = new java.util.HashSet<>();
        for (Course c : student.getCourses()) {
            enrolledCourseIds.add(c.getId());
        }

        model.addAttribute("student", student);
        model.addAttribute("programCourses", programCourses);
        model.addAttribute("enrolledCourseIds", enrolledCourseIds);
        model.addAttribute("enrolledCourses", student.getCourses());
        return "student_courses";
    }

    // 9. Enroll Student in a Course
    @PostMapping("/students/{studentId}/courses/enroll/{courseId}")
    public String enrollCourse(@PathVariable Long studentId, @PathVariable Long courseId, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        studentRepository.findById(studentId).ifPresent(student -> {
            courseRepository.findById(courseId).ifPresent(course -> {
                student.addCourse(course);
                studentRepository.save(student);
            });
        });
        return "redirect:/students/" + studentId + "/courses";
    }

    // 10. Unenroll Student from a Course
    @PostMapping("/students/{studentId}/courses/unenroll/{courseId}")
    public String unenrollCourse(@PathVariable Long studentId, @PathVariable Long courseId, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        studentRepository.findById(studentId).ifPresent(student -> {
            courseRepository.findById(courseId).ifPresent(course -> {
                student.removeCourse(course);
                studentRepository.save(student);
            });
        });
        return "redirect:/students/" + studentId + "/courses";
    }
}