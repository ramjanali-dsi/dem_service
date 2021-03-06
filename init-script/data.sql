----------------------------------------------------- Insert into dem_service database ------------------------------------------

INSERT INTO `dem_service`.`ref_employee_contact_number_type` (`employee_contact_type_id`, `description`, `is_active`, `name`) VALUES ('cf2689f0-5165-4c02-a55c-25a9a32d8dd4', NULL, b'1', 'Official');
INSERT INTO `dem_service`.`ref_employee_contact_number_type` (`employee_contact_type_id`, `description`, `is_active`, `name`) VALUES ('807c9237-7173-4a5e-a45e-384137b7fab2', NULL, b'1', 'Personal');
INSERT INTO `dem_service`.`ref_employee_contact_number_type` (`employee_contact_type_id`, `description`, `is_active`, `name`) VALUES ('cf9d4ed1-6f98-4e7f-a77a-2c36e936254d', NULL, b'1', 'Emergency');

INSERT INTO `dem_service`.`ref_employee_email_type` (`employee_email_type_id`, `description`, `is_active`, `name`) VALUES ('78b5c3c5-5470-49f7-aafe-b016a33b3883', NULL, b'1', 'Official');
INSERT INTO `dem_service`.`ref_employee_email_type` (`employee_email_type_id`, `description`, `is_active`, `name`) VALUES ('b6540691-773c-4ee8-9e24-07c292887e73', NULL, b'1', 'Personal');

INSERT INTO `dem_service`.`ref_employee_status` (`employee_status_id`, `description`, `is_active`, `name`) VALUES ('10021760-5ace-46b9-8b2b-b05cb2a65d43', NULL, b'1', 'Full-Time');
INSERT INTO `dem_service`.`ref_employee_status` (`employee_status_id`, `description`, `is_active`, `name`) VALUES ('4797c659-b2e6-42e7-ac93-28c9e781b0b1', NULL, b'1', 'Part-Time');

INSERT INTO `dem_service`.`ref_leave_status` (`leave_status_id`, `description`, `is_active`, `name`, `priority`) VALUES ('19bad1c8-5e6c-41c1-bccb-5d4c360baf25', NULL, b'1', 'Pending', 1);
INSERT INTO `dem_service`.`ref_leave_status` (`leave_status_id`, `description`, `is_active`, `name`, `priority`) VALUES ('0a30ce24-edbf-41b9-86cd-b6f9954bbf92', NULL, b'1', 'Approved', 2);
INSERT INTO `dem_service`.`ref_leave_status` (`leave_status_id`, `description`, `is_active`, `name`, `priority`) VALUES ('4c3b5ba4-f9ae-48d2-8320-a4d6e6d17583', NULL, b'1', 'Denied', 3);
INSERT INTO `dem_service`.`ref_leave_status` (`leave_status_id`, `description`, `is_active`, `name`, `priority`) VALUES ('24a487dc-0167-4054-816e-7efbb1f89f8f', NULL, b'1', 'Canceled', 4);

INSERT INTO `dem_service`.`ref_leave_type` (`leave_type_id`, `description`, `is_active`, `name`, `type`) VALUES ('553fb999-2ceb-4bf0-90f2-ccf52742c8a1', NULL, b'1', 'Casual Leave Application', 'General');
INSERT INTO `dem_service`.`ref_leave_type` (`leave_type_id`, `description`, `is_active`, `name`, `type`) VALUES ('7e1c5ac7-f7a6-43e0-b5da-31cf8b899f10', NULL, b'1', 'Sick Leave Application', 'General');
INSERT INTO `dem_service`.`ref_leave_type` (`leave_type_id`, `description`, `is_active`, `name`, `type`) VALUES ('32058c2f-adb9-4288-bc0a-3081b7982e37', NULL, b'1', 'Special Leave Application', 'Special');
INSERT INTO `dem_service`.`ref_leave_type` (`leave_type_id`, `description`, `is_active`, `name`, `type`) VALUES ('ad9ff1da-a844-431b-b9e0-9b2dcb642496', NULL, b'1', 'Sick Leave Application', 'Special');

INSERT INTO `dem_service`.`ref_leave_request_type` (`leave_request_type_id`, `description`, `is_active`, `name`) VALUES ('0b9cb019-74de-4e3a-90e0-ebca64c6df95', NULL, b'1', 'Pre');
INSERT INTO `dem_service`.`ref_leave_request_type` (`leave_request_type_id`, `description`, `is_active`, `name`) VALUES ('1d905432-0ba2-4681-bfcb-e91d313afd44', NULL, b'1', 'Post');

INSERT INTO `dem_service`.`ref_project_status` (`project_status_id`, `description`, `is_active`, `name`) VALUES ('1db5992a-aafb-4338-a267-4861e0a034f3', NULL, b'1', 'Active');
INSERT INTO `dem_service`.`ref_project_status` (`project_status_id`, `description`, `is_active`, `name`) VALUES ('ba709d6f-d236-4344-8634-cfa3d911913b', NULL, b'1', 'On Hold');
INSERT INTO `dem_service`.`ref_project_status` (`project_status_id`, `description`, `is_active`, `name`) VALUES ('b09eccab-c556-445e-a829-925ca5658565', NULL, b'1', 'Canceled');
INSERT INTO `dem_service`.`ref_project_status` (`project_status_id`, `description`, `is_active`, `name`) VALUES ('753404e1-e0a4-4cc0-b134-0d93788f59d7', NULL, b'1', 'Completed');

INSERT INTO `dem_service`.`ref_role` (`role_id`, `description`, `is_active`, `name`) VALUES ('31c1ab56-9ce4-463d-801d-e7be2f65ccb5', NULL, b'1', 'Manager');
INSERT INTO `dem_service`.`ref_role` (`role_id`, `description`, `is_active`, `name`) VALUES ('9716a905-a6c4-449c-b6f3-00faf17f9359', NULL, b'1', 'Architectural');
INSERT INTO `dem_service`.`ref_role` (`role_id`, `description`, `is_active`, `name`) VALUES ('ff4ea6f2-f0eb-4189-8d21-4673fd51a12a', NULL, b'1', 'Lead');
INSERT INTO `dem_service`.`ref_role` (`role_id`, `description`, `is_active`, `name`) VALUES ('d3478d16-5562-4701-ad0d-0fbea3efee94', NULL, b'1', 'Member');

INSERT INTO `dem_service`.`ref_holiday_type` (`holiday_type_id`, `description`, `name`, `is_active`) VALUES ('2be8ca20-8ebf-4572-bd85-140e6c7b2e23', NULL, 'National', '1');
INSERT INTO `dem_service`.`ref_holiday_type` (`holiday_type_id`, `description`, `name`, `is_active`) VALUES ('752f155d-acb7-4367-984f-9455872d2a5b', NULL, 'Religious', '1');
INSERT INTO `dem_service`.`ref_holiday_type` (`holiday_type_id`, `description`, `name`, `is_active`) VALUES (' 70efdf5b-0815-4fe6-9656-b275b02501cb', NULL, 'Organizational', '1');
INSERT INTO `dem_service`.`ref_holiday_type` (`holiday_type_id`, `description`, `name`, `is_active`) VALUES ('b15e1ef5-7289-4ec9-bfe6-c1b4dc484f97', NULL, 'Optional', '1');

