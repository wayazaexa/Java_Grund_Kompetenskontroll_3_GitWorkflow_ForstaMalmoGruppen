package org.example.services;

import org.example.entities.Booking;
import org.example.entities.BookingStatus;
import org.example.entities.Repair;
import org.example.store.NotificationRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockEmailService implements NotificationRepo {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void notifyBookingEvent(Booking booking, BookingStatus status) {
        if (booking instanceof Repair && booking.getStatus().equals(BookingStatus.DONE)) {
            log.info("E-mail has been sent to customer. To: {} E-mail body: The status of your booking is now {}, and the total cost is {}", booking.getEmail(), booking.getStatus(), booking.getPrice());
        }
        else {
            log.info("E-mail has been sent to customer. To: {} E-mail body: The status of your booking is now {}", booking.getEmail(), booking.getStatus());
        }
    }
}
