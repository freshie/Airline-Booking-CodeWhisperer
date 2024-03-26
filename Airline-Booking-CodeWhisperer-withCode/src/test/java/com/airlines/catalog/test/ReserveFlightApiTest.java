package com.airlines.catalog.test;
import com.airlines.catalog.FlightBookingApplication;
import com.airlines.catalog.dto.ReservationDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

/*
Create a BookingAPIReserveTest class to test the bookingApi using
web environment with random port.
*/
@SpringBootTest(classes = FlightBookingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReserveFlightApiTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /* Negative scenaio: 1, Invalid jwtToken
   create the reservationDetails object   with fields firstName, lastName, gender, age,
   flightId, travelClass, ticketPrice, currencyCode,paymentMode, contactNumber
   and contactEmail attributes
   Create the HTTP headers object and pass the jwtToken.
   Call /reserve end point using post method  pass reservationDetails as request body
   Assert that the response message is "Invalid Token"
 */
    @Test
    public void testInvalidJwtToken() {
        ReservationDetails reservationDetails = new ReservationDetails();
        reservationDetails.setFirstName("XXXX");
        reservationDetails.setLastName("XXX");
        reservationDetails.setGender("Male");
        reservationDetails.setAge(30);
        reservationDetails.setFlightId(1);
        reservationDetails.setTravelClass("First Class");
        reservationDetails.setTicketPrice(100.0);
        reservationDetails.setCurrencyCode("USD");
        reservationDetails.setPaymentMode("Credit Card");
        reservationDetails.setContactNumber("1234567890");
        reservationDetails.setContactEmail("XXXXXXXXXXXXXXXXXXXX");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer invalidJwtToken");

        HttpEntity<ReservationDetails> request = new HttpEntity<>(reservationDetails, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("/reserve", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isEqualTo("Invalid Token");
    }
        /* Negative scenaio:  Invalid Contact number
       create the reservationDetails object  with fields firstName, lastName, gender, age,
       flightId, travelClass, ticketPrice, currencyCode,paymentMode, invalid contactNumber
       and contactEmail attributes
       create the HTTP headers object and pass the jwtToken
       call /reserve end point using post method, pass reservationDetails as request body
       assert that the response message contains "Contact Number should be valid phone number"
     */
    @Test
    public void testInvalidContactNumber() {
        ReservationDetails reservationDetails = new ReservationDetails();
        reservationDetails.setFirstName("XXXX");
        reservationDetails.setLastName("XXX");
        reservationDetails.setGender("Male");
        reservationDetails.setAge(30);
        reservationDetails.setFlightId(1);
        reservationDetails.setTravelClass("First Class");
        reservationDetails.setTicketPrice(100.0);
        reservationDetails.setCurrencyCode("USD");
        reservationDetails.setPaymentMode("Credit Card");
        reservationDetails.setContactNumber("XXXX");
        reservationDetails.setContactEmail("XXXXXXXXXXXXXXXXXXXX");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "eyJraWQiOiJ4WlRlUjgxYytESFZkekd2bmJrXC9UeEh6c1wvUDV5dCtFR0E1ZVRvNUhvRUE9IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJkNjA1NTcyZC1kNzQzLTQ4MzMtOWJlMy0xYmZmNDRhYjJlZTgiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtd2VzdC0yLmFtYXpvbmF3cy5jb21cL3VzLXdlc3QtMl9kVGt5VkJlUGQiLCJ2ZXJzaW9uIjoyLCJjbGllbnRfaWQiOiI1bjhnZnF0ZG5nZzc5dTJnZDM1ZDc4Z2djYSIsImV2ZW50X2lkIjoiNzc4ZTY5OWMtZWFlNC00ZjQxLWI3MWEtOTA3MmE0NWFlYmNjIiwidG9rZW5fdXNlIjoiYWNjZXNzIiwic2NvcGUiOiJwaG9uZSBvcGVuaWQgZW1haWwiLCJhdXRoX3RpbWUiOjE2OTgzMTA3MTUsImV4cCI6MTY5ODMxNDMxNSwiaWF0IjoxNjk4MzEwNzE1LCJqdGkiOiIyOTBhOWNiZS1kYWJhLTQ0ZGQtYmU4Yi01NmRiMTIwNWEwZWUiLCJ1c2VybmFtZSI6InJyYW1lc2gifQ.G3ZSrEsBvGZlOqHsJ8H2dGr6dpwEhu5HUwSMcOM5f1pTuhsVc-wGXCXbgiDYKLJ_Kgs554etaCtUuGa_T4KT0n1BGbIus-emcmI9byOyTG1Ie7RT3lEiNR-Mtd5TyiJ45rSghTSUE6SiQFzx0JsBEPZmxIuOr2kyUEa88E8e555VDgAcjQNwvqlIKX01C8SEjllSVBnH0ETo6jknBpzs_nyd36Dj20V17Xx72DfYK_5yFH_75qGE4z3so6CUDwLYSNeWyB3BCZnVCRUJknBxS69u5ITH2N3O46YjvQdmpswvsJ3Bw-a3twCPhuOk0nZ61E1977ovU0MtfOag_GWFlQ");

        HttpEntity<ReservationDetails> request = new HttpEntity<>(reservationDetails, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("/reserve", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Contact Number should be valid phone number");
        assertThat(response.getBody()).contains("Contact Email should be valid email");
    }
        /* Positive scenario 1:
       create the reservationDetails object  with fields firstName, lastName, gender, age,
       flightId, travelClass, ticketPrice, currencyCode,paymentMode, contactNumber
       and contactEmail attributes
       create the HTTP headers object and pass the jwtToken
       call /reserve end point using post method, pass reservationDetails as request body
       assert that http status OK
       and  assert that the response message contains "reservation made successfully"
     */
    @Test
    public void testPositiveScenario() {
        ReservationDetails reservationDetails = new ReservationDetails();
        reservationDetails.setFirstName("XXXX");
        reservationDetails.setLastName("XXX");
        reservationDetails.setGender("Male");
        reservationDetails.setAge(30);
        reservationDetails.setFlightId(1);
        reservationDetails.setTravelClass("First Class");
        reservationDetails.setTicketPrice(100.0);
        reservationDetails.setCurrencyCode("USD");
        reservationDetails.setPaymentMode("Credit Card");
        reservationDetails.setContactNumber("+911234567890");
        reservationDetails.setContactEmail("XX@gmail.com");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "eyJraWQiOiJ4WlRlUjgxYytESFZkekd2bmJrXC9UeEh6c1wvUDV5dCtFR0E1ZVRvNUhvRUE9IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJkNjA1NTcyZC1kNzQzLTQ4MzMtOWJlMy0xYmZmNDRhYjJlZTgiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtd2VzdC0yLmFtYXpvbmF3cy5jb21cL3VzLXdlc3QtMl9kVGt5VkJlUGQiLCJ2ZXJzaW9uIjoyLCJjbGllbnRfaWQiOiI1bjhnZnF0ZG5nZzc5dTJnZDM1ZDc4Z2djYSIsImV2ZW50X2lkIjoiNzc4ZTY5OWMtZWFlNC00ZjQxLWI3MWEtOTA3MmE0NWFlYmNjIiwidG9rZW5fdXNlIjoiYWNjZXNzIiwic2NvcGUiOiJwaG9uZSBvcGVuaWQgZW1haWwiLCJhdXRoX3RpbWUiOjE2OTgzMTA3MTUsImV4cCI6MTY5ODMxNDMxNSwiaWF0IjoxNjk4MzEwNzE1LCJqdGkiOiIyOTBhOWNiZS1kYWJhLTQ0ZGQtYmU4Yi01NmRiMTIwNWEwZWUiLCJ1c2VybmFtZSI6InJyYW1lc2gifQ.G3ZSrEsBvGZlOqHsJ8H2dGr6dpwEhu5HUwSMcOM5f1pTuhsVc-wGXCXbgiDYKLJ_Kgs554etaCtUuGa_T4KT0n1BGbIus-emcmI9byOyTG1Ie7RT3lEiNR-Mtd5TyiJ45rSghTSUE6SiQFzx0JsBEPZmxIuOr2kyUEa88E8e555VDgAcjQNwvqlIKX01C8SEjllSVBnH0ETo6jknBpzs_nyd36Dj20V17Xx72DfYK_5yFH_75qGE4z3so6CUDwLYSNeWyB3BCZnVCRUJknBxS69u5ITH2N3O46YjvQdmpswvsJ3Bw-a3twCPhuOk0nZ61E1977ovU0MtfOag_GWFlQ");
        HttpEntity<ReservationDetails> request = new HttpEntity<>(reservationDetails, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("/reserve", request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}