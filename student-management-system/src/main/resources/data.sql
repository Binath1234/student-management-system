-- =================== Faculty of Computing - Degree Programs ===================
MERGE INTO program (id, program_code, program_name, degree_type, department, duration, credits, enrolled, coordinator, status, accreditation) KEY(id) VALUES
(1, 'CS001', 'BSc (Hons) in Computer Science', 'Bachelor', 'Computer Science', '4 Years', 128, 0, 'Dr. Amal Perera', 'Active', 'Accredited'),
(2, 'SE001', 'BSc (Hons) in Software Engineering', 'Bachelor', 'Software Engineering', '4 Years', 128, 0, 'Dr. Nimal Fernando', 'Active', 'Accredited'),
(3, 'IT001', 'BSc (Hons) in Information Technology', 'Bachelor', 'Information Technology', '4 Years', 120, 0, 'Dr. Kamal Silva', 'Active', 'Accredited'),
(4, 'CS002', 'BSc (Hons) in Data Science', 'Bachelor', 'Computer Science', '4 Years', 120, 0, 'Dr. Chaminda Bandara', 'Active', 'Accredited'),
(5, 'CS003', 'BSc (Hons) in Cyber Security', 'Bachelor', 'Computer Science', '4 Years', 120, 0, 'Dr. Ruwan Jayawardena', 'Active', 'Accredited'),
(6, 'IT002', 'Diploma in Information Technology', 'Diploma', 'Information Technology', '2 Years', 60, 0, 'Dr. Sunil Rathnayake', 'Active', 'Accredited'),
(7, 'CS004', 'Diploma in Computer Science', 'Diploma', 'Computer Science', '2 Years', 60, 0, 'Dr. Harsha Kumara', 'Active', 'Accredited'),
(8, 'MCS001', 'MSc in Computer Science', 'Master', 'Computer Science', '2 Years', 60, 0, 'Prof. Prasad Wimalaratne', 'Active', 'Accredited'),
(9, 'MIT001', 'MSc in Information Technology', 'Master', 'Information Technology', '2 Years', 60, 0, 'Prof. Dilani Wickramasinghe', 'Active', 'Accredited'),
(10, 'MCS002', 'MSc in Cyber Security', 'Master', 'Computer Science', '2 Years', 60, 0, 'Prof. Lakmal Seneviratne', 'Active', 'Accredited');

-- =================== Faculty of Computing - Courses ===================
-- BSc (Hons) in Computer Science - Courses
MERGE INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, program_name, created_at, updated_at) KEY(id) VALUES
(1, 'CS1101', 'Introduction to Programming', 3, 'Dr. Amal Perera', 'Computer Science', 'Semester 1', 'Monday 08:00-10:00', 'Active', 'BSc (Hons) in Computer Science', NOW(), NOW()),
(2, 'CS1201', 'Object Oriented Programming', 3, 'Dr. Nimal Fernando', 'Computer Science', 'Semester 2', 'Tuesday 10:00-12:00', 'Active', 'BSc (Hons) in Computer Science', NOW(), NOW()),
(3, 'CS2101', 'Data Structures and Algorithms', 3, 'Dr. Kamal Silva', 'Computer Science', 'Semester 3', 'Wednesday 08:00-10:00', 'Active', 'BSc (Hons) in Computer Science', NOW(), NOW()),
(4, 'CS2201', 'Database Management Systems', 3, 'Dr. Chaminda Bandara', 'Computer Science', 'Semester 4', 'Thursday 10:00-12:00', 'Active', 'BSc (Hons) in Computer Science', NOW(), NOW()),
(5, 'CS3101', 'Computer Networks', 3, 'Dr. Ruwan Jayawardena', 'Computer Science', 'Semester 5', 'Monday 10:00-12:00', 'Active', 'BSc (Hons) in Computer Science', NOW(), NOW()),
(6, 'CS3201', 'Operating Systems', 3, 'Dr. Harsha Kumara', 'Computer Science', 'Semester 6', 'Tuesday 08:00-10:00', 'Active', 'BSc (Hons) in Computer Science', NOW(), NOW());

