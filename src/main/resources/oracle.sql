create table CITY
(
    city_id     VARCHAR2(50) not null,
    city_name   VARCHAR2(50) not null,
    country_id  INTEGER not null,
    description VARCHAR2(50),
    province_id NUMBER(14),
    id          INTEGER
)