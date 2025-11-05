package org.example.store;

import org.example.entities.Booking;
import org.example.entities.BookingStatus;

public interface NotificationRepo {
    void notifyBookingEvent(Booking booking, BookingStatus status);
}
