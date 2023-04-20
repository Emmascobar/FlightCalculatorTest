package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.example.HaversineFormula.haversine;

public class FlightTests {
    final Logger log = LoggerFactory.getLogger(this.getClass());

    public void flightTesting(String flightNumber) throws IOException {


        //Mapper Json file with data about airports:
        ObjectMapper airportsMapper = new ObjectMapper();
        airportsMapper.registerModule(new JavaTimeModule());
        List<AirportInformation> airportInformationList = airportsMapper.readValue(new File("C:\\Users\\Emmanuel\\Desktop\\Novular Test\\FlightDistanceLimitationCalculator\\src\\main\\resources\\Airports.txt"), new TypeReference<List<AirportInformation>>() {
        });

        //First asking about destinations:
        System.out.println("Please select departure destination. For this example you can choose: Melbourne, Dublin, Vancouver, Barcelona, Paris, Los Angeles or London");
        String[] destinations = {"melbourne", "dublin", "vancouver", "barcelona", "paris", "los angeles", "london"};
        Scanner scanner = new Scanner(System.in);
        String departureScanner = scanner.nextLine().toLowerCase();
        boolean departureInList = Arrays.asList(destinations).contains(departureScanner);

        // Check if departure destination exist in System (JSON file)
        while (!departureInList) {
            System.out.println("Departure destination is not in System. Please input one from the given list");
            ;
            departureScanner = scanner.nextLine().toLowerCase();
            departureInList = Arrays.asList(destinations).contains(departureScanner);
        }

        System.out.println("Please select arrival destination. For this example you can choose: Melbourne, Dublin, Vancouver, Barcelona, Paris, Los Angeles or London\"");
        String arrivalScanner = scanner.nextLine().toLowerCase();
        boolean arrivalInList = Arrays.asList(destinations).contains(arrivalScanner);

        // Check if arrival destination exist in System (JSON file)
        while (!arrivalInList) {
            System.out.println("Arrival destination is not in System. Please input one from the given list");
            arrivalScanner = scanner.nextLine().toLowerCase();
            arrivalInList = Arrays.asList(destinations).contains(arrivalScanner);
        }
        while (departureScanner.equals(arrivalScanner)){
            System.out.println("Arrival destination is same than origin. Please select another one.");
            arrivalScanner = scanner.nextLine().toLowerCase();
            arrivalInList = Arrays.asList(destinations).contains(arrivalScanner);
        }

        //Now found, filter, and define departure and arrival from results:
        String finalDepartureScanner = departureScanner;
        AirportInformation departure = airportInformationList.stream().filter(airportInformation -> airportInformation.getName().toLowerCase().equals(finalDepartureScanner)).findFirst().orElse(null);
        String finalArrivalScanner = arrivalScanner;
        AirportInformation arrival = airportInformationList.stream().filter(airportInformation -> airportInformation.getName().toLowerCase().equals(finalArrivalScanner)).findFirst().orElse(null);

        //Then calculate the distance with Haversine formula :
        double distance = haversine(departure.getLatitude(), departure.getLongitude(), arrival.getLatitude(), arrival.getLongitude());

        //Mapper Json file with data about flights:
        ObjectMapper flightMapper = new ObjectMapper();
        flightMapper.registerModule(new JavaTimeModule());
        List<FlightInformation> flightInformationList = flightMapper.readValue(new File("C:\\Users\\Emmanuel\\Desktop\\Novular Test\\FlightDistanceLimitationCalculator\\src\\main\\resources\\FlightsInformation.txt "), new TypeReference<List<FlightInformation>>() {
        });

        // Now the information get like parameter from the client (flightNumber) is used to get a specific flight. In case of this app
        // destinations can be change when we want, but other option is get the departure and arrival destinations from the same Json file.
        FlightInformation flight = flightInformationList.stream().filter(flightInformation -> flightInformation.getFlightNumber().equals(flightNumber)).findFirst().orElseThrow(null);
        //Now we set the departure and arrival destinations of the flight with the information previously get by the client (ScannerLine).
        flight.setDeparture(departure.getName());
        flight.setArrival(arrival.getName());

        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("INFO!!! - Chosen flight Nº " + flightNumber + ", with origin in airport of " + flight.getDeparture() + " and arrival in airport of " + flight.getArrival() + ". Total distance between both are: " + Math.round(distance) + " Km." + " The total of passengers of this flight are: " + flight.getPassengers() + " and the take off time is " + flight.getTakeOffTime() + " hs.");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        // Time to apply the rules:
        //Variables:
        LocalTime now = LocalTime.now();
        LocalTime timeCut01 = LocalTime.of(14, 0);
        LocalTime timeCut02 = LocalTime.of(20, 0);
        int passengersLimits = 250;
        double distanceCut01 = 8000;
        double distanceCut02 = 9000;
        double distanceCut03 = 12000;
        boolean rule01 = false;
        boolean rule02 = false;

        // RULE 01:
        if (flight.getPassengers() > passengersLimits && distance <= distanceCut01 || flight.getPassengers() <= passengersLimits && distance <= distanceCut03) {
            rule01 = true;
            System.out.println("Flight Number " + flight.getFlightNumber() + " accomplish with rule number one: passengers - distance");
            log.info("Rule number one: Passengers - Distance are accomplish. Registering in system log.");
        } else {
            rule01 = false;
            System.out.println("Flight Number " + flight.getFlightNumber() + " don't accomplish with rule number one: passengers - distance");
            log.info("Rule number one: Passengers - Distance don't accomplish. Registering in system log.");

        }

        // RULE 2:
        if (flight.getTakeOffTime().isBefore(timeCut01) && distance >= distanceCut01 || flight.getTakeOffTime().isBefore(timeCut02) && distance <= distanceCut02) {
            rule02 = true;
            System.out.println("Flight Number " + flight.getFlightNumber() + " accomplish with rule number two: Take off hour");
            log.info(flight.getFlightNumber() + ": Rule number two: Take off hour are accomplish. Registering in system log.");
        } else {
            rule02 = false;
            System.out.println("Flight Number " + flight.getFlightNumber() + " don't accomplish with rule number two:Take off hour");
            log.info(flight.getFlightNumber() + ": Rule number two: Take off hour - Distance don't accomplish. Registering in system log.");

        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        String IsFeasible = "";
        if (rule01 && rule02) {
            IsFeasible = "Flight Number " + flight.getFlightNumber() + " with origin in " + flight.getDeparture() + " and final destination in " + flight.getArrival() + " is feasible to departure. Registering in System log ";
            log.info(IsFeasible + ">>> IS feasible to departure. ");
        } else {
            IsFeasible = "Flight Number " + flight.getFlightNumber() + " with origin in " + flight.getDeparture() + " and final destination in " + flight.getArrival() + " is NOT feasible to departure. Registering in System log ";
            log.info(IsFeasible + ">>>> IS NOT feasible to departure. ");
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        //Final Result
        System.out.println(IsFeasible.toUpperCase());
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

    }
}