INSERT INTO `dem_service`.`ref_work_from_home_status` (`work_from_home_status_id`, `description`, `is_active`, `name`, `priority`) VALUES ('c12bba6c-c255-4376-bfbe-6cbe34ab3552', NULL, b'1', 'Pending', 1);
INSERT INTO `dem_service`.`ref_work_from_home_status` (`work_from_home_status_id`, `description`, `is_active`, `name`, `priority`) VALUES ('fdb422fc-022b-4d09-9946-2004a974297b', NULL, b'1', 'Approved', 2);
INSERT INTO `dem_service`.`ref_work_from_home_status` (`work_from_home_status_id`, `description`, `is_active`, `name`, `priority`) VALUES ('fd2995c7-b9b8-4686-b648-9b01f9e4474c', NULL, b'1', 'Denied', 3);
INSERT INTO `dem_service`.`ref_work_from_home_status` (`work_from_home_status_id`, `description`, `is_active`, `name`, `priority`) VALUES ('f92902eb-1377-4424-a9f1-59a69a61cf62', NULL, b'1', 'Canceled', 4);


----------------------------------------------------- Insert into service_authenticate database ------------------------------------------

INSERT INTO `service_authentication`.`ref_auth_handler` (`auth_handler_id`, `name`, `type_impl`, `version`)
VALUES ('78fd9528-431a-4cfb-adde-552b6c87fba8', 'Database Login', 'com.dsi.authentication.service.impl.DBLoginHandlerImpl', '1');

INSERT INTO `service_authentication`.`dsi_tenant` (`tenant_id`, `is_active`, `name`, `short_name`, `version`, `auth_handler_id`, `secret_key`)
VALUES ('cc4e0554-6582-498b-9ae2-ad3c612f8e8e', b'1', 'Dynamic Solution', 'DSI', '1', '78fd9528-431a-4cfb-adde-552b6c87fba8', '87c63aae-917c-42ce-b4c7-8a4847db4133');

INSERT INTO `service_authentication`.`dsi_login` (`login_id`, `created_by`, `created_date`, `email`, `first_name`, `last_name`, `modified_by`, `modified_date`, `password`, `reset_password_token`, `reset_token_expire_time`, `salt`, `user_id`, `version`)
VALUES ('f264874e-0331-4fcd-8e92-49513a7724c2', '354fd26f-c642-40ac-b5cf-718c90081598', '2016-06-15 00:00:00', 'sabbir@gmail.com', 'Sabbir', 'Ahmed', '354fd26f-c642-40ac-b5cf-718c90081598', '2016-06-15 00:00:00', 'password', NULL, NULL, '87c63aae-917c-42ce-b4c7-8a4847db4133', 'f9e9a19f-4859-4e8c-a8f4-dc134629a57b', '1');


----------------------------------------------------- Insert into service_authorization database ------------------------------------------

---- System
INSERT INTO `service_authorization`.`dsi_system` (`system_id`, `is_active`, `name`, `short_name`, `tenant_id`, `version`)
VALUES ('425744ba-6c10-47c0-91cf-5a4c05265b56', b'1', 'DSI Employee Management', 'DEM', 'cc4e0554-6582-498b-9ae2-ad3c612f8e8e', '1');

---- Role
INSERT INTO `service_authorization`.`dsi_role` (`role_id`, `is_active`, `name`, `version`)
VALUES ('fe3e0492-c80b-431a-8b6e-658af6a2c8d7', b'1', 'Manager', '1');
INSERT INTO `service_authorization`.`dsi_role` (`role_id`, `is_active`, `name`, `version`)
VALUES ('33394716-ab11-4e6d-8baa-dd4cb214befc', b'1', 'HR', '1');
INSERT INTO `service_authorization`.`dsi_role` (`role_id`, `is_active`, `name`, `version`)
VALUES ('9183166d-2356-42df-a045-416748260106', b'1', 'Member', '1');
INSERT INTO `service_authorization`.`dsi_role` (`role_id`, `is_active`, `name`, `version`)
VALUES ('e0f72053-67c1-4964-a261-36886e1da074', b'1', 'Lead', '1');


INSERT INTO `service_authorization`.`dsi_user` (`user_id`, `created_by`, `created_date`, `email`, `first_name`, `gender`, `last_name`, `modified_by`, `modified_date`, `phone`, `tenant_id`, `version`)
VALUES ('f9e9a19f-4859-4e8c-a8f4-dc134629a57b', '', '2016-07-01 00:00:00', 'sabbir@gmail.com', 'Sabbir', 'Male', 'Ahmed', '', '2016-07-01 00:00:00', '01676661557', 'cc4e0554-6582-498b-9ae2-ad3c612f8e8e', '1');


