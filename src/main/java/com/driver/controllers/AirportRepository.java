package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {
    HashMap<String, Airport>airportDB = new HashMap<>();
    HashMap<Integer, Flight>flightDB = new HashMap<>();
    HashMap<Integer, Passenger>passengerDB = new HashMap<>();

    private Map<Integer, Set<Integer>> flightPassMap=new HashMap<>();
    private Map<Integer, Integer> revenueMap=new HashMap<>();
    private Map<Integer, Integer> paymentMap=new HashMap<>();

    public void addAirport(Airport airport) {
        airportDB.put(airport.getAirportName(), airport);

    }


    public void addFlight(Flight flight) {
        flightDB.put(flight.getFlightId(), flight);
    }

    public String addPassenger(Passenger passenger) {
        if(!passengerDB.containsKey(passenger.getPassengerId())){
            passengerDB.put(passenger.getPassengerId(), passenger);
        }
        return null;
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        if(Objects.nonNull(flightPassMap.get(flightId)) &&(flightPassMap.get(flightId).size()<flightDB.get(flightId).getMaxCapacity())) {
            Set<Integer> passengerList = flightPassMap.get(flightId);
            if(passengerList.contains(passengerId)) {
                return "FAILURE";
            }
            passengerList.add(passengerId);
            flightPassMap.put(flightId,passengerList);
            return "SUCCESS";
        }
        else if(Objects.isNull(flightPassMap.get(flightId))) {
            flightPassMap.put(flightId,new HashSet<>());
            Set<Integer> passengerList = flightPassMap.get(flightId);

            if(passengerList.contains(passengerId)) {
                return "FAILURE";
            }
            passengerList.add(passengerId);
            flightPassMap.put(flightId,passengerList);
            return "SUCCESS";
        }
        return "FAILURE";
    }

    public String cancleATicket(Integer flightId, Integer passengerId) {
        if(flightPassMap.containsKey(flightId)){
            Set<Integer>passengerList = flightPassMap.get(flightId);
            if(passengerList.contains(passengerId)){
                passengerList.remove(passengerId);
                flightPassMap.put(flightId, passengerList);
                return "SUCCESS";
            }
            return "FAILURE";
        }
        return "FAILURE";

    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {

        double time = Integer.MAX_VALUE;
        for(Flight f: flightDB.values()){
            if(f.getDuration() < time){
                time = f.getDuration();
            }
        }
        return time == Integer.MAX_VALUE? -1: time;
    }

    public String getLargestAirportName() {

        int terminals = 0;
        String name = "";
        for(Airport airport: airportDB.values()){
            if(airport.getNoOfTerminals() > terminals){
                terminals = airport.getNoOfTerminals();
                name = airport.getAirportName();
            }
            else if(airport.getNoOfTerminals() == terminals){
                if(airport.getAirportName().compareTo(name) < 0){
                    name = airport.getAirportName();
                }
            }
        }
        return name;
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        int revenue = 0;
        int noOfPassenger = flightPassMap.get(flightId).size();
        if(noOfPassenger == 1)return 3000;
        int variableFare = (noOfPassenger*(noOfPassenger+1))*50;
        int fixedFare = 3000*noOfPassenger;
        revenue = variableFare + fixedFare;
        return revenue;
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        String name = "";
        if(!flightDB.containsKey(flightId))return null;
        Flight flight = flightDB.get(flightId);
        City city = flight.getFromCity();
        for(String airportName: airportDB.keySet()){
            Airport airport = airportDB.get(airportName);
            if(city.equals(airport.getCity())){
                 return airportName;
            }
        }
        return null;
        }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        Airport airport = airportDB.get(airportName);
        int count = 0;
        if(airport != null){
            City city = airport.getCity();
            for(Flight flight: flightDB.values()){
                if(flight.getFlightDate().equals(date)){
                    if(city.equals(flight.getToCity()) || city.equals(flight.getFromCity())){
                        Integer flightId = flight.getFlightId();
                        Set<Integer> list = flightPassMap.get(flightId);
                        if(list!=null){
                            count+=list.size();
                        }
                    }
                }
            }
        }
        return count;
    }

    public int calculateFlightFare(Integer flightId) {
        int fare = 3000;
        int alreadyBooked = 0;
        if(flightPassMap.containsKey(flightId)){
            alreadyBooked=flightPassMap.get(flightId).size();
        }
        return (fare + (alreadyBooked*50));
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        int count = 0;
        for(Integer flightids:flightPassMap.keySet()){
            Set<Integer> list = flightPassMap.get(flightids);
            if(list.contains(passengerId)){
                count++;
            }
        }
        return count;
    }
}



