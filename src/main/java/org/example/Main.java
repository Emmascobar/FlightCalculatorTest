package org.example;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        FlightTests flightTests = new FlightTests();

        // flights in system.
        String[] flightNumbers = {"OAF568S", "BGA164F", "FRG458A", "HPO521S", "PAS666D", "GRE952A", "FOS457A"};

        // Start Commands
        System.out.println("Hello! Remember... check this calculator test before to give an order to departure a specific flight.");
        System.out.println("Please select a Flight Number. Remember the format (ABC123D) or select one of this examples: OAF568S / BGA164F / FRG458A / HPO521S / PAS666D / GRE952A / FOS457A.");
        Scanner scanner = new Scanner(System.in);
        String flightNumber = scanner.nextLine().toUpperCase();
        boolean flightExist = Arrays.asList(flightNumbers).contains(flightNumber);

        while (!flightExist) {
            System.out.println("Flight number is not in System registry. Please input a flight number from the given list");
            flightNumber = scanner.nextLine().toUpperCase();
            flightExist = Arrays.asList(flightNumbers).contains(flightNumber);
        }

        //If flight exist in System - launch test calculator:
        flightTests.flightTesting(flightNumber);
    }
}