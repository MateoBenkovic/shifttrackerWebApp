drop table if exists app_role cascade
drop table if exists app_user cascade
drop table if exists org_unit cascade
drop table if exists schedule_team_per_shift cascade
drop table if exists shift cascade
drop table if exists team cascade
drop table if exists team_role cascade
create table app_role (id integer generated by default as identity, order_number integer not null, name varchar(255), primary key (id))
create table app_user (app_role_id integer, app_user_id integer, id integer generated by default as identity, org_unit_id integer, schedule_team_per_shift_id integer, team_id integer, team_role_id integer, email varchar(255), first_name varchar(255), last_name varchar(255), oib varchar(255), telephone varchar(255), primary key (id))
create table org_unit (id integer generated by default as identity, order_number integer not null, name varchar(255), primary key (id))
create table schedule_team_per_shift (id integer generated by default as identity, schedule_per_month_id integer not null, shift_id integer unique, team_id integer unique, date timestamp(6), primary key (id))
create table shift (id integer generated by default as identity, end_time timestamp(6), start_time timestamp(6), name varchar(255), primary key (id))
create table team (id integer generated by default as identity, order_number integer not null, type integer not null, name varchar(255), primary key (id))
create table team_role (id integer generated by default as identity, order_number integer not null, name varchar(255), primary key (id))
alter table if exists app_user add constraint FK2lqvmk127ira6ybqnlrq5bkx4 foreign key (app_role_id) references app_role
alter table if exists app_user add constraint FK6jnvlspja49tr23aur70rtcou foreign key (org_unit_id) references org_unit
alter table if exists app_user add constraint FKngdkemk0odrql9wdpjj01qopm foreign key (schedule_team_per_shift_id) references schedule_team_per_shift
alter table if exists app_user add constraint FKaauds0ajouf9m311rl6vf57dm foreign key (team_id) references team
alter table if exists app_user add constraint FKbxuqlbsbrw3t50l0bl119n8v1 foreign key (team_role_id) references team_role
alter table if exists app_user add constraint FKc2nh3e8grlvty246x58qplqye foreign key (app_user_id) references schedule_team_per_shift
alter table if exists schedule_team_per_shift add constraint FKd8pq9b2ohy8bvs8scqptkbf48 foreign key (shift_id) references shift
alter table if exists schedule_team_per_shift add constraint FKhcllx4itx0168yl4vfp6ci2ws foreign key (team_id) references team