USE data;

SET foreign_key_checks = 0;
-- Drop tables
--drop table `AppLog`;
--drop table `AppProperty`;
--drop table `Contact`;
--drop table `CourseDate`;
--drop table `EmailTemplate`;
--drop table `EventDictionary`;
--drop table `StudentCourse`;
--drop table `StudentPrecense`;
--drop table `SMSTemplate`;
--drop table `Subject`;
--drop table `TeacherCourse`;
--drop table `User`;
--drop table `Notification`;


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
  `subjectID` int(10) unsigned NOT NULL,
  `startTime` time NOT NULL,
  `finishTime` time NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `fk_subject23` (`subjectID`),
  CONSTRAINT `fk_subject23` FOREIGN KEY (`subjectID`) REFERENCES `Subject` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
  `name` varchar(100) NOT NULL,
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

CREATE TABLE `Grade` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `subjectID` int(10) unsigned NOT NULL,
  `previousGradeID` int(10) unsigned NOT NULL,
  `value` int(3) unsigned NOT NULL,
  `isFinalGrade` boolean NOT NULL DEFAULT 0,
  `time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `subjectID` (`subjectID`),
  KEY `previousGradeID` (`previousGradeID`),
  CONSTRAINT `fk_subject_grade` FOREIGN KEY (`subjectID`) REFERENCES `Subject` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_grade_grade` FOREIGN KEY (`previousGradeID`) REFERENCES `Grade` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

CREATE TABLE `StudentCourse` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `studentID` int(10) unsigned NOT NULL,
  `subjectID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `studentID` (`studentID`),
  KEY `subjectID` (`subjectID`),
  CONSTRAINT `fk_subject2` FOREIGN KEY (`subjectID`) REFERENCES `Subject` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
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
  `year` int(10) unsigned NOT NULL,
  `fieldID` int(10) unsigned NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  CONSTRAINT `fk_Subject546` FOREIGN KEY (`fieldID`) REFERENCES `Field` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

CREATE TABLE `Field` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

CREATE TABLE `TeacherCourse` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `subjectID` int(10) unsigned NOT NULL,
  `teacherID` int(10) unsigned NOT NULL,
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
  `yearOfStudy` int(6) DEFAULT NULL,
  `fieldID` int(10) unsigned NOT NULL,
  `password` varchar(100) NOT NULL,
  `salt` varchar(100) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `contactID_UNIQUE` (`contactID`),
  CONSTRAINT `fk_User_1` FOREIGN KEY (`contactID`) REFERENCES `Contact` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_2` FOREIGN KEY (`fieldID`) REFERENCES `Field` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

CREATE TABLE `Notification` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userID` int(10) unsigned NOT NULL,
  `courseCanceled` varchar(50),
  `changeCourseDate` varchar(50),
  `absence` varchar(50),
  `criticalPresenceLevel` varchar(50),
  `badMark` varchar(50),
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `userID_UNIQUE` (`userID`),
  CONSTRAINT `fk_User_22` FOREIGN KEY (`userID`) REFERENCES `User` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
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