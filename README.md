REST API Project for hotel management
================================

Requirements
------------
* [Java Platform (JDK) 8]
* [Apache Maven 3.x]

Notes
---------
According to the assignment 'Hotel owners can define which rooms are available.'. The whole requirement is pretty vague, so I assumed that:
1. A room has a list of availability periods (Availability) which is something set up via db by theoretical hotel owner (i.e. when they plan to make a room available in the future or unavailable for renovation).
2. There's also booking (Booking) which represents client's reservation order. Booking for a a period during which a room's unavailable is not possible.
3. For the sake of simplicity I assumed that search parameters for available rooms are all required (since the assignment says nothing about this)

About testing
-----------
This projects includes both unit and acceptance tests.
Acceptance tests are done with MockMVC and full context on (services and db).
For the sake of simplicity, acceptance tests are using the same script to init db.

Running the tests
-----------
1. `mvn install` runs all JUnit and integration tests (including acceptance tests) 

Running the app
-----------
1. `mvn spring-boot:run`


Endpoints
---------
| Method | Url | Decription |
| ------ | --- | ---------- |
| GET    |/client  | get list of all clients |
| POST   |/client| register a new client |
| GET    |/room?startDate={startDate}&endDate={endDate}&minPrice={minPrice}&maxPrice={maxPrice}&city={city} | get list of available rooms for given criteria |
| POST   |/booking/client/{clientId} | register a new booking for given client |
| GET    |/booking/client/{clientId} | get list of all bookings for given client |
| DELETE |/booking/{bookingId}     | cancel booking |


Example manual tests
-----------
##Booking, canceling and search
1. POST to http://localhost:8080/client with data { "firstName": "Ignacy", "lastName": "Rower" } - extract {clientId}
2. GET to http://localhost:8080/room?startDate=2017-08-01&endDate=2017-08-03&minPrice=100.00&maxPrice=190.00&city=Szczecin - extract {roomId}
3. POST to http://localhost:8080/booking/client/{clientId} with data { "checkIn": "2017-08-01", "checkOut": "2017-08-03", "roomId":"{roomId}" } - extract {bookingId}
4. GET to http://localhost:8080/room?startDate=2017-08-01&endDate=2017-08-03&minPrice=100.00&maxPrice=190.00&city=Szczecin again - room with {roomId} should not be seen
5. DELETE to http://localhost:8080/booking/client/{clientId} 
6. GET to http://localhost:8080/room?startDate=2017-08-01&endDate=2017-08-03&minPrice=100.00&maxPrice=190.00&city=Szczecin one again - the room with {roomId} should be seen again

##Booking twice
1. GET to http://localhost:8080/room?startDate=2017-08-01&endDate=2017-08-03&minPrice=100.00&maxPrice=190.00&city=Szczecin - extract {roomId}
2. POST to http://localhost:8080/booking/client/{clientId} with data { "checkIn": "2017-08-01", "checkOut": "2017-08-03", "roomId":"{roomId}" }
3. POST to http://localhost:8080/booking/client/{clientId} with the same data - an error message should be seen

