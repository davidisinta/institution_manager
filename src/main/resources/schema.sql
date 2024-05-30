CREATE TABLE Institution (
                              institution_id INT PRIMARY KEY AUTO_INCREMENT,
                              name VARCHAR(255) NOT NULL,
                              president VARCHAR(255),
                              staffCount INT,
                              studentCount INT
);

CREATE TABLE Course (
                        courseId INT PRIMARY KEY AUTO_INCREMENT,
                        courseName VARCHAR(255) NOT NULL
);

CREATE TABLE Student (
                         studentId INT PRIMARY KEY AUTO_INCREMENT,
                         studentName VARCHAR(255) NOT NULL,
                         institution_id INT,
                         FOREIGN KEY (institution_id) REFERENCES Institution(institution_id)
);

CREATE TABLE InstitutionCourse (
                                   institution_id INT,
                                   courseId INT,
                                   PRIMARY KEY (institution_id, courseId),
                                   FOREIGN KEY (institution_id) REFERENCES Institution(institution_id),
                                   FOREIGN KEY (courseId) REFERENCES Course(courseId)
);

CREATE TABLE Enrollment (
                            studentId INT,
                            courseId INT,
                            PRIMARY KEY (studentId, courseId),
                            FOREIGN KEY (studentId) REFERENCES Student(studentId),
                            FOREIGN KEY (courseId) REFERENCES Course(courseId)
);
