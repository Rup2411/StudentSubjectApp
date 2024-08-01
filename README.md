# StudentSubjectApp
Student Subject Management System

This project is a Spring Boot application that provides APIs for managing student enrollment in subjects. The project includes authentication with JWT and uses an H2 in-memory database by default. However, it also supports MySQL if preferred.

Table of Contents
 * Prerequisites
 * Setup Instructions
    - H2 Database Configuration
    - MySQL Database Configuration
 * API Endpoints
 * Running the Application

Prerequisites
 * Java 17 or later
 * Maven 3.6.3 or later
 * IDE of your choice (IntelliJ IDEA, Eclipse, etc.)
 * MySQL (optional, if using MySQL instead of H2)
 * Setup Instructions
 * H2 Database Configuration
By default, the application is configured to use an H2 in-memory database, which requires no additional setup. The H2 console can be accessed at /h2-console after running the application.

MySQL Database Configuration
 - If you prefer to use MySQL, follow these steps:

 1 Uncomment the MySQL Configuration: In the application.properties file, uncomment the following lines:
  + spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  + spring.datasource.url=jdbc:mysql://localhost:3306/student_subject
  + spring.datasource.username=root(your username)
  + spring.datasource.password=password123(your password)
  + spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

 2 Comment the H2 Configuration: Comment out the H2 database settings:
  + #spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
  + #spring.datasource.driverClassName=org.h2.Driver
  + #spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
    
 3 Create the Database Schema: Ensure that a MySQL database schema named student_subject is created before running the application.
  + CREATE SCHEMA student_subject;

API Endpoints
 * Authentication
    + Register User: POST /api/auth/register
       - Registers a new user.
      
    + Login: POST /api/auth/login
       - Authenticates a user and returns a JWT token.
         
  * Subject Management
    + Create Subject: POST /api/subject/add
        - Creates a new subject. Requires JWT token.
      
    + Enroll in Subjects: POST /api/subject/enroll
      -  Enrolls a student in one or more subjects. Requires JWT token.
      
    + Get Subject by Code: GET /api/subject/{subjectCode}
      -  Retrieves a subject by its code.
      
    + Get All Students Enrolled in a Subject: GET /api/subject/user/{subjectCode}
      -  Lists all students enrolled in a specific subject. Requires JWT token.
      
 * User Management
    + Get All Student Users: GET /api/user/all
      -  Retrieves all student users. Requires JWT token.
      
    + Get User by Email: GET /api/user/{email}
      -  Retrieves a user by their email.
      
    + Get All Subjects Enrolled by User: GET /api/user/subject/{email}
      -  Lists all subjects a student is enrolled in. Requires JWT token.
     
* Running the Application
    1. Clone the repository to your local machine.
    2. Navigate to the project directory.
    3. Run the following Maven command to build and start the application:
      - mvn spring-boot:run
    4. The application will start on http://localhost:8080.

 
   * Using an IDE:
     1. Import the project as a Maven project into your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
     2. Build the project using Maven within the IDE.
     3. Run the application by executing the main method in the StudentSubjectApplication class.
     4. The application will start on http://localhost:8080
