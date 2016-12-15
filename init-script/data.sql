----------------------------------------------------- Insert into dem_service database ------------------------------------------

INSERT INTO `dem_service`.`ref_employee_contact_number_type` (`employee_contact_type_id`, `description`, `is_active`, `name`) VALUES ('cf2689f0-5165-4c02-a55c-25a9a32d8dd4', NULL, b'1', 'Official');
INSERT INTO `dem_service`.`ref_employee_contact_number_type` (`employee_contact_type_id`, `description`, `is_active`, `name`) VALUES ('807c9237-7173-4a5e-a45e-384137b7fab2', NULL, b'1', 'Personal');
INSERT INTO `dem_service`.`ref_employee_contact_number_type` (`employee_contact_type_id`, `description`, `is_active`, `name`) VALUES ('cf9d4ed1-6f98-4e7f-a77a-2c36e936254d', NULL, b'1', 'Emergency');

INSERT INTO `dem_service`.`ref_employee_email_type` (`employee_email_type_id`, `description`, `is_active`, `name`) VALUES ('78b5c3c5-5470-49f7-aafe-b016a33b3883', NULL, b'1', 'Official');
INSERT INTO `dem_service`.`ref_employee_email_type` (`employee_email_type_id`, `description`, `is_active`, `name`) VALUES ('b6540691-773c-4ee8-9e24-07c292887e73', NULL, b'1', 'Personal');

INSERT INTO `dem_service`.`ref_employee_status` (`employee_status_id`, `description`, `is_active`, `name`) VALUES ('10021760-5ace-46b9-8b2b-b05cb2a65d43', NULL, b'1', 'Full-Time');
INSERT INTO `dem_service`.`ref_employee_status` (`employee_status_id`, `description`, `is_active`, `name`) VALUES ('4797c659-b2e6-42e7-ac93-28c9e781b0b1', NULL, b'1', 'Part-Time');

INSERT INTO `dem_service`.`ref_leave_status` (`leave_status_id`, `description`, `is_active`, `name`, `priority`) VALUES ('19bad1c8-5e6c-41c1-bccb-5d4c360baf25', NULL, b'1', 'Applied', 1);
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


----------------------------------------------------- Insert into service_authenticate database ------------------------------------------

INSERT INTO `service_authentication`.`ref_auth_handler` (`auth_handler_id`, `name`, `type_impl`, `version`)
VALUES ('78fd9528-431a-4cfb-adde-552b6c87fba8', 'Database Login', 'com.dsi.authentication.service.impl.DBLoginHandlerImpl', '1');

INSERT INTO `service_authentication`.`dsi_tenant` (`tenant_id`, `is_active`, `name`, `short_name`, `version`, `auth_handler_id`, `secret_key`)
VALUES ('cc4e0554-6582-498b-9ae2-ad3c612f8e8e', b'1', 'Dynamic Solution', 'DSI', '1', '78fd9528-431a-4cfb-adde-552b6c87fba8', '87c63aae-917c-42ce-b4c7-8a4847db4133');

INSERT INTO `service_authentication`.`dsi_login` (`login_id`, `created_by`, `created_date`, `email`, `first_name`, `last_name`, `modified_by`, `modified_date`, `password`, `reset_password_token`, `reset_token_expire_time`, `salt`, `user_id`, `version`)
VALUES ('f264874e-0331-4fcd-8e92-49513a7724c2', '354fd26f-c642-40ac-b5cf-718c90081598', '2016-06-15 00:00:00', 'sabbir@gmail.com', 'Sabbir', 'Ahmed', '354fd26f-c642-40ac-b5cf-718c90081598', '2016-06-15 00:00:00', 'password', NULL, NULL, '87c63aae-917c-42ce-b4c7-8a4847db4133', 'f9e9a19f-4859-4e8c-a8f4-dc134629a57b', '1');


----------------------------------------------------- Insert into service_authorization database ------------------------------------------


INSERT INTO `service_authorization`.`dsi_system` (`system_id`, `is_active`, `name`, `short_name`, `tenant_id`, `version`)
VALUES ('425744ba-6c10-47c0-91cf-5a4c05265b56', b'1', 'DSI Employee Management', 'DEM', 'cc4e0554-6582-498b-9ae2-ad3c612f8e8e', '1');


INSERT INTO `service_authorization`.`dsi_role` (`role_id`, `is_active`, `name`, `version`)
VALUES ('fe3e0492-c80b-431a-8b6e-658af6a2c8d7', b'1', 'Manager', '1');
INSERT INTO `service_authorization`.`dsi_role` (`role_id`, `is_active`, `name`, `version`)
VALUES ('33394716-ab11-4e6d-8baa-dd4cb214befc', b'1', 'HR', '1');
INSERT INTO `service_authorization`.`dsi_role` (`role_id`, `is_active`, `name`, `version`)
VALUES ('9183166d-2356-42df-a045-416748260106', b'1', 'Member', '1');


