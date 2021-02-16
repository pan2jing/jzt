create table CITY
(
    city_id     varchar(50),
    city_name   varchar(50),
    country_id  INTEGER,
    description varchar(50),
    province_id INTEGER,
    id          INTEGER
)
insert into CITY value('a','sss',1,'ds',1,1)
select * from CITY


drop table user_t
select * from user_t
create table user_t
(
    user_name   varchar(50),
    password varchar(50),
    age INTEGER,
    id          INTEGER   PRIMARY KEY AUTO_INCREMENT,
    cityid INTEGER
)
ID自增
insert into user_t(user_name, password, age) value('sss','pwd',1)
insert into user_t (user_name, password, age) value('pjj','123',37)