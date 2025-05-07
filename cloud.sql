-- 유저 (User) 테이블
CREATE TABLE "User" (
    user_id NUMBER PRIMARY KEY,
    email VARCHAR2(200) NOT NULL,
    password VARCHAR2(30) NOT NULL,
    role VARCHAR2(10) CHECK (role IN ('admin', 'student')) NOT NULL,
    created_time TIMESTAMP
);
ALTER TABLE "User" ADD CONSTRAINT user_email_unique UNIQUE (email);
-- 폴더 (Folder) 테이블
CREATE TABLE Folder (
    folder_id NUMBER PRIMARY KEY,
    user_id NUMBER NOT NULL,
    folder_name VARCHAR2(50) NOT NULL,
    created_time TIMESTAMP,
    CONSTRAINT fk_folder_user FOREIGN KEY (user_id) REFERENCES "User"(user_id)
);

drop table "File";
-- 파일 (File) 테이블
CREATE TABLE "File" (
    file_id NUMBER PRIMARY KEY,
    folder_id NUMBER NOT NULL,
    fname VARCHAR2(200) NOT NULL,
    fsize NUMBER NOT NULL,
    ftype VARCHAR2(20) NOT NULL,
    created_time TIMESTAMP,
    CONSTRAINT fk_file_folder FOREIGN KEY (folder_id) REFERENCES Folder(folder_id) on delete cascade
);
ALTER TABLE "File" ADD user_id NUMBER;
ALTER TABLE "File" ADD CONSTRAINT fk_file_user FOREIGN KEY (user_id) REFERENCES "User"(user_id);
-- 링크 공유 (SharedLink) 테이블
CREATE TABLE SharedLink (
    link_id NUMBER PRIMARY KEY,
    file_id NUMBER NOT NULL,
    link_url VARCHAR2(200) NOT NULL,
    expiration_date DATE,
    visibility VARCHAR2(10) CHECK (visibility IN ('private', 'public')) NOT NULL,
    created_time TIMESTAMP,
    CONSTRAINT fk_sharedlink_file FOREIGN KEY (file_id) REFERENCES "File"(file_id)
);
ALTER TABLE SharedLink ADD user_id NUMBER;
ALTER TABLE SharedLink ADD CONSTRAINT fk_sharedlink_user FOREIGN KEY (user_id) REFERENCES "User"(user_id);
-- 신고 (Report) 테이블
CREATE TABLE Report (
    report_id NUMBER PRIMARY KEY,
    user_id NUMBER NOT NULL,
    file_id NUMBER NOT NULL,
    report_reason CLOB NOT NULL,
    created_time TIMESTAMP,
    CONSTRAINT fk_report_user FOREIGN KEY (user_id) REFERENCES "User"(user_id),
    CONSTRAINT fk_report_file FOREIGN KEY (file_id) REFERENCES "File"(file_id)
);

-- 시퀀스 생성 (PK 자동 증가용)
CREATE SEQUENCE seq_user_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_folder_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_file_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_link_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_report_id START WITH 1 INCREMENT BY 1;

-- 트리거 생성 (ID 자동 증가용)
CREATE OR REPLACE TRIGGER trg_user_id
BEFORE INSERT ON "User"
FOR EACH ROW
WHEN (NEW.user_id IS NULL)
BEGIN
    SELECT seq_user_id.NEXTVAL INTO :NEW.user_id FROM dual;
END;
/

CREATE OR REPLACE TRIGGER trg_folder_id
BEFORE INSERT ON Folder
FOR EACH ROW
WHEN (NEW.folder_id IS NULL)
BEGIN
    SELECT seq_folder_id.NEXTVAL INTO :NEW.folder_id FROM dual;
END;
/

CREATE OR REPLACE TRIGGER trg_file_id
BEFORE INSERT ON "File"
FOR EACH ROW
WHEN (NEW.file_id IS NULL)
BEGIN
    SELECT seq_file_id.NEXTVAL INTO :NEW.file_id FROM dual;
END;
/

CREATE OR REPLACE TRIGGER trg_link_id
BEFORE INSERT ON SharedLink
FOR EACH ROW
WHEN (NEW.link_id IS NULL)
BEGIN
    SELECT seq_link_id.NEXTVAL INTO :NEW.link_id FROM dual;
