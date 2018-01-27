----CLIENTS
insert into Client(id, first_name, last_name)
values(1,'Robert', 'Robson');

insert into Client(id, first_name, last_name)
values(2,'Piotr', '£owicz');

----HOTEL AND ROOMS

--hotel 1
insert into Hotel(id,name,city)
values(1,'Super hotel','Warszawa');

insert into Room(id,number,room_type,price,fk_hotel)
values(1,100,'STANDARD',60.00,1);

insert into Room(id,number,room_type,price,fk_hotel)
values(2,101,'DOUBLE',80.00,1);

--hotel 2
insert into Hotel(id,name,city)
values(2,'Grand Budapest','Szczecin');

insert into Room(id,number,room_type,price,fk_hotel)
values(3,1,'STANDARD',70.00,2);

insert into Room(id,number,room_type,price,fk_hotel)
values(4,2,'DOUBLE',90.00,2);

insert into Room(id,number,room_type,price,fk_hotel)
values(5,3,'DOUBLE',90.00,2);

insert into Room(id,number,room_type,price,fk_hotel)
values(6,4,'DELUXE',150.00,2); 


---AVAILABILITY PERIODS
insert into Availability(id, start_date, end_date, fk_room)
values(1, '2010-01-01 00:00:00', '2020-01-01 00:00:00', 1);
insert into Availability(id, start_date, end_date, fk_room)
values(2, '2010-01-01 00:00:00', '2020-01-01 00:00:00', 2);
insert into Availability(id, start_date, end_date, fk_room)
values(3, '2010-01-01 00:00:00', '2020-01-01 00:00:00', 3);
insert into Availability(id, start_date, end_date, fk_room)
values(4, '2010-01-01 00:00:00', '2020-01-01 00:00:00', 4);
insert into Availability(id, start_date, end_date, fk_room)
values(5, '2010-01-01 00:00:00', '2020-01-01 00:00:00', 5);
insert into Availability(id, start_date, end_date, fk_room)
values(6, '2015-01-01 00:00:00', '2017-07-01 00:00:00', 6);
insert into Availability(id, start_date, end_date, fk_room)
values(7, '2017-08-01 00:00:00', '2020-07-01 00:00:00', 6);
----BOOKINGS
insert into Booking(id,check_in, check_out, status, fk_client, fk_room)
values(1, '2017-01-01 00:00:00', '2017-12-01 00:00:00', 'ACTIVE', 2, 1);
