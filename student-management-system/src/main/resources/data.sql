-- =================== Faculty of Computing - Degree Programs ===================
INSERT INTO program (id, program_code, program_name, degree_type, department, duration, credits, enrolled, coordinator, status, accreditation) VALUES
(1, 'CS001', 'BSc (Hons) in Computer Science', 'Bachelor', 'Computer Science', '4 Years', 128, 0, 'Dr. Amal Perera', 'Active', 'Accredited'),
(2, 'SE001', 'BSc (Hons) in Software Engineering', 'Bachelor', 'Software Engineering', '4 Years', 128, 0, 'Dr. Nimal Fernando', 'Active', 'Accredited'),
(3, 'IT001', 'BSc (Hons) in Information Technology', 'Bachelor', 'Information Technology', '4 Years', 120, 0, 'Dr. Kamal Silva', 'Active', 'Accredited'),
(4, 'CS002', 'BSc (Hons) in Data Science', 'Bachelor', 'Computer Science', '4 Years', 120, 0, 'Dr. Chaminda Bandara', 'Active', 'Accredited'),
(5, 'MCS001', 'MSc in Computer Science', 'Master', 'Computer Science', '2 Years', 60, 0, 'Prof. Prasad Wimalaratne', 'Active', 'Accredited'),
(6, 'MIT001', 'MSc in Information Technology', 'Master', 'Information Technology', '2 Years', 60, 0, 'Prof. Dilani Wickramasinghe', 'Active', 'Accredited'),
(7, 'IS001', 'BSc (Hons) in Information Systems', 'Bachelor', 'Information Systems', '4 Years', 120, 0, 'Dr. Sunil Rathnayake', 'Active', 'Accredited')
ON CONFLICT (id) DO NOTHING;

