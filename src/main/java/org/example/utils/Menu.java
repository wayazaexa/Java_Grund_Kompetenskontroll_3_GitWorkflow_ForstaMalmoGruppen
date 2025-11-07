package org.example.utils;

import org.example.entities.Booking;
import org.example.entities.BookingType;
import org.example.entities.Vehicle;
import org.example.factories.*;
import org.example.services.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Scanner;

public class Menu {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final BookingService service;
    private final Scanner scanner = new Scanner(System.in);

    public Menu(BookingService service) {
        this.service = service;
    }

    public static void printMenu(String... args) {
        System.out.println();
        int i;
        for (i = 0; i < args.length; i++) {
            System.out.println(i + 1 + ") " + args[i]);
        }
    }

    public void run() {
        /// @param String... args take many words
        /// printMenu is static method come from Menu class, there is utils.
        while (true) {
            try {
                printMenu("Create booking", "Update booking", "Cancel booking", "Show bookings", "Show booking details", "Close app");
                System.out.print("Enter your choice: ");
                var choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1" -> createBooking();
                    case "2" -> updateBooking();
                    case "3" -> deleteBooking();
                    case "4" -> showBookings();
                    case "5" -> showBookingDetails();
                    case "6" -> { return; }
                    default -> System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void createBooking() {
        System.out.println("CreateBooking");
        BookingType bookingType = inputBookingType();
        Vehicle vehicle = inputVehicle();
        LocalDate date = inputDate();
        System.out.print("Enter customer's e-mail address: ");
        String email = scanner.nextLine().trim();
        service.createBooking(bookingType, vehicle, date, email);
    }

    private BookingType inputBookingType() {
        String input;
        while (true) {
            System.out.print("Enter what type of booking this is (r)epair/(s)ervice/(i)nspection: ");
            input = scanner.nextLine().trim().toLowerCase();
            switch (input) {
                case "repair", "r" -> {
                    return BookingType.REPAIR;
                }
                case "service", "s" -> {
                    return BookingType.SERVICE;
                }
                case "inspection", "i" -> {
                    return BookingType.INSPECTION;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private Vehicle inputVehicle() {
        VehicleFactory factory = new VehicleFactory();
        String regNr;
        String model;
        int year;
        while (true) {
            System.out.print("Enter registration plate: ");
            regNr = scanner.nextLine().trim().toUpperCase();
            if (validateRegistrationPlate(regNr)) {
                break;
            }
        }
        System.out.print("Enter model: ");
        model = scanner.nextLine().trim();
        year = handleIntInput("Enter manufacturing year: ");
        try {
            return factory.createVehicle(regNr, model, year);
        } catch (RuntimeException e) {
            log.error("Could not create vehicle with reg. plate {}, model {}, and year {}", regNr, model, year);
            return null;
        }
    }

    private boolean validateRegistrationPlate(String regNr) {
        return true;
    }

    private LocalDate inputDate() {
        System.out.println("Enter date the customer should hand in their vehicle (format YYMMDD): ");
        while (true) {
            try {
                return LocalDate.parse(scanner.nextLine().trim(), DateTimeFormatter.ofPattern("yyMMdd"));
            } catch (DateTimeParseException ex) {
                System.out.println("Invalid date format");
                System.out.print("Enter date (format YYMMDD): ");
            }
        }
    }

    int handleIntInput(String text) {
        String userInput;
        int number;
        while (true) {
            System.out.print(text);
            userInput = scanner.nextLine().trim();
            try {
                number = Integer.parseInt(userInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println(userInput + " is not a number");
            }
        }
        return number;
    }

    private void updateBooking() {
        System.out.println("ChangeStatus");
    }

    private void deleteBooking() {
        System.out.println("CancelBooking");
    }

    private void showBookings() {
        System.out.println("\nShow all bookings");
        service.getAll().forEach(Booking::printShortInfo);

        while (true) {
            printMenu("Sort by id", "Sort by date", "Sort by status", "Return to main menu");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> sortById();
                case "2" -> sortByDate();
                case "3" -> sortByStatus();
                case "4" -> { return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void sortById() {
        System.out.println("Bookings sorted by id");
        service.getAll().stream()
                .sorted(Comparator.comparing(Booking::getId))
                .forEach(Booking::printShortInfo);
    }

    private void sortByDate() {
        System.out.println("Bookings sorted by date");
        service.getAll().stream()
                .sorted(Comparator.comparing(Booking::getDate))
                .forEach(Booking::printShortInfo);
    }

    private void sortByStatus() {
        System.out.println("Bookings sorted by status");
        service.getAll().stream()
                .sorted(Comparator.comparing(Booking::getStatus))
                .forEach(Booking::printShortInfo);
    }

    private void showBookingDetails() {
        System.out.println("\nBookings:");
        service.getAll().forEach(Booking::printShortInfo);
        int choice = handleIntInput("Enter id of the booking you want to see details of: ");
        Booking tmp = service.findById(choice);
        if (tmp == null) {
            System.out.println("Booking was not found in the system");
        }
        else {
            System.out.println(tmp);
        }
    }
}
