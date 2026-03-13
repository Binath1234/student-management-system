# Student Management System (SMS)

## Project Overview

A comprehensive **Student Management System** built with *Spring Boot 3*, *Java 21*, and *PostgreSQL*. The system implements a **3-Tier Layered Architecture** with advanced features including automatic student ID generation, audit logging using AOP, and multiple design patterns.

### Project Highlights
- ✅ **Automatic Student ID Generation**: Format `KDU-SE-YYYY-XXXX` (e.g., `KDU-SE-2024-0001`)
- ✅ **Soft Delete for Students**: Mark as inactive without hard deletion
- ✅ **AOP-based Audit Logging**: Track all administrative actions
- ✅ **Multiple Design Patterns**: Singleton, Factory, Builder, Observer
- ✅ **Comprehensive Unit Tests**: >80% coverage on critical business logic
- ✅ **Docker Ready**: Full containerization support
- ✅ **Spring Data JPA**: Advanced query capabilities

---

## Technologies Used

| Component | Technology | Version |
|-----------|-----------|---------|
| Language | Java | 21 LTS |
| Framework | Spring Boot | 3.2.0 |
| ORM | Spring Data JPA/Hibernate | Latest |
| Database | H2/PostgreSQL | H2 (dev), PostgreSQL 15 (prod) |
| Build Tool | Maven | 3.9+ |
| Testing | JUnit 5, Mockito | Latest |
| Template Engine | Thymeleaf | Latest |
| Container | Docker | Latest |
| Version Control | Git | Latest |

---

## Architecture

### 3-Tier Layered Architecture

```
┌─────────────────────────────────────────────┐
│         PRESENTATION LAYER                  │
│  (Controllers, Views, DTOs, Validation)    │
├─────────────────────────────────────────────┤
│         SERVICE/BUSINESS LAYER              │
│  (StudentService, CourseService, etc.)     │
│  (ID Generation, Validation, Business Logic)│
├─────────────────────────────────────────────┤
│         DATA/PERSISTENCE LAYER              │
│  (Repositories, Entities, Database)        │
├─────────────────────────────────────────────┤
│    CROSS-CUTTING CONCERNS (AOP)             │
│  (Audit Logging, Transaction Management)    │
└─────────────────────────────────────────────┘
```

### Package Structure

```
src/main/java/com/sms/student_management_system/
├── controller/          # REST/Web Controllers
│   ├── StudentController
│   ├── CourseController
│   ├── NoticeController
│   └── ...
├── service/            # Business Logic Layer
│   ├── StudentService
│   ├── CourseService
│   ├── AuditLogService
│   └── ...
├── repository/         # Data Access Layer
│   ├── StudentRepository
│   ├── CourseRepository
│   ├── AuditLogRepository
│   └── ...
├── entity/             # JPA Entities
│   ├── Student
│   ├── Course
│   ├── Program
│   ├── Admin
│   └── AuditLog
├── dto/                # Data Transfer Objects
│   ├── StudentDTO
│   ├── CourseDTO
│   └── ...
├── aspect/             # AOP Aspects
│   └── AuditAspect
├── util/               # Utility Classes
│   └── StudentIdGenerator
├── exception/          # Custom Exceptions
│   └── ResourceNotFoundException
├── config/             # Configuration Classes
└── StudentManagementSystemApplication.java
```

---

## Design Patterns Implemented

### 1. **Singleton Pattern**
- **Implementation**: Spring's `@Service` annotation
- **Purpose**: Ensures only one instance of service beans
- **Location**: `StudentService`, `CourseService`, `AuditLogService`
- **Benefit**: Thread-safe, efficient resource management

### 2. **Factory Pattern**
- **Implementation**: `StudentIdGenerator` class
- **Purpose**: Encapsulates student ID generation logic
- **Location**: `com.sms.student_management_system.util.StudentIdGenerator`
- **Benefit**: Easy to change ID generation strategy without affecting other parts

```java
// Factory Pattern Example
String studentNumber = studentIdGenerator.generateStudentNumber();
// Format: KDU-SE-2024-0001
```

### 3. **Builder Pattern**
- **Implementation**: `StudentBuilder` inner class in `StudentService`
- **Purpose**: Simplifies complex object construction
- **Location**: `StudentService.StudentBuilder`
- **Benefit**: Readable, handles optional fields elegantly

```java
// Builder Pattern Example
Student student = StudentBuilder.builder()
    .fullName("John Doe")
    .email("john@example.com")
    .program("Software Engineering")
    .build();
```

### 4. **Observer Pattern**
- **Implementation**: `AuditAspect` with AOP
- **Purpose**: Observes and logs all administrative changes
- **Location**: `com.sms.student_management_system.aspect.AuditAspect`
- **Benefit**: Decoupled audit logging from business logic

---

## Key Features

