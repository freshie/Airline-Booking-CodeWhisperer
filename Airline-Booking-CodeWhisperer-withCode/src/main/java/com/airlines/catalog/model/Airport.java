package com.airlines.catalog.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;
import javax.persistence.*;

/* Create an Entity class Airport mapped to table airport with following 4 attributes;
airportCode as id, airportName, aiportCity and airportLocale.
They should be mapped to database columns with _ as separator.*/

@Entity
@Table(name = "airport")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Airport {
    @Id
    @Column(name = "airport_code")
    private String airportCode;
    @Column(name = "airport_name")
    private String airportName;
    @Column(name = "airport_city")
    private String airportCity;
    @Column(name = "airport_locale")
    private String airportLocale;
}
