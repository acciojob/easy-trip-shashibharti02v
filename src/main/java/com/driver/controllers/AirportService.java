package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AirportService {
    @Autowired
    AirportRepository airportRepository;

    public void addAirport(Airport airport) {
         airportRepository.addAirport(airport);

    }

    public void addFlight(Flight flight) {
        airportRepository.addFlight(flight);
    }

    public String addPassenger(Passenger passenger) {
        String s = airportRepository.addPassenger(passenger);
        return s;
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
      String s = airportRepository.bookATicket(flightId, passengerId);
      return s;
    }


    public String cancleATicket(Integer flightId, Integer passengerId) {
        String s = airportRepository.cancleATicket(flightId, passengerId);
        return s;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        return airportRepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity, toCity);
    }

    public String getLargestAirportName() {
        return airportRepository.getLargestAirportName();
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        return airportRepository.calculateRevenueOfAFlight(flightId);
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        return airportRepository.getAirportNameFromFlightId(flightId);
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        return airportRepository.getNumberOfPeopleOn(date, airportName);
    }

    public int calculateFlightFare(Integer flightId) {
        return airportRepository.calculateFlightFare(flightId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }
}
