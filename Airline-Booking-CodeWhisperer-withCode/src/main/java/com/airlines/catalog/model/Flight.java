package com.airlines.catalog.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;
import javax.persistence.*;

/* Create an Entity class Flight mapped to database table flight with following attributes:
 id as id, departureDate, departureTime, departureAirportCode,
 arrivalDate, arrivalTime, arrivalAirportCode,  flightNumber, flightDuration,
 ticketPrice as double, ticketCurrency,seatCapacity as int and seatAvailable as int,
 They should be mapped to database columns with _as separator */
@Entity
@Table(name = "flight")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "departure_date")
    private String departureDate;
    @Column(name = "departure_time")
    private String departureTime;
    @Column(name = "departure_airport_code")
    private String departureAirportCode;
    @Column(name = "arrival_date")
    private String arrivalDate;
    @Column(name = "arrival_time")
    private String arrivalTime;
    @Column(name = "arrival_airport_code")
    private String arrivalAirportCode;
    @Column(name = "flight_number")
    private String flightNumber;
    @Column(name = "flight_duration")
    private String flightDuration;
    @Column(name = "ticket_price")
    private double ticketPrice;
    @Column(name = "ticket_currency")
    private String ticketCurrency;
    @Column(name = "seat_capacity")
    private int seatCapacity;
    @Column(name = "seat_available")
    private int seatAvailable;

    //Create a tostring method to convert attributes to string
    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", departureDate='" + departureDate + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", departureAirportCode='" + departureAirportCode + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", arrivalAirportCode='" + arrivalAirportCode + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", flightDuration='" + flightDuration + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", ticketCurrency='" + ticketCurrency + '\'' +
                ", seatCapacity=" + seatCapacity +
                ", seatAvailable=" + seatAvailable +
                '}';
    }
    //Create a toJson method to convert the attributes to Json String
    public String toJson() {
        return "{" +
                "\"id\":" + id +
                ", \"departureDate\":\"" + departureDate + '\"' +
                ", \"departureTime\":\"" + departureTime + '\"' +
                ", \"departureAirportCode\":\"" + departureAirportCode + '\"' +
                ", \"arrivalDate\":\"" + arrivalDate + '\"' +
                ", \"arrivalTime\":\"" + arrivalTime + '\"' +
                ", \"arrivalAirportCode\":\"" + arrivalAirportCode + '\"' +
                ", \"flightNumber\":\"" + flightNumber + '\"' +
                ", \"flightDuration\":\"" + flightDuration + '\"' +
                ", \"ticketPrice\":" + ticketPrice +
                ", \"ticketCurrency\":\"" + ticketCurrency + '\"' +
                ", \"seatCapacity\":" + seatCapacity +
                ", \"seatAvailable\":" + seatAvailable +
                '}';
    }
}