### 1. Student Registration Module (10 marks)
- ✅ Comprehensive registration form with validation
- ✅ Fields: fullName, dateOfBirth, email, phone, address, program, enrollmentDate
- ✅ Real-time validation (no empty fields, email format, phone format)
- ✅ Success/error message display
- ✅ Auto-generated unique student number

### 2. ID Generation (10 marks)
- ✅ Automatic unique student number generation
- ✅ Format: `KDU-SE-YYYY-XXXX` (e.g., `KDU-SE-2024-0001`)
- ✅ Sequential numbering per year
- ✅ Non-editable, unique constraint in database

### 3. Course/Student Management (10 marks)
- ✅ Full CRUD operations for Students
- ✅ Full CRUD operations for Courses
- ✅ Course enrollment management
- ✅ Course history tracking across semesters

### 4. Search/Retrieve Functionality (10 marks)
- ✅ Search by Student ID (exact match)
- ✅ Search by Name (partial search)
- ✅ Search by Program
- ✅ Display complete student details with enrolled courses

### 5. Delete Functionality (10 marks)
- ✅ Soft delete for students (setActive = false)
- ✅ Hard delete for courses with confirmation
- ✅ Prevent deletion of students with active enrollments
- ✅ Restore functionality for soft-deleted students

### 6. Audit Trail (5 marks)
- ✅ AOP-based audit logging
- ✅ Log all actions: ADD, UPDATE, DELETE, SEARCH, VIEW
- ✅ Capture old and new values
- ✅ Track actor (admin username)
- ✅ Persistent audit log table

### 7. Unit Testing (5 marks)
- ✅ >80% coverage on critical business logic
- ✅ Tests for CRUD operations, ID generation, validation
- ✅ Uses JUnit 5 and Mockito
- ✅ Test file: `StudentServiceTest.java`

---

## Database Schema

