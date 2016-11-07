USE data;

SET foreign_key_checks = 0;

INSERT INTO `data`.`User` (`fieldID`, `login`, `firstName`, `lastName`, `type`, `password`, `yearOfStudy`, `group`, `email`, `Status`)
   VALUES (1, 'mateuszek', 'Mateusz', 'Wieczorek', 'Student', '92d7ddd2a010c59511dc2905b7e14f64', 1, 'I_2013', 'mati@edu.p.lodz.pl', 'ACTIVE');
INSERT INTO `data`.`User` (`login`, `firstName`, `lastName`, `type`, `password`, `email`, `Status`)
   VALUES ('grzegorzmaslowski', 'Grzegorz', 'Maslowski', 'Teacher', '92d7ddd2a010c59511dc2905b7e14f64', 'grzegorz@edu.p.lodz.pl', 'ACTIVE');
INSERT INTO `data`.`User` (`login`, `firstName`, `lastName`, `type`, `password`, `email`, `Status`)
   VALUES ('igordudek', 'Igor', 'Dudek', 'Admin', '92d7ddd2a010c59511dc2905b7e14f64', 'igor@edu.p.lodz.pl', 'ACTIVE');

INSERT INTO `data`.`TeacherCourse` (`subjectID`, `teacherID`, `studentGroup`, `description`) VALUES ('1', '2', 'I_2013', 'Nowy kurs 1');
INSERT INTO `data`.`TeacherCourse` (`subjectID`, `teacherID`, `studentGroup`, `description`) VALUES ('2', '2', 'I_2013', 'Jakis kurs 2');
INSERT INTO `data`.`TeacherCourse` (`subjectID`, `teacherID`, `studentGroup`, `description`) VALUES ('3', '2', 'I_2013', 'Kursik 3');

INSERT INTO `data`.`StudentCourse` (`teacherCourseID`, `studentID`) VALUES ('1', '1');
INSERT INTO `data`.`StudentCourse` (`teacherCourseID`, `studentID`) VALUES ('2', '1');
INSERT INTO `data`.`StudentCourse` (`teacherCourseID`, `studentID`) VALUES ('3', '1');

INSERT INTO `data`.`Subject` (`Name`, `fieldID`, `yearOfStudy`, `quantity`, `minQuantity`, `description`)
   VALUES ('Analiza matematyczna', 1, 1, 30, 28, 'Zajecia z analizy matematycznej, obowiazkowej dla kazdego kierunku inxynierskiego');
INSERT INTO `data`.`Subject` (`Name`, `fieldID`, `yearOfStudy`, `quantity`, `minQuantity`, `description`)
   VALUES ('Podstawy programowania', 2, 1, 30, 28, 'Podstawy jezyka C');
INSERT INTO `data`.`Subject` (`Name`, `fieldID`, `yearOfStudy`, `quantity`, `minQuantity`, `description`)
   VALUES ('Zaawansowane programowanie obiektowe', 2, 1, 30, 28, 'Obiektowosc, watki, kolekcje w jezyku JAVA');

INSERT INTO `data`.`CourseDate` (`teacherCourseID`, `startTime`, `finishTime`, `date`) VALUES ('1', '08:15:00', '09:45:00', '2015:01:19');
INSERT INTO `data`.`CourseDate` (`teacherCourseID`, `startTime`, `finishTime`, `date`) VALUES ('1', '08:15:00', '09:45:00', '2015:01:30');
INSERT INTO `data`.`CourseDate` (`teacherCourseID`, `startTime`, `finishTime`, `date`) VALUES ('1', '08:15:00', '09:45:00', '2015:02:07');
INSERT INTO `data`.`CourseDate` (`teacherCourseID`, `startTime`, `finishTime`, `date`) VALUES ('1', '08:15:00', '09:45:00', '2015:02:18');
INSERT INTO `data`.`CourseDate` (`teacherCourseID`, `startTime`, `finishTime`, `date`) VALUES ('1', '08:15:00', '09:45:00', '2015:02:27');
INSERT INTO `data`.`CourseDate` (`teacherCourseID`, `startTime`, `finishTime`, `date`) VALUES ('1', '08:15:00', '09:45:00', '2015:03:03');
INSERT INTO `data`.`CourseDate` (`teacherCourseID`, `startTime`, `finishTime`, `date`) VALUES ('2', '08:15:00', '09:45:00', '2015:03:10');
INSERT INTO `data`.`CourseDate` (`teacherCourseID`, `startTime`, `finishTime`, `date`) VALUES ('2', '08:15:00', '09:45:00', '2015:03:17');
INSERT INTO `data`.`CourseDate` (`teacherCourseID`, `startTime`, `finishTime`, `date`) VALUES ('2', '08:15:00', '09:45:00', '2015:03:24');
INSERT INTO `data`.`CourseDate` (`teacherCourseID`, `startTime`, `finishTime`, `date`) VALUES ('2', '08:15:00', '09:45:00', '2015:03:30');
INSERT INTO `data`.`CourseDate` (`teacherCourseID`, `startTime`, `finishTime`, `date`) VALUES ('2', '08:15:00', '09:45:00', '2015:04:08');
INSERT INTO `data`.`CourseDate` (`teacherCourseID`, `startTime`, `finishTime`, `date`) VALUES ('3', '08:15:00', '09:45:00', '2015:04:17');
INSERT INTO `data`.`CourseDate` (`teacherCourseID`, `startTime`, `finishTime`, `date`) VALUES ('3', '08:15:00', '09:45:00', '2015:04:28');
INSERT INTO `data`.`CourseDate` (`teacherCourseID`, `startTime`, `finishTime`, `date`) VALUES ('3', '08:15:00', '09:45:00', '2015:05:05');
INSERT INTO `data`.`CourseDate` (`teacherCourseID`, `startTime`, `finishTime`, `date`) VALUES ('3', '08:15:00', '09:45:00', '2015:05:17');
INSERT INTO `data`.`CourseDate` (`teacherCourseID`, `startTime`, `finishTime`, `date`) VALUES ('3', '08:15:00', '09:45:00', '2015:05:28');
INSERT INTO `data`.`CourseDate` (`teacherCourseID`, `startTime`, `finishTime`, `date`) VALUES ('3', '08:15:00', '09:45:00', '2015:09:02');

