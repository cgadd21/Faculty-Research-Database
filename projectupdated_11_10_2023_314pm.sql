DROP DATABASE IF EXISTS project;
CREATE DATABASE project;
use project;

-- ALL USERS ARE IN THIS TABLE
-- THIS TABLE IS USED TO DEFINE WHAT TYPE OF USER THEY ARE IE. STUDENT FACULTY GUEST
-- typeID DEFINES THE TYPE OF USER 
-- typeID IS ONE OF 3 VALUES F - Faculty, G - Guest, S - Student

CREATE TABLE users (
	userID INT NOT NULL auto_increment,
	typeID ENUM ('F','G','S') NOT NULL,
	constraint user_pk primary key (userID)
) AUTO_INCREMENT = 1; 

CREATE TABLE userLogin (
	userID int,
	username VARCHAR(30) UNIQUE,
    password VARCHAR(30),
    constraint user_id_fk foreign key (userID) references users(userID)
);
-- GUEST INFORMATION START
CREATE TABLE guest(
    guestID INT NOT NULL,
    business VARCHAR(30),
    fname VARCHAR(30),
    lname VARCHAR(30),
    contactinfo VARCHAR(30),
    CONSTRAINT guest_pk PRIMARY KEY (guestID),
    CONSTRAINT guest_id_fk FOREIGN KEY (guestID) REFERENCES users(userID)
);
-- GUEST INFORMATION END

CREATE TABLE student (
	studentID int,
    fname VARCHAR(30),
    lname VARCHAR(30),
    CONSTRAINT student_pk PRIMARY KEY (studentID),
    CONSTRAINT student_id_fk FOREIGN KEY (studentID) REFERENCES users(userID)
);

CREATE TABLE studentContact (
	studentID int,
    email VARCHAR(30),
    phonenumber VARCHAR(30), 
    CONSTRAINT studentContact_id_fk FOREIGN KEY (studentID) REFERENCES student(studentID)
);

CREATE TABLE interestList (
	interestID INT NOT NULL auto_increment,
    intDesc VARCHAR(30),
    constraint int_pk PRIMARY KEY (interestID)
);
CREATE TABLE studentInterests (
	studentID int,
    interestID int,
    CONSTRAINT studentInterests_pk PRIMARY KEY (studentID, interestID),
    CONSTRAINT studentInterests_id_fk FOREIGN KEY (studentID) REFERENCES student(studentID),
	CONSTRAINT studentInterests_int_fk FOREIGN KEY (interestID) REFERENCES interestList(interestID)
);

CREATE TABLE faculty (
	facultyID INT NOT NULL,
    fname VARCHAR(30),
    lname VARCHAR(30),
    CONSTRAINT faculty_pk PRIMARY KEY (facultyID),
    CONSTRAINT faculty_id_fk FOREIGN KEY (facultyID) REFERENCES users(userID)
);

CREATE TABLE facultyInterests (
    facultyID INT,
    interestID INT,
    CONSTRAINT facultyInterests_pk PRIMARY KEY (facultyID, interestID),
    CONSTRAINT facultyInterests_faculty_fk FOREIGN KEY (facultyID) REFERENCES faculty(facultyID),
    CONSTRAINT facultyInterests_int_fk FOREIGN KEY (interestID) REFERENCES interestList(interestID)
);
CREATE TABLE facultyContact (
	facultyID INT,
    email VARCHAR(30),
    phonenumber VARCHAR(30),
    location VARCHAR(50),
    CONSTRAINT facultyContact_id_fk FOREIGN KEY (facultyID) REFERENCES faculty(facultyID)
);
CREATE TABLE abstractList (
	abstractID int,
	professorAbstract TEXT,
	CONSTRAINT absractList_pk PRIMARY KEY (abstractID)
);
CREATE TABLE facultyAbstract (
	facultyID INT,
	abstractID INT,
	CONSTRAINT facultyAbstract_faculty_fk FOREIGN KEY (facultyID) REFERENCES faculty(facultyID),
    CONSTRAINT facultyAbstract_abstract_fk FOREIGN KEY (abstractID) REFERENCES abstractList(abstractID),
    CONSTRAINT facultyAbstract_pk PRIMARY KEY (facultyID, abstractID)
);




