----------------------------------------------------- Insert into dem_service database ------------------------------------------

INSERT INTO `dem_service`.`ref_employee_contact_number_type` (`employee_contact_type_id`, `description`, `is_active`, `name`) VALUES ('cf2689f0-5165-4c02-a55c-25a9a32d8dd4', NULL, b'1', 'Official');
INSERT INTO `dem_service`.`ref_employee_contact_number_type` (`employee_contact_type_id`, `description`, `is_active`, `name`) VALUES ('807c9237-7173-4a5e-a45e-384137b7fab2', NULL, b'1', 'Personal');
INSERT INTO `dem_service`.`ref_employee_contact_number_type` (`employee_contact_type_id`, `description`, `is_active`, `name`) VALUES ('cf9d4ed1-6f98-4e7f-a77a-2c36e936254d', NULL, b'1', 'Emergency');

INSERT INTO `dem_service`.`ref_employee_email_type` (`employee_email_type_id`, `description`, `is_active`, `name`) VALUES ('78b5c3c5-5470-49f7-aafe-b016a33b3883', NULL, b'1', 'Official');
INSERT INTO `dem_service`.`ref_employee_email_type` (`employee_email_type_id`, `description`, `is_active`, `name`) VALUES ('b6540691-773c-4ee8-9e24-07c292887e73', NULL, b'1', 'Personal');

INSERT INTO `dem_service`.`ref_leave_status` (`leave_status_id`, `description`, `is_active`, `name`) VALUES ('19bad1c8-5e6c-41c1-bccb-5d4c360baf25', NULL, b'1', 'Applied');
INSERT INTO `dem_service`.`ref_leave_status` (`leave_status_id`, `description`, `is_active`, `name`) VALUES ('0a30ce24-edbf-41b9-86cd-b6f9954bbf92', NULL, b'1', 'Approved');
INSERT INTO `dem_service`.`ref_leave_status` (`leave_status_id`, `description`, `is_active`, `name`) VALUES ('4c3b5ba4-f9ae-48d2-8320-a4d6e6d17583', NULL, b'1', 'Denied');
INSERT INTO `dem_service`.`ref_leave_status` (`leave_status_id`, `description`, `is_active`, `name`) VALUES ('24a487dc-0167-4054-816e-7efbb1f89f8f', NULL, b'1', 'Canceled');

INSERT INTO `dem_service`.`ref_leave_type` (`leave_type_id`, `description`, `is_active`, `name`) VALUES ('553fb999-2ceb-4bf0-90f2-ccf52742c8a1', NULL, b'1', 'Casual');
INSERT INTO `dem_service`.`ref_leave_type` (`leave_type_id`, `description`, `is_active`, `name`) VALUES ('7e1c5ac7-f7a6-43e0-b5da-31cf8b899f10', NULL, b'1', 'Sick');
INSERT INTO `dem_service`.`ref_leave_type` (`leave_type_id`, `description`, `is_active`, `name`) VALUES ('80f8e6fa-ff9b-4249-a919-f41942c0c86a', NULL, b'1', 'Hajj');
INSERT INTO `dem_service`.`ref_leave_type` (`leave_type_id`, `description`, `is_active`, `name`) VALUES ('f2d34cf6-dd04-42fb-a4a8-4ad29100b5da', NULL, b'1', 'Maternity');

INSERT INTO `dem_service`.`ref_project_status` (`project_status_id`, `description`, `is_active`, `name`) VALUES ('1db5992a-aafb-4338-a267-4861e0a034f3', NULL, b'1', 'Active');
INSERT INTO `dem_service`.`ref_project_status` (`project_status_id`, `description`, `is_active`, `name`) VALUES ('ba709d6f-d236-4344-8634-cfa3d911913b', NULL, b'1', 'On Hold');
INSERT INTO `dem_service`.`ref_project_status` (`project_status_id`, `description`, `is_active`, `name`) VALUES ('b09eccab-c556-445e-a829-925ca5658565', NULL, b'1', 'Canceled');
INSERT INTO `dem_service`.`ref_project_status` (`project_status_id`, `description`, `is_active`, `name`) VALUES ('753404e1-e0a4-4cc0-b134-0d93788f59d7', NULL, b'1', 'Completed');

