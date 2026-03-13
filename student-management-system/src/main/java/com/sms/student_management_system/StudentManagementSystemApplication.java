package com.sms.student_management_system;

import com.sms.student_management_system.entity.Course;
import com.sms.student_management_system.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StudentManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner loadData(CourseRepository repository) {
        return args -> {
            // දැනට දත්ත නැත්නම් පමණක් ඇතුළත් කරන්න
            if (repository.count() == 0) {
                Course c1 = new Course();
                c1.setCourseCode("CS101");
                c1.setCourseName("Introduction to Computer Science");
                c1.setCredits(3);
                c1.setInstructor("Dr. Sarah Mitchell");
                c1.setDepartment("Computer Science");
                c1.setSchedule("Mon/Wed 9:00-10:30 AM");
                c1.setStatus("Active");
                c1.setSemester("FALL-2024");

                Course c2 = new Course();
                c2.setCourseCode("CS201");
                c2.setCourseName("Data Structures and Algorithms");
                c2.setCredits(4);
                c2.setInstructor("Prof. Michael Zhang");
                c2.setDepartment("Computer Science");
                c2.setSchedule("Tue/Thu 1:00-2:30 PM");
                c2.setStatus("Active");
                c2.setSemester("FALL-2024");

                repository.save(c1);
                repository.save(c2);
                
                System.out.println("Sample Course Data Loaded!");
            }
        };
    }
}