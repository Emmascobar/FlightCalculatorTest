package org.example;

import java.time.LocalTime;

public class FlightInformation {

    private Integer id;
    private String flightNumber;
    private int passengers;
    private LocalTime takeOffTime;
    private String departure;
    private String arrival;

    public FlightInformation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public LocalTime getTakeOffTime() {
        return takeOffTime;
    }

    public void setTakeOffTime(LocalTime takeOffTime) {
        this.takeOffTime = takeOffTime;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }
}
