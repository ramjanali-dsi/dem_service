alter table dsi_employee add constraint UK_employee_no  unique (employee_no);

alter table dsi_employee_attendance add index FK6023EB152BA9B222 (employee_id), add constraint FK6023EB152BA9B222 foreign key (employee_id) references dsi_employee (employee_id);

alter table dsi_temp_attendance add index FKE0492FAF2BA9B222 (employee_id), add constraint FKE0492FAF2BA9B222 foreign key (employee_id) references dsi_employee (employee_id);

alter table dsi_employee_contact_number add constraint FK_employee_id_contact_no foreign key (employee_id) references dsi_employee (employee_id);

alter table dsi_employee_contact_number add constraint FK_type_id_contact_no foreign key (type_id) references ref_employee_contact_number_type (employee_contact_type_id);

alter table dsi_employee_designation add constraint FK_employee_id_designation foreign key (employee_id) references dsi_employee (employee_id);

alter table dsi_employee_email add constraint FK_employee_id_email foreign key (employee_id) references dsi_employee (employee_id);

alter table dsi_employee_email add constraint FK_type_id_email foreign key (type_id) references ref_employee_email_type (employee_email_type_id);

alter table dsi_employee_info add constraint FK_employee_id_info foreign key (employee_id) references dsi_employee (employee_id);

alter table dsi_employee_leave add constraint FK_employee_id_leave foreign key (employee_id) references dsi_employee (employee_id);

alter table dsi_leave_request add constraint FK_apply_id_leave_request foreign key (apply_id) references dsi_employee (employee_id);

alter table dsi_leave_request add constraint FK_leave_status_id_request foreign key (leave_status_id) references ref_leave_status (leave_status_id);

alter table dsi_leave_request add constraint FK_leave_type_id_request foreign key (leave_type_id) references ref_leave_type (leave_type_id);

alter table dsi_leave_request add constraint FK_leave_request_id_request foreign key (request_type_id) references ref_leave_request_type (leave_request_type_id);

alter table dsi_project add constraint FK_status_id_project foreign key (status_id) references ref_project_status (project_status_id);

alter table dsi_project_client add constraint FK_client_id foreign key (client_id) references dis_client (client_id);

alter table dsi_project_client add constraint FK_project_id_client foreign key (project_id) references dsi_project (project_id);

alter table dsi_project_team add constraint FK_project_id_team foreign key (project_id) references dsi_project (project_id);

alter table dsi_project_team add constraint FK_team_id foreign key (team_id) references dsi_team (team_id);

alter table dsi_team_member add constraint FK_employee_id_team foreign key (employee_id) references dsi_employee (employee_id);

alter table dsi_team_member add constraint FK_role_id_team foreign key (role_id) references ref_role (role_id);

alter table dsi_team_member add constraint FK_team_id_team foreign key (team_id) references dsi_team (team_id);

ALTER TABLE `dsi_employee_email` CHANGE `is_preferred` `is_preferred` BIT(1) NULL DEFAULT NULL;

ALTER TABLE  `ref_leave_status` ADD  `priority` INT NOT NULL AFTER  `name` ;

ALTER TABLE  `dsi_employee_info` ADD  `employee_status_id` VARCHAR( 40 ) NOT NULL AFTER  `employee_id` ;

ALTER TABLE `dsi_employee_info` ADD CONSTRAINT  FK_employee_status_id FOREIGN KEY (employee_status_id) REFERENCES ref_employee_status (employee_status_id);

