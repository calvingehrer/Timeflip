INSERT IGNORE INTO department(DEPARTMENT_NAME) VALUES ('Management')
INSERT IGNORE INTO department(DEPARTMENT_NAME) VALUES ('Accounting')
INSERT IGNORE INTO department(DEPARTMENT_NAME) VALUES ('IT')
INSERT IGNORE INTO department(DEPARTMENT_NAME) VALUES ('Sales & Marketing')
INSERT IGNORE INTO department(DEPARTMENT_NAME) VALUES ('Human Resources')


INSERT IGNORE INTO team (TEAM_NAME, DEPARTMENT_ID) VALUES  ('Top-Management', 'Management')

INSERT IGNORE INTO team (TEAM_NAME, DEPARTMENT_ID) VALUES  ('Coordination & Controll', 'Management')


INSERT IGNORE INTO team (TEAM_NAME, DEPARTMENT_ID) VALUES  ('Invoince Clients', 'Accounting')

INSERT IGNORE INTO team (TEAM_NAME, DEPARTMENT_ID) VALUES  ('Inventory Management', 'Accounting')

INSERT IGNORE INTO team (TEAM_NAME, DEPARTMENT_ID) VALUES  ('Network & Security', 'IT')

INSERT IGNORE INTO team (TEAM_NAME, DEPARTMENT_ID) VALUES  ('Webdevelopment', 'IT')

INSERT IGNORE INTO team (TEAM_NAME, DEPARTMENT_ID) VALUES  ('Advertising', 'Sales & Marketing')

INSERT IGNORE INTO team (TEAM_NAME, DEPARTMENT_ID) VALUES  ('Customer Service', 'Sales & Marketing')

INSERT IGNORE INTO team (TEAM_NAME, DEPARTMENT_ID) VALUES  ('Eployee Management', 'Human Resources')

INSERT IGNORE INTO team (TEAM_NAME, DEPARTMENT_ID) VALUES  ('Recruiting', 'Human Resources')

INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL) VALUES(TRUE, 'Admin', 'Istrator', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'admin', 'admin', '2016-01-01 00:00:00', 'NONE')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('admin', 'ADMIN')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('admin', 'EMPLOYEE')

INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, DEPARTMENT_ID) VALUES(TRUE, 'Miranda', 'Priestly', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user1', 'admin', '2016-01-01 00:00:00', 'NONE', 'Management')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user1', 'DEPARTMENTLEADER')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user1', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, DEPARTMENT_ID) VALUES(TRUE, 'Alvero', 'Morte', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user2', 'admin', '2016-01-01 00:00:00', 'NONE', 'Accounting')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user2', 'DEPARTMENTLEADER')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user2', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, DEPARTMENT_ID) VALUES(TRUE, 'Erlich', 'Bachman', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user3', 'admin', '2016-01-01 00:00:00', 'NONE', 'IT')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user3', 'DEPARTMENTLEADER')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user3', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, DEPARTMENT_ID) VALUES(TRUE, 'Michal', 'Scott', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user4', 'admin', '2016-01-01 00:00:00', 'NONE', 'Sales & Marketing')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user4', 'DEPARTMENTLEADER')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user4', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, DEPARTMENT_ID) VALUES(TRUE, 'Jen', 'Barber', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user5', 'admin', '2016-01-01 00:00:00', 'NONE', 'Human Resources')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user5', 'DEPARTMENTLEADER')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user5', 'EMPLOYEE')



INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Nigel', 'Tucci', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user6', 'admin', '2016-01-01 00:00:00', 'NONE', 'Top-Management', 'Management')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user6', 'TEAMLEADER')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user6', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Andrea', 'Sachs', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user7', 'admin', '2016-01-01 00:00:00', 'NONE',  'Coordination & Controll', 'Management')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user7', 'TEAMLEADER')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user7', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Pedro', 'Alonso', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user8', 'admin', '2016-01-01 00:00:00', 'NONE', 'Invoince Clients', 'Accounting')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user8', 'TEAMLEADER')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user8', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Raqhel', 'Murillo', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user9', 'admin', '2016-01-01 00:00:00', 'NONE', 'Inventory Management', 'Accounting')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user9', 'TEAMLEADER')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user9', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Monica', 'Hall', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user10', 'admin', '2016-01-01 00:00:00', 'NONE', 'Network & Security', 'IT')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user10', 'TEAMLEADER')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user10', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Richard', 'Hendricks', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user11', 'admin', '2016-01-01 00:00:00', 'NONE', 'Webdevelopment', 'IT')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user11', 'TEAMLEADER')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user11', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Pam', 'Beesly', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user12', 'admin', '2016-01-01 00:00:00', 'NONE', 'Advertising', 'Sales & Marketing')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user12', 'TEAMLEADER')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user12', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Jim', 'Halpert', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user13', 'admin', '2016-01-01 00:00:00', 'NONE', 'Customer Service', 'Sales & Marketing')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user13', 'TEAMLEADER')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user13', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE,  'Maurice', 'Moss', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user14', 'admin', '2016-01-01 00:00:00', 'NONE', 'Eployee Management', 'Human Resources')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user14', 'TEAMLEADER')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user14', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Roy', 'Trenneman', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user15', 'admin', '2016-01-01 00:00:00', 'NONE', 'Recruiting', 'Human Resources')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user15', 'TEAMLEADER')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user15', 'EMPLOYEE')

INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Emily', 'Blunt', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user16', 'admin', '2016-01-01 00:00:00', 'NONE', 'Top-Management', 'Management')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user16', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Christian', 'Thompson', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user17', 'admin', '2016-01-01 00:00:00', 'NONE', 'Top-Management', 'Management')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user17', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Serena', 'Bündchen', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user18', 'admin', '2016-01-01 00:00:00', 'NONE', 'Coordination & Controll', 'Management')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user18', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Ursula', 'Corbero', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user19', 'admin', '2016-01-01 00:00:00', 'NONE', 'Invoince Clients', 'Accounting')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user19', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Miguel', 'Herran', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user20', 'admin', '2016-01-01 00:00:00', 'NONE', 'Invoince Clients', 'Accounting')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user20', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Alba', 'Flores', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user21', 'admin', '2016-01-01 00:00:00', 'NONE', 'Inventory Management', 'Accounting')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user21', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Nelson', 'Bighetti', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user22', 'admin', '2016-01-01 00:00:00', 'NONE', 'Network & Security', 'IT')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user22', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Laurie', 'Bream', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user23', 'admin', '2016-01-01 00:00:00', 'NONE', 'Network & Security', 'IT')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user23', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Dinesh', 'Chugtai', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user24', 'admin', '2016-01-01 00:00:00', 'NONE', 'Webdevelopment', 'IT')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user24', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Dwight', 'Schrute', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user25', 'admin', '2016-01-01 00:00:00', 'NONE', 'Advertising', 'Sales & Marketing')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user25', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Angela', 'Kinsey', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user26', 'admin', '2016-01-01 00:00:00', 'NONE', 'Customer Service', 'Sales & Marketing')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user26', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Kelly', 'Kapoor', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user27', 'admin', '2016-01-01 00:00:00', 'NONE', 'Customer Service', 'Sales & Marketing')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user27', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Douglas', 'Reymond', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user28', 'admin', '2016-01-01 00:00:00', 'NONE', 'Eployee Management', 'Human Resources')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user28', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Richmond', 'Fielding', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user29', 'admin', '2016-01-01 00:00:00', 'NONE', 'Eployee Management', 'Human Resources')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user29', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL, TEAM_ID, DEPARTMENT_ID) VALUES(TRUE, 'Rachel', 'Riley', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user30', 'admin', '2016-01-01 00:00:00', 'NONE', 'Recruiting', 'Human Resources')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user30', 'EMPLOYEE')

INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL) VALUES(TRUE, 'Paul', 'McCartney', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user31', 'admin', '2016-01-01 00:00:00', 'NONE')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user31', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL) VALUES(TRUE, 'John', 'Lennon', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user32', 'admin', '2016-01-01 00:00:00', 'NONE')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user32', 'EMPLOYEE')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL) VALUES(TRUE, 'Ringo', 'Starr', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user33', 'admin', '2016-01-01 00:00:00', 'NONE')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user33', 'EMPLOYEE')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user33', 'TEAMLEADER')
INSERT IGNORE INTO user (ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_USERNAME, CREATE_DATE, INTERVALL) VALUES(TRUE, 'George', 'Harrison', '$2a$10$Mk/CZDjQgLiWd0veXxVKguSamdmUsBJqsgaiCFPx8inYP4.E1rNgO', 'user34', 'admin', '2016-01-01 00:00:00', 'NONE')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user34', 'EMPLOYEE')
INSERT INTO user_user_role (USER_USERNAME, ROLES) VALUES ('user34', 'DEPARTMENTLEADER')

INSERT IGNORE INTO task (TASK_ID, START_TIME, END_TIME, TASK, USER_ID) VALUES (1, '2020-02-01 08:00:00', '2020-02-01 08:50:00', 'DOKUMENTATION', 'user1')
INSERT IGNORE INTO task (TASK_ID, START_TIME, END_TIME, TASK, USER_ID) VALUES (2, '2020-02-02 08:00:00', '2020-02-02 08:50:00', 'DOKUMENTATION', 'user1')
INSERT IGNORE INTO task (TASK_ID, START_TIME, END_TIME, TASK, USER_ID) VALUES (3, '2020-05-07 08:00:00', '2020-05-07 08:50:00', 'DOKUMENTATION', 'admin')
INSERT IGNORE INTO task (TASK_ID, START_TIME, END_TIME, TASK, USER_ID) VALUES (4, '2020-05-07 08:50:00', '2020-05-07 09:30:00', 'AUSZEIT', 'admin')
INSERT IGNORE INTO task (TASK_ID, START_TIME, END_TIME, TASK, USER_ID) VALUES (5, '2020-05-07 09:30:00', '2020-05-07 10:30:00', 'MEETING', 'admin')
INSERT IGNORE INTO task (TASK_ID, START_TIME, END_TIME, TASK, USER_ID) VALUES (6, '2020-05-07 10:30:00', '2020-05-07 11:50:00', 'IMPLEMENTIERUNG', 'admin')
INSERT IGNORE INTO task (TASK_ID, START_TIME, END_TIME, TASK, USER_ID) VALUES (7, '2020-05-07 11:50:00', '2020-05-07 12:50:00', 'DOKUMENTATION', 'admin')

INSERT IGNORE INTO request (REQUEST_ID, REQUESTER, REQUEST_HANDLER, STATUS, DESCRIPTION) VALUES (1, 'user30', 'user15', 'OPEN', 'editing')