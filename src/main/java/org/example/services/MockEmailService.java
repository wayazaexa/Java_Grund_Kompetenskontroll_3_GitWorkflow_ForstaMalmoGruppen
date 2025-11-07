package org.example.services;

import org.example.entities.Booking;
import org.example.entities.BookingStatus;
import org.example.store.NotificationRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockEmailService implements NotificationRepo {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void notifyBookingEvent(Booking booking, BookingStatus status) {
        log.info("E-mail has been sent to customer. To: {} E-mail body: The status of your booking is now {}", booking.getEmail(), booking.getStatus());
    }
}
