package org.example.services;

import org.example.entities.Booking;
import org.example.entities.BookingStatus;

public record NotificationService(EmailSender sender) implements NotificationRepo {

    @Override
    public void notifyBookingEvent(Booking booking, BookingStatus status) {
        String subject;
        String body;

        switch (status) {
            case BOOKED -> {
                subject = "Booking created – Reg.nr " + booking.getVehicle().getRegNr();
                body = "Your booking is created for " + booking.getDate() + " " + booking.getTime();
            }
            case CANCELLED -> {
                subject = "Booking cancelled – Reg.nr " + booking.getVehicle().getRegNr();
                body = "Your booking was cancelled.";
            }
            case DONE -> {
                subject = "Booking finished – Reg.nr " + booking.getVehicle().getRegNr();
                body = "Your service is finished.";
            }
            default -> {
                return; // no message
            }
        }

        sender.send(booking.getEmail(), subject, body);
    }

}


