package com.sms.student_management_system.repository;

import com.sms.student_management_system.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/courses")
    public String listCourses(HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        
        // Dynamic Statistics (Database එකේ තියෙන දත්ත අනුව වෙනස් වේ)
        long totalCourses = courseRepository.count();
        long activeCount = courses.stream().filter(c -> "Active".equals(c.getStatus())).count();
        long fullCount = courses.stream().filter(c -> "Full".equals(c.getStatus())).count();
        
        model.addAttribute("totalCourses", totalCourses);
        model.addAttribute("activeCourses", activeCount); 
        model.addAttribute("fullCourses", fullCount);
        model.addAttribute("totalEnrolled", 0); // මේක පස්සේ Student සහ Course Join කළාම හදමු
        
        return "course_management";
    }

    // අලුත් Course එකක් ඇතුළත් කරන Form එක පෙන්වීමට
    @GetMapping("/courses/new")
    public String showCreateForm(HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        Course course = new Course();
        model.addAttribute("course", course);
        return "add_course"; // අපි ඊළඟට හදන HTML එක
    }

    // Form එකේ දත්ත Save කිරීමට
    @PostMapping("/courses")
    public String saveCourse(@ModelAttribute("course") Course course, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        courseRepository.save(course);
        return "redirect:/courses";
    }

    // 1. Edit Form එක පෙන්වීම
@GetMapping("/courses/edit/{id}")
public String showEditForm(@PathVariable("id") Long id, HttpSession session, Model model) {
    if (session.getAttribute("loggedInAdmin") == null) {
        return "redirect:/login";
    }
    Course course = courseRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));
    model.addAttribute("course", course);
    return "edit_course"; // අපි ඊළඟට හදන HTML එක
}

// 2. වෙනස් කළ දත්ත Update කිරීම
@PostMapping("/courses/update/{id}")
public String updateCourse(@PathVariable("id") Long id, @ModelAttribute("course") Course course, HttpSession session) {
    if (session.getAttribute("loggedInAdmin") == null) {
        return "redirect:/login";
    }
    course.setId(id); // පරණ ID එකම තියාගන්න
    courseRepository.save(course);
    return "redirect:/courses";
}

// 3. Course එකක් Delete කිරීම
@GetMapping("/courses/delete/{id}")
public String deleteCourse(@PathVariable("id") Long id, HttpSession session) {
    if (session.getAttribute("loggedInAdmin") == null) {
        return "redirect:/login";
    }
    courseRepository.deleteById(id);
    return "redirect:/courses";
}
}