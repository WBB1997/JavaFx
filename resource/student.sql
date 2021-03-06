DROP TABLE STUDENT;
CREATE TABLE STUDENT(
	SNO VARCHAR2(20) PRIMARY KEY,
	SNAME VARCHAR2(20),
	SSEX VARCHAR2(20),
	SAGE INT,
	SDEPT VARCHAR2(20),
	CNO VARCHAR2(20),
	GRADE INT
);

INSERT INTO STUDENT VALUES('201215121', '李勇', '男', 20, 'CS', '1', 92);
INSERT INTO STUDENT VALUES('201215122', '刘晨', '女', 19, 'BS', '2', 90);
INSERT INTO STUDENT VALUES('201215123', '刘晨', '女', 19, 'CS', '1', 80);
INSERT INTO STUDENT VALUES('201215124', '王敏', '女', 18, 'IS', '4', 97);
INSERT INTO STUDENT VALUES('201215125', '张立', '男', 19, 'CS', '1', 68);
INSERT INTO STUDENT VALUES('201215126', '李勇', '男', 20, 'CS', '1', 92);
INSERT INTO STUDENT VALUES('201215127', '刘晨', '女', 19, 'BS', '2', 90);
INSERT INTO STUDENT VALUES('201215128', '刘晨', '女', 19, 'CS', '1', 80);
INSERT INTO STUDENT VALUES('201215129', '王敏', '女', 18, 'IS', '4', 97);
INSERT INTO STUDENT VALUES('201215130', '张立', '男', 19, 'CS', '1', 68);
INSERT INTO STUDENT VALUES('201215131', '李勇', '男', 20, 'CS', '1', 92);
INSERT INTO STUDENT VALUES('201215132', '刘晨', '女', 19, 'BS', '2', 90);
INSERT INTO STUDENT VALUES('201215133', '刘晨', '女', 19, 'CS', '1', 80);
INSERT INTO STUDENT VALUES('201215134', '王敏', '女', 18, 'IS', '4', 97);
INSERT INTO STUDENT VALUES('201215145', '张立', '男', 19, 'CS', '1', 68);


SELECT STUDENT.*,ROWNUM rn FROM STUDENT

SELECT * FROM (SELECT tmp.*, ROWNUM rn FROM (SELECT * FROM STUDENT) tmp WHERE ROWNUM <= 10) WHERE rn >= 1

SELECT * FROM (SELECT tmp.*, ROWNUM rn FROM (SELECT * FROM STUDENT) tmp WHERE ROWNUM <= 20) WHERE rn >= 11


SELECT SNO, SNAME, SSEX, SAGE, SDEPT, CNO, GRADE FROM (SELECT  ROWNUM rn,tmp.* FROM (SELECT * FROM STUDENT) tmp WHERE ROWNUM <= 20) WHERE rn >= 11
SELECT count(*) FROM  Student;