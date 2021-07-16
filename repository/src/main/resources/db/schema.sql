
create table certificate(
                            id int primary key auto_increment,
                            name varchar(50),
                            description varchar(120),
                            price decimal(5, 1),
                            duration integer,
                            create_date varchar(100),
                            last_update_date varchar(100)
);

create table tag(
                    id integer primary key auto_increment,
                    name varchar(120) unique
);

create table certificateTags(
                                id  integer primary key auto_increment,
                                certificate_id integer,
                                tag_id integer
);