INSERT INTO `service_authorization`.`dsi_user_role` (`user_role_id`, `created_by`, `created_date`, `is_active`, `modified_by`, `modified_date`, `version`, `role_id`, `system_id`, `user_id`)
VALUES ('bc524e71-a785-40b3-9f5c-81e610dd994a', '', '2016-08-10 00:00:00', b'1', '', '2016-08-10 00:00:00', '1', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56', 'f9e9a19f-4859-4e8c-a8f4-dc134629a57b');


------ HR & Manager Menu
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('79d53582-ed8c-47a3-b734-f41651f688ad', 'Employee Management', b'1', 'Employee', '1', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('136e5d70-c4bf-4d63-832e-afe51a2b8606', 'Team Management', b'1', 'Team', '2', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('3809289a-ba52-42d2-8b5e-a54d5e5a51b8', 'Project Management', b'1', 'Project', '3', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('adccaaab-6b78-438b-a8e2-32bb41cac366', 'Client Management', b'1', 'Client', '4', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('108be377-64bc-4877-92fd-00fca650eedb', 'Leave Management', b'1', 'Leave', '5', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('bf29b1c9-ef67-4475-9364-d4c044b26925', 'Attendance Management', b'1', 'Attendance', '6', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('25037cc4-21ad-48f2-bee8-cc39151b9c79', 'Attendance History', b'1', 'History', '2', 'bf29b1c9-ef67-4475-9364-d4c044b26925', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('17ef042b-0ebe-4e3e-8955-8c12d83f5b08', 'Upload Attendance', b'1', 'Upload', '1', 'bf29b1c9-ef67-4475-9364-d4c044b26925', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('b40bbe0c-52b6-4a83-a7a8-a5cdb32d909f', 'Work From Home', b'1', 'WFH', '3', 'bf29b1c9-ef67-4475-9364-d4c044b26925', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('1ab0176c-ad4d-4cce-bda4-dd1b9001150e', 'Pending Work From Home Applications', b'1', 'Pending WFH', '1', 'b40bbe0c-52b6-4a83-a7a8-a5cdb32d909f', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('7f68cf98-635e-4ced-98ab-b26c6e4fbd0c', 'Work From Home Details', b'1', 'WFH Details', '2', 'b40bbe0c-52b6-4a83-a7a8-a5cdb32d909f', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('5c4a7a90-145b-43a5-a68f-35195bc4dd25', 'My Work From Home Applications', b'1', 'My WFH', '3', 'b40bbe0c-52b6-4a83-a7a8-a5cdb32d909f', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('d4465b72-2bc0-4db9-950a-9d22885f6f47', 'Leave Summary History', b'1', 'Leave Summary', '1', '108be377-64bc-4877-92fd-00fca650eedb', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('37bc385d-b3ae-42a7-8c84-e92057cf8f2d', 'Leave Detail History', b'1', 'Leave Detail', '2', '108be377-64bc-4877-92fd-00fca650eedb', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('7a586ab0-8785-4b72-8091-b741e9a26ec5', 'Pending Leave Applications', b'1', 'Pending Leave', '3', '108be377-64bc-4877-92fd-00fca650eedb', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('6da57037-88af-43db-a871-fd4be7b675be', 'My Leave Applications', b'1', 'My Leave', '4', '108be377-64bc-4877-92fd-00fca650eedb', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('096e7e16-8439-4cf2-b6c7-0ddb8c749cec', 'Special Leave', b'1', 'Special Leave', '5', '108be377-64bc-4877-92fd-00fca650eedb', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('83ffac93-1403-4eeb-b070-078b768efb5d', 'Holiday Management', b'1', 'Holiday', '7', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('da368c3d-760d-4efc-8864-e845b28e0183', 'Notification Management', b'1', 'Notification', '8', NULL, '1');


---- Lead Menu
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('7141dd5e-1998-4975-90ae-74d6a24fa7cf', 'Employee Management', b'1', 'Employee', '1', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('b60399fd-dc74-4b81-8bcd-f14c7d38a040', 'Team Management', b'1', 'Team', '2', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('c2a9db50-cc20-4a74-a82b-3fe565d577d5', 'Project Management', b'1', 'Project', '3', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('d24a40a9-aada-4c7b-b50b-e053a4fbe03d', 'Leave Management', b'1', 'Leave', '4', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('da11dd0c-5bc7-42f8-bd1b-62cd7207fee0', 'Leave Summary History', b'1', 'Leave Summary', '1', 'd24a40a9-aada-4c7b-b50b-e053a4fbe03d', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('77a75531-89d3-4c05-99ff-8a29b2f935e6', 'Leave Detail History', b'1', 'Leave Detail', '2', 'd24a40a9-aada-4c7b-b50b-e053a4fbe03d', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('88ea7da2-36a1-454a-a735-5ca1ce60dda4', 'Pending Leave Applications', b'1', 'Pending Leave', '3', 'd24a40a9-aada-4c7b-b50b-e053a4fbe03d', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('6ffc2994-6993-4334-a69f-996b438dd212', 'My Leave Applications', b'1', 'My Leave', '4', 'd24a40a9-aada-4c7b-b50b-e053a4fbe03d', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('99c33c8c-d404-44fb-933f-7293fef2c485', 'Attendance Management', b'1', 'Attendance', '5', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('6a0a5d7c-7903-46f1-9a3d-fd44373fa85c', 'Attendance History', b'1', 'History', '1', '99c33c8c-d404-44fb-933f-7293fef2c485', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('2581c180-e8f6-426d-9b4f-22b9bd7ad96e', 'Work From Home', b'1', 'WFH', '2', '99c33c8c-d404-44fb-933f-7293fef2c485', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('3c1b925b-0356-4d0b-832d-81047ceaaf30', 'Pending Work From Home Applications', b'1', 'Pending WFH', '1', '2581c180-e8f6-426d-9b4f-22b9bd7ad96e', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('c10699f6-b19e-42f9-b459-976108a2cccb', 'Work From Home Details', b'1', 'WFH Details', '2', '2581c180-e8f6-426d-9b4f-22b9bd7ad96e', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('7f2738c3-917e-42f4-900d-8d8b58c302a4', 'My Work From Home Applications', b'1', 'My WFH', '3', '2581c180-e8f6-426d-9b4f-22b9bd7ad96e', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('9ff595ee-cf01-4475-b89a-9678c25e80d0', 'Holiday Management', b'1', 'Holiday', '6', NULL, '1');


---- Member Menu
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('2d96b168-7334-46fa-9776-65438cb719eb', 'My Profile', b'1', 'My Profile', '1', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('c5e3e9c8-bcfb-40dc-b9ae-10c809786599', 'My Teams', b'1', 'My Teams', '2', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('806f4f41-e767-486f-897e-f53609351491', 'My Projects', b'1', 'My Projects', '3', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('5e41eb25-73c8-445b-b4bb-d6b0eb13b8be', 'Leave Management', b'1', 'Leave', '4', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('017c4814-2f06-423c-b162-6f4e914c740b', 'Leave Summary History', b'1', 'Leave Summary', '1', '5e41eb25-73c8-445b-b4bb-d6b0eb13b8be', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('334ac8ae-25fb-47af-ae29-7cf11577af88', 'My Leave Applications', b'1', 'My Leave', '2', '5e41eb25-73c8-445b-b4bb-d6b0eb13b8be', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('c0cbe425-fe5f-47c1-932a-f8bcd702a869', 'Attendance Management', b'1', 'Attendance', '5', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('d46c0f38-8910-43af-b644-b8133adb8aa2', 'Attendance History', b'1', 'History', '1', 'c0cbe425-fe5f-47c1-932a-f8bcd702a869', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('631c01cf-45a3-4ec8-9f84-20ca3f1500ad', 'Work From Home', b'1', 'WFH', '2', 'c0cbe425-fe5f-47c1-932a-f8bcd702a869', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('c6a1c81e-fc91-4492-a569-517dffe68840', 'My Work From Home Applications', b'1', 'My WFH', '1', '631c01cf-45a3-4ec8-9f84-20ca3f1500ad', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('0bf7fac0-1b1e-428f-96ef-175f9eeb656c', 'Holiday Management', b'1', 'Holiday', '6', NULL, '1');


---- HR role
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('e4bb717d-de8c-4201-af00-42270bfcc1d2', b'1', '1', '37bc385d-b3ae-42a7-8c84-e92057cf8f2d', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('8fb040ea-161b-4a94-b15d-d0c2604bf639', b'1', '1', '79d53582-ed8c-47a3-b734-f41651f688ad', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('38bbf526-214a-41e8-a808-112205e08693', b'1', '1', '108be377-64bc-4877-92fd-00fca650eedb', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('4ad1e17f-281d-475e-ad79-b6849d18bb61', b'1', '1', '136e5d70-c4bf-4d63-832e-afe51a2b8606', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('5c8a7afd-c10e-480c-9e98-50d04effc8ce', b'1', '1', '8176dae9-c6c0-4d7b-8112-4e2875e42a6b', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('22c453a5-999f-4b7f-9b78-104287a853e9', b'1', '1', '3809289a-ba52-42d2-8b5e-a54d5e5a51b8', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('32af2021-6da3-4d22-aafd-da1eac693a4f', b'1', '1', 'adccaaab-6b78-438b-a8e2-32bb41cac366', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('71629bf3-f969-405d-85e0-a503da28f17d', b'1', '1', 'bf29b1c9-ef67-4475-9364-d4c044b26925', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('3ea47aa8-b60f-4f8e-aed7-44d0bb259365', b'1', '1', 'd4465b72-2bc0-4db9-950a-9d22885f6f47', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('c95beb5b-1fa5-4b09-9b98-6d1b1effcb5e', b'1', '1', '7a586ab0-8785-4b72-8091-b741e9a26ec5', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('3319ad63-5636-426a-a772-5c0bd366bfa9', b'1', '1', '6da57037-88af-43db-a871-fd4be7b675be', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('3131488c-f092-421e-8530-49da117eeef7', b'1', '1', '096e7e16-8439-4cf2-b6c7-0ddb8c749cec', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('a58c751b-923b-4173-a1e3-0fe498211bd9', b'1', '1', '25037cc4-21ad-48f2-bee8-cc39151b9c79', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('4feca094-e53e-4f1e-b087-d45144ba8e11', b'1', '1', '17ef042b-0ebe-4e3e-8955-8c12d83f5b08', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('764053e1-1277-4d91-8958-df706a93ad96', b'1', '1', '83ffac93-1403-4eeb-b070-078b768efb5d', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('9f8692a5-ad66-416a-84be-6903e4d6f6a4', b'1', '1', 'b40bbe0c-52b6-4a83-a7a8-a5cdb32d909f', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('2ab8b9ab-ae28-4cc8-8044-32e46cbc0c4c', b'1', '1', '1ab0176c-ad4d-4cce-bda4-dd1b9001150e', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('f8e5b29a-901f-4328-8f8e-c60c67cf8d18', b'1', '1', '7f68cf98-635e-4ced-98ab-b26c6e4fbd0c', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');


---- Manager role
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('6cdd0f54-d99d-4cf5-af55-74a58e74bad9', b'1', '1', '096e7e16-8439-4cf2-b6c7-0ddb8c749cec', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('4dc5d474-ac11-4f71-99a1-0eaaab0008b0', b'1', '1', '108be377-64bc-4877-92fd-00fca650eedb', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('af44c7e3-5ef8-4c9a-9e4d-f39331bf6cca', b'1', '1', '136e5d70-c4bf-4d63-832e-afe51a2b8606', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('0925eb4b-0428-4cff-8675-f230c5dbd02b', b'1', '1', '17ef042b-0ebe-4e3e-8955-8c12d83f5b08', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('b20857e1-2c07-4586-85b6-76088e9c02ef', b'1', '1', '25037cc4-21ad-48f2-bee8-cc39151b9c79', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('5cc815a9-0aa7-4427-bbcf-edd600d6b46d', b'1', '1', '37bc385d-b3ae-42a7-8c84-e92057cf8f2d', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('f758b046-5b12-424b-bf22-eae55aa2345f', b'1', '1', '3809289a-ba52-42d2-8b5e-a54d5e5a51b8', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('7a5125a8-7c26-4b3e-be29-d25ff39a3a9c', b'1', '1', '6da57037-88af-43db-a871-fd4be7b675be', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('5bd25e8c-64d0-4be6-9125-c3ffba5a6ae7', b'1', '1', '79d53582-ed8c-47a3-b734-f41651f688ad', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('08a68e84-8021-4837-b30e-0f18126cdfb2', b'1', '1', '7a586ab0-8785-4b72-8091-b741e9a26ec5', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('bba104e3-b850-4001-8063-decb68bda65f', b'1', '1', '8176dae9-c6c0-4d7b-8112-4e2875e42a6b', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('d3305b0a-4724-4f98-8c60-80c5db146388', b'1', '1', '83ffac93-1403-4eeb-b070-078b768efb5d', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('7e271390-31d5-40ef-8fc5-e87b05fa79b6', b'1', '1', 'adccaaab-6b78-438b-a8e2-32bb41cac366', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('5dc508e0-2d90-4127-8b82-be6b371681e7', b'1', '1', 'bf29b1c9-ef67-4475-9364-d4c044b26925', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('e2d4b679-d606-41bc-983e-5f8956c763eb', b'1', '1', 'd4465b72-2bc0-4db9-950a-9d22885f6f47', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('8c7d01ee-cdc3-4dcc-aa0e-a4c1688a1a44', b'1', '1', 'da368c3d-760d-4efc-8864-e845b28e0183', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('7e5b53c0-1ac2-4ca0-b9ef-66637a675bff', b'1', '1', 'b40bbe0c-52b6-4a83-a7a8-a5cdb32d909f', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('8d66e0d3-c491-4f8e-8ff5-0d68daf93680', b'1', '1', '1ab0176c-ad4d-4cce-bda4-dd1b9001150e', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('aa960f1c-1204-42a9-ac75-0b8e49b67515', b'1', '1', '7f68cf98-635e-4ced-98ab-b26c6e4fbd0c', 'fe3e0492-c80b-431a-8b6e-658af6a2c8d7', '425744ba-6c10-47c0-91cf-5a4c05265b56');


-----Lead Role
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('4033ab49-b6e6-442c-9d99-bfb974fa7c6d', b'1', '1', '7141dd5e-1998-4975-90ae-74d6a24fa7cf', 'e0f72053-67c1-4964-a261-36886e1da074', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('0e41c65f-ab2e-4ee1-be80-8e741d8aa465', b'1', '1', 'b60399fd-dc74-4b81-8bcd-f14c7d38a040', 'e0f72053-67c1-4964-a261-36886e1da074', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('50972fb4-390f-4698-b2e7-397e1c55f881', b'1', '1', 'c2a9db50-cc20-4a74-a82b-3fe565d577d5', 'e0f72053-67c1-4964-a261-36886e1da074', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('fc46a0d5-53f0-487c-b887-3e6da781c02d', b'1', '1', 'd24a40a9-aada-4c7b-b50b-e053a4fbe03d', 'e0f72053-67c1-4964-a261-36886e1da074', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('8d148bb3-449b-49ab-ac45-7d1d52ea24bd', b'1', '1', 'da11dd0c-5bc7-42f8-bd1b-62cd7207fee0', 'e0f72053-67c1-4964-a261-36886e1da074', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('e93f33b6-becb-4a00-a752-657d7047961e', b'1', '1', '77a75531-89d3-4c05-99ff-8a29b2f935e6', 'e0f72053-67c1-4964-a261-36886e1da074', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('e9467238-2049-4008-810a-0494f8e602b9', b'1', '1', '88ea7da2-36a1-454a-a735-5ca1ce60dda4', 'e0f72053-67c1-4964-a261-36886e1da074', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('eaa05313-90bd-4ba7-8fe6-857975443673', b'1', '1', '6ffc2994-6993-4334-a69f-996b438dd212', 'e0f72053-67c1-4964-a261-36886e1da074', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('13d3f18a-2aa7-418c-89a6-2053c4d01724', b'1', '1', '99c33c8c-d404-44fb-933f-7293fef2c485', 'e0f72053-67c1-4964-a261-36886e1da074', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('2b1ac28d-5c15-4acd-97ec-c09e06e2608f', b'1', '1', '6a0a5d7c-7903-46f1-9a3d-fd44373fa85c', 'e0f72053-67c1-4964-a261-36886e1da074', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('e7330c86-05f8-4f4e-81eb-1fc54fb4e8a9', b'1', '1', '2581c180-e8f6-426d-9b4f-22b9bd7ad96e', 'e0f72053-67c1-4964-a261-36886e1da074', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('89026cbd-1153-4fb0-853b-d1b8d5293cba', b'1', '1', '3c1b925b-0356-4d0b-832d-81047ceaaf30', 'e0f72053-67c1-4964-a261-36886e1da074', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('b4efae92-b3ed-4b28-bfc7-220f2d8cc3b1', b'1', '1', '7f2738c3-917e-42f4-900d-8d8b58c302a4', 'e0f72053-67c1-4964-a261-36886e1da074', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('fb6e62ac-1c42-4e9c-b224-d589cd04ecd7', b'1', '1', '9ff595ee-cf01-4475-b89a-9678c25e80d0', 'e0f72053-67c1-4964-a261-36886e1da074', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('aadd374c-cc8a-4fa3-b6bc-0dafc19ef0a0', b'1', '1', 'c10699f6-b19e-42f9-b459-976108a2cccb', 'e0f72053-67c1-4964-a261-36886e1da074', '425744ba-6c10-47c0-91cf-5a4c05265b56');


-----Member Role
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('6773faf9-b983-46ff-93fc-8f23f23506c7', b'1', '1', '2d96b168-7334-46fa-9776-65438cb719eb', '9183166d-2356-42df-a045-416748260106', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('ac4c4bbe-511c-4696-b8f9-f5511ec7b760', b'1', '1', 'c5e3e9c8-bcfb-40dc-b9ae-10c809786599', '9183166d-2356-42df-a045-416748260106', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('2117488e-8c45-4f20-9f7a-438145e889cf', b'1', '1', '806f4f41-e767-486f-897e-f53609351491', '9183166d-2356-42df-a045-416748260106', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('667bd203-04a3-445c-81f3-8834153e0ce0', b'1', '1', '5e41eb25-73c8-445b-b4bb-d6b0eb13b8be', '9183166d-2356-42df-a045-416748260106', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('1ab46ddf-7357-4781-8039-17fa2edec8c3', b'1', '1', '017c4814-2f06-423c-b162-6f4e914c740b', '9183166d-2356-42df-a045-416748260106', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('777e2434-0ea4-4d51-92a9-85dc51fcc22d', b'1', '1', '334ac8ae-25fb-47af-ae29-7cf11577af88', '9183166d-2356-42df-a045-416748260106', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('7165e3b5-aeb6-48b6-81f2-7a1867211d72', b'1', '1', 'c0cbe425-fe5f-47c1-932a-f8bcd702a869', '9183166d-2356-42df-a045-416748260106', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('6a0a54a5-f15d-4712-be95-f956dac5a2d7', b'1', '1', 'd46c0f38-8910-43af-b644-b8133adb8aa2', '9183166d-2356-42df-a045-416748260106', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('6d5aa37d-5757-4ec8-8b4b-75be55d98127', b'1', '1', '631c01cf-45a3-4ec8-9f84-20ca3f1500ad', '9183166d-2356-42df-a045-416748260106', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('064ef460-18ab-4e43-986b-c0e438e96d4a', b'1', '1', 'c6a1c81e-fc91-4492-a569-517dffe68840', '9183166d-2356-42df-a045-416748260106', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`)
VALUES ('8cad44f4-f333-4e15-93ec-a708b15b3f35', b'1', '1', '0bf7fac0-1b1e-428f-96ef-175f9eeb656c', '9183166d-2356-42df-a045-416748260106', '425744ba-6c10-47c0-91cf-5a4c05265b56');


INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('7bfc7da9-67fb-4bc1-bcb6-ad1e4be6ad8b', b'1', 'v1/user_role', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('cd50860e-a4dc-4453-a055-6bee3212437d', b'1', 'v1/photo', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('ec76a4ca-9198-4e8d-bf23-fc2d521b676d', b'1', 'v1/user', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('50e8bd15-2f28-4328-b509-112f1ee1063c', b'1', 'v1/user', 'PUT', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('29ad3715-bdf4-451b-8fc1-de5e7e763c19', b'1', 'v1/user/role', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('a0c2f5ce-c329-422a-8c80-cc0b2ab3b80e', b'1', 'v1/user/context', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('0547c687-13f4-427c-b8ab-40624d89cb10', b'1', 'v1/user/context', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('78fcd436-259a-4641-ada6-334a8b289c73', b'1', 'v1/employee', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('e917ae09-4fd4-4926-b7a0-3a51b103ad04', b'1', 'v1/employee', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('76c7d653-da66-4c4a-9aca-dfa8bc420cce', b'1', 'v1/employee', 'PUT', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('fb488703-da43-4e71-b30d-d7803a6be7ff', b'1', 'v1/employee', 'DELETE', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('fb3d0e98-4c9c-404a-bc01-1ec9ed97b032', b'1', 'v1/employee/email', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('0ce9947e-90cf-4985-b887-06d89845fef7', b'1', 'v1/employee/email', 'PUT', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('c1ed9198-8e87-4ae6-8320-fd3a5abfcd15', b'1', 'v1/employee/email', 'DELETE', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('fe94998d-c4f4-485f-9572-cac338e34008', b'1', 'v1/employee/contact', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('c53b3e6a-1d44-46d1-890e-91b85b41ecda', b'1', 'v1/employee/contact', 'PUT', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('458fbc36-50d7-4d21-8b62-21c139287f1f', b'1', 'v1/employee/contact', 'DELETE', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('15d33003-5ed8-4b75-9069-d812adb08ba8', b'1', 'v1/employee/additional', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('026c398a-1aac-435b-9ab4-2292ab3b3c70', b'1', 'v1/employee/designation', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('42b9e6a0-641d-4bb8-924f-0ab58dc78d11', b'1', 'v1/employee/designation', 'PUT', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('a09986b4-80bc-4400-a29b-35edbe5e72b7', b'1', 'v1/employee/designation', 'DELETE', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('cfbc3788-b45b-41ba-8775-2e91aac6059f', b'1', 'v1/team', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('f00868cb-b274-4ce9-8382-db84975710f3', b'1', 'v1/team', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('c204581d-e58d-404e-ae4f-39d3cbe1a1b5', b'1', 'v1/team', 'PUT', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('c7881855-8f26-4f47-9f4f-0e9fd1ab00e0', b'1', 'v1/team', 'DELETE', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('62a4c617-525c-4379-b836-7097ef819d4e', b'1', 'v1/team/member', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('b8b7b379-5c61-4876-ae6b-25978d8428b3', b'1', 'v1/team/project', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('5929e88e-4f54-4668-bb89-c87ef9d8edde', b'1', 'v1/project', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('d4f06a41-410c-4333-8389-b36da02b99fa', b'1', 'v1/project', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('3bffaa52-bfcd-4fd7-9d5d-9bc3337fa298', b'1', 'v1/project', 'PUT', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('95faed98-8979-4b56-8400-64a497388420', b'1', 'v1/project', 'DELETE', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('3aaa1c45-b222-4e0b-a321-0049c371ebd7', b'1', 'v1/project/team', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('8d044a8f-2f21-4abc-b423-0e31aa023bd9', b'1', 'v1/project/client', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('e8db9345-d585-4a37-8ca9-d49820390ce0', b'1', 'v1/client', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('59c09986-3289-4a49-bf5a-b0145a4c2a92', b'1', 'v1/client', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('19d52982-814c-4da0-88a5-2eccc3bdbfab', b'1', 'v1/client', 'PUT', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('e86c5f03-f514-4b55-8164-1912a8c96c8f', b'1', 'v1/client', 'DELETE', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('66d9c39d-a372-401e-baec-a3281fe2e031', b'1', 'v1/client/project', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('4c311228-f611-449c-8ff5-bfcf0d6e8798', b'1', 'v1/my_leave_requests', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('47311515-21fe-43bb-b268-9f5f940d8cf0', b'1', 'v1/my_leave_requests', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('500c3daa-3e05-40f9-8d34-bc43c5d610d5', b'1', 'v1/my_leave_requests', 'PATCH', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('0d09576d-a013-46ee-88b0-8366007a7bf7', b'1', 'v1/my_leave_requests', 'DELETE', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('2ea485a7-7924-4fbf-81aa-44d120f5137e', b'1', 'v1/my_leave_requests/is_available', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('981b0f41-295b-4f56-bb2d-16afd62be6d8', b'1', 'v1/leave_summaries', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('4b2fc151-9a9f-4825-9cb4-e1d2da6b1272', b'1', 'v1/leave_details', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('c8162a0c-a97b-4fc3-9976-c764e9511856', b'1', 'v1/leave_request/approval', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('c555b2a3-1d28-4846-afd2-d9acaa409c1f', b'1', 'v1/employees/leave_summaries', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('f0bb4bd8-2455-48ca-8f35-a6ed15fbb2e0', b'1', 'v1/employees/leave_details', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('31050599-92b0-464c-8326-7f5674e2c9ed', b'1', 'v1/special_leave_requests', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('52e05c90-899c-472f-bb20-eed9d97a44f7', b'1', 'v1/special_leave_requests', 'PATCH', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('aa85fa9f-b9cd-4a4e-a6d5-4eea0c3c678e', b'1', 'v1/special_leave_requests', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('055f93af-39fe-4db4-af6b-54b4a9ce0cf5', b'1', 'v1/special_leave_requests', 'DELETE', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('b5a0e905-12e7-4c45-aeb1-18e385667e0f', b'1', 'v1/attendance_schedule/is_available', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('383b0e3b-7460-42f1-96d0-76516562947e', b'1', 'v1/attendance_schedule/upload', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('96464902-d164-419c-afa8-ba3d60b2e466', b'1', 'v1/attendance_schedule/temporary', 'PATCH', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('926ed2c4-904a-406f-a415-0793b4e62ea6', b'1', 'v1/attendance_schedule/temporary', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('9bd8afe5-2cd4-4e40-97f6-de67d3b6ebc8', b'1', 'v1/attendance_schedule/status', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('93c7ee1d-f870-4854-8847-d97b6965286e', b'1', 'v1/attendance_schedule', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('89a99177-7e3c-43f4-93b4-2ac2238eaa4a', b'1', 'v1/attendance_schedule', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('fb4a8228-cd9a-4628-a27d-86c85a0783da', b'1', 'v1/attendance_schedule/draft', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('be87d9ed-969b-4185-bcc5-d727b111cc1a', b'1', 'v1/attendance_schedule', 'DELETE', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('414470f3-3b59-44e6-b017-26db1a144cef', b'1', 'v1/my_work_from_home_requests', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('4c751fe7-0e6f-425f-994a-ed8dd13f1624', b'1', 'v1/my_work_from_home_requests', 'PATCH', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('8acf9d5a-523e-40a6-a734-b0d21b1873cb', b'1', 'v1/my_work_from_home_requests', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('45f037f7-806d-4576-b7a0-d4981d017486', b'1', 'v1/work_from_home_requests', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('3ee8c84d-e981-4b30-aea8-ac7de2622704', b'1', 'v1/work_from_home_requests/approval', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('1431fbd5-e59b-405b-9f40-082af5310531', b'1', 'v1/holiday', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('70a1e455-8517-43b7-a53c-3c85d179ea97', b'1', 'v1/holiday', 'PATCH', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('f9f8d78a-d981-404c-939c-946517c9b35b', b'1', 'v1/holiday', 'DELETE', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('3543cc8a-6d4f-4bf8-82ca-991be243c72d', b'1', 'v1/holiday', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('31624f57-c484-4d0e-b132-9e912380d1fb', b'1', 'v1/holiday/copy', 'PATCH', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('46d387ae-6f60-4030-a25d-47a935e5e907', b'1', 'v1/holiday/publish', 'PATCH', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('415a9f83-7408-49cf-9aaf-ac502e626da1', b'1', 'v1/notification', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('e21652e2-99f0-4041-80aa-ac13eb3aaaf6', b'1', 'v1/notification_template', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('c9681e2b-1b23-4938-b6e9-583890da3061', b'1', 'v1/notification_template', 'PUT', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('ba4c0274-35c5-4bee-9242-8918793fc87b', b'1', 'v1/notification_template', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');


INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('2d6e2046-4eb8-4094-bbdd-fb1a2b481988', 'System', '1', 'a0c2f5ce-c329-422a-8c80-cc0b2ab3b80e');
INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('260703bc-11f6-4faf-a901-3335b4af7adc', 'System', '1', '0547c687-13f4-427c-b8ab-40624d89cb10');
INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('4e26290b-0b16-4b23-a606-d4d8d9045d8a', 'Authenticated', '1', '0d09576d-a013-46ee-88b0-8366007a7bf7');
INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('b5868acc-e42d-4115-ac75-b706a2441c2b', 'Authenticated', '1', '500c3daa-3e05-40f9-8d34-bc43c5d610d5');
INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('fcad5588-56b0-4998-afd5-53fa79e9326a', 'Authenticated', '1', '47311515-21fe-43bb-b268-9f5f940d8cf0');
INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('180e2df7-63c3-43b4-827a-b0bdd43c85be', 'Authenticated', '1', 'cd50860e-a4dc-4453-a055-6bee3212437d');
INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('afea7fa2-857b-48a2-8857-597037cc0f6e', 'Authenticated', '1', '4c311228-f611-449c-8ff5-bfcf0d6e8798');
INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('3cce6260-cb80-422c-9867-bc2c13889b2b', 'System', '1', '7bfc7da9-67fb-4bc1-bcb6-ad1e4be6ad8b');
INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('5d8d3f5d-d982-4183-801f-06d3bbf13ff2', 'Authenticated', '1', '2ea485a7-7924-4fbf-81aa-44d120f5137e');
INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('4a2dfacf-6c6f-4660-8d37-e8e030ce503b', 'System', '1', '415a9f83-7408-49cf-9aaf-ac502e626da1');
INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('73c7880a-1f2c-4a41-ab24-1311655633d3', 'System', '1', 'ba4c0274-35c5-4bee-9242-8918793fc87b');
INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('3d919f35-592e-4cd7-a429-a8695342cf31', 'System', '1', '29ad3715-bdf4-451b-8fc1-de5e7e763c19');
INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('c7e866cb-1ea8-45ff-aef1-1f3ca23dbb87', 'Authenticated', '1', '414470f3-3b59-44e6-b017-26db1a144cef');
INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('38ef809d-a951-4fd6-94a3-854a9e36d256', 'Authenticated', '1', '4c751fe7-0e6f-425f-994a-ed8dd13f1624');
INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('217f35eb-2c4a-4e1f-9089-aefe91a0c6b3', 'Authenticated', '1', '8acf9d5a-523e-40a6-a734-b0d21b1873cb');
INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('a3cd7eaa-7aa1-4caa-af7e-5b9dc70844af', 'System', '1', '50e8bd15-2f28-4328-b509-112f1ee1063c');
INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('b44da690-c986-4ef6-98d8-190bc77df4b0', 'System', '1', 'ec76a4ca-9198-4e8d-bf23-fc2d521b676d');
INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('6bf12e86-1824-4722-8fb2-9813aa2fa3d4', 'Authenticated', '1', '3349ac71-c9de-4638-bcdf-285743809f23');


------- HR & Manager Menu API --------
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('4c6830cb-011b-412b-844d-137899316f23', b'1', '1', '78fcd436-259a-4641-ada6-334a8b289c73', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('e78a5cc4-d659-4648-9454-e1cd71724a27', b'1', '1', 'e917ae09-4fd4-4926-b7a0-3a51b103ad04', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('f0c1dc94-c4ac-4fb9-92ad-ee22707e60cf', b'1', '1', '76c7d653-da66-4c4a-9aca-dfa8bc420cce', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('5a1b71da-6eda-4520-99ef-55b772627cf1', b'1', '1', 'fb488703-da43-4e71-b30d-d7803a6be7ff', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('0869528e-4a1f-4772-9917-d2cac9e01434', b'1', '1', 'fb3d0e98-4c9c-404a-bc01-1ec9ed97b032', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('3c56b1dc-fa8f-4f43-93e3-3115f88a7fc6', b'1', '1', '0ce9947e-90cf-4985-b887-06d89845fef7', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('da9ed58e-e878-4f89-87f4-6e06ac6a2f19', b'1', '1', 'c1ed9198-8e87-4ae6-8320-fd3a5abfcd15', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('c4548d3c-d114-4fc1-9cf6-4521136a333b', b'1', '1', 'fe94998d-c4f4-485f-9572-cac338e34008', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('714883f8-4f49-4759-9e7c-1f850c6d9ce3', b'1', '1', 'c53b3e6a-1d44-46d1-890e-91b85b41ecda', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('1d8e2ab1-6761-447a-8d85-089ce4f27ea8', b'1', '1', '458fbc36-50d7-4d21-8b62-21c139287f1f', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('1694f55a-0daf-4ab7-a5ec-af5e9b7db8cc', b'1', '1', 'cfbc3788-b45b-41ba-8775-2e91aac6059f', '136e5d70-c4bf-4d63-832e-afe51a2b8606', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('08911320-245e-4e47-8c74-3ddeb2db2e9f', b'1', '1', 'f00868cb-b274-4ce9-8382-db84975710f3', '136e5d70-c4bf-4d63-832e-afe51a2b8606', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('84ae9f8e-4d19-4174-970f-5ac1f694e2e4', b'1', '1', 'c204581d-e58d-404e-ae4f-39d3cbe1a1b5', '136e5d70-c4bf-4d63-832e-afe51a2b8606', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('dad94326-cfa5-4525-8e84-f16011451428', b'1', '1', 'c7881855-8f26-4f47-9f4f-0e9fd1ab00e0', '136e5d70-c4bf-4d63-832e-afe51a2b8606', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('3d3085ff-b75d-42f9-8a2f-e4edc51e3c2f', b'1', '1', '5929e88e-4f54-4668-bb89-c87ef9d8edde', '3809289a-ba52-42d2-8b5e-a54d5e5a51b8', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('72776965-75ea-4cab-805b-51ba58dbb525', b'1', '1', 'e8db9345-d585-4a37-8ca9-d49820390ce0', 'adccaaab-6b78-438b-a8e2-32bb41cac366', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('afe10549-f083-4d5f-8ec0-c9fb06b1048b', b'1', '1', '15d33003-5ed8-4b75-9069-d812adb08ba8', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('e8fea43e-aa75-4495-9208-3714d787fab6', b'1', '1', '026c398a-1aac-435b-9ab4-2292ab3b3c70', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('6a4372f9-d938-47b7-8b2e-7adda0126865', b'1', '1', '42b9e6a0-641d-4bb8-924f-0ab58dc78d11', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('590536cc-6084-4c26-ae09-f4a247577cec', b'1', '1', 'a09986b4-80bc-4400-a29b-35edbe5e72b7', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('8ca61bf5-cee5-4a20-a045-55d4a58460fd', b'1', '1', '62a4c617-525c-4379-b836-7097ef819d4e', '136e5d70-c4bf-4d63-832e-afe51a2b8606', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('46a557d5-3696-4f7b-8d8a-8e319b732a00', b'1', '1', 'b8b7b379-5c61-4876-ae6b-25978d8428b3', '136e5d70-c4bf-4d63-832e-afe51a2b8606', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('83b882c1-d59e-4768-88b4-195bb2c0937c', b'1', '1', 'd4f06a41-410c-4333-8389-b36da02b99fa', '3809289a-ba52-42d2-8b5e-a54d5e5a51b8', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('129b8211-a306-43c8-8f09-0939a64af1e9', b'1', '1', '3bffaa52-bfcd-4fd7-9d5d-9bc3337fa298', '3809289a-ba52-42d2-8b5e-a54d5e5a51b8', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('c81c9306-4c74-470f-82e9-b7207dead37c', b'1', '1', '95faed98-8979-4b56-8400-64a497388420', '3809289a-ba52-42d2-8b5e-a54d5e5a51b8', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('4fa7f8d3-8805-4265-a470-f7471b77cf9b', b'1', '1', '3aaa1c45-b222-4e0b-a321-0049c371ebd7', '3809289a-ba52-42d2-8b5e-a54d5e5a51b8', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('342313c2-8961-4603-880f-fa70367ce320', b'1', '1', '8d044a8f-2f21-4abc-b423-0e31aa023bd9', '3809289a-ba52-42d2-8b5e-a54d5e5a51b8', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('bd18840b-9fdc-4acb-b320-0dfe108ec901', b'1', '1', '59c09986-3289-4a49-bf5a-b0145a4c2a92', 'adccaaab-6b78-438b-a8e2-32bb41cac366', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('b28c61f4-93d1-4bdf-8833-0fe38a1c904e', b'1', '1', '19d52982-814c-4da0-88a5-2eccc3bdbfab', 'adccaaab-6b78-438b-a8e2-32bb41cac366', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('27937ea1-26f7-413b-b3fd-cad5f1afd876', b'1', '1', 'e86c5f03-f514-4b55-8164-1912a8c96c8f', 'adccaaab-6b78-438b-a8e2-32bb41cac366', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('fc3244c7-06ce-4eb9-8488-f5b04922feea', b'1', '1', '66d9c39d-a372-401e-baec-a3281fe2e031', 'adccaaab-6b78-438b-a8e2-32bb41cac366', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('db42a575-bcc4-4b8b-ae7b-969b37e09132', b'1', '1', '981b0f41-295b-4f56-bb2d-16afd62be6d8', 'd4465b72-2bc0-4db9-950a-9d22885f6f47', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('81ab8075-c321-4df0-882d-06c65a953595', b'1', '1', 'c555b2a3-1d28-4846-afd2-d9acaa409c1f', 'd4465b72-2bc0-4db9-950a-9d22885f6f47', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('47f66a85-ddb4-43bc-b18f-6419ceb60c62', b'1', '1', 'f0bb4bd8-2455-48ca-8f35-a6ed15fbb2e0', '37bc385d-b3ae-42a7-8c84-e92057cf8f2d', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('811ae0df-f278-4c95-ac6c-2a61814c5e66', b'1', '1', '4b2fc151-9a9f-4825-9cb4-e1d2da6b1272', '37bc385d-b3ae-42a7-8c84-e92057cf8f2d', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('873033f2-2410-4174-98f2-841a13a1b633', b'1', '1', 'c8162a0c-a97b-4fc3-9976-c764e9511856', '7a586ab0-8785-4b72-8091-b741e9a26ec5', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('8d113e2b-db60-4f39-8d3f-80e2eff15496', b'1', '1', 'b5a0e905-12e7-4c45-aeb1-18e385667e0f', '17ef042b-0ebe-4e3e-8955-8c12d83f5b08', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('999bcf13-cf51-4079-b2c3-7afc22315e31', b'1', '1', '383b0e3b-7460-42f1-96d0-76516562947e', '17ef042b-0ebe-4e3e-8955-8c12d83f5b08', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('42f153ad-d155-489e-b179-ca2223214e20', b'1', '1', '96464902-d164-419c-afa8-ba3d60b2e466', '17ef042b-0ebe-4e3e-8955-8c12d83f5b08', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('dbb9d3ac-959a-471f-b088-304c47ded2ab', b'1', '1', '926ed2c4-904a-406f-a415-0793b4e62ea6', '17ef042b-0ebe-4e3e-8955-8c12d83f5b08', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('607a4324-35ee-4cfb-ae31-33642d1d86fb', b'1', '1', '9bd8afe5-2cd4-4e40-97f6-de67d3b6ebc8', 'bf29b1c9-ef67-4475-9364-d4c044b26925', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('1acaf354-e6bf-49ae-8220-a80e9af233fb', b'1', '1', '93c7ee1d-f870-4854-8847-d97b6965286e', '25037cc4-21ad-48f2-bee8-cc39151b9c79', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('28244773-c057-4c00-b7e7-92e85730a648', b'1', '1', '89a99177-7e3c-43f4-93b4-2ac2238eaa4a', '25037cc4-21ad-48f2-bee8-cc39151b9c79', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('050a8b44-7fd5-495d-9863-90bbd2432c07', b'1', '1', 'fb4a8228-cd9a-4628-a27d-86c85a0783da', '17ef042b-0ebe-4e3e-8955-8c12d83f5b08', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('b556bf51-bf68-48df-8a8b-7f11b045452a', b'1', '1', 'be87d9ed-969b-4185-bcc5-d727b111cc1a', '25037cc4-21ad-48f2-bee8-cc39151b9c79', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('3b1fe512-ded8-480b-9ca4-de630c352995', b'1', '1', '31050599-92b0-464c-8326-7f5674e2c9ed', '096e7e16-8439-4cf2-b6c7-0ddb8c749cec', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('89ead701-0d27-4896-88c2-0e3d071c2d3e', b'1', '1', '52e05c90-899c-472f-bb20-eed9d97a44f7', '096e7e16-8439-4cf2-b6c7-0ddb8c749cec', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('6f573cd8-4409-4bb4-ab7c-dbc6ccd81303', b'1', '1', 'aa85fa9f-b9cd-4a4e-a6d5-4eea0c3c678e', '096e7e16-8439-4cf2-b6c7-0ddb8c749cec', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('c75179e9-4d33-4151-9794-1d463abe8153', b'1', '1', '055f93af-39fe-4db4-af6b-54b4a9ce0cf5', '096e7e16-8439-4cf2-b6c7-0ddb8c749cec', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('6a0baf2c-1bcf-4682-b5a7-daac840da18f', b'1', '1', '1431fbd5-e59b-405b-9f40-082af5310531', '83ffac93-1403-4eeb-b070-078b768efb5d', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('b2f9fbfe-005f-4df8-b49c-50fb2d6f7a59', b'1', '1', '70a1e455-8517-43b7-a53c-3c85d179ea97', '83ffac93-1403-4eeb-b070-078b768efb5d', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('9012398f-e418-416a-9d72-9ecb3f4fe229', b'1', '1', 'f9f8d78a-d981-404c-939c-946517c9b35b', '83ffac93-1403-4eeb-b070-078b768efb5d', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('89e64893-9ab5-4b79-826e-d1101522b8b5', b'1', '1', '3543cc8a-6d4f-4bf8-82ca-991be243c72d', '83ffac93-1403-4eeb-b070-078b768efb5d', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('345b58ac-baf2-44b0-b844-ecbaa2557a19', b'1', '1', '31624f57-c484-4d0e-b132-9e912380d1fb', '83ffac93-1403-4eeb-b070-078b768efb5d', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('641cf3a1-cd7d-4925-a64c-b39ebcffc0cd', b'1', '1', '46d387ae-6f60-4030-a25d-47a935e5e907', '83ffac93-1403-4eeb-b070-078b768efb5d', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('309ab7f0-82a3-409a-8254-b95077460b9c', b'1', '1', 'e21652e2-99f0-4041-80aa-ac13eb3aaaf6', 'da368c3d-760d-4efc-8864-e845b28e0183', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('aba4da21-1f48-45dc-86bf-30cfc5b22dbc', b'1', '1', 'c9681e2b-1b23-4938-b6e9-583890da3061', 'da368c3d-760d-4efc-8864-e845b28e0183', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('2304eb2c-6b3f-44aa-b373-5f7f423b93e2', b'1', '1', 'ec76a4ca-9198-4e8d-bf23-fc2d521b676d', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('0a41a743-683d-4401-af7a-1ca9399f2071', b'1', '1', '45f037f7-806d-4576-b7a0-d4981d017486', '1ab0176c-ad4d-4cce-bda4-dd1b9001150e', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('9d867499-b307-40f3-85e9-4cff52682bd2', b'1', '1', '3ee8c84d-e981-4b30-aea8-ac7de2622704', '1ab0176c-ad4d-4cce-bda4-dd1b9001150e', '425744ba-6c10-47c0-91cf-5a4c05265b56');


----------- Lead Menu API ---------------
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('3ce7917f-bdbd-4db8-8954-adb486bb947d', b'1', '1', 'e917ae09-4fd4-4926-b7a0-3a51b103ad04', '7141dd5e-1998-4975-90ae-74d6a24fa7cf', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('41bf833a-bc2d-4ff4-bd0b-c63ea6b430f2', b'1', '1', 'cfbc3788-b45b-41ba-8775-2e91aac6059f', 'b60399fd-dc74-4b81-8bcd-f14c7d38a040', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('85d55037-9ff6-4944-b72f-00ffa18a20c6', b'1', '1', '5929e88e-4f54-4668-bb89-c87ef9d8edde', 'c2a9db50-cc20-4a74-a82b-3fe565d577d5', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('1dc6d4cd-2e68-452e-a7db-eae2e5f4bc58', b'1', '1', '981b0f41-295b-4f56-bb2d-16afd62be6d8', 'da11dd0c-5bc7-42f8-bd1b-62cd7207fee0', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('e9eda4ae-aa9f-438e-8072-04b1fefe82fe', b'1', '1', 'c555b2a3-1d28-4846-afd2-d9acaa409c1f', 'da11dd0c-5bc7-42f8-bd1b-62cd7207fee0', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('39ccb407-199d-4d2e-9bca-79424671c7e3', b'1', '1', '4b2fc151-9a9f-4825-9cb4-e1d2da6b1272', '77a75531-89d3-4c05-99ff-8a29b2f935e6', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('66849403-8941-44b1-9658-037761facbaa', b'1', '1', 'f0bb4bd8-2455-48ca-8f35-a6ed15fbb2e0', '77a75531-89d3-4c05-99ff-8a29b2f935e6', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('95ee6672-bcf8-48d8-89e4-d30bf87152bf', b'1', '1', 'c8162a0c-a97b-4fc3-9976-c764e9511856', '88ea7da2-36a1-454a-a735-5ca1ce60dda4', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('289389f9-5a8b-40de-86df-545746f494c7', b'1', '1', '89a99177-7e3c-43f4-93b4-2ac2238eaa4a', '6a0a5d7c-7903-46f1-9a3d-fd44373fa85c', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('468ac42b-fc6a-459c-ae46-9e1f1892f820', b'1', '1', '3ee8c84d-e981-4b30-aea8-ac7de2622704', '3c1b925b-0356-4d0b-832d-81047ceaaf30', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('ad3f720a-a8e3-45c7-8d7b-9331ed079e04', b'1', '1', '45f037f7-806d-4576-b7a0-d4981d017486', '3c1b925b-0356-4d0b-832d-81047ceaaf30', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('47b328f8-e8d6-4a48-87ad-5883096e2e33', b'1', '1', '3543cc8a-6d4f-4bf8-82ca-991be243c72d', '9ff595ee-cf01-4475-b89a-9678c25e80d0', '425744ba-6c10-47c0-91cf-5a4c05265b56');


----------- Member Menu API -------------
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('57d704a4-142f-4a2b-86ec-d5040c4a9827', b'1', '1', 'e917ae09-4fd4-4926-b7a0-3a51b103ad04', '2d96b168-7334-46fa-9776-65438cb719eb', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('e491e8ad-8064-40d4-ba90-6ce8ba249553', b'1', '1', 'cfbc3788-b45b-41ba-8775-2e91aac6059f', 'c5e3e9c8-bcfb-40dc-b9ae-10c809786599', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('7f8bacd7-c4d5-4a3a-b24e-e523f1e91c22', b'1', '1', '5929e88e-4f54-4668-bb89-c87ef9d8edde', '806f4f41-e767-486f-897e-f53609351491', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('7e0b6e43-15a1-4242-b066-28d7256a13f8', b'1', '1', '981b0f41-295b-4f56-bb2d-16afd62be6d8', '017c4814-2f06-423c-b162-6f4e914c740b', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('cb8e59c6-98a2-4dda-8e44-3cbfe18de29b', b'1', '1', 'c555b2a3-1d28-4846-afd2-d9acaa409c1f', '017c4814-2f06-423c-b162-6f4e914c740b', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('5f2f5e19-9a0b-4dc2-a222-bd9b5f22209d', b'1', '1', '89a99177-7e3c-43f4-93b4-2ac2238eaa4a', 'd46c0f38-8910-43af-b644-b8133adb8aa2', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('1463656f-c196-45b5-942a-4b1a280e3e14', b'1', '1', '3543cc8a-6d4f-4bf8-82ca-991be243c72d', '0bf7fac0-1b1e-428f-96ef-175f9eeb656c', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('9c38629b-c068-42bd-86a9-cd6e7ef877d3', b'1', '1', 'f0bb4bd8-2455-48ca-8f35-a6ed15fbb2e0', '017c4814-2f06-423c-b162-6f4e914c740b', '425744ba-6c10-47c0-91cf-5a4c05265b56');

