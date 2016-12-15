ALTER TABLE dsi_employee ADD CONSTRAINT UK_employee_no  UNIQUE (employee_no);

ALTER TABLE dsi_employee_attendance ADD INDEX FK6023EB152BA9B222 (employee_id), ADD CONSTRAINT FK6023EB152BA9B222 FOREIGN KEY (employee_id) REFERENCES dsi_employee (employee_id);

ALTER TABLE dsi_temp_attendance ADD INDEX FKE0492FAF2BA9B222 (employee_id), ADD CONSTRAINT FKE0492FAF2BA9B222 FOREIGN KEY (employee_id) REFERENCES dsi_employee (employee_id);

ALTER TABLE dsi_employee_contact_number ADD CONSTRAINT FK_employee_id_contact_no FOREIGN KEY (employee_id) REFERENCES dsi_employee (employee_id);

ALTER TABLE dsi_employee_contact_number ADD CONSTRAINT FK_type_id_contact_no FOREIGN KEY (type_id) REFERENCES ref_employee_contact_number_type (employee_contact_type_id);

ALTER TABLE dsi_employee_designation ADD CONSTRAINT FK_employee_id_designation FOREIGN KEY (employee_id) REFERENCES dsi_employee (employee_id);

ALTER TABLE dsi_employee_email ADD CONSTRAINT FK_employee_id_email FOREIGN KEY (employee_id) REFERENCES dsi_employee (employee_id);

ALTER TABLE dsi_employee_email ADD CONSTRAINT FK_type_id_email FOREIGN KEY (type_id) REFERENCES ref_employee_email_type (employee_email_type_id);

ALTER TABLE dsi_employee_info ADD CONSTRAINT FK_employee_id_info FOREIGN KEY (employee_id) REFERENCES dsi_employee (employee_id);

ALTER TABLE dsi_employee_leave ADD CONSTRAINT FK_employee_id_leave FOREIGN KEY (employee_id) REFERENCES dsi_employee (employee_id);

ALTER TABLE dsi_leave_request ADD CONSTRAINT FK_apply_id_leave_request FOREIGN KEY (apply_id) REFERENCES dsi_employee (employee_id);

ALTER TABLE dsi_leave_request ADD CONSTRAINT FK_leave_status_id_request FOREIGN KEY (leave_status_id) REFERENCES ref_leave_status (leave_status_id);

ALTER TABLE dsi_leave_request ADD CONSTRAINT FK_leave_type_id_request FOREIGN KEY (leave_type_id) REFERENCES ref_leave_type (leave_type_id);

ALTER TABLE dsi_leave_request ADD CONSTRAINT FK_leave_request_id_request FOREIGN KEY (request_type_id) REFERENCES ref_leave_request_type (leave_request_type_id);

ALTER TABLE dsi_project ADD CONSTRAINT FK_status_id_project FOREIGN KEY (status_id) REFERENCES ref_project_status (project_status_id);

ALTER TABLE dsi_project_client ADD CONSTRAINT FK_client_id FOREIGN KEY (client_id) REFERENCES dis_client (client_id);

ALTER TABLE dsi_project_client ADD CONSTRAINT FK_project_id_client FOREIGN KEY (project_id) REFERENCES dsi_project (project_id);

ALTER TABLE dsi_project_team ADD CONSTRAINT FK_project_id_team FOREIGN KEY (project_id) REFERENCES dsi_project (project_id);

ALTER TABLE dsi_project_team ADD CONSTRAINT FK_team_id FOREIGN KEY (team_id) REFERENCES dsi_team (team_id);

ALTER TABLE dsi_team_member ADD CONSTRAINT FK_employee_id_team FOREIGN KEY (employee_id) REFERENCES dsi_employee (employee_id);

ALTER TABLE dsi_team_member ADD CONSTRAINT FK_role_id_team FOREIGN KEY (role_id) REFERENCES ref_role (role_id);

ALTER TABLE dsi_team_member ADD CONSTRAINT FK_team_id_team FOREIGN KEY (team_id) REFERENCES dsi_team (team_id);

ALTER TABLE `dsi_employee_email` CHANGE `is_preferred` `is_preferred` BIT(1) NULL DEFAULT NULL;

ALTER TABLE  `ref_leave_status` ADD  `priority` INT NOT NULL AFTER  `name`;

ALTER TABLE dsi_holiday ADD INDEX FK8409FAD33B3207CA (type_id), ADD CONSTRAINT FK8409FAD33B3207CA FOREIGN KEY (type_id) REFERENCES ref_holiday_type (holiday_type_id);

ALTER TABLE  `dsi_employee_info` ADD  `employee_status_id` VARCHAR( 40 ) NOT NULL AFTER  `employee_id` ;

ALTER TABLE `dsi_employee_info` ADD CONSTRAINT  FK_employee_status_id FOREIGN KEY (employee_status_id) REFERENCES ref_employee_status (employee_status_id);