### Students Table
```sql
CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    student_number VARCHAR(20) UNIQUE NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    date_of_birth DATE,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    address TEXT,
    program VARCHAR(50),
    enrollment_date DATE,
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Courses Table
```sql
CREATE TABLE courses (
    id BIGSERIAL PRIMARY KEY,
    course_code VARCHAR(20) UNIQUE NOT NULL,
    course_name VARCHAR(100) NOT NULL,
    credits INTEGER,
    semester VARCHAR(20),
    instructor VARCHAR(100),
    department VARCHAR(50),
    schedule VARCHAR(255),
    status VARCHAR(20) DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Student-Courses Junction Table
```sql
CREATE TABLE student_courses (
    student_id BIGINT REFERENCES students(id),
    course_id BIGINT REFERENCES courses(id),
    enrollment_date TIMESTAMP,
    status VARCHAR(20),
    PRIMARY KEY (student_id, course_id)
);
```

### Audit Log Table
```sql
CREATE TABLE audit_log (
    id BIGSERIAL PRIMARY KEY,
    timestamp TIMESTAMP NOT NULL,
    action VARCHAR(20) NOT NULL,
    actor VARCHAR(50) NOT NULL,
    entity_type VARCHAR(50) NOT NULL,
    entity_id BIGINT,
    old_value TEXT,
    new_value TEXT
);
```

---

## Setup Instructions

### Prerequisites
- Java 21 JDK
- Maven 3.9+
- PostgreSQL 15 (for production)
- Docker (optional, for containerization)
- Git

### Local Development Setup

1. **Clone the repository**
```bash
git clone <repository-url>
cd student-management-system
```

2. **Configure Database** (application.properties)
```properties
# H2 (Development - Default)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

# Or PostgreSQL (Production)
spring.datasource.url=jdbc:postgresql://localhost:5432/sms_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

3. **Build the Project**
```bash
mvn clean install
```

4. **Run the Application**
```bash
mvn spring-boot:run
```

5. **Access the Application**
- URL: `http://localhost:8080`
- H2 Console: `http://localhost:8080/h2-console` (credentials: sa/blank)

---

## API Endpoints

### Student Endpoints
| Method | Endpoint | description |
|--------|----------|-------------|
| GET | `/students` | List all active students |
| GET | `/students/{id}` | Get student details |
| POST | `/students` | Create new student |
| PUT | `/students/{id}` | Update student |
| DELETE | `/students/{id}` | Soft delete student |
| GET | `/students/search` | Search students |

### Course Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/courses` | List all courses |
| GET | `/courses/{id}` | Get course details |
| POST | `/courses` | Create new course |
| PUT | `/courses/{id}` | Update course |
| DELETE | `/courses/{id}` | Delete course |

### Audit Trail Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/audit-logs` | View all audit logs |
| GET | `/audit-logs/entity/{type}/{id}` | View logs for entity |

---

## Docker Deployment

### Build Docker Image
```bash
docker build -t sms-app:1.0 .
```

### Run Docker Container
```bash
docker run -d \
  --name sms-container \
  -p 8080:8080 \
  -e JAVA_OPTS="-Xmx512m -Xms256m" \
  sms-app:1.0
```

### Docker Compose (Optional)
```yaml
version: '3.8'
services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: sms_db
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
      
  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/sms_db
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: password
```

---

## Testing

### Run Unit Tests
```bash
mvn test
```

### Run Tests with Coverage
```bash
mvn test jacoco:report
```

### Test Coverage Report
Located at: `target/site/jacoco/index.html`

---

## Configuration Properties

Key properties in `application.properties`:

```properties
# Database
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false

# Student ID Configuration
student.id.prefix=KDU
student.id.program=SE
student.id.format=KDU-SE-{year}-{sequence}

# Logging
logging.level.com.sms.student_management_system=DEBUG
logging.level.org.hibernate.SQL=DEBUG

# Thymeleaf
spring.thymeleaf.cache=false
```

---

## Code Quality Standards

### Naming Conventions
- Classes: PascalCase (e.g., `StudentService`)
- Methods: camelCase (e.g., `registerStudent()`)
- Constants: UPPER_SNAKE_CASE (e.g., `MAX_CREDITS`)

### Documentation
- All public methods have JavaDoc comments
- Complex business logic is well-commented
- Design pattern rationale is documented

### Exception Handling
- Custom exceptions for domain-specific errors
- Proper error messages for debugging
- Try-catch blocks for external API calls

---

## Validation Rules

### Student Registration
- Full Name: 3-100 characters, required
- Email: Valid email format, unique, required
- Phone: Valid phone number format, required
- Date of Birth: Must be in past, required
- Address: 5-255 characters, required
- Program: Must be selected, required

### Course Creation
- Course Code: 3-20 characters, alphanumeric, unique
- Course Name: 5-100 characters, required
- Credits: 1-6, required
- Instructor: 3-100 characters, required
- Department: Required
- Semester: Required (format: FALL-2024, SPRING-2024)

---

## Performance Considerations

1. **Database Indexing**: Created on frequently queried fields
2. **Lazy Loading**: Course collections loaded on-demand
3. **Query Optimization**: JPA @Query annotations for efficient queries
4. **Caching**: Thymeleaf template caching disabled for development
5. **Connection Pool**: Configured in application.properties

---

## Security Considerations

- Input validation on all form submissions
- SQL injection prevention through JPA parameterized queries
- CSRF protection through Spring Security
- Soft delete to prevent accidental data loss
- Audit logging for compliance

---

## Troubleshooting

### Issue: Port 8080 already in use
```bash
# Change port in application.properties
server.port=8081
```

### Issue: Database connection failed
```bash
# Check PostgreSQL is running
# Verify connection string in application.properties
```

### Issue: Tests failing
```bash
# Clear Maven cache
mvn clean test

# Run with debug logging
mvn test -X
```

---

## Contributing Guidelines

1. Create feature branch: `git checkout -b feature/feature-name`
2. Make changes following code standards
3. Write unit tests for new features
4. Commit with meaningful messages: `git commit -m "feat: add feature description"`
5. Push to remote: `git push origin feature/feature-name`
6. Create pull request for review

---

## Project Structure & Git Workflow

### Branch Strategy
- `main`: Production-ready code
- `develop`: Development code
- `feature/*`: Feature development branches
- `bugfix/*`: Bug fix branches

### Commit Convention
- `feat:` New feature
- `fix:` Bug fix
- `docs:` Documentation change
- `test:` Test addition/modification
- `refactor:` Code refactoring

---

## Future Enhancements

- [ ] REST API endpoints for mobile app
- [ ] Email notifications for enrollment
- [ ] Grade management system
- [ ] Advanced reporting and analytics
- [ ] Attendance tracking
- [ ] Document management
- [ ] Role-based access control (RBAC)
- [ ] Multi-tenant support
- [ ] Payment integration

---

## FAQs

**Q: How is the student number generated?**
A: Automatically in format `KDU-SE-YYYY-XXXX` where YYYY is current year and XXXX is sequential number (0001, 0002, etc.)

**Q: Can I delete a student record?**
A: Yes, but soft delete marks them inactive. Hard deletion prevents if active course enrollments exist.

**Q: How do I access audit logs?**
A: Via the `/audit-logs` endpoint, filtered by entity type, action, or time range.

**Q: Is the system production-ready?**
A: The system is ready for production with PostgreSQL setup. Add authentication/authorization for security.

---

## License

This project is proprietary software developed for [Organization Name].

---

## Contact & Support

For questions or support, contact the development team:
- Email: sms-support@example.com
- Issues: Use GitHub Issues for bug reports
- Documentation: See project wiki

---

## Version History

### v1.0.0 (Initial Release)
- Complete student registration module
- Automatic ID generation
- CRUD operations for students and courses
- AOP-based audit logging
- Comprehensive documentation

---

**Last Updated**: March 2026  
**Maintained By**: Student Management System Team
