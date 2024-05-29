CREATE TABLE Institution (
                              institution_id INT PRIMARY KEY AUTO_INCREMENT,
                              name VARCHAR(255) NOT NULL,
                              president VARCHAR(255),
                              staffCount INT,
                              studentCount INT
);

CREATE TABLE Course (
                        course_id INT PRIMARY KEY AUTO_INCREMENT,
                        course_name VARCHAR(255) NOT NULL
);

CREATE TABLE Student (
                         student_id INT PRIMARY KEY AUTO_INCREMENT,
                         student_name VARCHAR(255) NOT NULL,
                         institution_id INT,
                         FOREIGN KEY (institution_id) REFERENCES Institution(institution_id)
);

CREATE TABLE InstitutionCourse (
                                   institution_id INT,
                                   course_id INT,
                                   PRIMARY KEY (institution_id, course_id),
                                   FOREIGN KEY (institution_id) REFERENCES Institution(institution_id),
                                   FOREIGN KEY (course_id) REFERENCES Course(course_id)
);

CREATE TABLE Enrollment (
                            enrollment_id INT PRIMARY KEY AUTO_INCREMENT,
                            student_id INT,
                            course_id INT,
                            FOREIGN KEY (student_id) REFERENCES Student(student_id),
                            FOREIGN KEY (course_id) REFERENCES Course(course_id)
);
