create table dis_client (client_id varchar(40) not null, is_notify bit, member_email varchar(40), member_name varchar(50), member_postition varchar(40), organization varchar(50), version integer, primary key (client_id));

create table dsi_configuration (configuration_id varchar(40) not null, total_casual integer, total_leave integer, total_sick integer, version integer, primary key (configuration_id));

create table dsi_employee (employee_id varchar(40) not null, bank_ac_no varchar(50), created_date date, date_of_confirmation date, e_tin_id varchar(50), employee_no varchar(20), first_name varchar(40), github_id varchar(50), ip_address varchar(40), is_active bit, joining_date date, last_modified_date date, last_name varchar(40), mac_address varchar(40), national_id varchar(50), nick_name varchar(30), user_id varchar(40), resign_date date, skype_id varchar(50), version integer, primary key (employee_id));

create table dsi_employee_attendance (employee_attendance_id varchar(40) not null, attendance_date date, check_in_time varchar(10), check_out_time varchar(10), created_by varchar(40), created_date date, is_absent boolean, last_modified_date date, modified_by varchar(40), total_hour varchar(10), version integer not null, employee_id varchar(40), primary key (employee_attendance_id));

create table dsi_temp_attendance (temp_attendance_id varchar(40) not null, attendance_date date, check_in_time varchar(10), check_out_time varchar(10), created_by varchar(40), created_date date, is_absent boolean, is_transferred boolean, last_modified_date date, modified_by varchar(40), total_hour varchar(10), version integer not null, employee_id varchar(40), primary key (temp_attendance_id));

create table dsi_employee_contact_number (employee_contact_number_id varchar(40) not null, phone varchar(15), employee_id varchar(40), type_id varchar(40), version integer, primary key (employee_contact_number_id));

create table dsi_employee_designation (employee_designation_id varchar(40) not null, designation_date date, name varchar(50), employee_id varchar(40), is_current bit, version integer, primary key (employee_designation_id));

create table dsi_employee_email (employee_email_id varchar(40) not null, email varchar(50), employee_id varchar(40), type_id varchar(40), version integer, primary key (employee_email_id));

create table dsi_employee_info (employee_info_id varchar(40) not null, blood_group varchar(15), date_of_birth date, father_name varchar(40), gender varchar(10), mother_name varchar(40), permanent_address TEXT, photo_url TEXT, present_address TEXT, spouse_name varchar(40), employee_id varchar(40), version integer, primary key (employee_info_id));

create table dsi_employee_leave (employee_leave_id varchar(40) not null, total_casual integer, total_leave integer, total_not_notify integer, total_sick integer, total_special_leave integer, employee_id varchar(40), version integer, primary key (employee_leave_id));

create table dsi_leave_request (leave_request_id varchar(40) not null, apply_date date, approval_id varchar(40), approved_date date, approved_days_count integer, approved_end_date date, approved_start_date date, days_count integer, denied_reason TEXT, end_date date, is_client_notify bit, last_modified_date date, leave_reason TEXT, start_date date, apply_id varchar(40), leave_status_id varchar(40), leave_type_id varchar(40), request_type_id varchar(40), version integer, primary key (leave_request_id));

create table dsi_project (project_id varchar(40) not null, description TEXT, name varchar(50), status_id varchar(40), version integer, primary key (project_id));

create table dsi_project_client (project_client_id varchar(40) not null, client_id varchar(40), project_id varchar(40), version integer, primary key (project_client_id));

create table dsi_project_team (project_team_id varchar(40) not null, project_id varchar(40), team_id varchar(40), version integer, primary key (project_team_id));

create table dsi_team (team_id varchar(40) not null, floor varchar(20), is_active bit, member_count integer, name varchar(50), room varchar(20), version integer, primary key (team_id));

create table dsi_team_member (team_member_id varchar(40) not null, employee_id varchar(40), role_id varchar(40), team_id varchar(40), version integer, primary key (team_member_id));

create table ref_employee_contact_number_type (employee_contact_type_id varchar(40) not null, description TEXT, is_active bit, name varchar(50), primary key (employee_contact_type_id));

create table ref_employee_email_type (employee_email_type_id varchar(40) not null, description TEXT, is_active bit, name varchar(50), primary key (employee_email_type_id));

create table ref_employee_status (employee_status_id varchar(40) not null, description TEXT, is_active bit, name varchar(50), primary key (employee_status_id));

create table ref_leave_status (leave_status_id varchar(40) not null, description TEXT, is_active bit, name varchar(50), primary key (leave_status_id));

create table ref_leave_type (leave_type_id varchar(40) not null, description TEXT, is_active bit, name varchar(50), primary key (leave_type_id));

create table ref_leave_request_type(leave_request_type_id varchar(40) not null, description TEXT, is_active bit, name varchar(50), primary key (leave_request_type_id));

create table ref_project_status (project_status_id varchar(40) not null, description TEXT, is_active bit, name varchar(50), primary key (project_status_id));

create table ref_role (role_id varchar(40) not null, description TEXT, is_active bit, name varchar(50), primary key (role_id));

