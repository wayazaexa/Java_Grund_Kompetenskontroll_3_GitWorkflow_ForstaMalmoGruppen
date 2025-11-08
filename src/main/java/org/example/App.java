package org.example;

import org.example.dto.BookingRepository;
import org.example.services.BookingService;
//import org.example.services.MockEmailService;
import org.example.services.NotificationService;
import org.example.store.BookingStore;
import org.example.store.NotificationRepo;
import org.example.utils.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.services.InitializationService.init;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        log.info("Booking app started");
        BookingStore store = init();
        BookingRepository bookingStore = new BookingRepository();
        NotificationRepo notificationService = new NotificationService();
        BookingService service = new BookingService(bookingStore, notificationService);
        new Menu(service).run();
        log.info("Exiting app");
    }
}