INSERT INTO `dem_service`.`ref_role` (`role_id`, `description`, `is_active`, `name`) VALUES ('31c1ab56-9ce4-463d-801d-e7be2f65ccb5', NULL, b'1', 'Manager');
INSERT INTO `dem_service`.`ref_role` (`role_id`, `description`, `is_active`, `name`) VALUES ('9716a905-a6c4-449c-b6f3-00faf17f9359', NULL, b'1', 'Architectural');
INSERT INTO `dem_service`.`ref_role` (`role_id`, `description`, `is_active`, `name`) VALUES ('ff4ea6f2-f0eb-4189-8d21-4673fd51a12a', NULL, b'1', 'Lead');
INSERT INTO `dem_service`.`ref_role` (`role_id`, `description`, `is_active`, `name`) VALUES ('d3478d16-5562-4701-ad0d-0fbea3efee94', NULL, b'1', 'Member');



----------------------------------------------------- Insert into service_authenticate database ------------------------------------------

INSERT INTO `service_authentication`.`ref_auth_handler` (`auth_handler_id`, `name`, `type_impl`, `version`) VALUES ('78fd9528-431a-4cfb-adde-552b6c87fba8', 'Database Login', 'com.dsi.authentication.service.impl.DBLoginHandlerImpl', '1');

INSERT INTO `service_authentication`.`dsi_tenant` (`tenant_id`, `is_active`, `name`, `short_name`, `version`, `auth_handler_id`, `secret_key`) VALUES ('cc4e0554-6582-498b-9ae2-ad3c612f8e8e', b'1', 'Dynamic Solution', 'DSI', '1', '78fd9528-431a-4cfb-adde-552b6c87fba8', '87c63aae-917c-42ce-b4c7-8a4847db4133');

INSERT INTO `service_authentication`.`dsi_login` (`login_id`, `created_by`, `created_date`, `email`, `first_name`, `last_name`, `modified_by`, `modified_date`, `password`, `reset_password_token`, `reset_token_expire_time`, `salt`, `user_id`, `version`) VALUES ('f264874e-0331-4fcd-8e92-49513a7724c2', '354fd26f-c642-40ac-b5cf-718c90081598',
'2016-06-15 00:00:00', 'sabbir@gmail.com', 'Sabbir', 'Ahmed', '354fd26f-c642-40ac-b5cf-718c90081598', '2016-06-15 00:00:00', 'password', NULL, NULL, '87c63aae-917c-42ce-b4c7-8a4847db4133', 'f9e9a19f-4859-4e8c-a8f4-dc134629a57b', '1');



----------------------------------------------------- Insert into service_authorization database ------------------------------------------

INSERT INTO `service_authorization`.`dsi_system` (`system_id`, `is_active`, `name`, `short_name`, `tenant_id`, `version`) VALUES ('425744ba-6c10-47c0-91cf-5a4c05265b56', b'1', 'DSI Employee Management', 'DEM', 'cc4e0554-6582-498b-9ae2-ad3c612f8e8e', '1');

INSERT INTO `service_authorization`.`dsi_role` (`role_id`, `is_active`, `name`, `version`) VALUES ('fe3e0492-c80b-431a-8b6e-658af6a2c8d7', b'1', 'Super Admin', '1');
INSERT INTO `service_authorization`.`dsi_role` (`role_id`, `is_active`, `name`, `version`) VALUES ('33394716-ab11-4e6d-8baa-dd4cb214befc', b'1', 'Admin', '1');
INSERT INTO `service_authorization`.`dsi_role` (`role_id`, `is_active`, `name`, `version`) VALUES ('9183166d-2356-42df-a045-416748260106', b'1', 'Member', '1');

INSERT INTO `service_authorization`.`dsi_user` (`user_id`, `created_by`, `created_date`, `email`, `first_name`, `gender`, `last_name`, `modified_by`, `modified_date`, `phone`, `tenant_id`, `version`) VALUES ('f9e9a19f-4859-4e8c-a8f4-dc134629a57b', '', '2016-07-01 00:00:00', 'sabbir@gmail.com', 'Sabbir', 'Male', 'Ahmed', '', '2016-07-01 00:00:00', '01676661557', 'cc4e0554-6582-498b-9ae2-ad3c612f8e8e', '1');
INSERT INTO `service_authorization`.`dsi_user_role` (`user_role_id`, `created_by`, `created_date`, `is_active`, `modified_by`, `modified_date`, `version`, `role_id`, `system_id`, `user_id`) VALUES ('bc524e71-a785-40b3-9f5c-81e610dd994a', '', '2016-08-10 00:00:00', b'1', '', '2016-08-10 00:00:00', '1', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56', 'f9e9a19f-4859-4e8c-a8f4-dc134629a57b');

INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`) VALUES ('79d53582-ed8c-47a3-b734-f41651f688ad', 'Employee Management', b'1', 'Employee', '2', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`) VALUES ('136e5d70-c4bf-4d63-832e-afe51a2b8606', 'Team Management', b'1', 'Team', '3', NULL, '3');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`) VALUES ('3809289a-ba52-42d2-8b5e-a54d5e5a51b8', 'Project Management', b'1', 'Project', '4', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`) VALUES ('adccaaab-6b78-438b-a8e2-32bb41cac366', 'Client Management', b'1', 'Client', '5', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`) VALUES ('108be377-64bc-4877-92fd-00fca650eedb', 'Leave Management', b'1', 'Leave', '6', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`) VALUES ('bf29b1c9-ef67-4475-9364-d4c044b26925', 'Attendance Management', b'1', 'Attendance', '7', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`) VALUES ('8176dae9-c6c0-4d7b-8112-4e2875e42a6b', 'Home Panel', b'1', 'Home', '1', NULL, '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`) VALUES ('d4465b72-2bc0-4db9-950a-9d22885f6f47', 'Leave Summary History', b'1', 'Leave Summary', '1', '108be377-64bc-4877-92fd-00fca650eedb', '1');
INSERT INTO `service_authorization`.`dsi_menu` (`menu_id`, `description`, `is_active`, `name`, `position`, `parent_menu_id`, `version`) VALUES ('37bc385d-b3ae-42a7-8c84-e92057cf8f2d', 'Leave Detail History', b'1', 'Leave Detail', '2', '108be377-64bc-4877-92fd-00fca650eedb', '1');

INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`) VALUES ('e4bb717d-de8c-4201-af00-42270bfcc1d2', b'1', '1', '37bc385d-b3ae-42a7-8c84-e92057cf8f2d', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`) VALUES ('8fb040ea-161b-4a94-b15d-d0c2604bf639', b'1', '1', '79d53582-ed8c-47a3-b734-f41651f688ad', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`) VALUES ('38bbf526-214a-41e8-a808-112205e08693', b'1', '1', '108be377-64bc-4877-92fd-00fca650eedb', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`) VALUES ('4ad1e17f-281d-475e-ad79-b6849d18bb61', b'1', '1', '136e5d70-c4bf-4d63-832e-afe51a2b8606', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`) VALUES ('5c8a7afd-c10e-480c-9e98-50d04effc8ce', b'1', '1', '8176dae9-c6c0-4d7b-8112-4e2875e42a6b', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`) VALUES ('22c453a5-999f-4b7f-9b78-104287a853e9', b'1', '1', '3809289a-ba52-42d2-8b5e-a54d5e5a51b8', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`) VALUES ('32af2021-6da3-4d22-aafd-da1eac693a4f', b'1', '1', 'adccaaab-6b78-438b-a8e2-32bb41cac366', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`) VALUES ('71629bf3-f969-405d-85e0-a503da28f17d', b'1', '1', 'bf29b1c9-ef67-4475-9364-d4c044b26925', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_role_menu` (`role_menu_id`, `is_active`, `version`, `menu_id`, `role_id`, `system_id`) VALUES ('3ea47aa8-b60f-4f8e-aed7-44d0bb259365', b'1', '1', 'd4465b72-2bc0-4db9-950a-9d22885f6f47', '33394716-ab11-4e6d-8baa-dd4cb214befc', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`) VALUES ('78fcd436-259a-4641-ada6-334a8b289c73', b'1', 'v1/employee', 'POST', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`) VALUES ('e917ae09-4fd4-4926-b7a0-3a51b103ad04', b'1', 'v1/employee', 'GET', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`) VALUES ('76c7d653-da66-4c4a-9aca-dfa8bc420cce', b'1', 'v1/employee', 'PUT', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`) VALUES ('fb488703-da43-4e71-b30d-d7803a6be7ff', b'1', 'v1/employee', 'DELETE', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`) VALUES ('4c6830cb-011b-412b-844d-137899316f23', b'1', '1', '78fcd436-259a-4641-ada6-334a8b289c73', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`) VALUES ('e78a5cc4-d659-4648-9454-e1cd71724a27', b'1', '1', 'e917ae09-4fd4-4926-b7a0-3a51b103ad04', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`) VALUES ('f0c1dc94-c4ac-4fb9-92ad-ee22707e60cf', b'1', '1', '76c7d653-da66-4c4a-9aca-dfa8bc420cce', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');
INSERT INTO `service_authorization`.`dsi_menu_api` (`menu_api_id`, `is_active`, `version`, `api_id`, `menu_id`, `system_id`) VALUES ('5a1b71da-6eda-4520-99ef-55b772627cf1', b'1', '1', 'fb488703-da43-4e71-b30d-d7803a6be7ff', '79d53582-ed8c-47a3-b734-f41651f688ad', '425744ba-6c10-47c0-91cf-5a4c05265b56');


