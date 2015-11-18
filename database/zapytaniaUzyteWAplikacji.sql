# student -> lista zajęc do zapisania sie

SELECT concat(Users.firstName, ' ', Users.lastName) AS teacherName,Subjects.Name AS subjectName, Departments.Name AS departmentName, Courses.type, Courses.coursesQuantity, Subjects.description
FROM Subjects
	JOIN Departments
		ON Subjects.departmentID=Departments.ID
	JOIN Courses
		ON Courses.subjectID=Subjects.ID
	JOIN Users_Subjects
		ON Users_Subjects.courseID=Courses.ID
	JOIN Users
		ON Users_Subjects.teacherID=Users.ID

