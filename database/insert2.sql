use mydb;
delete from mydb.announces where announcementId=1;
delete from mydb.announces where announcementId=2;
delete from mydb.announces where announcementId=3;
delete from mydb.announces where announcementId=4;


delete from mydb.categories where categoryId=1;
delete from mydb.categories where categoryId=2;
delete from mydb.categories where categoryId=3;
delete from mydb.categories where categoryId=4;

delete from mydb.users where userId=1;
delete from mydb.users where userId=2;
delete from mydb.users where userId=3;
delete from mydb.users where userId=4;


INSERT INTO mydb.categories (categoryName) 
VALUES ('ทั่วไป'),('ทุนการศึกษา'),('หางาน'),('ฝึกงาน');
select *
from `mydb`.`categories`;
-- INSERT INTO `mydb`.`users` (`username`, `name`, `email`, `role`, `createdOn`, `updatedOn`)
-- VALUES ('ice044', 'Nanthawan', 'ice@example.com', 'announcer', NOW(), NOW()),
-- ('jeng003', 'Korapin', 'jeng@example.com', 'admin', NOW(), NOW());

-- INSERT INTO `mydb`.`users` (`username`, `name`, `email`,`createdOn`, `updatedOn`)
-- VALUES ('jimmy058', 'Patsakorn', 'jimmy@example.com',NOW(), NOW());


-- 1.
-- insert into `mydb`.`users`(userId, username, name, email, role, createdOn, updatedOn) values
--           (1,'sanit','Sanit Sirisawatvatana','sanit.sir@kmutt.ac.th','admin', '2023-08-15 08:00:00+07:00', '2023-08-15 08:00:00+07:00' ),
-- 		  (2,'pornthip','Pornthip Sirijutikul','pornthip.sri@kmutt.ac.th','announcer', '2023-08-15 09:30:00+07:00' , '2023-08-15 09:30:00+07:00'),
--           (3,'jaruwan_w','Jaruwan Maneesart','jaruwan.wee@kmutt.ac.th','announcer', '2023-08-16 08:00:00+07:00' , '2023-08-16 08:00:00+07:00'),
--           (4,'vichchuda','Vichchuda Tedoloh','vichchuda.ted@kmutt.ac.th','announcer','2023-08-16 08:00:00+07:00' , '2023-08-16 08:00:00+07:00');

-- 2. for PUT methode
insert into `mydb`.`users`(userId, username, password , name, email, role, createdOn, updatedOn) values
          (1,'sanit','Sanit Sirisawatvatana','Sasadmin22','sanit.sir@kmutt.ac.th','admin', '2023-08-15 08:00:00+07:00', '2023-08-15 08:00:00+07:00' ),
          (2,'pornthip','Pornthip Sirijutikul','Sasbscit22','pornthip.sri@kmutt.ac.th','announcer', '2023-08-15 09:30:00+07:00' , '2023-08-15 09:30:00+07:00'),
          (3,'jaruwan_w','Jaruwan Maneesart','Sasinter22','jaruwan.wee@kmutt.ac.th','announcer', '2023-08-16 08:00:00+07:00' , '2023-08-16 08:00:00+07:00'),
          (4,'vichchuda','Vichchuda Tedoloh','Sasgrant22','vichchuda.ted@kmutt.ac.th','announcer','2023-08-16 09:30:00+07:00' , '2023-08-16 09:30:00+07:00');
select *
from `mydb`.`users`;

INSERT INTO `mydb`.`announces` (announcementTitle, announcementDescription, publishDate, closeDate, announcementDisplay, categoryId,userId) 
VALUES 
('บริษัท เน็ตเซอร์พลัส จำกัด รับสมัครงาน 2 ตำแหน่ง', 'บริษัท เน็ตเซอร์พลัส จำกัด เปิดรับสมัครงาน 2 ตำแหน่ง Application Support และ Customer Support', null, null, 'N', 1,1),
('รายชื่อนักศึกษาที่ได้รับทุนการศึกษาประเภท "ทุนจ้างงาน" 2/2565', 'คณะ ฯ ประกาศรายชื่อนักศึกษาที่ได้รับทุนการศึกษาประเภท "ทุนจ้างงาน" ประจำภาคการศึกษา 2/2565', null, '2566-05-31 11:00:00', 'Y', 2,2),
('แนวปฎิบัติการสอบออนไลน์ พ.ศ. 2565', 'ประกาศมหาวิทยาลัยเทคโนโลยีพระจอมเกล้าธนบุรี เรื่องแนวทางปฎิบัติการสอบออนไลน์ พ.ศ. 2565', '2566-01-26 23:00:00', null, 'Y', 3,3),
('กิจกรรมพี่อ้อย พี่ฉอด On Tour 2566', 'ขอเชิญนักศึกษาทุกชั้นปี เข้าร่วมกิจกรรมพี่อ้อย พี่ฉอด On Tour', '2566-04-18 23:00:00',  '2566-05-08 11:00:00', 'Y', 3,1);

select *
from `mydb`.`announces`;
