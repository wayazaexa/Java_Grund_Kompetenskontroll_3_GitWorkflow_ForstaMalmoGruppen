package org.example.utils;

import java.util.Scanner;

public class Menu {
    public static void printMenu(String... args) {
        int i;
        for (i = 0; i < args.length; i++) {
            System.out.println(i + 1 + ") " + args[i]);
        }
    }

    public static void main() {
        /// @param String... args take many words
        /// printMenu is static method come from Menu class, there is utils.
        printMenu("Create", "Change date of booking", "Cancel booking", "Show bookings", "close");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                var choice = scanner.nextLine();
                switch (choice) {
                    case "1" -> System.out.println("CreateBooking ");
                    case "2" -> System.out.println("ChangeStatus ");
                    case "3" -> System.out.println("CancelBooking ");
                    case "4" -> System.out.println("Show all bookings");
                    case "5" -> System.out.println("Close ");
                    default -> System.out.println("Invalid choice");

                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
