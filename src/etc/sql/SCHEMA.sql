USE data;

SET foreign_key_checks = 0;
-- Drop tables
--drop table `AppLog`;
--drop table `AppProperty`;
--drop table `Contact`;
--drop table `CourseDate`;
--drop table `Department`;
--drop table `EmailTemplate`;
--drop table `EventDictionary`;
--drop table `StudentCourse`;
--drop table `StudentPrecense`;
--drop table `SMSTemplate`;
--drop table `Subject`;
--drop table `TeacherCourse`;
--drop table `User`;


CREATE TABLE `AppLog` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `eventDictionaryID` int(10) unsigned NOT NULL,
  `studentID` int(10) unsigned NOT NULL,
  `description` varchar(500) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `fk_eventDictionary` (`eventDictionaryID`),
  KEY `fk_student_idx` (`studentID`),
  CONSTRAINT `fk_AppLog_eventDictionary` FOREIGN KEY (`eventDictionaryID`) REFERENCES `EventDictionary` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_AppLog_student` FOREIGN KEY (`studentID`) REFERENCES `User` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

CREATE TABLE `AppProperty` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `value` varchar(500) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

CREATE TABLE `Contact` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `group` varchar(11) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `street` varchar(100) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

CREATE TABLE `CourseDate` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `teacherCourseID` int(10) unsigned NOT NULL,
  `startTime` time NOT NULL,
  `finishTime` time NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `fk_CourseDates_1` (`teacherCourseID`),
  CONSTRAINT `fk_teacherCourse` FOREIGN KEY (`teacherCourseID`) REFERENCES `TeacherCourse` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

CREATE TABLE `Department` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

CREATE TABLE `EmailTemplate` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `value` varchar(500) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

CREATE TABLE `EventDictionary` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `eventName` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

CREATE TABLE `StudentPrecense` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `studentID` int(10) unsigned NOT NULL,
  `courseDateID` int(10) unsigned NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `studentID` (`studentID`),
  KEY `courseDateID` (`courseDateID`),
  CONSTRAINT `fk_courseDate` FOREIGN KEY (`courseDateID`) REFERENCES `CourseDate` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_student2` FOREIGN KEY (`studentID`) REFERENCES `User` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

CREATE TABLE `StudentCourse` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `studentID` int(10) unsigned NOT NULL,
  `teacherCourseID` int(10) unsigned NOT NULL,
  `saveTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `studentID` (`studentID`),
  KEY `teacherCourseID` (`teacherCourseID`),
  CONSTRAINT `fk_teacherCourse1` FOREIGN KEY (`teacherCourseID`) REFERENCES `TeacherCourse` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student3` FOREIGN KEY (`studentID`) REFERENCES `User` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

CREATE TABLE `SMSTemplate` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `value` varchar(500) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

CREATE TABLE `Subject` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `departmentID` int(10) unsigned NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `departmentID` (`departmentID`),
  CONSTRAINT `fk_department` FOREIGN KEY (`departmentID`) REFERENCES `Department` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

CREATE TABLE `TeacherCourse` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `subjectID` int(10) unsigned NOT NULL,
  `teacherID` int(10) unsigned NOT NULL,
  `type` varchar(15) NOT NULL,
  `coursesQuantity` int(10) unsigned DEFAULT NULL,
  `minPresence` int(10) unsigned DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `subjectID` (`subjectID`),
  KEY `teacherID` (`teacherID`),
  CONSTRAINT `fk_subject` FOREIGN KEY (`subjectID`) REFERENCES `Subject` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_teacher` FOREIGN KEY (`teacherID`) REFERENCES `User` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

CREATE TABLE `User` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `contactID` int(10) unsigned NOT NULL,
  `login` varchar(50) NOT NULL,
  `firstName` varchar(25) NOT NULL,
  `lastName` varchar(25) NOT NULL,
  `type` varchar(20) NOT NULL,
  `lastLogin` datetime DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `salt` varchar(100) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `contactID_UNIQUE` (`contactID`),
  CONSTRAINT `fk_User_1` FOREIGN KEY (`contactID`) REFERENCES `Contact` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

--ALTER TABLE AppLog CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
--ALTER TABLE Contact CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
--ALTER TABLE CourseDate CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
--ALTER TABLE Department CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
--ALTER TABLE EventDictionary CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
--ALTER TABLE StudentCourse CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
--ALTER TABLE StudentPrecense CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
--ALTER TABLE Subject CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
--ALTER TABLE TeacherCourse CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
--ALTER TABLE User CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;

SET foreign_key_checks = 1;