-- BSc (Hons) in Software Engineering - Courses
MERGE INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, program_name, created_at, updated_at) KEY(id) VALUES
(7, 'SE1101', 'Introduction to Programming', 3, 'Dr. Nimal Fernando', 'Software Engineering', 'Semester 1', 'Monday 08:00-10:00', 'Active', 'BSc (Hons) in Software Engineering', NOW(), NOW()),
(8, 'SE1201', 'Object Oriented Programming', 3, 'Dr. Amal Perera', 'Software Engineering', 'Semester 2', 'Tuesday 10:00-12:00', 'Active', 'BSc (Hons) in Software Engineering', NOW(), NOW()),
(9, 'SE2101', 'Software Engineering Principles', 3, 'Dr. Nimal Fernando', 'Software Engineering', 'Semester 3', 'Friday 08:00-10:00', 'Active', 'BSc (Hons) in Software Engineering', NOW(), NOW()),
(10, 'SE2201', 'Software Design Patterns', 3, 'Dr. Kamal Silva', 'Software Engineering', 'Semester 4', 'Wednesday 10:00-12:00', 'Active', 'BSc (Hons) in Software Engineering', NOW(), NOW()),
(11, 'SE3101', 'Software Project Management', 3, 'Dr. Chaminda Bandara', 'Software Engineering', 'Semester 5', 'Thursday 08:00-10:00', 'Active', 'BSc (Hons) in Software Engineering', NOW(), NOW()),
(12, 'SE3201', 'Software Quality Assurance', 3, 'Dr. Ruwan Jayawardena', 'Software Engineering', 'Semester 6', 'Friday 10:00-12:00', 'Active', 'BSc (Hons) in Software Engineering', NOW(), NOW());

-- BSc (Hons) in Information Technology - Courses
MERGE INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, program_name, created_at, updated_at) KEY(id) VALUES
(13, 'IT1101', 'Introduction to IT', 3, 'Dr. Sunil Rathnayake', 'Information Technology', 'Semester 1', 'Monday 08:00-10:00', 'Active', 'BSc (Hons) in Information Technology', NOW(), NOW()),
(14, 'IT1201', 'Computer Fundamentals', 3, 'Dr. Kamal Silva', 'Information Technology', 'Semester 2', 'Tuesday 10:00-12:00', 'Active', 'BSc (Hons) in Information Technology', NOW(), NOW()),
(15, 'IT2101', 'Web Application Development', 3, 'Dr. Sunil Rathnayake', 'Information Technology', 'Semester 3', 'Wednesday 10:00-12:00', 'Active', 'BSc (Hons) in Information Technology', NOW(), NOW()),
(16, 'IT2201', 'Network Administration', 3, 'Dr. Ruwan Jayawardena', 'Information Technology', 'Semester 4', 'Thursday 10:00-12:00', 'Active', 'BSc (Hons) in Information Technology', NOW(), NOW()),
(17, 'IT3101', 'Cloud Computing', 3, 'Dr. Harsha Kumara', 'Information Technology', 'Semester 5', 'Friday 08:00-10:00', 'Active', 'BSc (Hons) in Information Technology', NOW(), NOW()),
(18, 'IT3201', 'IT Project Management', 3, 'Dr. Chaminda Bandara', 'Information Technology', 'Semester 6', 'Monday 14:00-16:00', 'Active', 'BSc (Hons) in Information Technology', NOW(), NOW());

-- BSc (Hons) in Data Science - Courses
MERGE INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, program_name, created_at, updated_at) KEY(id) VALUES
(19, 'DS1101', 'Introduction to Data Science', 3, 'Dr. Chaminda Bandara', 'Computer Science', 'Semester 1', 'Monday 08:00-10:00', 'Active', 'BSc (Hons) in Data Science', NOW(), NOW()),
(20, 'DS1201', 'Statistics for Data Science', 3, 'Prof. Prasad Wimalaratne', 'Computer Science', 'Semester 2', 'Tuesday 10:00-12:00', 'Active', 'BSc (Hons) in Data Science', NOW(), NOW()),
(21, 'DS2101', 'Machine Learning', 3, 'Prof. Prasad Wimalaratne', 'Computer Science', 'Semester 3', 'Wednesday 08:00-10:00', 'Active', 'BSc (Hons) in Data Science', NOW(), NOW()),
(22, 'DS2201', 'Data Mining and Warehousing', 3, 'Dr. Chaminda Bandara', 'Computer Science', 'Semester 4', 'Thursday 10:00-12:00', 'Active', 'BSc (Hons) in Data Science', NOW(), NOW()),
(23, 'DS3101', 'Artificial Intelligence', 3, 'Prof. Prasad Wimalaratne', 'Computer Science', 'Semester 5', 'Thursday 08:00-10:00', 'Active', 'BSc (Hons) in Data Science', NOW(), NOW()),
(24, 'DS3201', 'Big Data Analytics', 3, 'Dr. Harsha Kumara', 'Computer Science', 'Semester 6', 'Friday 10:00-12:00', 'Active', 'BSc (Hons) in Data Science', NOW(), NOW());

