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


-- 1. 
-- insert into `mydb`.`users`(userId, username, name , password, email, role, createdOn, updatedOn) values
--           (1,'sanit','Sanit Sirisawatvatana','Sasadmin22','sanit.sir@kmutt.ac.th','admin', '2023-08-15 08:00:00+07:00', '2023-08-15 08:00:00+07:00' ),
--           (2,'pornthip','Pornthip Sirijutikul','Sasbscit22','pornthip.sri@kmutt.ac.th','announcer', '2023-08-15 09:30:00+07:00' , '2023-08-15 09:30:00+07:00'),
--           (3,'jaruwan_w','Jaruwan Maneesart','Sasinter22','jaruwan.wee@kmutt.ac.th','announcer', '2023-08-16 08:00:00+07:00' , '2023-08-16 08:00:00+07:00'),
--           (4,'vichchuda','Vichchuda Tedoloh','Sasgrant22','vichchuda.ted@kmutt.ac.th','announcer','2023-08-16 09:30:00+07:00' , '2023-08-16 09:30:00+07:00');

-- 2. for PUT methode
-- insert into `mydb`.`users`(userId, username, name , password, email, role, createdOn, updatedOn) values
-- 		  (1,'sanit','Sanit Sirisawatvatana','$argon2id$v=19$m=16,t=2,p=1$Z25kV0dzWkoxY2JKRmRYbQ$pQ0HF35SUAM1IhZGe0Ebig','sanit.sir@kmutt.ac.th','admin', '2023-08-15 08:00:00+07:00', '2023-08-15 08:00:00+07:00' ),
--           (2,'pornthip','Pornthip Sirijutikul','$argon2id$v=19$m=16,t=2,p=1$Z25kV0dzWkoxY2JKRmRYbQ$yRW4b1FEwljBSumo8ypzgA','pornthip.sri@kmutt.ac.th','announcer', '2023-08-15 09:30:00+07:00' , '2023-08-15 09:30:00+07:00'),
--           (3,'jaruwan_w','Jaruwan Maneesart','$argon2id$v=19$m=16,t=2,p=1$Z25kV0dzWkoxY2JKRmRYbQ$5Mb1rU1A17Bn6iSnAUTgsg','jaruwan.wee@kmutt.ac.th','announcer', '2023-08-16 08:00:00+07:00' , '2023-08-16 08:00:00+07:00'),
--           (4,'vichchuda','Vichchuda Tedoloh','$argon2id$v=19$m=16,t=2,p=1$Z25kV0dzWkoxY2JKRmRYbQ$bz2bXhNzaRRR1+9ULa2RIw','vichchuda.ted@kmutt.ac.th','announcer','2023-08-16 09:30:00+07:00' , '2023-08-16 09:30:00+07:00');

-- 3. UAT encode
insert into `mydb`.`users`(userId, username, name , password, email, role, createdOn, updatedOn) values
 (1,'sanit','Sanit Sirisawatvatana','$argon2id$v=19$m=4096,t=3,p=1$fabZIRZZrUuxRrnXoGkMjA$DZ90F9+3/rnHGKY/YmyQoZwOToS4mJbuLYZkpXQt2VY','sanit.sir@kmutt.ac.th','admin', '2023-08-15 08:00:00+07:00', '2023-08-15 08:00:00+07:00' ),
          (2,'pornthip','Pornthip Sirijutikul','$argon2id$v=19$m=4096,t=3,p=1$mZdjYXK6+s34Zd6PeVQG2Q$S9TGRhlIw2l7kIFYyPLJk6T5TDNHYa90Z/4gHubUNE8','pornthip.sri@kmutt.ac.th','announcer', '2023-08-15 09:30:00+07:00' , '2023-08-15 09:30:00+07:00'),
          (3,'jaruwan_w','Jaruwan Maneesart','$argon2id$v=19$m=4096,t=3,p=1$pTop1432FBQ8IlbizoaWsQ$6tG5y9QZ5uOz7qQe41vSwdr6wwnmLU9RFWrORpRaLdo','jaruwan.wee@kmutt.ac.th','announcer', '2023-08-16 08:00:00+07:00' , '2023-08-16 08:00:00+07:00'),
          (4,'vichchuda','Vichchuda Tedoloh','$argon2id$v=19$m=4096,t=3,p=1$XV/IqnCROw+/qCsQrD918g$bkcBkt4zmphxiVdYN0f2IvH8YW10jaHG9Sp7jmijycU','vichchuda.ted@kmutt.ac.th','announcer','2023-08-16 09:30:00+07:00' , '2023-08-16 09:30:00+07:00'),
          (5,'nanthawan','Nanthawan Pran','$argon2i$v=19$m=16,t=2,p=1$MGpXd1lZRjJSbEZLU1hrMA$2evoBOvib2DGlqgZWw+P91Q55byUQQvTmWY/UE9qA4U','nanthawan.ice@kmutt.ac.th','announcer','2023-08-24 09:30:00+07:00' , '2023-08-24 09:30:00+07:00');

-- 3.
-- insert into users( userId, username, password, name, email, role, createdOn, updatedOn) values
--           (1,'sanit','$argon2id$v=19$m=4096,t=3,p=1$Znh2ak13dTRSbU9QVXNGYg$6iSakMRomYqleXWeUdF4Yg','Sanit Sirisawatvatana','sanit.sir@kmutt.ac.th','admin', '2023-08-15 08:00:00+07:00', '2023-08-15 08:00:00+07:00' ),
--           (2,'pornthip','$argon2id$v=19$m=4096,t=3,p=1$Znh2ak13dTRSbU9QVXNGYg$5avkkx8dENgMN5jyX0qrbQ','Pornthip Sirijutikul','pornthip.sri@kmutt.ac.th','announcer', '2023-08-15 09:30:00+07:00' , '2023-08-15 09:30:00+07:00'),
--           (3,'jaruwan_w','$argon2id$v=19$m=4096,t=3,p=1$Znh2ak13dTRSbU9QVXNGYg$+7aPNDQc0wGNI1gffe4gWQ','Jaruwan Maneesart','jaruwan.wee@kmutt.ac.th','announcer', '2023-08-16 08:00:00+07:00' , '2023-08-16 08:00:00+07:00'),
--           (4,'vichchuda','$argon2id$v=19$m=4096,t=3,p=1$Znh2ak13dTRSbU9QVXNGYg$VDsx4Gg34lXAoIxJO9JcbQ','Vichchuda Tedoloh','vichchuda.ted@kmutt.ac.th','announcer','2023-08-16 09:30:00+07:00' , '2023-08-16 09:30:00+07:00');


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
