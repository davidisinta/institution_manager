# Institution, Course, and Student Management API

This project provides APIs for managing institutions, courses, and students. It includes functionalities for adding, listing, filtering, sorting, editing, and deleting institutions, courses, and students. Additionally, a simple UI or a POSTMAN collection is included for testing the APIs.

## Features

### Institution
- [ ] Add a new institution
  - If an institution with the same name exists, don’t save the new institution. Instead, inform the user that there’s another institution with the same name.
- [ ] List all institutions
- [ ] Filter (search) the list of institutions
- [ ] Sort the list of institutions by name (ascending and descending) by clicking the NAME header of the table
- [ ] Delete an institution
  - Only if the institution is not assigned a course, otherwise show a relevant error message to the user.
- [ ] Edit the name of an institution
  - You can’t change the institution name to that of an existing institution. In such a case, show the relevant error.

### Course
- [ ] List all courses done by an institution
  - This should be a list of courses per institution (use a drop-down to achieve this).
- [ ] Filter the courses by searching
- [ ] Change the institution and have the list of courses change to reflect this
- [ ] Sort the courses by name in ascending and descending order by clicking on the course name table header
- [ ] Delete a course
  - You shouldn’t be able to delete a course that’s been assigned to at least one student. Show relevant errors in such a case.
- [ ] Add a course to an institution
  - You shouldn’t be able to add two courses with the same name to the same institution. In such a case, show the relevant error to the user.
- [ ] Edit the name of a course
  - The above condition applies as well.

### Student
- [ ] Add a student and assign them a course
- [ ] Delete a student
- [ ] Edit the name of a student
- [ ] Change the course the student is doing within the same institution
- [ ] Transfer the student to another institution and assign them a course
- [ ] List all students in each institution
  - Be able to search and filter the list of students by course.
- [ ] When listing students, show 10 students at a time

## Additional Features (Optional)
- Add any additional features that could make the application more useful to the administrator.

## Testing the APIs
You can implement a simple UI to test your APIs or use the attached POSTMAN collection.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/institution-course-student-management.git
