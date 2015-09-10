# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table daily_raport_model (
  id                        bigint auto_increment not null,
  name                      varchar(100),
  date                      datetime(6),
  constraint pk_daily_raport_model primary key (id))
;

create table error_log (
  id                        bigint auto_increment not null,
  message                   varchar(255),
  loged_date                datetime,
  constraint pk_error_log primary key (id))
;

create table field_model (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_field_model primary key (id))
;

create table post (
  id                        bigint auto_increment not null,
  content                   TEXT,
  user_id                   bigint,
  title                     varchar(255),
  post_type                 integer,
  visible_mentors           integer,
  date_create               datetime,
  constraint pk_post primary key (id))
;

create table raport_relation_model (
  id                        bigint,
  daily_id                  bigint,
  field_model_id            bigint,
  value                     varchar(255))
;

create table role (
  id                        bigint auto_increment not null,
  name                      varchar(120),
  status                    integer(1),
  description               varchar(4000),
  created                   datetime(6),
  created_by                varchar(45),
  modified                  datetime(6),
  modified_by               varchar(45),
  constraint pk_role primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  email                     varchar(100),
  password                  varchar(100),
  status                    tinyint(1) default 0,
  first_name                varchar(100),
  last_name                 varchar(100),
  role                      integer(1),
  birth_date                datetime(6),
  gender                    integer(1),
  skype_name                varchar(100),
  facebook_profile          varchar(300),
  create_date               datetime,
  created_by                varchar(100),
  update_date               datetime,
  updated_by                varchar(100),
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id))
;

create table user_role (
  id                        bigint auto_increment not null,
  user_id                   bigint,
  role_code                 varchar(45),
  status                    varchar(1),
  description               varchar(4000),
  created                   datetime(6),
  created_by                varchar(100),
  modified                  datetime(6),
  modified_by               varchar(255),
  role_id                   bigint,
  constraint pk_user_role primary key (id))
;

alter table post add constraint fk_post_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_post_user_1 on post (user_id);
alter table raport_relation_model add constraint fk_raport_relation_model_dailyRaportModel_2 foreign key (daily_id) references daily_raport_model (id) on delete restrict on update restrict;
create index ix_raport_relation_model_dailyRaportModel_2 on raport_relation_model (daily_id);
alter table raport_relation_model add constraint fk_raport_relation_model_fieldModel_3 foreign key (field_model_id) references field_model (id) on delete restrict on update restrict;
create index ix_raport_relation_model_fieldModel_3 on raport_relation_model (field_model_id);
alter table user_role add constraint fk_user_role_user_4 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_user_role_user_4 on user_role (user_id);
alter table user_role add constraint fk_user_role_role_5 foreign key (role_id) references role (id) on delete restrict on update restrict;
create index ix_user_role_role_5 on user_role (role_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table daily_raport_model;

drop table error_log;

drop table field_model;

drop table post;

drop table raport_relation_model;

drop table role;

drop table user;

drop table user_role;

SET FOREIGN_KEY_CHECKS=1;

