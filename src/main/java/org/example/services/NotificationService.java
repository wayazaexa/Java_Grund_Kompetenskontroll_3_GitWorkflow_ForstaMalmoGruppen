package org.example.services;

import org.example.entities.Booking;
import org.example.entities.BookingStatus;
import org.example.entities.EmailSender;
import org.example.store.NotificationRepo;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * Service responsible for building and sending notification emails
 * based on booking events (BOOKED, CANCELLED, DONE).
 * It maps each BookingStatus to a message builder and delegates
 * the actual sending to EmailSender.
 */
public class NotificationService implements NotificationRepo {

    // Dependency responsible for actually sending emails via SMTP
    private final EmailSender sender;

    // Constructor injection: we pass in the email sender from the outside (good for testing)
    public NotificationService(EmailSender sender) {
        this.sender = sender;
    }

    // A static mapping from booking status -> function that builds a NotificationMessage
    // BiFunction<Booking, BookingStatus, NotificationMessage>
    // so we can generate subject/body dynamically from the booking
    private final Map<BookingStatus, BiFunction<Booking, BookingStatus, NotificationMessage>> messageBuilders =
            Map.of(
                    // When a booking is created
                    BookingStatus.BOOKED, (booking, st) -> new NotificationMessage(
                            // email subject
                            "Booking created – Reg.nr " + booking.getVehicle().getRegNr(),
                            // email body (with date and time on separate line)
                            "Your booking is created for " + booking.getDate()
                    ),
                    // When a booking is cancelled
                    BookingStatus.CANCELLED, (booking, st) -> new NotificationMessage(
                            "Booking cancelled – Reg.nr " + booking.getVehicle().getRegNr(),
                            "Your booking was cancelled."
                    ),
                    // When the service is finished
                    BookingStatus.DONE, (booking, st) -> new NotificationMessage(
                            "Booking finished – Reg.nr " + booking.getVehicle().getRegNr(),
                            "Your service is finished."
                    )
            );

    /**
     * Build and send a notification email for a given booking and status.
     * Steps:
     * 1. guard against null status
     * 2. look up a message builder for that status
     * 3. if found, build subject/body
     * 4. delegate actual sending to EmailSender
     *
     * @param booking the booking we are notifying about (contains email, vehicle, date/time)
     * @param status  which event happened to this booking (BOOKED/CANCELLED/DONE)
     */
    @Override
    public void notifyBookingEvent(Booking booking, BookingStatus status) {
        // do nothing if status is null to avoid NPE when using the map
        if (status == null) return;

        // try to find a message builder for this status
        var builder = messageBuilders.get(status);

        // if we don't have a template for this status, just exit silently
        if (builder == null) return;

        // build the concrete subject/body using the booking data
        NotificationMessage msg = builder.apply(booking, status);

        // send the email to the booking's email address
        sender.send(booking.getEmail(), msg.subject(), msg.body());
    }

    /**
     * Simple DTO-like record to hold the email subject and body together.
     */
    public record NotificationMessage(String subject, String body) {}

}