INSERT INTO `service_authorization`.`dsi_user` (`user_id`, `created_by`, `created_date`, `email`, `first_name`, `gender`, `last_name`, `modified_by`, `modified_date`, `phone`, `tenant_id`, `version`)
VALUES ('f9e9a19f-4859-4e8c-a8f4-dc134629a57b', '', '2016-07-01 00:00:00', 'sabbir@gmail.com', 'Sabbir', 'Male', 'Ahmed', '', '2016-07-01 00:00:00', '01676661557', 'cc4e0554-6582-498b-9ae2-ad3c612f8e8e', '1');


INSERT INTO `service_authorization`.`dsi_user_role` (`user_role_id`, `created_by`, `created_date`, `is_active`, `modified_by`, `modified_date`, `version`, `role_id`, `system_id`, `user_id`)
VALUES ('bc524e71-a785-40b3-9f5c-81e610dd994a', '', '2016-08-10 00:00:00', b'1', '', '2016-08-10 00:00:00', '1', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56', 'f9e9a19f-4859-4e8c-a8f4-dc134629a57b');


INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('79d53582-ed8c-47a3-b734-f41651f688ad', 'Employee Management', b'1', 'Employee', '2', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('136e5d70-c4bf-4d63-832e-afe51a2b8606', 'Team Management', b'1', 'Team', '3', NULL, '3');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('3809289a-ba52-42d2-8b5e-a54d5e5a51b8', 'Project Management', b'1', 'Project', '4', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('adccaaab-6b78-438b-a8e2-32bb41cac366', 'Client Management', b'1', 'Client', '5', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('108be377-64bc-4877-92fd-00fca650eedb', 'Leave Management', b'1', 'Leave', '6', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('bf29b1c9-ef67-4475-9364-d4c044b26925', 'Attendance Management', b'1', 'Attendance', '8', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('25037cc4-21ad-48f2-bee8-cc39151b9c79', 'Attendance History', b'1', 'History', '2', 'bf29b1c9-ef67-4475-9364-d4c044b26925', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('17ef042b-0ebe-4e3e-8955-8c12d83f5b08', 'Upload Attendance', b'1', 'Upload', '1', 'bf29b1c9-ef67-4475-9364-d4c044b26925', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('8176dae9-c6c0-4d7b-8112-4e2875e42a6b', 'Home Panel', b'1', 'Home', '1', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('d4465b72-2bc0-4db9-950a-9d22885f6f47', 'Leave Summary History', b'1', 'Leave Summary', '1', '108be377-64bc-4877-92fd-00fca650eedb', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('37bc385d-b3ae-42a7-8c84-e92057cf8f2d', 'Leave Detail History', b'1', 'Leave Detail', '2', '108be377-64bc-4877-92fd-00fca650eedb', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('7a586ab0-8785-4b72-8091-b741e9a26ec5', 'Pending Leave Applications', b'1', 'Pending Leave', '3', '108be377-64bc-4877-92fd-00fca650eedb', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('6da57037-88af-43db-a871-fd4be7b675be', 'My Leave Applications', b'1', 'My Leave', '4', '108be377-64bc-4877-92fd-00fca650eedb', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('096e7e16-8439-4cf2-b6c7-0ddb8c749cec', 'Special Leave', b'1', 'Special Leave', '7', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`)
VALUES ('83ffac93-1403-4eeb-b070-078b768efb5d', 'Holiday Management', b'1', 'Holiday', '8', NULL, '1');

-- Admin role
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

-- Member role



INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('7bfc7da9-67fb-4bc1-bcb6-ad1e4be6ad8b', b'1', 'v1/user_role', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('cd50860e-a4dc-4453-a055-6bee3212437d', b'1', 'v1/photo', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

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
VALUES ('8d113e2b-db60-4f39-8d3f-80e2eff15496', b'1', '1', 'b5a0e905-12e7-4c45-aeb1-18e385667e0f', 'bf29b1c9-ef67-4475-9364-d4c044b26925', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('999bcf13-cf51-4079-b2c3-7afc22315e31', b'1', '1', '383b0e3b-7460-42f1-96d0-76516562947e', 'bf29b1c9-ef67-4475-9364-d4c044b26925', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('42f153ad-d155-489e-b179-ca2223214e20', b'1', '1', '96464902-d164-419c-afa8-ba3d60b2e466', 'bf29b1c9-ef67-4475-9364-d4c044b26925', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('dbb9d3ac-959a-471f-b088-304c47ded2ab', b'1', '1', '926ed2c4-904a-406f-a415-0793b4e62ea6', 'bf29b1c9-ef67-4475-9364-d4c044b26925', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('607a4324-35ee-4cfb-ae31-33642d1d86fb', b'1', '1', '9bd8afe5-2cd4-4e40-97f6-de67d3b6ebc8', 'bf29b1c9-ef67-4475-9364-d4c044b26925', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('1acaf354-e6bf-49ae-8220-a80e9af233fb', b'1', '1', '93c7ee1d-f870-4854-8847-d97b6965286e', 'bf29b1c9-ef67-4475-9364-d4c044b26925', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('28244773-c057-4c00-b7e7-92e85730a648', b'1', '1', '89a99177-7e3c-43f4-93b4-2ac2238eaa4a', 'bf29b1c9-ef67-4475-9364-d4c044b26925', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('050a8b44-7fd5-495d-9863-90bbd2432c07', b'1', '1', 'fb4a8228-cd9a-4628-a27d-86c85a0783da', 'bf29b1c9-ef67-4475-9364-d4c044b26925', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`)
VALUES ('b556bf51-bf68-48df-8a8b-7f11b045452a', b'1', '1', 'be87d9ed-969b-4185-bcc5-d727b111cc1a', 'bf29b1c9-ef67-4475-9364-d4c044b26925', '425744ba-6c10-47c0-91cf-5a4c05265b56');
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


INSERT INTO `dem_service`.`dsi_employee` (`employee_id`, `bank_ac_no`, `created_date`, `date_of_confirmation`, `e_tin_id`, `employee_no`, `first_name`, `github_id`, `ip_address`, `is_active`, `joining_date`, `last_modified_date`, `last_name`, `mac_address`, `national_id`, `nick_name`, `user_id`, `resign_date`, `skype_id`, `version`)
VALUES ('e64412db-01e2-4691-bd52-76dac012b1be', '1234RT', '2016-10-04 00:00:00', '2016-09-01 00:00:00', 'RTG234', '121', 'Sabbir', 'sabbir.dsi', '127.0.0.184', NULL, '2016-09-01 00:00:00', '2016-10-04 00:00:00', 'Ahmed', NULL, NULL, NULL, 'f9e9a19f-4859-4e8c-a8f4-dc134629a57b', NULL, 'sabbir.dsi', '1');

INSERT INTO `dem_service`.`dsi_employee_info` (`employee_info_id`, `blood_group`, `date_of_birth`, `father_name`, `gender`, `mother_name`, `permanent_address`, `photo_url`, `present_address`, `spouse_name`, `employee_id`, `version`)
VALUES ('dce8d5e6-9a78-4c58-aaab-f3fc1f31be79', 'A+', '1991-05-04 00:00:00', NULL, 'Male', NULL, 'Mirpur', NULL, 'Mirpur', NULL, 'e64412db-01e2-4691-bd52-76dac012b1be', '1');

INSERT INTO `dem_service`.`dsi_employee_contact_number` (`employee_contact_number_id`, `phone`, `employee_id`, `type_id`, `version`)
VALUES ('6c875966-50f4-4a52-8dd2-bb9bbfdb8a78', '01676661557', 'e64412db-01e2-4691-bd52-76dac012b1be', '807c9237-7173-4a5e-a45e-384137b7fab2', '1');

INSERT INTO `dem_service`.`dsi_employee_designation` (`employee_designation_id`, `designation_date`, `name`, `employee_id`, `is_current`, `version`)
VALUES ('a0302b09-c242-4640-90e2-f27602739415', '2016-10-01 00:00:00', 'Software Engineer', 'e64412db-01e2-4691-bd52-76dac012b1be', NULL, '1');

INSERT INTO `dem_service`.`dsi_employee_email` (`employee_email_id`, `email`, `employee_id`, `type_id`, `is_preferred`, `version`)
VALUES ('2c300dbd-adb5-4743-9f0a-5e412cf82ab1', 'sabbir@gmail.com', 'e64412db-01e2-4691-bd52-76dac012b1be', '78b5c3c5-5470-49f7-aafe-b016a33b3883', NULL, '1');

INSERT INTO `dem_service`.`dsi_employee_leave` (`employee_leave_id`, `total_casual`, `total_leave`, `total_not_notify`, `total_sick`, `total_special_leave`, `employee_id`, `version`)
VALUES ('5778ba72-5937-4185-b00e-feff98ff8756', '0', '0', '0', '0', '0', 'e64412db-01e2-4691-bd52-76dac012b1be', '1');
