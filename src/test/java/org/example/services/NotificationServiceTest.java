package org.example.services;

import org.example.entities.Booking;
import org.example.entities.BookingStatus;
import org.example.entities.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class NotificationServiceTest {

    private EmailSender emailSender;
    private NotificationService service;

    @BeforeEach
    void setUp() {
        emailSender = mock(EmailSender.class);
        service = new NotificationService(emailSender);
    }

    @Test
    void shouldSendBookedEmailWithCorrectSubjectAndBody() {
        // arrange
        Vehicle vehicle = new Vehicle("ABC123", "Volvo XC60", 2020);
        Booking booking = new Booking(
                vehicle,
                LocalDate.of(2025, 11, 6),
                "user@test.com"
        );

        // act
        service.notifyBookingEvent(booking, BookingStatus.BOOKED);

        // assert
        ArgumentCaptor<String> toCap = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> subCap = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> bodyCap = ArgumentCaptor.forClass(String.class);

        verify(emailSender, times(1)).send(
                toCap.capture(),
                subCap.capture(),
                bodyCap.capture()
        );

        assertEquals("user@test.com", toCap.getValue());
        assertEquals("Booking created – Reg.nr ABC123", subCap.getValue());
        assertEquals("Your booking is created for 2025-11-06", bodyCap.getValue());
    }

    @Test
    void shouldSendCancelledEmail() {
        // arrange
        Vehicle vehicle = new Vehicle("XYZ999", "VW Golf", 2018);
        Booking booking = new Booking(
                vehicle,
                LocalDate.of(2025, 11, 6),
                "u@ex.com"
        );
        booking.setReady(BookingStatus.CANCELLED);

        // act
        service.notifyBookingEvent(booking, BookingStatus.CANCELLED);

        // assert
        ArgumentCaptor<String> toCap = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> subCap = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> bodyCap = ArgumentCaptor.forClass(String.class);

        verify(emailSender, times(1)).send(
                toCap.capture(),
                subCap.capture(),
                bodyCap.capture()
        );

        assertEquals("u@ex.com", toCap.getValue());
        assertEquals("Booking cancelled – Reg.nr XYZ999", subCap.getValue());
        assertEquals("Your booking was cancelled.", bodyCap.getValue());
    }

    @Test
    void shouldNotSendEmailForUnknownStatus() {
        // arrange
        Vehicle vehicle = new Vehicle("ZZZ777", "Tesla", 2024);
        Booking booking = new Booking(
                vehicle,
                LocalDate.of(2025, 11, 6),
                "no@ex.com"
        );
        booking.setReady(BookingStatus.DONE);

        // act
        service.notifyBookingEvent(booking, null);

        // assert
        verify(emailSender, never()).send(anyString(), anyString(), anyString());
    }
}