END;
/

CREATE OR REPLACE TRIGGER trg_report_id
BEFORE INSERT ON Report
FOR EACH ROW
WHEN (NEW.report_id IS NULL)
BEGIN
    SELECT seq_report_id.NEXTVAL INTO :NEW.report_id FROM dual;
END;
/



-- 관리자(admin) 계정 생성
INSERT INTO "User" (user_id, email, password, role, created_time)
VALUES (seq_user_id.NEXTVAL,
        'admin@cloud.com',         
        'admin1234',                 
        'admin',                      
        SYSTIMESTAMP                 
);
-- 사용자(student) 계정 생성
INSERT INTO "User" (user_id, email, password, role, created_time)
VALUES (
    seq_user_id.NEXTVAL,           
    'student1@cloud.com',         
    'student1234',                 
    'student',                      
    SYSTIMESTAMP                  
);
select * from "User";
COMMIT;

-- 로그인 검증 프로시저 생성
DROP PROCEDURE login_user;
/

CREATE OR REPLACE PROCEDURE cloud_login_user(
    p_email    IN VARCHAR2,
    p_password IN VARCHAR2,
    p_result   OUT VARCHAR2
)
IS
    v_db_password VARCHAR2(30);
BEGIN
    -- 이메일로 비밀번호 단일 조회
    SELECT password
    INTO v_db_password
    FROM "User"
    WHERE email = p_email;

    IF v_db_password = p_password THEN
        p_result := 'SUCCESS';
    ELSE
        p_result := 'INVALID_PASSWORD';
    END IF;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        p_result := 'USER_NOT_FOUND';
    WHEN TOO_MANY_ROWS THEN
        p_result := 'DUPLICATE_EMAIL';
    WHEN OTHERS THEN
        p_result := 'ERROR';
END;
/

-- 회원가입 프로시저
drop procedure register_user;
/

CREATE OR REPLACE PROCEDURE register_user (
    p_email    IN VARCHAR2,
    p_password IN VARCHAR2,
    p_role     IN VARCHAR2,
    p_result   OUT VARCHAR2
)
IS
    v_exists NUMBER;
BEGIN
    -- 이메일 중복 확인
    SELECT COUNT(*) INTO v_exists
    FROM "User"
    WHERE email = p_email;

    IF v_exists > 0 THEN
        p_result := 'EMAIL_EXISTS';
        RETURN;
    END IF;

    -- 새 user_id 생성 (시퀀스 사용 추천)
    INSERT INTO "User"(user_id, email, password, role, created_time)
    VALUES (seq_user_id.NEXTVAL, p_email, p_password, p_role, SYSTIMESTAMP);

    p_result := 'REGISTER_SUCCESS';

EXCEPTION
    WHEN OTHERS THEN
        p_result := 'REGISTER_ERROR';
END;
/

SELECT object_name, status
FROM user_objects
WHERE object_type = 'PROCEDURE' AND object_name = 'LOGIN_USER';

DECLARE
    v_result VARCHAR2(100);
BEGIN
    login_user('admin@cloud.com', 'admin1234', v_result);
    DBMS_OUTPUT.PUT_LINE(v_result);
END;
/

-- 폴더 생성 프로시저
CREATE OR REPLACE PROCEDURE create_folder (
    p_user_id     IN NUMBER,
    p_folder_name IN VARCHAR2,
    p_result      OUT VARCHAR2
)
IS
BEGIN
    INSERT INTO Folder (
        folder_id, user_id, folder_name, created_time
    ) VALUES (
        seq_folder_id.NEXTVAL, p_user_id, p_folder_name, SYSTIMESTAMP
    );

    p_result := 'SUCCESS';

EXCEPTION
    WHEN OTHERS THEN
        p_result := 'ERROR: ' || SQLERRM;
END;
/

VARIABLE result VARCHAR2(100);
EXEC create_folder(1, 'Documents', :result);
PRINT result;

SELECT table_name FROM user_tables WHERE table_name LIKE '%FOLDER%';
SELECT * FROM "User" WHERE user_id = 1;
SELECT constraint_name, table_name
FROM user_constraints
WHERE constraint_type = 'R' AND table_name = 'FOLDER';