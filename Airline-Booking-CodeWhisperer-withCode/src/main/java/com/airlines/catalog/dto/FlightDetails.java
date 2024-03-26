package com.airlines.catalog.dto;

import com.airlines.catalog.model.Airport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/* Create Class FlightDetails with following attributes
flightId as int, departureDate, departureTime, departureAirportCode as string, departureAirportName as string,
departureAirportCity, departureAirportLocale,
arrivalAirportCode, arrivalAirportName, arrivalAirportCity, arrivalAirportLocale,
arrivalDate, arrivalTime, ticketPrice as double,
ticketCurrency, flightNumber, flightDuration, seatAvailable as int
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class FlightDetails {
    private int flightId;
    private String departureDate;
    private String departureTime;
    private String departureAirportCode;
    private String departureAirportName;
    private String departureAirportCity;
    private String departureAirportLocale;
    private String arrivalAirportCode;
    private String arrivalAirportName;
    private String arrivalAirportCity;
    private String arrivalAirportLocale;
    private String arrivalDate;
    private String arrivalTime;
    private double ticketPrice;
    private String ticketCurrency;
    private String flightNumber;
    private String flightDuration;
    private int seatAvailable;

    //Create toString method for the object
    @Override
    public String toString() {
        return "FlightDetails{" +
                "flightId=" + flightId +
                ", departureDate='" + departureDate + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", departureAirportCode='" + departureAirportCode + '\'' +
                ", departureAirportName='" + departureAirportName + '\'' +
                ", departureAirportCity='" + departureAirportCity + '\'' +
                ", departureAirportLocale='" + departureAirportLocale + '\'' +
                ", arrivalAirportCode='" + arrivalAirportCode + '\'' +
                ", arrivalAirportName='" + arrivalAirportName + '\'' +
                ", arrivalAirportCity='" + arrivalAirportCity + '\'' +
                ", arrivalAirportLocale='" + arrivalAirportLocale + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", ticketCurrency='" + ticketCurrency + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                ", flightDuration='" + flightDuration + '\'' +
                ", seatAvailable=" + seatAvailable +
                '}';

    }
    //Create a toJson method to convert the attributes to Json String
    public String toJson() {
        return "{" +
                "\"flightId\":" + flightId +
                ", \"departureDate\":\"" + departureDate + '\"' +
                ", \"departureTime\":\"" + departureTime + '\"' +
                ", \"departureAirportCode\":\"" + departureAirportCode + '\"' +
                ", \"departureAirportName\":\"" + departureAirportName + '\"' +
                ", \"departureAirportCity\":\"" + departureAirportCity + '\"' +
                ", \"departureAirportLocale\":\"" + departureAirportLocale + '\"' +
                ", \"arrivalAirportCode\":\"" + arrivalAirportCode + '\"' +
                ", \"arrivalAirportName\":\"" + arrivalAirportName + '\"' +
                ", \"arrivalAirportCity\":\"" + arrivalAirportCity + '\"' +
                ", \"arrivalAirportLocale\":\"" + arrivalAirportLocale + '\"' +
                ", \"arrivalDate\":\"" + arrivalDate + '\"' +
                ", \"arrivalTime\":\"" + arrivalTime + '\"' +
                ", \"ticketPrice\":" + ticketPrice +
                ", \"ticketCurrency\":\"" + ticketCurrency + '\"' +
                ", \"flightNumber\":\"" + flightNumber + '\"' +
                ", \"flightDuration\":\"" + flightDuration + '\"' +
                ", \"seatAvailable\":" + seatAvailable +
                '}';
    }
}






