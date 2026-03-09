package com.sms.student_management_system.repository;

import com.sms.student_management_system.entity.Course;
import com.sms.student_management_system.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/courses")
    public String listCourses(Model model) {
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
    public String showCreateForm(Model model) {
        Course course = new Course();
        model.addAttribute("course", course);
        return "add_course"; // අපි ඊළඟට හදන HTML එක
    }

    // Form එකේ දත්ත Save කිරීමට
    @PostMapping("/courses")
    public String saveCourse(@ModelAttribute("course") Course course) {
        courseRepository.save(course);
        return "redirect:/courses";
    }
}