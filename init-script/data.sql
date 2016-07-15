
---------------------------------- Contact number type -----------------------------------

INSERT INTO `dem_service`.`ref_employee_contact_number_type` (`employee_contact_type_id`, `description`, `is_active`, `name`) VALUES ('cf2689f0-5165-4c02-a55c-25a9a32d8dd4', NULL, b'1', 'Official');
INSERT INTO `dem_service`.`ref_employee_contact_number_type` (`employee_contact_type_id`, `description`, `is_active`, `name`) VALUES ('807c9237-7173-4a5e-a45e-384137b7fab2', NULL, b'1', 'Personal');
INSERT INTO `dem_service`.`ref_employee_contact_number_type` (`employee_contact_type_id`, `description`, `is_active`, `name`) VALUES ('cf9d4ed1-6f98-4e7f-a77a-2c36e936254d', NULL, b'1', 'Emergency');


---------------------------------- Email type -----------------------------------

INSERT INTO `dem_service`.`ref_employee_email_type` (`employee_email_type_id`, `description`, `is_active`, `name`) VALUES ('78b5c3c5-5470-49f7-aafe-b016a33b3883', NULL, b'1', 'Official');
INSERT INTO `dem_service`.`ref_employee_email_type` (`employee_email_type_id`, `description`, `is_active`, `name`) VALUES ('b6540691-773c-4ee8-9e24-07c292887e73', NULL, b'1', 'Personal');


---------------------------------- Leave status -----------------------------------

INSERT INTO `dem_service`.`ref_leave_status` (`leave_status_id`, `description`, `is_active`, `name`) VALUES ('19bad1c8-5e6c-41c1-bccb-5d4c360baf25', NULL, b'1', 'Applied');
INSERT INTO `dem_service`.`ref_leave_status` (`leave_status_id`, `description`, `is_active`, `name`) VALUES ('0a30ce24-edbf-41b9-86cd-b6f9954bbf92', NULL, b'1', 'Approved');
INSERT INTO `dem_service`.`ref_leave_status` (`leave_status_id`, `description`, `is_active`, `name`) VALUES ('4c3b5ba4-f9ae-48d2-8320-a4d6e6d17583', NULL, b'1', 'Denied');
INSERT INTO `dem_service`.`ref_leave_status` (`leave_status_id`, `description`, `is_active`, `name`) VALUES ('24a487dc-0167-4054-816e-7efbb1f89f8f', NULL, b'1', 'Canceled');


---------------------------------- Leave type -----------------------------------

INSERT INTO `dem_service`.`ref_leave_type` (`leave_type_id`, `description`, `is_active`, `name`) VALUES ('553fb999-2ceb-4bf0-90f2-ccf52742c8a1', NULL, b'1', 'Casual');
INSERT INTO `dem_service`.`ref_leave_type` (`leave_type_id`, `description`, `is_active`, `name`) VALUES ('7e1c5ac7-f7a6-43e0-b5da-31cf8b899f10', NULL, b'1', 'Sick');
INSERT INTO `dem_service`.`ref_leave_type` (`leave_type_id`, `description`, `is_active`, `name`) VALUES ('80f8e6fa-ff9b-4249-a919-f41942c0c86a', NULL, b'1', 'Hajj');
INSERT INTO `dem_service`.`ref_leave_type` (`leave_type_id`, `description`, `is_active`, `name`) VALUES ('f2d34cf6-dd04-42fb-a4a8-4ad29100b5da', NULL, b'1', 'Maternity');


---------------------------------- Project status -----------------------------------

INSERT INTO `dem_service`.`ref_project_status` (`project_status_id`, `description`, `is_active`, `name`) VALUES ('1db5992a-aafb-4338-a267-4861e0a034f3', NULL, b'1', 'Active');
INSERT INTO `dem_service`.`ref_project_status` (`project_status_id`, `description`, `is_active`, `name`) VALUES ('ba709d6f-d236-4344-8634-cfa3d911913b', NULL, b'1', 'On Hold');
INSERT INTO `dem_service`.`ref_project_status` (`project_status_id`, `description`, `is_active`, `name`) VALUES ('b09eccab-c556-445e-a829-925ca5658565', NULL, b'1', 'Canceled');
INSERT INTO `dem_service`.`ref_project_status` (`project_status_id`, `description`, `is_active`, `name`) VALUES ('753404e1-e0a4-4cc0-b134-0d93788f59d7', NULL, b'1', 'Completed');


---------------------------------- Role type -----------------------------------

INSERT INTO `dem_service`.`ref_role` (`role_id`, `description`, `is_active`, `name`) VALUES ('31c1ab56-9ce4-463d-801d-e7be2f65ccb5', NULL, b'1', 'Manager');
INSERT INTO `dem_service`.`ref_role` (`role_id`, `description`, `is_active`, `name`) VALUES ('9716a905-a6c4-449c-b6f3-00faf17f9359', NULL, b'1', 'Architectural');
INSERT INTO `dem_service`.`ref_role` (`role_id`, `description`, `is_active`, `name`) VALUES ('ff4ea6f2-f0eb-4189-8d21-4673fd51a12a', NULL, b'1', 'Lead');
INSERT INTO `dem_service`.`ref_role` (`role_id`, `description`, `is_active`, `name`) VALUES ('d3478d16-5562-4701-ad0d-0fbea3efee94', NULL, b'1', 'Member');

