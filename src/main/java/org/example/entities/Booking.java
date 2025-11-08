package org.example.entities;

import org.example.exceptions.InvalidVehicleException;
import org.example.factories.ServiceFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {

    private static int idGenerator;
    private int id;
    private Vehicle vehicle;
    private LocalDateTime date;
    private int price;
    private String email;
    private BookingStatus status;
    private BookingType bookingType;
    public Booking(Vehicle vehicle, LocalDateTime date, String email,BookingType bookingType) {
        if (vehicle == null) {
            throw new InvalidVehicleException("Vehicle was not created correctly");
        }
        this.vehicle = vehicle;
        this.date = date;
        this.email = email;
        this.status = BookingStatus.BOOKED;
        this.id = ++idGenerator;
        this.bookingType = bookingType;

    }

    public BookingType getBookingType() {
        return bookingType;
    }

    public void setBookingType(BookingType bookingType) {
        this.bookingType = bookingType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getDate() {
       return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void printShortInfo() {
        DateTimeFormatter nice = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy 'kl.' HH:mm");
        String formatted = date.format(nice);
        System.out.println(
                "Booking - id: " + id +
                        " ,RegNr: " + vehicle.getRegNr() +
                        ", date: " + formatted +
                        " , status: " + status +
                        ", price: " + price +
                        ", bookingType: " + bookingType
        );
    }

    @Override
    public String toString() {
        DateTimeFormatter nice = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "id: " + id +
                ", vehicle: [ " + vehicle + " ]" +
                ", date: " + date.format(nice) +
                ", price: " + price +
                ", email: '" + email + '\'' +
                ", status: " + status +
                ", Booking's type: " + bookingType;
    }

}
