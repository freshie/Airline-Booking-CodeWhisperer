// Create Database FlightReservationDB
CREATE DATABASE FlightReservationDB;
GO

USE FlightReservationDB;
// Create Table airport with columns airport_code, airport_name, airport_city, airport_locale with airport_code as primary key
CREATE TABLE airport (
    airport_code VARCHAR(3) PRIMARY KEY,
    airport_name VARCHAR(50),
    airport_city VARCHAR(50),
    airport_locale VARCHAR(50)
);
GO

// Create table flight with columns id, departure_date, departure_time,
// departure_airport_code, arrival_date, arrival_time, arrival_airport_code,
// flight_number, flight_duration as int, ticket_price as decimal, ticket_currency, seat_capacity as int
// and seat_available as int
// id as primary key
// departure_airport_code references airport.airport_code, arrival_airport_code references airport.airport_code
CREATE TABLE flight (
    id INT PRIMARY KEY,
    departure_date DATE,
    departure_time TIME,
    departure_airport_code VARCHAR(3) REFERENCES airport(airport_code),
    arrival_date DATE,
    arrival_time TIME,
    arrival_airport_code VARCHAR(3) REFERENCES airport(airport_code),
    flight_number VARCHAR(10),
    flight_duration INT,
    ticket_price DECIMAL,
    ticket_currency VARCHAR(3),
    seat_capacity INT,
    seat_available INT
);

//create new records in airport table with airport_codes LHR, MIA, CDG, LAX
insert into airport (airport_code, airport_name, airport_city, airport_locale)
VALUES ('LHR', 'London Heathrow', 'London', 'United Kingdom'),
       ('MIA', 'Miami International', 'Miami', 'United States'),
       ('CDG', 'Paris Charles De Gaulle', 'Paris', 'France'),
       ('LAX', 'Los Angeles International', 'Los Angeles', 'United States');

//create new records in flight table
//departure_date=2023-08-01, departure_time=10:00, departure_airport_code=MIA, arrival_airport_code=LAX
//departure_date=2023-08-01, departure_time=14:00, departure_airport_code=MIA, arrival_airport_code=LAX
//departure_date=2023-08-01, departure_time=15:00,departure_airport_code=LHR, arrival_airport_code=CDG
//departure_date=2023-08-01, departure_time=17:00, departure_airport_code=LHR, arrival_airport_code=CDG
//departure_date=2023-08-02, departure_time=16:00,departure_airport_code=LHR, arrival_airport_code=CDG

insert into flight (id, departure_date, departure_time, departure_airport_code, arrival_date, arrival_time, arrival_airport_code, flight_number, flight_duration, ticket_price, ticket_currency, seat_capacity, seat_available)
VALUES (1, '2023-08-01', '10:00', 'MIA', '2023-08-01', '14:00', 'LAX', 'MIA-LAX-1', 120, 150.00, 'USD', 100, 100),
       (2, '2023-08-01', '14:00', 'MIA', '2023-08-01', '14:00', 'LAX', 'MIA-LAX-2', 120, 150.00, 'USD', 100, 100),
       (3, '2023-08-01', '15:00', 'LHR', '2023-08-01', '17:00', 'CDG', 'LHR-CDG-1', 120, 150.00, 'USD', 100, 100),
       (4, '2023-08-01', '17:00', 'LHR', '2023-08-01', '17:00', 'CDG', 'LHR-CDG-2', 120, 150.00, 'USD', 100, 100),
       (5, '2023-08-02', '16:00', 'LHR', '2023-08-02', '16:00', 'CDG', 'LHR-CDG-3', 120, 150.00, 'USD', 100, 100);