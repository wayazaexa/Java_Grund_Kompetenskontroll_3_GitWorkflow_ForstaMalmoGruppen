package org.example.services;

import org.example.entities.Booking;
import org.example.entities.BookingStatus;
import org.example.store.NotificationRepo;

import java.util.Map;
import java.util.function.BiFunction;

public class NotificationService implements NotificationRepo {

    public NotificationService() {
    }

    private final Map<BookingStatus, BiFunction<Booking, BookingStatus, NotificationMessage>> messageBuilders =
            Map.of(
                    BookingStatus.BOOKED, (booking, st) -> new NotificationMessage(
                            "Booking created – Reg.nr " + booking.getVehicle().getRegNr(),
                            "Your booking is booked for " + booking.getDate()
                    ),
                    BookingStatus.CANCELLED, (booking, st) -> new NotificationMessage(
                            "Booking cancelled – Reg.nr " + booking.getVehicle().getRegNr(),
                            "Your booking was cancelled."
                    ),
                    BookingStatus.DONE, (booking, st) -> new NotificationMessage(
                            "Booking finished – Reg.nr " + booking.getVehicle().getRegNr(),
                            "Your service is finished."
                    )
            );

    @Override
    public void notifyBookingEvent(Booking booking, BookingStatus status) {
        if (status == null) return;

        var builder = messageBuilders.get(status);
        if (builder == null) return;

        NotificationMessage msg = builder.apply(booking, status);

        //
        System.out.println("=== Sending booking notification ===");
        System.out.println("To: " + booking.getEmail());
        System.out.println("Subject: " + msg.subject());
        System.out.println("Body: " + msg.body());
        System.out.println("Status: " + status);
        System.out.println("====================================");

    }

    public record NotificationMessage(String subject, String body) {}
}
