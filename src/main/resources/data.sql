--select * from owner;
insert into MEMBER (role, nick_name, password, phone_number, email, created_by, created_date,modified_by, modified_date)
values ('OWNER','오너계정','$2a$10$nyOJzE5TYbAIpKXCx5ST6OqYM.PKxvZeRPpdqsZHWm7DLb5/zBfw.', '01011112222','owner@owner.com','owner', '2024-04-03 18:50:15.666609','owner','2024-04-03 18:50:15.666609');
insert into MEMBER (role, nick_name, password, phone_number, email, created_by, created_date,modified_by, modified_date)
values ('OWNER','오너계정2','$2a$10$nyOJzE5TYbAIpKXCx5ST6OqYM.PKxvZeRPpdqsZHWm7DLb5/zBfw.', '01011112222','owner2@owner2.com','owner2', '2024-04-03 18:50:15.666609','owner2','2024-04-03 18:50:15.666609');
insert into MEMBER (role, nick_name, password, phone_number, email, created_by, created_date,modified_by, modified_date)
values ('USER','유저계정','$2a$10$nyOJzE5TYbAIpKXCx5ST6OqYM.PKxvZeRPpdqsZHWm7DLb5/zBfw.', '01048452111','user@user.com','user', '2024-04-03 18:50:15.666609','user', '2024-04-03 18:50:15.666609');


insert into shop (shop_name, member_id, shop_number, zipcode,address1,address2,country_category,
                  status,description, open_time, last_order_time, waiting_yn, like_cnt, created_by, created_date,modified_by, modified_date)
    values ('김가네', 1, '021112222','15481','서울특별시 강남구', null,'KOREAN', 'OPEN', '분식집입니다.','10:00:00','21:00:00','Y',0,
        'owner001', '2024-04-03 18:50:15.666609','owner001', '2024-04-03 18:50:15.666609');

insert into shop (shop_name, member_id, shop_number, zipcode,address1,address2,country_category,
                  status,description, open_time, last_order_time, waiting_yn, like_cnt, created_by, created_date,modified_by, modified_date)
    values ('떡보끼날', 1, '027841345','15481','서울특별시 강남구', null,'KOREAN', 'READY', '떡보끼날 분식집입니다.','07:00:00','22:00:00','Y',0,
        'owner001', '2024-04-03 18:50:15.666609','owner001', '2024-04-03 18:50:15.666609');

insert into shop (shop_name, member_id, shop_number, zipcode,address1,address2,country_category,
                  status,description, open_time, last_order_time, waiting_yn, like_cnt, created_by, created_date,modified_by, modified_date)
    values ('중화루', 2, '029874561','15481','서울특별시 종로구', null, 'CHINESE', 'OPEN', '짜장면이 맛있는 중국집~','11:00:00','22:00:00','Y',0,
        'owner002', '2024-04-03 18:50:15.666609','owner002', '2024-04-03 18:50:15.666609');


insert into waiting (member_id, shop_id, head_count, waiting_status, waiting_number, registered_date_time, created_by, created_date,modified_by, modified_date)
    values(1, 1, 2, 'READY', 1, CURRENT_TIMESTAMP,'user001', '2024-04-03 18:50:15.666609','user001', '2024-04-03 18:50:15.666609');
insert into waiting (member_id, shop_id, head_count, waiting_status, waiting_number, registered_date_time, created_by, created_date,modified_by, modified_date)
values(1, 1, 2, 'VISITED', 1, CURRENT_TIMESTAMP,'user001', '2024-04-02 15:50:15.666609','user001', '2024-04-02 16:20:15.666609');
insert into waiting (member_id, shop_id, head_count, waiting_status, waiting_number, registered_date_time, created_by, created_date,modified_by, modified_date)
    values(2, 1, 4, 'READY', 2, CURRENT_TIMESTAMP,'user002', '2024-04-03 18:50:15.666609','user002', '2024-04-03 18:50:15.666609');
insert into waiting (member_id, shop_id, head_count, waiting_status, waiting_number, registered_date_time, created_by, created_date,modified_by, modified_date)
    values(3, 1, 2, 'READY', 3, CURRENT_TIMESTAMP,'user003', '2024-04-03 18:50:15.666609','user003', '2024-04-03 18:50:15.666609');