-- BSc (Hons) in Cyber Security - Courses
MERGE INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, program_name, created_at, updated_at) KEY(id) VALUES
(25, 'CY1101', 'Introduction to Cyber Security', 3, 'Dr. Ruwan Jayawardena', 'Computer Science', 'Semester 1', 'Monday 10:00-12:00', 'Active', 'BSc (Hons) in Cyber Security', NOW(), NOW()),
(26, 'CY1201', 'Computer Networks Fundamentals', 3, 'Dr. Harsha Kumara', 'Computer Science', 'Semester 2', 'Tuesday 08:00-10:00', 'Active', 'BSc (Hons) in Cyber Security', NOW(), NOW()),
(27, 'CY2101', 'Ethical Hacking', 3, 'Dr. Ruwan Jayawardena', 'Computer Science', 'Semester 3', 'Wednesday 10:00-12:00', 'Active', 'BSc (Hons) in Cyber Security', NOW(), NOW()),
(28, 'CY2201', 'Digital Forensics', 3, 'Prof. Lakmal Seneviratne', 'Computer Science', 'Semester 4', 'Thursday 08:00-10:00', 'Active', 'BSc (Hons) in Cyber Security', NOW(), NOW()),
(29, 'CY3101', 'Network Security', 3, 'Dr. Ruwan Jayawardena', 'Computer Science', 'Semester 5', 'Friday 08:00-10:00', 'Active', 'BSc (Hons) in Cyber Security', NOW(), NOW()),
(30, 'CY3201', 'Cryptography', 3, 'Prof. Lakmal Seneviratne', 'Computer Science', 'Semester 6', 'Monday 14:00-16:00', 'Active', 'BSc (Hons) in Cyber Security', NOW(), NOW());

-- Diploma in Information Technology - Courses
MERGE INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, program_name, created_at, updated_at) KEY(id) VALUES
(31, 'DIT101', 'Computer Fundamentals', 3, 'Dr. Sunil Rathnayake', 'Information Technology', 'Semester 1', 'Monday 08:00-10:00', 'Active', 'Diploma in Information Technology', NOW(), NOW()),
(32, 'DIT102', 'Introduction to Programming', 3, 'Dr. Amal Perera', 'Information Technology', 'Semester 1', 'Wednesday 08:00-10:00', 'Active', 'Diploma in Information Technology', NOW(), NOW()),
(33, 'DIT201', 'Web Development Basics', 3, 'Dr. Sunil Rathnayake', 'Information Technology', 'Semester 2', 'Tuesday 10:00-12:00', 'Active', 'Diploma in Information Technology', NOW(), NOW()),
(34, 'DIT202', 'Database Fundamentals', 3, 'Dr. Chaminda Bandara', 'Information Technology', 'Semester 2', 'Thursday 10:00-12:00', 'Active', 'Diploma in Information Technology', NOW(), NOW());

-- Diploma in Computer Science - Courses
MERGE INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, program_name, created_at, updated_at) KEY(id) VALUES
(35, 'DCS101', 'Programming Fundamentals', 3, 'Dr. Amal Perera', 'Computer Science', 'Semester 1', 'Monday 10:00-12:00', 'Active', 'Diploma in Computer Science', NOW(), NOW()),
(36, 'DCS102', 'Mathematics for Computing', 3, 'Dr. Harsha Kumara', 'Computer Science', 'Semester 1', 'Wednesday 10:00-12:00', 'Active', 'Diploma in Computer Science', NOW(), NOW()),
(37, 'DCS201', 'Data Structures Basics', 3, 'Dr. Kamal Silva', 'Computer Science', 'Semester 2', 'Tuesday 08:00-10:00', 'Active', 'Diploma in Computer Science', NOW(), NOW()),
(38, 'DCS202', 'Introduction to Databases', 3, 'Dr. Chaminda Bandara', 'Computer Science', 'Semester 2', 'Thursday 08:00-10:00', 'Active', 'Diploma in Computer Science', NOW(), NOW());

