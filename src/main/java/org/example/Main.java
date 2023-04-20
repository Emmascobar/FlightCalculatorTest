package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import static org.example.HaversineFormula.haversine;

public class Main {

    public static void main(String[] args) throws IOException {

        FlightTests flightTests = new FlightTests();
        System.out.println("Hello Mrs. Remember check this calculator before get the order to departure a specific flight");
        System.out.println("Please select a Flight Number. Remember the format (ABC123D) or select one of this examples: OAF568S / BGA164F / FRG458A / HPO521S / PAS666D / GRE952A / FOS457A.");
        String[] flightNumbers = {"OAF568S", "BGA164F", "FRG458A", "HPO521S", "PAS666D", "GRE952A", "FOS457A"};
        Scanner scanner = new Scanner(System.in);
        String flightNumber = scanner.nextLine().toUpperCase();
        //Lunch calculator;
        flightTests.flightTesting(flightNumber);
    }
}