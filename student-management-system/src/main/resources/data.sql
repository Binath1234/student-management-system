-- =================== Sample Programs Data ===================
MERGE INTO program (id, program_code, program_name, degree_type, department, duration, credits, enrolled, coordinator, status, accreditation) KEY(id) VALUES
(1, 'CS001', 'Bachelor of Science in Computer Science', 'Bachelor', 'Computing', '4 Years', 120, 0, 'Dr. John Smith', 'Active', 'Accredited'),
(2, 'IT001', 'Bachelor of Science in Information Technology', 'Bachelor', 'Computing', '4 Years', 120, 0, 'Dr. Jane Doe', 'Active', 'Accredited'),
(3, 'BIS001', 'Bachelor of Science in Business Information Systems', 'Bachelor', 'Business', '4 Years', 120, 0, 'Prof. Sarah Lee', 'Active', 'Accredited'),
(4, 'ENG001', 'Bachelor of Science in Software Engineering', 'Bachelor', 'Engineering', '4 Years', 120, 0, 'Dr. Robert Brown', 'Active', 'Accredited'),
(5, 'DIS001', 'Diploma in Information Technology', 'Diploma', 'Computing', '2 Years', 60, 0, 'Dr. Emily Wilson', 'Active', 'Accredited'),
(6, 'WEB001', 'Diploma in Web Development', 'Diploma', 'Computing', '2 Years', 60, 0, 'Dr. Michael Johnson', 'Active', 'Accredited'),
(7, 'BUS001', 'Bachelor of Business Administration', 'Bachelor', 'Business', '4 Years', 120, 0, 'Prof. David Clark', 'Active', 'Accredited'),
(8, 'ACC001', 'Bachelor of Commerce in Accounting', 'Bachelor', 'Business', '4 Years', 120, 0, 'Prof. Lisa Anderson', 'Active', 'Accredited'),
(9, 'MAS001', 'Master of Science in Computer Science', 'Master', 'Computing', '2 Years', 60, 0, 'Prof. James Taylor', 'Active', 'Accredited'),
(10, 'MBA001', 'Master of Business Administration', 'Master', 'Business', '2 Years', 60, 0, 'Prof. Maria Martinez', 'Active', 'Accredited');

-- =================== Sample Courses Data ===================
MERGE INTO courses (id, course_code, course_name, credits, instructor, department, semester, schedule, status, created_at, updated_at) KEY(id) VALUES
(1, 'CS101', 'Introduction to Programming', 3, 'Dr. John Smith', 'Computing', 'Semester 1', 'Monday 10:00-12:00', 'Active', NOW(), NOW()),
(2, 'CS102', 'Data Structures', 3, 'Dr. Jane Doe', 'Computing', 'Semester 2', 'Tuesday 14:00-16:00', 'Active', NOW(), NOW()),
(3, 'CS201', 'Database Management Systems', 3, 'Dr. Robert Brown', 'Computing', 'Semester 3', 'Wednesday 10:00-12:00', 'Active', NOW(), NOW()),
(4, 'CS202', 'Web Development', 3, 'Dr. Emily Wilson', 'Computing', 'Semester 4', 'Thursday 14:00-16:00', 'Active', NOW(), NOW()),
(5, 'CS301', 'Software Engineering', 3, 'Dr. Michael Johnson', 'Computing', 'Semester 5', 'Friday 10:00-12:00', 'Active', NOW(), NOW()),
(6, 'BUS101', 'Business Fundamentals', 3, 'Prof. Sarah Lee', 'Business', 'Semester 1', 'Monday 14:00-16:00', 'Active', NOW(), NOW()),
(7, 'BUS102', 'Business Mathematics', 3, 'Prof. David Clark', 'Business', 'Semester 2', 'Tuesday 10:00-12:00', 'Active', NOW(), NOW()),
(8, 'ENG101', 'Technical Writing', 2, 'Prof. Lisa Anderson', 'General', 'Semester 1', 'Wednesday 14:00-15:30', 'Active', NOW(), NOW()),
(9, 'ENG102', 'Communication Skills', 2, 'Prof. James Taylor', 'General', 'Semester 2', 'Friday 14:00-15:30', 'Active', NOW(), NOW()),
(10, 'MATH101', 'Calculus I', 4, 'Prof. Maria Martinez', 'Mathematics', 'Semester 1', 'Monday 10:00-12:00', 'Active', NOW(), NOW());

-- =================== Admin User ===================
MERGE INTO admins (id, username, password, full_name, phone, department, email) KEY(id) VALUES
(1, 'admin', 'password', 'Admin User', '+1 (555) 123-4567', 'IT Department', 'admin@university.edu');

-- =================== Reset Auto-Increment Sequences ===================
ALTER TABLE program ALTER COLUMN id RESTART WITH 11;
ALTER TABLE courses ALTER COLUMN id RESTART WITH 11;
ALTER TABLE admins ALTER COLUMN id RESTART WITH 2;
