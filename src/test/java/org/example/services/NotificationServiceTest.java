package org.example.services;

import org.example.entities.Booking;
import org.example.entities.BookingStatus;
import org.example.entities.EmailSender;
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
                1,
                vehicle,
                LocalTime.of(10, 30),
                LocalDate.of(2025, 11, 6),
                1200,
                "user@test.com",
                BookingStatus.BOOKED
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
        assertEquals("Your booking is created for 2025-11-06\nTid: 10:30", bodyCap.getValue());
    }

    @Test
    void shouldSendCancelledEmail() {
        // arrange
        Vehicle vehicle = new Vehicle("XYZ999", "VW Golf", 2018);
        Booking booking = new Booking(
                2,
                vehicle,
                LocalTime.of(9, 0),
                LocalDate.of(2025, 11, 6),
                900,
                "u@ex.com",
                BookingStatus.CANCELLED
        );

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
                3,
                vehicle,
                LocalTime.of(8, 0),
                LocalDate.of(2025, 11, 6),
                1500,
                "no@ex.com",
                BookingStatus.DONE
        );

        // act
        service.notifyBookingEvent(booking, null);

        // assert
        verify(emailSender, never()).send(anyString(), anyString(), anyString());
    }
}