-- MSc in Computer Science - Courses
MERGE INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, program_name, created_at, updated_at) KEY(id) VALUES
(39, 'MCS101', 'Advanced Algorithms', 3, 'Prof. Prasad Wimalaratne', 'Computer Science', 'Semester 1', 'Monday 18:00-20:00', 'Active', 'MSc in Computer Science', NOW(), NOW()),
(40, 'MCS102', 'Advanced Database Systems', 3, 'Dr. Chaminda Bandara', 'Computer Science', 'Semester 1', 'Wednesday 18:00-20:00', 'Active', 'MSc in Computer Science', NOW(), NOW()),
(41, 'MCS201', 'Research Methodology', 3, 'Prof. Prasad Wimalaratne', 'Computer Science', 'Semester 2', 'Tuesday 18:00-20:00', 'Active', 'MSc in Computer Science', NOW(), NOW()),
(42, 'MCS202', 'Machine Learning and AI', 3, 'Prof. Prasad Wimalaratne', 'Computer Science', 'Semester 2', 'Thursday 18:00-20:00', 'Active', 'MSc in Computer Science', NOW(), NOW());

-- MSc in Information Technology - Courses
MERGE INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, program_name, created_at, updated_at) KEY(id) VALUES
(43, 'MIT101', 'Enterprise Architecture', 3, 'Prof. Dilani Wickramasinghe', 'Information Technology', 'Semester 1', 'Monday 18:00-20:00', 'Active', 'MSc in Information Technology', NOW(), NOW()),
(44, 'MIT102', 'Cloud and DevOps', 3, 'Dr. Harsha Kumara', 'Information Technology', 'Semester 1', 'Wednesday 18:00-20:00', 'Active', 'MSc in Information Technology', NOW(), NOW()),
(45, 'MIT201', 'IT Governance and Strategy', 3, 'Prof. Dilani Wickramasinghe', 'Information Technology', 'Semester 2', 'Tuesday 18:00-20:00', 'Active', 'MSc in Information Technology', NOW(), NOW()),
(46, 'MIT202', 'Advanced Web Technologies', 3, 'Dr. Sunil Rathnayake', 'Information Technology', 'Semester 2', 'Thursday 18:00-20:00', 'Active', 'MSc in Information Technology', NOW(), NOW());

-- MSc in Cyber Security - Courses
MERGE INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, program_name, created_at, updated_at) KEY(id) VALUES
(47, 'MCY101', 'Advanced Network Security', 3, 'Prof. Lakmal Seneviratne', 'Computer Science', 'Semester 1', 'Monday 18:00-20:00', 'Active', 'MSc in Cyber Security', NOW(), NOW()),
(48, 'MCY102', 'Advanced Cryptography', 3, 'Prof. Lakmal Seneviratne', 'Computer Science', 'Semester 1', 'Wednesday 18:00-20:00', 'Active', 'MSc in Cyber Security', NOW(), NOW()),
(49, 'MCY201', 'Cyber Threat Intelligence', 3, 'Dr. Ruwan Jayawardena', 'Computer Science', 'Semester 2', 'Tuesday 18:00-20:00', 'Active', 'MSc in Cyber Security', NOW(), NOW()),
(50, 'MCY202', 'Security Operations and Incident Response', 3, 'Prof. Lakmal Seneviratne', 'Computer Science', 'Semester 2', 'Thursday 18:00-20:00', 'Active', 'MSc in Cyber Security', NOW(), NOW());

-- =================== Admin User ===================
MERGE INTO admins (id, username, password, full_name, phone, department, email) KEY(id) VALUES
(1, 'admin', 'password', 'Admin User', '+1 (555) 123-4567', 'IT Department', 'admin@university.edu');

-- =================== Reset Auto-Increment Sequences ===================
ALTER TABLE program ALTER COLUMN id RESTART WITH 11;
ALTER TABLE courses ALTER COLUMN id RESTART WITH 51;
ALTER TABLE admins ALTER COLUMN id RESTART WITH 2;
