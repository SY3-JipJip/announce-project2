use mydb;
SET SQL_SAFE_UPDATES = 0;
delete from mydb.announces;
delete from mydb.categories;
delete from mydb.users;

INSERT INTO mydb.categories (categoryName) 
VALUES ('หางาน'),('ทุนการศึกษา'),('ทั่วไป');

-- PBI 16-1
insert into mydb.users(userId, username, name, email, role, createdOn, updatedOn) values
          (1,'sanit','Sanit Sirisawatvatana','sanit.sir@kmutt.ac.th','admin', '2023-08-15 08:00:00+07:00', '2023-08-15 08:00:00+07:00' ),
          (2,'pornthip','Pornthip Sirijutikul','pornthip.sri@kmutt.ac.th','announcer', '2023-08-15 09:30:00+07:00' , '2023-08-15 09:30:00+07:00'),
          (3,'jaruwan_w','Jaruwan Maneesart','jaruwan.wee@kmutt.ac.th','announcer', '2023-08-16 08:00:00+07:00' , '2023-08-16 08:00:00+07:00'),
          (4,'vichchuda','Vichchuda Tedoloh','vichchuda.ted@kmutt.ac.th','announcer','2023-08-16 09:30:00+07:00' , '2023-08-16 09:30:00+07:00');



