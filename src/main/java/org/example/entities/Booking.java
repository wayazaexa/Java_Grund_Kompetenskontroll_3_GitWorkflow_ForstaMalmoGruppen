package org.example.entities;

import org.example.exceptions.InvalidVehicleException;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {

    private static int idGenerator;
    private int id;
    private Vehicle vehicle;
    private LocalDate date;
    private int price;
    private String email;
    private BookingStatus isReady;

    public Booking(Vehicle vehicle, LocalDate date, String email) {
        if (vehicle == null) {
            throw new InvalidVehicleException("Vehicle was not created correctly");
        }
        this.vehicle = vehicle;
        this.date = date;
        this.email = email;
        this.isReady = BookingStatus.BOOKED;
        this.id = ++idGenerator;
    }

    public static int getIdGenerator() {
        return idGenerator;
    }

    public static void setIdGenerator(int idGenerator) {
        Booking.idGenerator = idGenerator;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public BookingStatus isReady() {
        return isReady;
    }

    public void setReady(BookingStatus ready) {
        isReady = ready;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", vehicle='" + vehicle + '\'' +
                ", date=" + date +
                ", price=" + price +
                ", email='" + email + '\'' +
                ", isReady=" + isReady;
    }
}
