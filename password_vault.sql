
create database vault;

use vault;

create table users(
	user_id int auto_increment primary key,
    user_site varchar(255) not null,
    user_name varchar(255) not null,
    user_email varchar(255) not null,
    user_password varchar(255) not null
);

desc users;

select * from users;