-- =================== Faculty of Computing - Courses ===================
-- BSc (Hons) in Computer Science - Courses
INSERT INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, program_name, created_at, updated_at) VALUES
(1, 'CS1101', 'Introduction to Programming', 3, 'Dr. Amal Perera', 'Computer Science', 'Semester 1', 'Monday 08:00-10:00', 'Active', 'BSc (Hons) in Computer Science', NOW(), NOW()),
(2, 'CS1201', 'Object Oriented Programming', 3, 'Dr. Nimal Fernando', 'Computer Science', 'Semester 2', 'Tuesday 10:00-12:00', 'Active', 'BSc (Hons) in Computer Science', NOW(), NOW()),
(3, 'CS2101', 'Data Structures and Algorithms', 3, 'Dr. Kamal Silva', 'Computer Science', 'Semester 3', 'Wednesday 08:00-10:00', 'Active', 'BSc (Hons) in Computer Science', NOW(), NOW()),
(4, 'CS2201', 'Database Management Systems', 3, 'Dr. Chaminda Bandara', 'Computer Science', 'Semester 4', 'Thursday 10:00-12:00', 'Active', 'BSc (Hons) in Computer Science', NOW(), NOW()),
(5, 'CS3101', 'Computer Networks', 3, 'Dr. Ruwan Jayawardena', 'Computer Science', 'Semester 5', 'Monday 10:00-12:00', 'Active', 'BSc (Hons) in Computer Science', NOW(), NOW()),
(6, 'CS3201', 'Operating Systems', 3, 'Dr. Harsha Kumara', 'Computer Science', 'Semester 6', 'Tuesday 08:00-10:00', 'Active', 'BSc (Hons) in Computer Science', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- BSc (Hons) in Software Engineering - Courses
INSERT INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, program_name, created_at, updated_at) VALUES
(7, 'SE1101', 'Introduction to Programming', 3, 'Dr. Nimal Fernando', 'Software Engineering', 'Semester 1', 'Monday 08:00-10:00', 'Active', 'BSc (Hons) in Software Engineering', NOW(), NOW()),
(8, 'SE1201', 'Object Oriented Programming', 3, 'Dr. Amal Perera', 'Software Engineering', 'Semester 2', 'Tuesday 10:00-12:00', 'Active', 'BSc (Hons) in Software Engineering', NOW(), NOW()),
(9, 'SE2101', 'Software Engineering Principles', 3, 'Dr. Nimal Fernando', 'Software Engineering', 'Semester 3', 'Friday 08:00-10:00', 'Active', 'BSc (Hons) in Software Engineering', NOW(), NOW()),
(10, 'SE2201', 'Software Design Patterns', 3, 'Dr. Kamal Silva', 'Software Engineering', 'Semester 4', 'Wednesday 10:00-12:00', 'Active', 'BSc (Hons) in Software Engineering', NOW(), NOW()),
(11, 'SE3101', 'Software Project Management', 3, 'Dr. Chaminda Bandara', 'Software Engineering', 'Semester 5', 'Thursday 08:00-10:00', 'Active', 'BSc (Hons) in Software Engineering', NOW(), NOW()),
(12, 'SE3201', 'Software Quality Assurance', 3, 'Dr. Ruwan Jayawardena', 'Software Engineering', 'Semester 6', 'Friday 10:00-12:00', 'Active', 'BSc (Hons) in Software Engineering', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- BSc (Hons) in Information Technology - Courses
INSERT INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, program_name, created_at, updated_at) VALUES
(13, 'IT1101', 'Introduction to IT', 3, 'Dr. Sunil Rathnayake', 'Information Technology', 'Semester 1', 'Monday 08:00-10:00', 'Active', 'BSc (Hons) in Information Technology', NOW(), NOW()),
(14, 'IT1201', 'Computer Fundamentals', 3, 'Dr. Kamal Silva', 'Information Technology', 'Semester 2', 'Tuesday 10:00-12:00', 'Active', 'BSc (Hons) in Information Technology', NOW(), NOW()),
(15, 'IT2101', 'Web Application Development', 3, 'Dr. Sunil Rathnayake', 'Information Technology', 'Semester 3', 'Wednesday 10:00-12:00', 'Active', 'BSc (Hons) in Information Technology', NOW(), NOW()),
(16, 'IT2201', 'Network Administration', 3, 'Dr. Ruwan Jayawardena', 'Information Technology', 'Semester 4', 'Thursday 10:00-12:00', 'Active', 'BSc (Hons) in Information Technology', NOW(), NOW()),
(17, 'IT3101', 'Cloud Computing', 3, 'Dr. Harsha Kumara', 'Information Technology', 'Semester 5', 'Friday 08:00-10:00', 'Active', 'BSc (Hons) in Information Technology', NOW(), NOW()),
(18, 'IT3201', 'IT Project Management', 3, 'Dr. Chaminda Bandara', 'Information Technology', 'Semester 6', 'Monday 14:00-16:00', 'Active', 'BSc (Hons) in Information Technology', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- BSc (Hons) in Data Science - Courses
INSERT INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, program_name, created_at, updated_at) VALUES
(19, 'DS1101', 'Introduction to Data Science', 3, 'Dr. Chaminda Bandara', 'Computer Science', 'Semester 1', 'Monday 08:00-10:00', 'Active', 'BSc (Hons) in Data Science', NOW(), NOW()),
(20, 'DS1201', 'Statistics for Data Science', 3, 'Prof. Prasad Wimalaratne', 'Computer Science', 'Semester 2', 'Tuesday 10:00-12:00', 'Active', 'BSc (Hons) in Data Science', NOW(), NOW()),
(21, 'DS2101', 'Machine Learning', 3, 'Prof. Prasad Wimalaratne', 'Computer Science', 'Semester 3', 'Wednesday 08:00-10:00', 'Active', 'BSc (Hons) in Data Science', NOW(), NOW()),
(22, 'DS2201', 'Data Mining and Warehousing', 3, 'Dr. Chaminda Bandara', 'Computer Science', 'Semester 4', 'Thursday 10:00-12:00', 'Active', 'BSc (Hons) in Data Science', NOW(), NOW()),
(23, 'DS3101', 'Artificial Intelligence', 3, 'Prof. Prasad Wimalaratne', 'Computer Science', 'Semester 5', 'Thursday 08:00-10:00', 'Active', 'BSc (Hons) in Data Science', NOW(), NOW()),
(24, 'DS3201', 'Big Data Analytics', 3, 'Dr. Harsha Kumara', 'Computer Science', 'Semester 6', 'Friday 10:00-12:00', 'Active', 'BSc (Hons) in Data Science', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- BSc (Hons) in Information Systems - Courses
INSERT INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, program_name, created_at, updated_at) VALUES
(33, 'IS1101', 'Introduction to Information Systems', 3, 'Dr. Sunil Rathnayake', 'Information Systems', 'Semester 1', 'Monday 10:00-12:00', 'Active', 'BSc (Hons) in Information Systems', NOW(), NOW()),
(34, 'IS1201', 'Systems Analysis and Design', 3, 'Dr. Kamal Silva', 'Information Systems', 'Semester 2', 'Tuesday 08:00-10:00', 'Active', 'BSc (Hons) in Information Systems', NOW(), NOW()),
(35, 'IS2101', 'Database Systems', 3, 'Dr. Chaminda Bandara', 'Information Systems', 'Semester 3', 'Wednesday 10:00-12:00', 'Active', 'BSc (Hons) in Information Systems', NOW(), NOW()),
(36, 'IS2201', 'Enterprise Resource Planning', 3, 'Dr. Sunil Rathnayake', 'Information Systems', 'Semester 4', 'Thursday 08:00-10:00', 'Active', 'BSc (Hons) in Information Systems', NOW(), NOW()),
(37, 'IS3101', 'Business Intelligence', 3, 'Prof. Prasad Wimalaratne', 'Information Systems', 'Semester 5', 'Friday 10:00-12:00', 'Active', 'BSc (Hons) in Information Systems', NOW(), NOW()),
(38, 'IS3201', 'IT Governance and Compliance', 3, 'Prof. Dilani Wickramasinghe', 'Information Systems', 'Semester 6', 'Monday 14:00-16:00', 'Active', 'BSc (Hons) in Information Systems', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- MSc in Computer Science - Courses
INSERT INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, program_name, created_at, updated_at) VALUES
(25, 'MCS101', 'Advanced Algorithms', 3, 'Prof. Prasad Wimalaratne', 'Computer Science', 'Semester 1', 'Monday 18:00-20:00', 'Active', 'MSc in Computer Science', NOW(), NOW()),
(26, 'MCS102', 'Advanced Database Systems', 3, 'Dr. Chaminda Bandara', 'Computer Science', 'Semester 1', 'Wednesday 18:00-20:00', 'Active', 'MSc in Computer Science', NOW(), NOW()),
(27, 'MCS201', 'Research Methodology', 3, 'Prof. Prasad Wimalaratne', 'Computer Science', 'Semester 2', 'Tuesday 18:00-20:00', 'Active', 'MSc in Computer Science', NOW(), NOW()),
(28, 'MCS202', 'Machine Learning and AI', 3, 'Prof. Prasad Wimalaratne', 'Computer Science', 'Semester 2', 'Thursday 18:00-20:00', 'Active', 'MSc in Computer Science', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- MSc in Information Technology - Courses
INSERT INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, program_name, created_at, updated_at) VALUES
(29, 'MIT101', 'Enterprise Architecture', 3, 'Prof. Dilani Wickramasinghe', 'Information Technology', 'Semester 1', 'Monday 18:00-20:00', 'Active', 'MSc in Information Technology', NOW(), NOW()),
(30, 'MIT102', 'Cloud and DevOps', 3, 'Dr. Harsha Kumara', 'Information Technology', 'Semester 1', 'Wednesday 18:00-20:00', 'Active', 'MSc in Information Technology', NOW(), NOW()),
(31, 'MIT201', 'IT Governance and Strategy', 3, 'Prof. Dilani Wickramasinghe', 'Information Technology', 'Semester 2', 'Tuesday 18:00-20:00', 'Active', 'MSc in Information Technology', NOW(), NOW()),
(32, 'MIT202', 'Advanced Web Technologies', 3, 'Dr. Sunil Rathnayake', 'Information Technology', 'Semester 2', 'Thursday 18:00-20:00', 'Active', 'MSc in Information Technology', NOW(), NOW())
ON CONFLICT (id) DO NOTHING;

-- =================== Admin User ===================
INSERT INTO admins (id, username, password, full_name, phone, department, email) VALUES
(1, 'Binath', '$2a$10$4/xKGlp7gQQxBnXWzgE3aeAba.qydDoY2mbbojdUxk/2/fjMvMfsi', 'Binath Lakvidu', '+94 71 5313357', 'CS Department', 'lakvidusb@gmail.com')
ON CONFLICT (id) DO NOTHING;


-- =================== Reset Auto-Increment Sequences ===================
SELECT setval('program_id_seq', (SELECT COALESCE(MAX(id), 1) FROM program));
SELECT setval('courses_id_seq', (SELECT COALESCE(MAX(id), 1) FROM courses));
SELECT setval('admins_id_seq', (SELECT COALESCE(MAX(id), 1) FROM admins));
