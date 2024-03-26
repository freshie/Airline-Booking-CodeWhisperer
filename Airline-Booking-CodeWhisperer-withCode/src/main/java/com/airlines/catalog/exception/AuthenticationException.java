package com.airlines.catalog.exception;

import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import java.net.MalformedURLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.Getter;
/*Create a public class AuthenticationException that extends Runtime Exception with member variable
response entity and constructor with Exception as input parameter.
Check for different types of JWT exceptions
Store the message in member variable response entity.
*/
@Getter
public class AuthenticationException extends RuntimeException {

    private ResponseEntity responseEntity;

    public AuthenticationException(Exception e) {
        if (e instanceof JWTDecodeException) {
            responseEntity = new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
        } else if (e instanceof TokenExpiredException) {
            responseEntity = new ResponseEntity<>("Token Expired", HttpStatus.UNAUTHORIZED);
        } else if (e instanceof InvalidClaimException) {
            responseEntity = new ResponseEntity<>("Invalid Claim", HttpStatus.UNAUTHORIZED);
        } else if (e instanceof MalformedURLException) {
            responseEntity = new ResponseEntity<>("Invalid URL", HttpStatus.UNAUTHORIZED);
        }
    }
}