package org.example.utils;

import org.example.entities.*;
import org.example.factories.*;
import org.example.services.BookingService;
import org.example.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Menu {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final BookingService service;
    private final Scanner scanner = new Scanner(System.in);
    private final NotificationService notificationService = new NotificationService();
    private final Set<LocalDateTime> bookedSlots = new HashSet<>();

    public Menu(BookingService service) {
        this.service = service;
    }
    List<LocalTime> dailySlots = List.of(
            LocalTime.of(9, 0),
            LocalTime.of(10, 0),
            LocalTime.of(11, 0),
            LocalTime.of(13, 0),
            LocalTime.of(14, 0),
            LocalTime.of(15, 0)
    );

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
                printMenu("Create booking", "Update booking", "Remove booking", "Confirm booking as done", "Show bookings", "Show booking details", "Close app");
                System.out.print("Enter your choice: ");
                var choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1" -> createBooking();
                    case "2" -> updateBooking(scanner);
                    case "3" -> deleteBooking();
                    case "4" -> markBookingAsDone();
                    case "5" -> showBookings();
                    case "6" -> showBookingDetails();
                    case "7" -> { return; }
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
        LocalDateTime date = inputDateTime(scanner);
        System.out.print("Enter customer's e-mail address: ");
        String email = promptForEmail(scanner);
        service.createBooking(bookingType, vehicle, date, email);
    }

    private boolean isValidEmail(String email) {
            String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            return Pattern.matches(regex, email);
    }

    private String promptForEmail(Scanner scanner) {
        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine().trim();
            if (isValidEmail(email)) {
                return email;
            }
            System.out.println("Invalid email, please try again.");
        }
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
        do {
            System.out.print("Enter registration plate: ");
            regNr = scanner.nextLine().trim().toUpperCase();
        } while (!validateRegistrationPlate(regNr));
        System.out.print("Enter model: ");
        model = scanner.nextLine().trim();
        year = handleYearInput();
        try {
            return factory.createVehicle(regNr, model, year);
        } catch (RuntimeException e) {
            log.error("Could not create vehicle with reg. plate {}, model {}, and year {}", regNr, model, year);
            return null;
        }
    }

    private boolean validateRegistrationPlate(String regNr) {
            String regex1 = "^[A-Za-zÅÄÖåäö]{3}[0-9]{3}$";       // ABC123
            String regex2 = "^[A-Za-zÅÄÖåäö]{3}[0-9]{2}[A-Za-zÅÄÖåäö]$"; // ABC12A
            return Pattern.matches(regex1, regNr) || Pattern.matches(regex2, regNr);

    }
    public boolean isAvailable(LocalDate date, LocalTime time) {
        return !bookedSlots.contains(LocalDateTime.of(date, time));
    }
    private LocalDateTime inputDateTime(Scanner scanner) {
        // build list of the next 3 available days
        List<LocalDate> availableDays = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            availableDays.add(LocalDate.now().plusDays(i));
        }

        LocalDate chosenDate;

        // loop until user picks a valid date
        while (true) {
            System.out.println("Choose date:");
            for (int i = 0; i < availableDays.size(); i++) {
                System.out.println((i + 1) + ") " + availableDays.get(i));
            }

            int dch = safeInt(scanner);
            if (dch >= 1 && dch <= availableDays.size()) {
                chosenDate = availableDays.get(dch - 1);
                break;
            }
            System.out.println("Invalid date, please try again.");
        }

        // now pick a time for the chosen date
        while (true) {
            List<LocalTime> availableTimes = new ArrayList<>();

            // collect only free time slots for that day
            for (LocalTime t : dailySlots) {
                if (isAvailable(chosenDate, t)) {
                    availableTimes.add(t);
                }
            }

            // if no times are free, force user to pick another day
            if (availableTimes.isEmpty()) {
                System.out.println("No available times for this date. Please choose another date.");
                return inputDateTime(scanner); // restart the whole process
            }

            System.out.println("Available times:");
            for (int i = 0; i < availableTimes.size(); i++) {
                System.out.println((i + 1) + ") " + availableTimes.get(i));
            }


            int tch = safeInt(scanner);
            if (tch >= 1 && tch <= availableTimes.size()) {
                LocalTime chosenTime = availableTimes.get(tch - 1);
                LocalDateTime chosenSlot = LocalDateTime.of(chosenDate, chosenTime);

                // IMPORTANT: mark the slot as booked so it won’t show next time
                bookedSlots.add(chosenSlot);

                return chosenSlot;
            }

            System.out.println("Invalid time, please try again.");
        }
    }



    private static int safeInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.nextLine();
            System.out.print("Skriv ett heltal: ");
        }
        int v = sc.nextInt();
        sc.nextLine();
        return v;
    }

    int handleIntInput(String text) {
        while (true) {
            System.out.print(text);
            String userInput = scanner.nextLine().trim();

            try {
                return Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println(userInput + " is not a number, try again.");
            }
        }
    }
    // use this for vehicle year
    int handleYearInput() {
        int currentYear = Year.now().getValue();
        while (true) {
            System.out.print("Enter manufacturing year: ");
            String userInput = scanner.nextLine().trim();

            try {
                int year = Integer.parseInt(userInput);
                if (year >= 1950 && year <= currentYear) {
                    return year;
                }
                System.out.println("Year must be between 1950 and " + currentYear);
            } catch (NumberFormatException e) {
                System.out.println(userInput + " is not a valid year, try again.");
            }
        }
    }

    private void updateBooking(Scanner scanner) {
        System.out.print("Enter id: ");
        int bookingId;
        try {
            bookingId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid id.");
            return;
        }

        var existingOpt = service.getAll()
                .stream()
                .filter(b -> b.getId() == bookingId)
                .findFirst();

        if (existingOpt.isEmpty()) {
            System.out.println("Booking not found.");
            return;
        }

        Booking existing = existingOpt.get();

        System.out.println("What do you want to update?");
        System.out.println("1) Status");
        System.out.println("2) Vehicle reg.nr");
        System.out.println("3) Email");
        System.out.print("Choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        BookingStatus newStatus = null;
        Booking updated = existing;

        switch (choice) {
            case 1 -> {
                printMenu("BOOKED", "CANCELLED", "DONE");
                int s = scanner.nextInt();
                scanner.nextLine();
                 newStatus = switch (s) {
                    case 2 -> BookingStatus.CANCELLED;
                    case 3 -> BookingStatus.DONE;
                    default -> BookingStatus.BOOKED;
                };

                existing.setStatus(newStatus);

                // optional: notify
                notificationService.notifyBookingEvent(updated, newStatus);
            }
            case 2 -> {
                System.out.print("New reg.nr: ");
                String newReg = scanner.nextLine();

                // create new vehicle with same model/year but new reg
                var oldV = existing.getVehicle();
                var newVehicle = new Vehicle(newReg, oldV.getModel(), oldV.getYear());
                existing.setVehicle(newVehicle);
                existing.setId(existing.getId());
                existing.setEmail(existing.getEmail());
                existing.setStatus(existing.getStatus());

            }
            case 3 -> {
                System.out.print("New email: ");
                String newEmail = scanner.nextLine();

                updated.setEmail(newEmail);
                System.out.println("Booking's email has been updated. " + updated.getEmail());

            }
            default -> {
                System.out.println("Invalid choice.");
                return;
            }
        }

        // save update in service/repo
        service.update(updated, newStatus);
    }


    private void deleteBooking() {
        System.out.println("\nBookings:");
        service.getAll().forEach(Booking::printShortInfo);
        int choice = handleIntInput("Enter id of the booking you want to delete: ");

        Booking tmp = service.delete(choice);
        if (tmp == null) {
            System.out.println("\nBooking was not found in the system");
        } else {
            System.out.println("\nBooking with id " + choice + " has been deleted.");
        }
    }

    private void markBookingAsDone() {
        System.out.println("\nBookings:");
        service.getAll().forEach(Booking::printShortInfo);
        int choice = handleIntInput("Enter id of the booking that's done: ");

        Booking tmp = service.findById(choice);
        if (tmp == null) {
            System.out.println("\nBooking was not found in the system");
        } else {
            tmp.setStatus(BookingStatus.DONE);
            if (tmp instanceof Repair) {
                int cost = handleIntInput("Enter how much this repair costs: ");
                tmp.setPrice(cost);
            }
            service.update(tmp,tmp.getStatus());
            System.out.println("\nBooking with id " + choice + " has been marked as done.");
        }
    }

    private void showBookings() {
        System.out.println("\nShow all bookings");
        service.getAll().forEach(Booking::printShortInfo);

        while (true) {
            printMenu("Sort by id", "Sort by date", "Sort by status", "Sort by type", "Return to main menu");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> sortById();
                case "2" -> sortByDate();
                case "3" -> sortByStatus();
                case "4" -> sortByType();
                case "5" -> { return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void sortById() {
        System.out.println("\nBookings sorted by id");
        service.getAll().stream()
                .sorted(Comparator.comparing(Booking::getId))
                .forEach(Booking::printShortInfo);
        log.info("Bookings was sorted by id");
    }

    private void sortByDate() {
        System.out.println("\nBookings sorted by date");
        service.getAll().stream()
                .sorted(Comparator.comparing(Booking::getDate))
                .forEach(Booking::printShortInfo);
        log.info("Bookings was sorted by date");
    }

    private void sortByStatus() {
        System.out.println("\nBookings sorted by status");
        service.getAll().stream()
                .sorted(Comparator.comparing(Booking::getStatus))
                .forEach(Booking::printShortInfo);
        log.info("Bookings was sorted by status");
    }

    private void sortByType() {
        System.out.println("\nBookings sorted by type");
        Map<Class<? extends Booking>, List<Booking>> groupedBySubclass =
                service.getAll().stream()
                        .collect(Collectors.groupingBy(Booking::getClass));
        groupedBySubclass.forEach((c, l) -> {
            System.out.println("\nBooking type: " + c.getSimpleName());
            l.forEach(Booking::printShortInfo);
        });
        log.info("Bookings was sorted by type");
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
