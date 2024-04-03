--select * from owner;
insert into owner (owner_id, nick_name, password, phone_number, email, created_by, created_date,modified_by, modified_date)
    values ('owner001', '분식집오너','1234', '01011112222','owner001@abc.com','owner001', '2024-04-03 18:50:15.666609','owner001','2024-04-03 18:50:15.666609');
insert into owner (owner_id, nick_name, password, phone_number, email, created_by, created_date,modified_by, modified_date)
    values ('owner002', '중화루','gdg848', '01048452111','owner002@befe.com','owner002', '2024-04-03 18:50:15.666609','owner002', '2024-04-03 18:50:15.666609');


insert into shop (shop_name, owner_id, shop_number, zipcode,address1,address2,country_category,
                  status,description, open_time, last_order_time, waiting_yn, created_by, created_date,modified_by, modified_date)
    values ('김가네', 'owner001', '021112222','15481','서울특별시 강남구', null,'KOREAN', 'OPEN', '분식집입니다.','10:00:00','21:00:00','Y',
        'owner001', '2024-04-03 18:50:15.666609','owner001', '2024-04-03 18:50:15.666609');

insert into shop (shop_name, owner_id, shop_number, zipcode,address1,address2,country_category,
                  status,description, open_time, last_order_time, waiting_yn, created_by, created_date,modified_by, modified_date)
    values ('떡보끼날', 'owner001', '027841345','15481','서울특별시 강남구', null,'KOREAN', 'READY', '떡보끼날 분식집입니다.','07:00:00','22:00:00','Y',
        'owner001', '2024-04-03 18:50:15.666609','owner001', '2024-04-03 18:50:15.666609');

insert into shop (shop_name, owner_id, shop_number, zipcode,address1,address2,country_category,
                  status,description, open_time, last_order_time, waiting_yn, created_by, created_date,modified_by, modified_date)
    values ('중화루', 'owner002', '029874561','15481','서울특별시 종로구', null, 'CHINESE', 'OPEN', '짜장면이 맛있는 중국집~','11:00:00','22:00:00','Y',
        'owner002', '2024-04-03 18:50:15.666609','owner002', '2024-04-03 18:50:15.666609');


insert into waiting (user_id, shop_id, head_count, waiting_status, waiting_number, registered_date_time, created_by, created_date,modified_by, modified_date)
    values(1, 1, 2, 'READY', 1, CURRENT_TIMESTAMP,'user001', '2024-04-03 18:50:15.666609','user001', '2024-04-03 18:50:15.666609');
insert into waiting (user_id, shop_id, head_count, waiting_status, waiting_number, registered_date_time, created_by, created_date,modified_by, modified_date)
values(1, 1, 2, 'VISITED', 1, CURRENT_TIMESTAMP,'user001', '2024-04-02 15:50:15.666609','user001', '2024-04-02 16:20:15.666609');
insert into waiting (user_id, shop_id, head_count, waiting_status, waiting_number, registered_date_time, created_by, created_date,modified_by, modified_date)
    values(2, 1, 4, 'READY', 2, CURRENT_TIMESTAMP,'user002', '2024-04-03 18:50:15.666609','user002', '2024-04-03 18:50:15.666609');
insert into waiting (user_id, shop_id, head_count, waiting_status, waiting_number, registered_date_time, created_by, created_date,modified_by, modified_date)
    values(3, 1, 2, 'READY', 3, CURRENT_TIMESTAMP,'user003', '2024-04-03 18:50:15.666609','user003', '2024-04-03 18:50:15.666609');