INSERT INTO `data`.`Field` (`name`) VALUES ('Informatyka');
INSERT INTO `data`.`Field` (`name`) VALUES ('Elektronika');
INSERT INTO `data`.`Field` (`name`) VALUES ('Grafika');
INSERT INTO `data`.`Field` (`name`) VALUES ('Matematyka stosowana');

INSERT INTO `data`.`Grade` (`subjectID`, `previousGradeID`, `studentID`, `value`, `isFinalGrade`) VALUES (1, 1, 1, 5, false);
INSERT INTO `data`.`Grade` (`subjectID`, `previousGradeID`, `studentID`, `value`, `isFinalGrade`) VALUES (1, 1, 1, 1, false);
INSERT INTO `data`.`Grade` (`subjectID`, `previousGradeID`, `studentID`, `value`, `isFinalGrade`) VALUES (2, 1, 1, 2, false);
INSERT INTO `data`.`Grade` (`subjectID`, `previousGradeID`, `studentID`, `value`, `isFinalGrade`) VALUES (2, 1, 1, 3, false);
INSERT INTO `data`.`Grade` (`subjectID`,  `studentID`, `value`, `isFinalGrade`) VALUES (2, 1, 5, false);
INSERT INTO `data`.`Grade` (`subjectID`,  `studentID`, `value`, `isFinalGrade`) VALUES (2, 1, 1, false);
INSERT INTO `data`.`Grade` (`subjectID`,  `studentID`, `value`, `isFinalGrade`) VALUES (1, 1, 4, false);
INSERT INTO `data`.`Grade` (`subjectID`,  `studentID`, `value`, `isFinalGrade`) VALUES (1, 1, 4, false);

INSERT INTO `data`.`StudentPrecense` (`courseDateID`, `studentID`, `status`) VALUES (1, 1, 'Obecny');
INSERT INTO `data`.`StudentPrecense` (`courseDateID`, `studentID`, `status`) VALUES (2, 1, 'Obecny');
INSERT INTO `data`.`StudentPrecense` (`courseDateID`, `studentID`, `status`) VALUES (3, 1, 'Obecny');
INSERT INTO `data`.`StudentPrecense` (`courseDateID`, `studentID`, `status`) VALUES (4, 1, 'Obecny');
INSERT INTO `data`.`StudentPrecense` (`courseDateID`, `studentID`, `status`) VALUES (5, 1, 'Nieobecny');
INSERT INTO `data`.`StudentPrecense` (`courseDateID`, `studentID`, `status`) VALUES (6, 1, 'Nieobecny');
INSERT INTO `data`.`StudentPrecense` (`courseDateID`, `studentID`, `status`) VALUES (7, 1, 'Nieobecny');
INSERT INTO `data`.`StudentPrecense` (`courseDateID`, `studentID`, `status`) VALUES (8, 1, 'Nieobecny');
INSERT INTO `data`.`StudentPrecense` (`courseDateID`, `studentID`, `status`) VALUES (9, 1, 'Spozniony');
INSERT INTO `data`.`StudentPrecense` (`courseDateID`, `studentID`, `status`) VALUES (10, 1, 'Spozniony');
INSERT INTO `data`.`StudentPrecense` (`courseDateID`, `studentID`, `status`) VALUES (11, 1, 'Spozniony');

INSERT INTO `data`.`AppProperty` (`name`, `value`) VALUES ('email.from.adress', 'dmcs.p.lodz.pl@gmail.com');
INSERT INTO `data`.`AppProperty` (`name`, `value`) VALUES ('email.from.username', 'dmcs.p.lodz.pl');
INSERT INTO `data`.`AppProperty` (`name`, `value`) VALUES ('email.from.password', 'dmcs1234');
INSERT INTO `data`.`AppProperty` (`name`, `value`) VALUES ('email.port', '587');
INSERT INTO `data`.`AppProperty` (`name`, `value`) VALUES ('email.host', 'smtp.gmail.com');
INSERT INTO `data`.`AppProperty` (`name`, `value`) VALUES ('email.smtp.auth', 'true');
INSERT INTO `data`.`AppProperty` (`name`, `value`) VALUES ('email.smtp.starttls.enable', 'true');
INSERT INTO `data`.`AppProperty` (`name`, `value`) VALUES ('sms.account_sid', 'AC3d8d5a6178f996ab92bdc4d2d5931e44');
INSERT INTO `data`.`AppProperty` (`name`, `value`) VALUES ('sms.auth_token', 'd181f81cfc1f9fa21efb427aebe1b67d');
INSERT INTO `data`.`AppProperty` (`name`, `value`) VALUES ('sms.from.number', '+48732230590');
INSERT INTO `data`.`AppProperty` (`name`, `value`) VALUES ('contact.email.content', 'Dziękujemy za kontakt. Odpowiemy w ciągu 24godzin.');
INSERT INTO `data`.`AppProperty` (`name`, `value`) VALUES ('contact.email.subject', 'Kontakt');

SET foreign_key_checks = 1;