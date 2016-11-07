USE data;

SET foreign_key_checks = 0;

drop table `AppLog`;
drop table `AppProperty`;
drop table `CourseDate`;
drop table `EmailTemplate`;
drop table `EventDictionary`;
drop table `Field`;
drop table `Grade`;
drop table `Notification`;
drop table `SMSTemplate`;
drop table `StudentCourse`;
drop table `StudentPrecense`;
drop table `Subject`;
drop table `TeacherCourse`;
drop table `User`;

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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `AppProperty` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `value` varchar(500) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `EmailTemplate` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `value` varchar(500) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `EventDictionary` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `SMSTemplate` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `value` varchar(500) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `CourseDate` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `teacherCourseID` int(10) unsigned NOT NULL,
  `startTime` time NOT NULL,
  `finishTime` time NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `fk_subject23` (`teacherCourseID`),
  CONSTRAINT `fk_subject23` FOREIGN KEY (`teacherCourseID`) REFERENCES `TeacherCourse` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `StudentPrecense` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `studentID` int(10) unsigned NOT NULL,
  `courseDateID` int(10) unsigned NOT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `studentID` (`studentID`),
  KEY `courseDateID` (`courseDateID`),
  CONSTRAINT `fk_courseDate` FOREIGN KEY (`courseDateID`) REFERENCES `CourseDate` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_student2` FOREIGN KEY (`studentID`) REFERENCES `User` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `Grade` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `subjectID` int(10) unsigned NOT NULL,
  `previousGradeID` int(10) unsigned DEFAULT NULL,
  `studentID` int(10) unsigned NOT NULL,
  `value` int(3) unsigned NOT NULL,
  `isFinalGrade` boolean NOT NULL DEFAULT 0,
  `time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `subjectID` (`subjectID`),
  KEY `previousGradeID` (`previousGradeID`),
  KEY `studentID` (`studentID`),
  CONSTRAINT `fk_subject_grade` FOREIGN KEY (`subjectID`) REFERENCES `Subject` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_grade_grade` FOREIGN KEY (`previousGradeID`) REFERENCES `Grade` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_grade` FOREIGN KEY (`studentID`) REFERENCES `User` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `StudentCourse` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `studentID` int(10) unsigned NOT NULL,
  `teacherCourseID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `studentID` (`studentID`),
  KEY `teacherCourseID` (`teacherCourseID`),
  CONSTRAINT `fk_teacherCourse332` FOREIGN KEY (`teacherCourseID`) REFERENCES `TeacherCourse` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student3` FOREIGN KEY (`studentID`) REFERENCES `User` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `Subject` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `yearOfStudy` int(10) unsigned NOT NULL,
  `fieldID` int(10) unsigned NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `quantity` int(10) unsigned NOT NULL,
  `minQuantity` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  CONSTRAINT `fk_Subject546` FOREIGN KEY (`fieldID`) REFERENCES `Field` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `Field` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `TeacherCourse` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `subjectID` int(10) unsigned NOT NULL,
  `teacherID` int(10) unsigned NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `studentGroup` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `subjectID` (`subjectID`),
  KEY `teacherID` (`teacherID`),
  CONSTRAINT `fk_subject` FOREIGN KEY (`subjectID`) REFERENCES `Subject` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_teacher` FOREIGN KEY (`teacherID`) REFERENCES `User` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `User` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `firstName` varchar(25) NOT NULL,
  `lastName` varchar(25) NOT NULL,
  `type` varchar(20) NOT NULL,
  `lastLogin` datetime DEFAULT NULL,
  `yearOfStudy` int(6) DEFAULT 0,
  `fieldID` int(10) unsigned DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `salt` varchar(100) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `group` varchar(100) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `street` varchar(100) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  CONSTRAINT `fk_User_2` FOREIGN KEY (`fieldID`) REFERENCES `Field` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


SET foreign_key_checks = 1;