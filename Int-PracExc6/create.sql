DROP TABLE tblUsers;

CREATE TABLE tblUsers (
	userName	VARCHAR(10) NOT NULL PRIMARY KEY,
	firstName	VARCHAR(20)	NOT NULL,
	lastName	VARCHAR(20)	NOT NULL,
	txtPassword	VARCHAR(10)	NOT NULL,
	AccessLevel	INT			NOT NULL,
	Enabled		INT			NOT NULL
) Engine=InnoDB;

INSERT INTO tblUsers VALUES ('admin', 'Manager', 'Admin', 'tafe123', 2, 1);
INSERT INTO tblUsers VALUES ('jimt', 'Jim', 'Tresinits', 'tafe123', 1, 1);
INSERT INTO tblUsers VALUES ('fred', 'Fred', 'Roberts', 'tafe123', 1, 1);
INSERT INTO tblUsers VALUES ('sams', 'Sam', 'Smith', 'tafe123', 1, 1);