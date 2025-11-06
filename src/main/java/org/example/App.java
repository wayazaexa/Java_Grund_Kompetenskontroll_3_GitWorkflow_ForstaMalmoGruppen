package org.example;

import org.example.services.BookingService;
import org.example.services.MockEmailService;
import org.example.store.BookingStore;
import org.example.store.NotificationRepo;
import org.example.utils.Menu;

import static org.example.services.InitializationService.init;

public class App {
    public static void main(String[] args) {
        BookingStore store = init();
        NotificationRepo notificationService = new MockEmailService();
        BookingService service = new BookingService(store, notificationService);
        new Menu(service).run();
    }
}
